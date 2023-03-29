package com.infogain.rewards.v1.repository;

import com.infogain.rewards.v1.repository.dto.Transaction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TransactionJdbcRepository {
    private static final Logger LOGGER = LogManager.getLogger(TransactionJdbcRepository.class);
    @Autowired
    JdbcTemplate jdbcTemplate;

    public int insert(@NotNull Transaction transaction) {
        LOGGER.info(" Inserting Data in Transaction Table : " + transaction);
        return jdbcTemplate.update("INSERT INTO TRANSACTION(TRANSACTION_ID, CUSTOMER_ID,TRANSACTION_DATE,AMOUNT) " + "values(?,  ?, ?, ?)",
                transaction.getTransactionId(), transaction.getCustomerId(), transaction.getTransactionDate(), transaction.getAmount());
    }
}
