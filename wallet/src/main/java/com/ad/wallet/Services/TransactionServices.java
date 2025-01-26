package com.ad.wallet.Services;

import com.ad.wallet.Entity.Transaction;
import com.ad.wallet.Repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionServices {

    @Autowired
    private TransactionRepository transactionRepository;

    public List<Transaction> transactionByMobile(String mobile) {
        return transactionRepository.findBySender(mobile);
    }

    public void addTransaction(Transaction transaction) {
        transactionRepository.save(transaction);
    }
}
