package com.app.trading.controller;

import com.app.trading.config.JwtProvider;
import com.app.trading.model.TwoFactorAuth;
import com.app.trading.model.TwoFactorOTP;
import com.app.trading.model.User;
import com.app.trading.repository.UserRepo;
import com.app.trading.response.AuthResponse;
import com.app.trading.service.CustomUserDetailsService;
import com.app.trading.service.EmailService;
import com.app.trading.service.TwoFactorOtpService;
import com.app.trading.service.WatchlistService;
import com.app.trading.utils.OtpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private TwoFactorOtpService twoFactorOtpService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private WatchlistService watchlistService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> register(@RequestBody User user) throws Exception {

        User isEmailExist =userRepo.findByEmail(user.getEmail());

        if(isEmailExist != null){
            throw  new Exception("Email is already used with another account");
        }

        User newUser = new User();
        newUser.setFullName(user.getFullName());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(user.getPassword());

        User savedUser = userRepo.save(newUser);

        watchlistService.createWatchlist(savedUser);

        Authentication auth = new UsernamePasswordAuthenticationToken(
                user.getEmail(),
                user.getPassword()
        );

        SecurityContextHolder.getContext().setAuthentication(auth);

        String jwt = JwtProvider.generateToken(auth);

        AuthResponse res = new AuthResponse();

        res.setJwt(jwt);
        res.setStatus(true);
        res.setMessage("Register success");

        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> login(@RequestBody User user) throws Exception {

        String userName = user.getEmail();
        String password = user.getPassword();

        Authentication auth = authenticate(userName,password);
        SecurityContextHolder.getContext().setAuthentication(auth);

        String jwt = JwtProvider.generateToken(auth);

        User authUser = userRepo.findByEmail(userName);

        if (user.getTwoFactorAuth().isEnabled()){

            AuthResponse res = new AuthResponse();
            res.setMessage("Two Factor is Enable");
            res.setTwoFactorIsEnabled(true);
            String otp = OtpUtils.generateOTP();

            TwoFactorOTP oldTwoFactorOTP = twoFactorOtpService.findByUser(authUser.getId());

            if(oldTwoFactorOTP != null){
                twoFactorOtpService.deleteTwoFactorOtp(oldTwoFactorOTP);
            }

            TwoFactorOTP newTwoFactorOTP =twoFactorOtpService.createTwoFactorOtp(authUser,otp,jwt);

            emailService.sendVarificationOtpEmail(userName,otp);

            res.setSession(newTwoFactorOTP.getId());

            return  new ResponseEntity<>(res,HttpStatus.ACCEPTED);
        }


        AuthResponse res = new AuthResponse();
        res.setJwt(jwt);
        res.setStatus(true);
        res.setMessage("login success");

        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    private Authentication authenticate(String userName, String password) {

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(userName);

        if(userDetails == null){
            throw new BadCredentialsException("Invalid username");
        }
        if (!password.equals(userDetails.getPassword())){
            throw new BadCredentialsException("Invalid password");

        }

        return  new UsernamePasswordAuthenticationToken(userDetails,password, userDetails.getAuthorities());
    }

    @PostMapping("/two-factor/otp/{otp}")
    public ResponseEntity<AuthResponse> varifySignInOtp(
            @PathVariable String otp,
            @RequestParam String id) throws Exception {

        TwoFactorOTP twoFactorOTP = twoFactorOtpService.findById(id);

        if (twoFactorOtpService.varifyTwoFactorOtp(twoFactorOTP,otp)){

            AuthResponse res =new AuthResponse();

            res.setMessage("Two factor authentication is varified");
            res.setTwoFactorIsEnabled(true);
            res.setJwt(twoFactorOTP.getJwt());
            return  new ResponseEntity<>(res,HttpStatus.OK);

        }

        throw  new Exception("Invalid OTP");

    }


}
