package com.ad.wallet.Controller;


import com.ad.wallet.Entity.AmountLoad;
import com.ad.wallet.Entity.User;
import com.ad.wallet.Services.AmountLoadServices;
import com.ad.wallet.Services.UserServices;
import com.ad.wallet.vo.TransactionStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "*")
public class AmountLoadController {
    @Autowired
    private AmountLoadServices amountLoadServices;

    @Autowired
    private UserServices userServices;

    @GetMapping("redeem/{admin}/{mobile}/{amount}")
    public TransactionStatus redeemAmount(@PathVariable("admin") String admin ,@PathVariable("mobile") String mobile, @PathVariable("amount") String amount){
        TransactionStatus transactionStatus = new TransactionStatus();
        AmountLoad amountLoad = new AmountLoad();
        User user = userServices.findUserData(mobile);
        if(user == null){
            transactionStatus.setRc("01");
            transactionStatus.setDesc("User Not found");
            return transactionStatus;
        }
        int redeemAmount = Integer.parseInt(user.getBalance());
        if(redeemAmount< Integer.parseInt(amount)){
            transactionStatus.setRc("02");
            transactionStatus.setDesc("Insufficient balance");
            return transactionStatus;
        }
        redeemAmount = redeemAmount - Integer.parseInt(amount);
        boolean isSenderUpdated = userServices.updateUserBalance(mobile,String.valueOf(redeemAmount));
        if(!isSenderUpdated){
            transactionStatus.setRc("02");
            transactionStatus.setDesc("Error Loading Balances");
            return transactionStatus;
        }

        amountLoad.setMobile(mobile);
        amountLoad.setAmount(amount);
        amountLoad.setType("Debit");
        amountLoad.setAdmin(admin);
        amountLoad.setTimestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        amountLoadServices.LoadUserAccount(amountLoad);
        transactionStatus.setRc("00");
        transactionStatus.setDesc("Amount Redeemed Successfully");
        return transactionStatus;

    }

    @GetMapping("load/{admin}/{mobile}/{amount}")
    public TransactionStatus addLoadAmount(@PathVariable("admin") String admin,@PathVariable("mobile") String mobile, @PathVariable("amount") String amount){
        TransactionStatus transactionStatus = new TransactionStatus();
        AmountLoad amountLoad = new AmountLoad();
        User user = userServices.findUserData(mobile);


        if(user == null){
            transactionStatus.setDesc("User not found");
            transactionStatus.setRc("01");
            return  transactionStatus;
        }

        int updatedAmount = Integer.parseInt(user.getBalance()) + Integer.parseInt(amount);

        boolean isSenderUpdated = userServices.updateUserBalance(mobile, String.valueOf(updatedAmount));

        if(!isSenderUpdated){
            transactionStatus.setRc("02");
            transactionStatus.setDesc("Error Loading Balances");
            return transactionStatus;
        }

        amountLoad.setMobile(mobile);
        amountLoad.setAmount(amount);
        amountLoad.setType("Credit");
        amountLoad.setAdmin(admin);
        amountLoad.setTimestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        amountLoadServices.LoadUserAccount(amountLoad);
        transactionStatus.setRc("00");
        transactionStatus.setDesc("Amount Loaded Successfully");
        return transactionStatus;
    }

    @GetMapping("admin/transaction/{mobile}")
    public List<AmountLoad> getAllLoadTransactionsByMobile(@PathVariable("mobile") String mobile){
        return amountLoadServices.getLoadAmountDataByMobile(mobile);
    }

    @GetMapping("user/load/transaction/{mobile}")
    public List<AmountLoad> getAllUserTransactionsByMobile(@PathVariable("mobile") String mobile){
        return amountLoadServices.getUserLoadAmountDataByMobile(mobile);
    }
}
