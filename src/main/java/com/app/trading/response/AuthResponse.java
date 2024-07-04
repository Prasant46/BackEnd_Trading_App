package com.app.trading.response;

import lombok.Data;

@Data
public class AuthResponse {

    private String jwt;
    private boolean status;
    private  String message;
    private boolean isTwoFactorIsEnabled;
    private String session;
}
