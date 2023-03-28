package com.infogain.rewards.v1.service;

import com.infogain.rewards.v1.model.CustomerBo;
import com.infogain.rewards.v1.model.Rewards;
import com.infogain.rewards.v1.model.TransactionBo;

public interface RewardsService {
    public Rewards getRewardsByCustomerId(Long customerId);
    public CustomerBo findCustomerById(Long customerId);

    public int saveTransaction(TransactionBo transaction);

    public Long updateTransaction(TransactionBo transaction);

    public void deleteTransaction(TransactionBo transaction);

}
