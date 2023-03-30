package com.infogain.rewards.v1;

import com.infogain.rewards.v1.repository.dto.Transaction;
import com.infogain.rewards.v1.service.model.CustomerBo;
import com.infogain.rewards.v1.service.model.TransactionBo;
import com.infogain.rewards.v1.utility.RewardsUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.xmlunit.builder.Input;


import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;


import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;

public class RewardsCalculationTest {

    private static final Logger LOGGER = LogManager.getLogger(RewardsCalculationTest.class);

    @Autowired
    private RewardsUtil rewardsUtil;

    @Test
    public void testRewardsPoints() {
        final String text = "20123-02-02 18:48:05.123";
        double purchaseAmount = 120.0;
        int expectedPoints = 90;
        int actualPoints = calculatePoints.apply(purchaseAmount);
        assertEquals(expectedPoints, actualPoints);


        purchaseAmount = 50.0;
        expectedPoints = 0;
        actualPoints = calculatePoints.apply(purchaseAmount);
        assertEquals(expectedPoints, actualPoints);

        purchaseAmount = 150.0;
        expectedPoints = 150;
        actualPoints = calculatePoints.apply(purchaseAmount);
        assertEquals(expectedPoints, actualPoints);

        purchaseAmount = 75.0;
        expectedPoints = 25;
        actualPoints = calculatePoints.apply(purchaseAmount);
        assertEquals(expectedPoints, actualPoints);

        purchaseAmount = 200.0;
        expectedPoints = 250;
        actualPoints = calculatePoints.apply(purchaseAmount);
        assertEquals(expectedPoints, actualPoints);

    }

    private Function<Double, Integer> calculatePoints = purchaseAmount -> {
        int points = 0;

        if (purchaseAmount > 100) {
            points += 2 * (int) (purchaseAmount - 100);
            purchaseAmount = 100.00;
        }

       if (purchaseAmount > 50 && purchaseAmount <= 100) {
            points += 1 * (int) (purchaseAmount - 50);
        }

        LOGGER.info(" Total Points :::  {}",points);
        return points;
    };

}
