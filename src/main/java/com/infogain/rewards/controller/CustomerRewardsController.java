package com.infogain.rewards.controller;

import com.infogain.rewards.RewardsApplication;
import com.infogain.rewards.dto.Customer;
import com.infogain.rewards.model.CustomerBo;
import com.infogain.rewards.model.Rewards;
import com.infogain.rewards.model.TransactionBo;
import com.infogain.rewards.service.RewardsService;
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

//    @Autowired
//    CustomerRepository customerRepository;


    @GetMapping(value = "/rewards/{customerId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Rewards> getRewardsByCustomerId(@PathVariable("customerId") Long customerId) {

        LOGGER.info("Info level log message");
        LOGGER.debug("Debug level log message");
        LOGGER.error("Error level log message");

        CustomerBo customer = rewardsService.findCustomerById(customerId);
        if(customer == null)
        {
            throw new RuntimeException("Invalid / Missing customer Id ");
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

}
