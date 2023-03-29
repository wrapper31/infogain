package com.infogain.rewards.v1;

import com.infogain.rewards.v1.controller.CustomerRewardsController;
import com.infogain.rewards.v1.service.model.TransactionBo;
import com.infogain.rewards.v1.service.RewardsService;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.sql.Timestamp;

import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class TransactionControllerMockTest {

    @InjectMocks
    CustomerRewardsController customerRewardsController;

    @InjectMocks
    RewardsService rewardsService;

    @Test
    public void testSaveTransactions() {
        final String text = "20123-02-02 18:48:05.123";
        Timestamp ts = Timestamp.valueOf(text);
        System.out.println(ts);
        TransactionBo t1 = new TransactionBo(10023L, 1002L, ts,89.00);
        TransactionBo TransactionBo = null;

        when(rewardsService.saveTransaction(t1)).thenReturn(1);
        ResponseEntity<TransactionBo> result =  customerRewardsController.findTransactionbyId(10023);
        assertThat(result.getBody().getTransactionId()).isEqualTo(t1.getTransactionId());
        assertThat(result.getBody().getCustomerId()).isEqualTo(t1.getCustomerId());
    }

    @Test
    public void testUpdateTransactions() {
        final String text = "20123-02-02 18:48:05.123";
        Timestamp ts = Timestamp.valueOf(text);
        System.out.println(ts);
        TransactionBo t1 = new TransactionBo(10023L, 1002L, ts,49.00);
        TransactionBo TransactionBo = null;

        when(rewardsService.updateTransaction(t1)).thenReturn(t1.getTransactionId());
        ResponseEntity<TransactionBo> result =  customerRewardsController.findTransactionbyId(10023);
        assertThat(result.getBody().getTransactionId()).isEqualTo(t1.getTransactionId());
        assertThat(result.getBody().getCustomerId()).isEqualTo(t1.getCustomerId());
    }

    @Test
    public void testDeleteTransactions() {
        final String text = "20123-02-02 18:48:05.123";
        Timestamp ts = Timestamp.valueOf(text);
        System.out.println(ts);
        TransactionBo t = new TransactionBo(10023L, 1002L, ts,49.00);
        ResponseEntity<TransactionBo> result = customerRewardsController.deleteransaction(t);
        assertThat(result.getBody()== null);
    }
}
