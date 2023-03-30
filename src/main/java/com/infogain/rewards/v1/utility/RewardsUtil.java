package com.infogain.rewards.v1.utility;

import com.infogain.rewards.v1.repository.dto.Transaction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;

@Component
public class RewardsUtil {
    private static final Logger LOGGER = LogManager.getLogger(RewardsUtil.class);

    public Timestamp getDateBasedOnOffSetDays(int days) {
        return Timestamp.valueOf(LocalDateTime.now().minusDays(days));
    }

    public Map<String, Integer> getRewardPoints(List<Transaction> transactions) {

        Map<String, Integer> rewardPointsByMonth = new TreeMap<>();
        int totalRewardPoints = 0;

        if (transactions != null) {
            for (Transaction transaction : transactions) {
                String month =String.valueOf(getMonthFromTimeStamp.apply(transaction.getTransactionDate()));
                int points = calculatePoints.apply(transaction.getAmount());
                rewardPointsByMonth.merge(month, points, Integer::sum);
                totalRewardPoints += points;
                LOGGER.info("Month :" + month + " : Amount : " + transaction.getAmount() + " : Points : " + points );
            }
        }

        LOGGER.info("Total Rewards Point :" + totalRewardPoints );
        rewardPointsByMonth.put("TOTAL", totalRewardPoints);
        return rewardPointsByMonth;
    }

    private Function<Double, Integer> calculatePoints = transactionAmount -> {
        int points = 0;

        if (transactionAmount > 100) {
            points += 2 * (int) (transactionAmount - 100);
            transactionAmount = 100.00;
        }

        if (transactionAmount > 50 && transactionAmount <= 100) {
            points += 1 * (int) (transactionAmount - 50);
        }

        LOGGER.info(" Total Points :::  {}",points);
        return points;
    };


    private Function<Timestamp, Integer> getMonthFromTimeStamp = ts -> {
        long timestamp = ts.getTime();
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timestamp);
        return cal.get(Calendar.MONTH);
    };

}
