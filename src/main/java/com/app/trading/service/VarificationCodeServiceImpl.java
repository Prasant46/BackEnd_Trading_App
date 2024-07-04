package com.app.trading.service;

import com.app.trading.domain.VarificationType;
import com.app.trading.model.User;
import com.app.trading.model.VarificationCode;
import com.app.trading.repository.VarificationCodeRepo;
import com.app.trading.utils.OtpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VarificationCodeServiceImpl implements VarificationCodeService {

    @Autowired
    private VarificationCodeRepo varificationCodeRepo;

    @Override
    public VarificationCode sendVarificationCode(User user, VarificationType varificationType) {

        VarificationCode varificationCode1=new VarificationCode();
        varificationCode1.setOtp(OtpUtils.generateOTP());
        varificationCode1.setVarificationType(varificationType);
        varificationCode1.setUser(user);


        return varificationCodeRepo.save(varificationCode1);
    }

    @Override
    public VarificationCode getVarificationCodeById(Long id) throws Exception {
        Optional<VarificationCode> varificationCode =
                varificationCodeRepo.findById(id);

        if (varificationCode.isPresent()){
            return varificationCode.get();
        }

       throw new Exception("varification code not found");
    }

    @Override
    public VarificationCode getVarificationCodeByUser(Long userId) {
        return varificationCodeRepo.findByUserId(userId);
    }

    @Override
    public void deleteVarificationCodeById(VarificationCode varificationCode) {
        varificationCodeRepo.delete(varificationCode);

    }
}
