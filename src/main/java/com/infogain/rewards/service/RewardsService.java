package com.infogain.rewards.service;

import com.infogain.rewards.model.CustomerBo;
import com.infogain.rewards.model.Rewards;
import com.infogain.rewards.model.TransactionBo;

public interface RewardsService {
    public Rewards getRewardsByCustomerId(Long customerId);
    public CustomerBo findCustomerById(Long customerId);

    public int saveTransaction(TransactionBo transaction);

}
