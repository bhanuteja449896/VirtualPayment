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
@CrossOrigin(origins = "*")
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
    public TransactionStatus addNewTransaction(@PathVariable("sender") String sender, @PathVariable("receiver") String receiver, @PathVariable("amount") String amount) {
        TransactionStatus transactionStatus = new TransactionStatus();
        User senderUser = userServices.findUserData(sender);
        User receiverUser = userServices.findUserData(receiver);

        // Validate sender
        if (senderUser == null) {
            transactionStatus.setRc("01");
            transactionStatus.setDesc("Sender Not Found");
            return transactionStatus;
        }

        // Validate receiver
        if (receiverUser == null) {
            transactionStatus.setRc("02");
            transactionStatus.setDesc("Receiver Not Found");
            return transactionStatus;
        }

        int senderBalance;
        try {
            senderBalance = Integer.parseInt(senderUser.getBalance());
        } catch (NumberFormatException e) {
            transactionStatus.setRc("03");
            transactionStatus.setDesc("Invalid Balance Format for Sender");
            System.out.println("Error: Invalid balance format for sender " + sender);  // Debug log
            return transactionStatus;
        }

        int transferAmount;
        try {
            transferAmount = Integer.parseInt(amount);
        } catch (NumberFormatException e) {
            transactionStatus.setRc("04");
            transactionStatus.setDesc("Invalid Amount Format");
            System.out.println("Error: Invalid transfer amount format");  // Debug log
            return transactionStatus;
        }

        // Check sender's balance
        if (senderBalance < transferAmount) {
            transactionStatus.setRc("05");
            transactionStatus.setDesc("Insufficient Balance");
            return transactionStatus;
        }

        // Update balances
        boolean isSenderUpdated = userServices.updateUserBalance(sender, String.valueOf(senderBalance - transferAmount));
        boolean isReceiverUpdated = userServices.updateUserBalance(receiver, String.valueOf(Integer.parseInt(receiverUser.getBalance()) + transferAmount));

        if (!isSenderUpdated || !isReceiverUpdated) {
            transactionStatus.setRc("06");
            transactionStatus.setDesc("Error Updating Balances");
            return transactionStatus;
        }

        // Create transaction entries
        Transaction senderTransaction = new Transaction();
        senderTransaction.setSender(sender);
        senderTransaction.setReceiver(receiver);
        senderTransaction.setAmount(amount);
        senderTransaction.setStatus("Success");
        senderTransaction.setType("Debit");
        senderTransaction.setTimestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        Transaction receiverTransaction = new Transaction();
        receiverTransaction.setSender(sender);
        receiverTransaction.setReceiver(receiver);
        receiverTransaction.setAmount(amount);
        receiverTransaction.setStatus("Success");
        receiverTransaction.setType("Credit");
        receiverTransaction.setTimestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        try {
            transactionServices.addTransaction(senderTransaction);
            transactionServices.addTransaction(receiverTransaction);
        } catch (Exception e) {
            transactionStatus.setRc("06");
            transactionStatus.setDesc("Error saving transaction");
            System.out.println("Error saving transaction: " + e.getMessage());  // Debug log
            return transactionStatus;
        }

        // Successful transaction
        transactionStatus.setRc("00");
        transactionStatus.setDesc("Transaction Successful");
//        transactionStatus.setTimestamp(transaction.getTransactionId());
        return transactionStatus;
    }


}

