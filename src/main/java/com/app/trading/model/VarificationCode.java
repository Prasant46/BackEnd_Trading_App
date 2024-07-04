package com.app.trading.model;

import com.app.trading.domain.VarificationType;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class VarificationCode {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String otp;

    @OneToOne
    private User user;

    private String email;

    private  String mobile;

    private VarificationType varificationType;




}
