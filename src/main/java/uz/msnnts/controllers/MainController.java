package uz.msnnts.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import uz.msnnts.dtos.StatisticsDto;
import uz.msnnts.dtos.TransactionDto;
import uz.msnnts.services.StatisticsService;

@Controller
public class MainController {

    @Autowired
    private StatisticsService statisticsService;

    //Cache the response object so we don't have create new object on every response
    // for the "/transactions endpoint
    private final ResponseEntity reCreated = new ResponseEntity(HttpStatus.CREATED);
    private final ResponseEntity reNoContent = new ResponseEntity(HttpStatus.NO_CONTENT);

    /**
     * Receives transaction dto in JSON format
     * @param transaction - TransactionDto in JSON format
     * @return CREATED (201) if transaction is in last 60 seconds and registered, NO_CONTENT (204) otherwise
     */
    @RequestMapping(value = "/transactions", method = RequestMethod.POST, consumes = {"application/json"})
    public ResponseEntity accountTransaction(@RequestBody TransactionDto transaction) {

        if (statisticsService.nextData(transaction.getTimestamp(), transaction.getAmount())) {
            return reCreated;
        } else {
            return reNoContent;
        }
    }

    /**
     * Responds with the actual statistics in StatisticsDto object in JSON format
     * @return the actual statistics in StatisticsDto object in JSON format
     */
    @RequestMapping(value = "/statistics", produces = "application/json")
    @ResponseBody
    public ResponseEntity<StatisticsDto> getStatisticsOfLastMinute() {
        return new ResponseEntity<>(statisticsService.getStatistics(), HttpStatus.OK);
    }
}