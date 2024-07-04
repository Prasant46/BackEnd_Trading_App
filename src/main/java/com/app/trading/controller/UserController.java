package com.app.trading.controller;

import com.app.trading.request.ForgotPasswordTokenRequest;
import com.app.trading.domain.VarificationType;
import com.app.trading.model.ForgotPasswordToken;
import com.app.trading.model.User;
import com.app.trading.model.VarificationCode;
import com.app.trading.request.ResetPasswordRequest;
import com.app.trading.response.ApiResponse;
import com.app.trading.response.AuthResponse;
import com.app.trading.service.EmailService;
import com.app.trading.service.ForgotPasswordService;
import com.app.trading.service.UserService;
import com.app.trading.service.VarificationCodeService;
import com.app.trading.utils.OtpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private VarificationCodeService varificationCodeService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ForgotPasswordService forgotPasswordService;

    @GetMapping("/api/users/profile")
    public ResponseEntity<User> getUserProfile(@RequestHeader("Authorization") String jwt) throws Exception {

        User user=userService.findUserProfileByJwt(jwt);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/api/users/varification/{varificationType}/send-otp")
    public ResponseEntity<String> sendVarificationOtp(
            @RequestHeader("Authorization") String jwt,
            @PathVariable VarificationType varificationType) throws Exception {

        User user=userService.findUserProfileByJwt(jwt);

        VarificationCode varificationCode = varificationCodeService.getVarificationCodeByUser(user.getId());
        if (varificationCode==null){
            varificationCode = varificationCodeService.sendVarificationCode(user,varificationType);

        }

        if (varificationType.equals(varificationType.EMAIL)){
            emailService.sendVarificationOtpEmail(user.getEmail(),varificationCode.getOtp());
        }
        return new ResponseEntity<>("varification otp sent successfully", HttpStatus.OK);
    }

    @PatchMapping("/api/users/enable-two-factor/varify-otp/{otp}")
    public ResponseEntity<User> enableTwoFactorAuthentication(@PathVariable String otp,
                                                              @RequestHeader("Authorization") String jwt) throws Exception {

        User user=userService.findUserProfileByJwt(jwt);

        VarificationCode varificationCode = varificationCodeService.getVarificationCodeByUser(user.getId());

        String  sendTo =varificationCode.getVarificationType().equals(VarificationType.EMAIL)
                ?varificationCode.getEmail()
                :varificationCode.getMobile();

        boolean isVarified = varificationCode.getOtp().equals(otp);

        if (isVarified){
            User updatedUser =userService.enableTwoFactorAuthentication(
                    varificationCode.getVarificationType(),sendTo,user);

            varificationCodeService.deleteVarificationCodeById(varificationCode);
            return new ResponseEntity<>(user,HttpStatus.OK);

        }


        throw new Exception("Wrong otp");
    }

    @PostMapping("/auth/users/reset-password/send-otp")
    public ResponseEntity<AuthResponse> sendForgotPasswordOtp(
            @RequestBody ForgotPasswordTokenRequest req) throws Exception {

        User user=userService.findUserByEmail(req.getSendTo());
        String otp = OtpUtils.generateOTP();
        UUID uuid = UUID.randomUUID();
        String id=uuid.toString();

        ForgotPasswordToken token = forgotPasswordService.findByUser(user.getId());

        if(token ==null){
            token = forgotPasswordService.createToken(user,id,otp,
                    req.getVarificationType(),req.getSendTo());
        }

        if(req.getVarificationType().equals(VarificationType.EMAIL)){
            emailService.sendVarificationOtpEmail(user.getEmail(),token.getOtp());
        }

        AuthResponse response = new AuthResponse();
        response.setSession(token.getId());
        response.setMessage("Password reset otp sent successfully");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/auth/users/reset-password/varify-otp")
    public ResponseEntity<ApiResponse> resetPassword(@RequestParam String id,
                                              @RequestBody ResetPasswordRequest req,
                                              @RequestHeader("Authorization") String jwt) throws Exception {

       ForgotPasswordToken forgotPasswordToken = forgotPasswordService.findById(id);

       boolean isVarified=forgotPasswordToken.getOtp().equals(req.getOtp());

       if(isVarified){
           userService.updatePassword(forgotPasswordToken.getUser(),req.getPassword());

           ApiResponse res =new ApiResponse();
           res.setMessage("Password updated successfully");

           return new ResponseEntity<>(res,HttpStatus.ACCEPTED);
       }

       throw new Exception("Wrong otp");
    }


}
