package com.infogain.rewards.v1.service;


import com.infogain.rewards.v1.dto.Customer;
import com.infogain.rewards.v1.dto.Transaction;
import com.infogain.rewards.v1.exceptions.CustomerNotFoundException;
import com.infogain.rewards.v1.exceptions.TransactionNotFoundException;
import com.infogain.rewards.v1.model.CustomerBo;
import com.infogain.rewards.v1.model.Rewards;
import com.infogain.rewards.v1.model.TransactionBo;
import com.infogain.rewards.v1.repository.CustomerRepository;
import com.infogain.rewards.v1.repository.TransactionJdbcRepository;
import com.infogain.rewards.v1.repository.TransactionRepository;
import com.infogain.rewards.v1.utility.Constants;
import com.infogain.rewards.v1.utility.RewardsUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Map;

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
        if(lastMonthTransactions == null ) throw new TransactionNotFoundException("Invalid / Missing Transactions! ");

        LOGGER.info("lastMonthTransactions : " + lastMonthTransactions.size());

        Map<String, Integer> firstmonthRewardsPoint = util.getRewardPoints(lastMonthTransactions);
        if(firstmonthRewardsPoint == null ) throw new TransactionNotFoundException("Invalid / Missing Transactions! ");


        List<Transaction> lastSecondMonthTransactions = transactionRepository
                .findAllByCustomerIdAndTransactionDateBetween(customerId, lastSecondMonthTimestamp, lastMonthTimestamp);

        if(lastSecondMonthTransactions == null ) throw new TransactionNotFoundException("Invalid / Missing Transactions! ");

        Map<String, Integer> secondMonthRewards = util.getRewardPoints(lastSecondMonthTransactions);
        LOGGER.info("lastSecondMonthTransactions : " + lastSecondMonthTransactions.size());

        List<Transaction> lastThirdMonthTransactions = transactionRepository
                .findAllByCustomerIdAndTransactionDateBetween(customerId, lastThirdMonthTimestamp,
                        lastSecondMonthTimestamp);

        Map<String, Integer> thirdMonthRewards = util.getRewardPoints(lastThirdMonthTransactions);
        LOGGER.info("lastThirdMonthTransactions : " + lastThirdMonthTransactions.size());

        Rewards customerRewards = new Rewards(customerId, firstmonthRewardsPoint.get("TOTAL"), secondMonthRewards.get("TOTAL"), thirdMonthRewards.get("TOTAL")
                , firstmonthRewardsPoint.get("TOTAL") + secondMonthRewards.get("TOTAL") + thirdMonthRewards.get("TOTAL"));
        return customerRewards;

    }

    @Override
    public CustomerBo findCustomerById(Long customerId) {
        Customer customer = customerRepository.findByCustomerId(customerId);
        if (customer == null)
            throw new CustomerNotFoundException("Customer with id was not found" + customerId);
        else
            return new CustomerBo(customer.getCustomerId(),customer.getCustomerName());
    }

    @Override
    public int saveTransaction(TransactionBo transaction) {
        LOGGER.info(" Save Transaction " + transaction);
        int noOfUpdate = 0;
        try {
            noOfUpdate =  transactionJdbcRepository.insert(new Transaction(transaction.getTransactionId(),
                    transaction.getCustomerId(), transaction.getTransactionDate(), transaction.getAmount()));

        } catch (TransactionNotFoundException transactionNotFoundException) {
            LOGGER.error("Error Saving Transactions."+ transactionNotFoundException.getMessage());
        }
        catch (Exception e) {
            LOGGER.error("Error Saving Transactions."+ e.getMessage());
        }

        return noOfUpdate;
    }

    @Override
    public Long updateTransaction(TransactionBo transaction) {
        LOGGER.info(" update Transaction " + transaction);
        try {
            Transaction t = transactionRepository.save(new Transaction(transaction.getTransactionId(),
                    transaction.getCustomerId(), transaction.getTransactionDate(), transaction.getAmount()));

        } catch (TransactionNotFoundException transactionNotFoundException) {
            LOGGER.error("Error updating Transactions."+ transactionNotFoundException.getMessage());
        }
        catch (Exception e) {
            LOGGER.error("Error updating Transactions."+ e.getMessage());
        }

        return transaction.getTransactionId();
    }

    @Override
    public void deleteTransaction(TransactionBo transaction) {
        LOGGER.info(" Deleting Transaction " + transaction);
        try {
           transactionRepository.delete(new Transaction(transaction.getTransactionId(),
                    transaction.getCustomerId(), transaction.getTransactionDate(), transaction.getAmount()));

        } catch (TransactionNotFoundException transactionNotFoundException) {
            LOGGER.error("Error Deleting Transactions."+ transactionNotFoundException.getMessage());
        }
        catch (Exception e) {
            LOGGER.error("Error Deleting Transactions."+ e.getMessage());
        }

    }

}
