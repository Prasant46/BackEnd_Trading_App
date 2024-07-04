package com.app.trading.service;

import com.app.trading.domain.VarificationType;
import com.app.trading.model.User;
import com.app.trading.model.VarificationCode;

public interface VarificationCodeService {

    VarificationCode sendVarificationCode(User user, VarificationType varificationType);

    VarificationCode getVarificationCodeById(Long id) throws Exception;

    VarificationCode getVarificationCodeByUser(Long userId);

    void deleteVarificationCodeById(VarificationCode varificationCode);



}
