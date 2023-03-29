package com.infogain.rewards.v1.repository.entity;

import lombok.Data;

@Data
public class RewardPoints {

    private String customerId;
    private int points;
    private int month;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }
}

