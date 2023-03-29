package com.infogain.rewards.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.infogain.rewards.v1.service.model.CustomerBo;
import com.infogain.rewards.v1.service.RewardsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class RewardsApplicationTests {

    private static final Logger LOGGER = LogManager.getLogger(RewardsApplicationTests.class);
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RewardsService rewardsService;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    public void givenInvalidcustomerId_whenGetCustomerById_thenReturnEmpty() throws Exception{
        // given - precondition or setup
        long customerId = 1001L;
        given(rewardsService.findCustomerById(customerId)).willReturn(
                new CustomerBo(1002L, "Ajit Rai")) ;
        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/api/v1/rewards/1001", customerId));
        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print());
    }


}
