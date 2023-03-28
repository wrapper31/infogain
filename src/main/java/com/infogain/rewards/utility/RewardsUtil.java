package com.infogain.rewards.utility;

import com.infogain.rewards.dto.Transaction;
import com.infogain.rewards.service.RewardsServiceImpl;
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
import java.util.stream.Collectors;

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

    private Function<Double, Integer> calculatePoints = amount -> {
        int points = 0;
        if (amount > 100) points += 2 * (amount - 100); amount=100.00;
        if (amount > 50) points += (amount - 50);
        return points;
    };


    private Function<Timestamp, Integer> getMonthFromTimeStamp = ts -> {
        long timestamp = ts.getTime();
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timestamp);
        return cal.get(Calendar.MONTH);
    };

}
