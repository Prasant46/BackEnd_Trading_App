package com.app.trading.model;

import com.app.trading.domain.VarificationType;
import lombok.Data;

@Data
public class TwoFactorAuth {
    private boolean isEnabled = false;
    private VarificationType sendTo;
}
