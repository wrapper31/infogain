package com.infogain.rewards.service;


import com.infogain.rewards.controller.CustomerRewardsController;
import com.infogain.rewards.dto.Customer;
import com.infogain.rewards.dto.Transaction;
import com.infogain.rewards.model.CustomerBo;
import com.infogain.rewards.model.Rewards;
import com.infogain.rewards.model.TransactionBo;
import com.infogain.rewards.repository.CustomerRepository;
import com.infogain.rewards.repository.TransactionJdbcRepository;
import com.infogain.rewards.repository.TransactionRepository;
import com.infogain.rewards.utility.Constants;
import com.infogain.rewards.utility.RewardsUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RewardsServiceImpl implements RewardsService {

    private static final Logger LOGGER = LogManager.getLogger(RewardsServiceImpl.class);

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    TransactionJdbcRepository transactionJdbcRepository;

    @Autowired
    RewardsUtil util;

    public Rewards getRewardsByCustomerId(Long customerId) {

        Timestamp lastMonthTimestamp = util.getDateBasedOnOffSetDays(Constants.daysInMonths);
        Timestamp lastSecondMonthTimestamp = util.getDateBasedOnOffSetDays(2 * Constants.daysInMonths);
        Timestamp lastThirdMonthTimestamp = util.getDateBasedOnOffSetDays(3 * Constants.daysInMonths);
        LOGGER.info("Start Date : " + lastMonthTimestamp + " Now : " + Timestamp.from(Instant.now()));

        List<Transaction> lastMonthTransactions = transactionRepository.findAllByCustomerIdAndTransactionDateBetween(
                customerId, lastMonthTimestamp, Timestamp.from(Instant.now()));
        LOGGER.info("lastMonthTransactions : " + lastMonthTransactions.size());

        Map<String, Integer> firstmonthRewardsPoint = util.getRewardPoints(lastMonthTransactions);
        List<Transaction> lastSecondMonthTransactions = transactionRepository
                .findAllByCustomerIdAndTransactionDateBetween(customerId, lastSecondMonthTimestamp, lastMonthTimestamp);

        Map<String, Integer> secondMonthRewards = util.getRewardPoints(lastSecondMonthTransactions);
        LOGGER.info("lastSecondMonthTransactions : " + lastSecondMonthTransactions.size());

        List<Transaction> lastThirdMonthTransactions = transactionRepository
                .findAllByCustomerIdAndTransactionDateBetween(customerId, lastThirdMonthTimestamp,
                        lastSecondMonthTimestamp);

        Map<String, Integer> thirdMonthRewards = util.getRewardPoints(lastThirdMonthTransactions);
        LOGGER.info("lastThirdMonthTransactions : " + lastThirdMonthTransactions.size());

//        Long lastMonthRewardPoints = util.getRewardsPerMonth(lastMonthTransactions);
//        Long lastSecondMonthRewardPoints = util.getRewardsPerMonth(lastSecondMonthTransactions);
//        Long lastThirdMonthRewardPoints = util.getRewardsPerMonth(lastThirdMonthTransactions);

        Rewards customerRewards = new Rewards(customerId, firstmonthRewardsPoint.get("TOTAL"), secondMonthRewards.get("TOTAL"), thirdMonthRewards.get("TOTAL")
                , firstmonthRewardsPoint.get("TOTAL") + secondMonthRewards.get("TOTAL") + thirdMonthRewards.get("TOTAL"));
        return customerRewards;

    }

    @Override
    public CustomerBo findCustomerById(Long customerId) {
        Customer customer = customerRepository.findByCustomerId(customerId);
        if (customer != null)
            return new CustomerBo(customer.getCustomerId(), customer.getCustomerName());
        else
            return null;
    }

    @Override
    public int saveTransaction(TransactionBo transaction) {
        LOGGER.info(" Save Transaction " + transaction);
        int i = 0;
        try {
           i =  transactionJdbcRepository.insert(new Transaction(transaction.getTransactionId(),
                    transaction.getCustomerId(), transaction.getTransactionDate(), transaction.getAmount()));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return i;
    }

}
