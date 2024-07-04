package com.app.trading.model;

import com.app.trading.domain.VarificationType;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ForgotPasswordToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  String id;

    @OneToOne
    private User user;

    private String otp;

    private VarificationType varificationType;

    private  String sendTo;

}
