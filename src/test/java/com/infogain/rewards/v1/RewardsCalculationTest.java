package com.infogain.rewards.v1;



import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import java.util.function.Function;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RewardsCalculationTest {
    private static final Logger LOGGER = LogManager.getLogger(RewardsCalculationTest.class);

    @Test
    public void testRewardsPoints() {

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

  final  private Function<Double, Integer>  calculatePoints = purchaseAmount -> {
        int points = 0;
        if (purchaseAmount > 100) {
            points += 2 * (int) (purchaseAmount - 100);
            purchaseAmount = 100.00;
        }
       if (purchaseAmount > 50 && purchaseAmount <= 100) {
            points += (int) (purchaseAmount - 50);
        }
        LOGGER.info(" Total Points :::  {}",points);
        return points;
    };

}
