package com.infogain.rewards.v1;

import com.infogain.rewards.v1.model.CustomerBo;
import com.infogain.rewards.v1.model.TransactionBo;
import net.minidev.json.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RewardsApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql({"/customer.sql", "/transactions.sql"})
public class TransactionControllerIntegrationTests {

    private static final Logger LOGGER = LogManager.getLogger(TransactionControllerIntegrationTests.class);

    @Autowired
    private  TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private String getRootUrl() {
        return "http://localhost:" + port;
    }

    @Test
    public void contextLoads() {

    }
    static JSONObject transactionObject;
    static HttpHeaders  headers;


    @BeforeClass
    public static void runBeforeAllTestMethods() {

        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
         transactionObject = new JSONObject();
        transactionObject.put("transactionId", 10022);
        transactionObject.put("customerId", "1003");
        transactionObject.put("transactionDate", "2023-02-01");
        transactionObject.put("amount", "89.00");
    }

    @Test
    public void testGetAllTransactions() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<CustomerBo> entity = new HttpEntity<CustomerBo>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/api/v1/rewards/1001",
                HttpMethod.GET, entity, String.class);

        assertNotNull(response.getBody());
    }

    @Test
    public void testFindTransactionById() {
        TransactionBo transaction = restTemplate.getForObject(getRootUrl() + "/api/v1/rewards/transaction/10001", TransactionBo.class);
        LOGGER.info("Transaction received is :{}",transaction.toString());
        assertNotNull(transaction);
    }

    @Test
    public void testCreateTransaction() {
        JSONObject transactionObject = new JSONObject();
        transactionObject.put("transactionId", 10022);
        transactionObject.put("customerId", "1003");
        transactionObject.put("transactionDate", "2023-02-01");
        transactionObject.put("amount", "89.00");
        HttpEntity<String> request =
                new HttpEntity<String>(transactionObject.toString(), headers);


        restTemplate.put(getRootUrl() + "/api/v1/rewards/transaction", request);
        LOGGER.info("Transaction created  is :{}",transactionObject.toString());
        assertNotNull(transactionObject.toString());
    }

    @Test
    public void testUpdateTransaction() {
        JSONObject transactionObject = new JSONObject();
        transactionObject.put("transactionId", 10022);
        transactionObject.put("customerId", "1003");
        transactionObject.put("transactionDate", "2023-02-01");
        transactionObject.put("amount", "49.00");

        HttpEntity<String> request =
                new HttpEntity<String>(transactionObject.toString(), headers);

        TransactionBo transaction = restTemplate.postForObject(getRootUrl() + "/api/v1/rewards/transaction", request, TransactionBo.class);
        LOGGER.info("Transaction received is :{}",transaction.toString());
        assertNotNull(transaction);
    }

    @Test
    public void testDeleteTransaction() {
        JSONObject transactionObject = new JSONObject();
        transactionObject.put("transactionId", 10022);
        transactionObject.put("customerId", "1003");
        transactionObject.put("transactionDate", "2023-02-01");
        transactionObject.put("amount", "49.00");
        HttpEntity<String> request =
                new HttpEntity<String>(transactionObject.toString(), headers);
        restTemplate.delete(getRootUrl() + "/api/v1/rewards/transaction", request);
        LOGGER.info("Transaction deleted is :{}",transactionObject.toString());
        assertNotNull(transactionObject);
    }

}
