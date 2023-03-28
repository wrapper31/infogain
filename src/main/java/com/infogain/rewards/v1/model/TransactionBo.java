package com.infogain.rewards.v1.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import javax.persistence.Column;
import java.sql.Timestamp;

public class TransactionBo {

    private Long transactionId;
    private Long customerId;
    private Timestamp transactionDate;
    private double amount;

    public TransactionBo(Long transactionId, Long customerId, Timestamp transactionDate, double amount) {
        this.transactionId = transactionId;
        this.customerId = customerId;
        this.transactionDate = transactionDate;
        this.amount = amount;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Timestamp getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Timestamp transactionDate) {
        this.transactionDate = transactionDate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "TransactionBo{" +
                "transactionId=" + transactionId +
                ", customerId=" + customerId +
                ", transactionDate=" + transactionDate +
                ", amount=" + amount +
                '}';
    }
}
