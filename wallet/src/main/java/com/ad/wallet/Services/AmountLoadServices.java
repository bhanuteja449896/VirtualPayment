package com.ad.wallet.Services;

import com.ad.wallet.Entity.AmountLoad;
import com.ad.wallet.Entity.User;
import com.ad.wallet.Repository.AmountLoadRepository;
import com.ad.wallet.vo.TransactionStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AmountLoadServices {
    @Autowired
    private AmountLoadRepository amountLoadRepository;

    public void LoadUserAccount(AmountLoad amountLoad){
        amountLoadRepository.save(amountLoad);
    }

    public List<AmountLoad> getLoadAmountDataByMobile(String mobile){
        List<AmountLoad> transactions = amountLoadRepository.findByMobile(mobile);
        return transactions;
    }

}