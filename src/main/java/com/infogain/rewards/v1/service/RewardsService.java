package com.infogain.rewards.v1.service;

import com.infogain.rewards.v1.service.model.CustomerBo;
import com.infogain.rewards.v1.service.model.Rewards;
import com.infogain.rewards.v1.service.model.TransactionBo;

public interface RewardsService {
    public Rewards getRewardsByCustomerId(Long customerId);
    public CustomerBo findCustomerById(Long customerId);

    public TransactionBo findTransactionById(Long transactionId);

    public int saveTransaction(TransactionBo transaction);

    public Long updateTransaction(TransactionBo transaction);

    public void deleteTransaction(TransactionBo transaction);

}
