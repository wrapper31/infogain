package com.infogain.rewards.v1.controller;

import com.infogain.rewards.v1.exceptions.CustomerNotFoundException;
import com.infogain.rewards.v1.model.CustomerBo;
import com.infogain.rewards.v1.model.Rewards;
import com.infogain.rewards.v1.model.TransactionBo;
import com.infogain.rewards.v1.service.RewardsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class CustomerRewardsController {

    private static final Logger LOGGER = LogManager.getLogger(CustomerRewardsController.class);

    @Autowired
    RewardsService rewardsService;


    @GetMapping(value = "/rewards/{customerId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Rewards> getRewardsByCustomerId(@PathVariable("customerId") Long customerId) {
        LOGGER.info("Getting Rewards By Customer id {}",customerId);
        CustomerBo customer = rewardsService.findCustomerById(customerId);
        if(customer == null)
        {
            throw new CustomerNotFoundException("Invalid / Missing customer Id ");
        }
        LOGGER.info(" Customer name is  : " + customer.getCustomerName());

        Rewards customerRewards = rewardsService.getRewardsByCustomerId(customerId);
        return new ResponseEntity<>(customerRewards, HttpStatus.OK);
    }

    @PutMapping(value = "/rewards/transaction",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TransactionBo> createTransaction(@RequestBody TransactionBo transaction) {
        LOGGER.info("Info level log message" + transaction.toString());
        try {
            int i = rewardsService.saveTransaction(transaction);
            return new ResponseEntity<>(transaction, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/rewards/transaction",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TransactionBo> updateTransaction(@RequestBody TransactionBo transaction) {
        LOGGER.info("Updating a Transaction" + transaction.toString());
        try {
            Long i = rewardsService.updateTransaction(transaction);
            return new ResponseEntity<>(transaction, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping (value = "/rewards/transaction",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TransactionBo> deleteransaction(@RequestBody TransactionBo transaction) {
        LOGGER.info("Deleting a Transaction" + transaction.toString());
        try {
            rewardsService.deleteTransaction(transaction);
            return new ResponseEntity<>(transaction, HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
