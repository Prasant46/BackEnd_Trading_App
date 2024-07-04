package com.app.trading.service;

import com.app.trading.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        com.app.trading.model.User user = userRepo.findByEmail(username);
        if (user == null){
            throw new UsernameNotFoundException(username);

        }

        List<GrantedAuthority> authorityList = new ArrayList<>();

        return new User(user.getEmail(),user.getPassword(),authorityList);
    }
}
