package com.app.trading.service;

import com.app.trading.domain.VarificationType;
import com.app.trading.model.User;


public interface UserService {
    public User findUserProfileByJwt(String jwt) throws Exception;
    public User findUserByEmail(String email) throws Exception;
    public  User findUserById(Long userId) throws Exception;

    public User enableTwoFactorAuthentication(VarificationType varificationType,String sendTo, User user);

    User updatePassword(User user,String newPassword);

}
