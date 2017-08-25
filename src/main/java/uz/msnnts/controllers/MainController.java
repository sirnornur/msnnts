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

    private final ResponseEntity reCreated = new ResponseEntity(HttpStatus.CREATED);
    private final ResponseEntity reNoContent = new ResponseEntity(HttpStatus.NO_CONTENT);

    @RequestMapping(value = "/transactions",method = RequestMethod.POST)
    public ResponseEntity accountTransaction(@RequestBody TransactionDto transaction) {

        if (statisticsService.nextData(transaction.getTime(), transaction.getAmount())) {
            return reCreated;
        } else {
            return reNoContent;
        }
    }

    @RequestMapping(value = "/statistics", produces = "application/json")
    @ResponseBody
    public ResponseEntity<StatisticsDto> getStatisticsOfLastMinute() {
        return new ResponseEntity<>(statisticsService.getStatistics(), HttpStatus.OK);
    }
}