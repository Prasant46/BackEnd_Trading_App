package com.app.trading.service;

import com.app.trading.config.JwtProvider;
import com.app.trading.domain.VarificationType;
import com.app.trading.model.TwoFactorAuth;
import com.app.trading.model.User;
import com.app.trading.repository.UserRepo;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepo userRepo;


    @Override
    public User findUserProfileByJwt(String jwt) throws Exception {
        String email= JwtProvider.getEmailFromToken(jwt);
        User user =userRepo.findByEmail(email);

        if (user==null){
            throw new Exception("User not found");
        }
        return user;
    }

    @Override
    public User findUserByEmail(String email) throws Exception {

        User user =userRepo.findByEmail(email);

        if (user==null){
            throw new Exception("User not found");
        }
        return user;

    }

    @Override
    public User findUserById(Long userId) throws Exception {
        Optional<User> user = userRepo.findById(userId);
        if (user.isEmpty()){
            throw  new Exception("user not found");
        }
        return user.get();
    }

    @Override
    public User enableTwoFactorAuthentication(VarificationType varificationType, String sendTo, User user) {
        TwoFactorAuth twoFactorAuth = new TwoFactorAuth();
        twoFactorAuth.setEnabled(true);
        twoFactorAuth.setSendTo(varificationType);

        user.setTwoFactorAuth(twoFactorAuth);

        return userRepo.save(user);
    }


    @Override
    public User updatePassword(User user, String newPassword) {
        user.setPassword(newPassword);

        return userRepo.save(user);
    }
}
