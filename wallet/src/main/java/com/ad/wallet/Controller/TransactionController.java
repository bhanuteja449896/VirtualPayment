package com.ad.wallet.Controller;

import com.ad.wallet.Entity.Transaction;
import com.ad.wallet.Entity.User;
import com.ad.wallet.Services.TransactionServices;
import com.ad.wallet.Services.UserServices;
import com.ad.wallet.vo.TransactionStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/transaction/")
public class TransactionController {

    @Autowired
    private TransactionServices transactionServices;

    @Autowired
    private UserServices userServices;

    @GetMapping("{mobile}")
    public List<Transaction> getAllTransactionByMobile(@PathVariable String mobile) {
        return transactionServices.transactionByMobile(mobile);
    }

    @GetMapping("pay/{sender}/{receiver}/{amount}")
    public TransactionStatus addNewTransaction(
            @PathVariable("sender") String sender,
            @PathVariable("receiver") String receiver,
            @PathVariable("amount") String amount) {

        TransactionStatus transactionStatus = new TransactionStatus();
        User senderUser = userServices.findUserData(sender);
        User receiverUser = userServices.findUserData(receiver);

        if (receiverUser == null) {
            transactionStatus.setRc("01");
            transactionStatus.setDesc("Receiver Not Found");
            return transactionStatus;
        }

        int senderBalance = Integer.parseInt(senderUser.getBalance());
        int transferAmount = Integer.parseInt(amount);

        if (senderBalance < transferAmount) {
            transactionStatus.setRc("02");
            transactionStatus.setDesc("Insufficient Balance");
            return transactionStatus;
        }

        // Update sender and receiver balances
        senderUser.setBalance(String.valueOf(senderBalance - transferAmount));
        receiverUser.setBalance(String.valueOf(Integer.parseInt(receiverUser.getBalance()) + transferAmount));

        // Save updated user data
//        userServices.updateUser(senderUser);
//        userServices.updateUser(receiverUser);

        // Create transaction entries
        Transaction senderTransaction = new Transaction();
        senderTransaction.setSender(sender);
        senderTransaction.setReceiver(receiver);
        senderTransaction.setAmount(amount);
        senderTransaction.setStatus("Success");
        senderTransaction.setType("Debit");
        senderTransaction.setTimestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        Transaction receiverTransaction = new Transaction();
        receiverTransaction.setSender(receiver);
        receiverTransaction.setReceiver(sender);
        receiverTransaction.setAmount(amount);
        receiverTransaction.setStatus("Success");
        receiverTransaction.setType("Credit");
        receiverTransaction.setTimestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        // Save transactions
        transactionServices.addTransaction(senderTransaction);
        transactionServices.addTransaction(receiverTransaction);

        transactionStatus.setRc("00");
        transactionStatus.setDesc("Transaction Successful");
        return transactionStatus;
    }
}
