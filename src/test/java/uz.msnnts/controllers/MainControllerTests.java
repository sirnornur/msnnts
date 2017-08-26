package uz.msnnts.controllers;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import uz.msnnts.dtos.TransactionDto;

import java.math.BigDecimal;
import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(MainController.class)
public class MainControllerTests {

    @Autowired
    private MockMvc mvc;

    /**
     * Checks the transactions endpoint with the transaction in last 60 seconds
     * @throws Exception
     */
    @Test
    public void checkPostTransactionsMethodForTransactionInLastMinute() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper() ;
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setAmount(BigDecimal.valueOf(Math.random() * 200));
        transactionDto.setTimestamp(new Date().getTime());
        String requestBody = objectMapper.writeValueAsString(transactionDto);

        mvc.perform(
                MockMvcRequestBuilders.post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(requestBody)
        ).andExpect(
                MockMvcResultMatchers.status().is(HttpStatus.CREATED.value())
        );

    }

    /**
     * Checks the transaction endpoint with the expired transaction
     * @throws Exception
     */
    @Test
    public void checkPostTransactionForExpiredTransaction() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper() ;
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setAmount(BigDecimal.valueOf(Math.random() * 200));
        transactionDto.setTimestamp(new Date().getTime() - 60001);
        String requestBody = objectMapper.writeValueAsString(transactionDto);

        mvc.perform(
                MockMvcRequestBuilders.post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(requestBody)
        ).andExpect(
                MockMvcResultMatchers.status().is(HttpStatus.NO_CONTENT.value())
        );
    }
}