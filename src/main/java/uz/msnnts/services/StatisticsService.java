package uz.msnnts.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.msnnts.dtos.StatisticsDto;
import uz.msnnts.dtos.TimeSlotDto;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by sirnornur on 26.08.17.
 */
@Service
public class StatisticsService {

    private TimeSlotDto[] statDeque = new TimeSlotDto[62];
    private int endIdx = 59;
    private int totalCount;
    private BigDecimal totalAmount;

//    private Object mutex = new Object();
    private ResponseEntity<StatisticsDto> statistics;

    public StatisticsService() {
        totalAmount = BigDecimal.ZERO;
        for (int i = 0; i < 60; i++) {
            statDeque[i] = new TimeSlotDto();
        }
    }


    public synchronized void nextSecond() {
        endIdx++;
        if (endIdx == 60) endIdx = 0;
        totalAmount = totalAmount.subtract(statDeque[endIdx].getAmount());
        totalCount -= statDeque[endIdx].getCount();
        statDeque[endIdx].setCount(0);
        statDeque[endIdx].setAmount(BigDecimal.ZERO);
        System.out.println("endIdx=" + endIdx);
    }

    private int getCurrentIndexOfSecond(int second) {
        int curIdx = endIdx - second;
        if (curIdx < 0) curIdx = 60 - curIdx;
        return curIdx;
    }

    public boolean nextData(long time, BigDecimal amount) {
        long difference = new Date().getTime() - time;
        if (difference < 60000) {
            int second = (int) difference / 1000;

            synchronized (this) {
                int curIdx = getCurrentIndexOfSecond(second);
                statDeque[curIdx].addAmount(amount);
                statDeque[curIdx].addCount();
                totalAmount = totalAmount.add(amount);
                totalCount++;
            }

            return true;
        } else {
            return false;
        }
    }


    public synchronized StatisticsDto getStatistics() {
        return new StatisticsDto(totalAmount, totalCount, BigDecimal.ZERO, BigDecimal.ZERO);
    }
}
