package com.app.trading.request;

import com.app.trading.domain.VarificationType;
import lombok.Data;

@Data
public class ForgotPasswordTokenRequest {
    private String sendTo;
    private VarificationType varificationType;
}
