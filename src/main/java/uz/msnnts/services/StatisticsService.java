package uz.msnnts.services;

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

    //Statistics array for every one of the last 60 seconds
    //Contains: total amount, count, minimum and maximum for
    //each of the second
    private TimeSlotDto[] statDeque = new TimeSlotDto[62];

    //Index of the Current second (marker for the beginning of the circular buffer)
    private int endIdx = 59;

    //Total number of transactions for the last 60 seconds
    private int totalCount;

    //Total amount of transactions for the last 60 seconds
    private BigDecimal totalAmount;

    //Overall minimum amount of transactions for the last 60 seconds
    private BigDecimal overallMinimum;

    //Overall maximum amount of transactions for the last 60 seconds
    private BigDecimal overallMaximum;

    //Initial value of minimum and maximum fields, this value will be
    //served when there was not any transaction in the last 60 seconds
    private final BigDecimal noLocalMinimum = null;
    private final BigDecimal noLocalMaximum = null;

    //Constructor, we need to initialize our circular buffer
    public StatisticsService() {
        //initial total amount of transactions
        totalAmount = BigDecimal.ZERO;

        //fill our circular buffer with empty second statistics objects
        for (int i = 0; i < 60; i++) {
            //Every object in the array should contain
            //total amount =0, total count=0, minimum=null, maximum=null
            statDeque[i] = new TimeSlotDto();
        }

        //We don't have transactions yet, so set min and max to null value initially
        overallMinimum = noLocalMinimum;
        overallMaximum = noLocalMaximum;
    }

    /**
     * Ticker method, its responsibilities:
     * - Remove the data of 61th second from the statistics
     * - Start accounting the new seconds data in the statistics
     */
    public synchronized void nextSecond() {
        //Move the current starting point of our circular buffer
        endIdx++;
        if (endIdx == 60) endIdx = 0;

        //Subtract the 61th seconds transactions amount from the total transactions amount
        totalAmount = totalAmount.subtract(statDeque[endIdx].getAmount());

        //Subtract the 61th seconds transactions count from the total transactions count
        totalCount -= statDeque[endIdx].getCount();

        //If the global maximum equals to the 61st seconds minimum:
        if (isAEqualsToB(overallMaximum, statDeque[endIdx].getMaximum())) {
            //Then this maximum has expired
            overallMaximum = null;

            //We need to recalculate the global maximum
            for (int i = 1; i < 60; i++) {
                int secondIdx = getCurrentIndexOfSecond(i);
                if (isAGreaterThanB(statDeque[secondIdx].getMaximum(), overallMaximum)) {
                    overallMaximum = statDeque[secondIdx].getMaximum();
                }
            }
        }
        //If the global maximum equals to the 61st seconds minimum:
        if (isAEqualsToB(overallMinimum, statDeque[endIdx].getMinimum())) {
            //Then this minimum has expired
            overallMinimum = null;

            //We need to recalculate the global minimum
            for (int i = 1; i < 60; i++) {
                int secondIdx = getCurrentIndexOfSecond(i);
                if (isALessThanB(statDeque[secondIdx].getMinimum(), overallMinimum)) {
                    overallMinimum = statDeque[secondIdx].getMinimum();
                }
            }
        }

        //Set new second's statistics to initial values
        statDeque[endIdx].setCount(0);
        statDeque[endIdx].setAmount(BigDecimal.ZERO);
        statDeque[endIdx].setMinimum(noLocalMinimum);
        statDeque[endIdx].setMaximum(noLocalMaximum);
    }

    /**
     * Calculates the position of the given second within our circular buffer
     * Based on the head marker (endIdx)
     * @param second how many seconds ago did the transaction happen?
     * @return index of the second in our circular buffer
     */
    private int getCurrentIndexOfSecond(int second) {
        int curIdx = endIdx - second;
        if (curIdx < 0) curIdx = 60 + curIdx;
        return curIdx;
    }

    /**
     * This method is to check the new transaction request and to register it
     * if it was registered in last 60 seconds
     * @param time milliseconds since epoch when the transaction was registered
     * @param amount amount of the tarnsaction
     * @return true if transaction was registered within last 60 seconds, false otherwise (transaction hsa expired)
     */
    public boolean nextData(long time, BigDecimal amount) {
        //Calculate the time passed since the transaction was registered
        long difference = new Date().getTime() - time;

        //If it is less that 60 seconds
        if (difference < 60000) {
            //to make it thread safe
            synchronized (this) {
                //Get the second from milliseconds
                int second = (int) difference / 1000;

                //find the index of the second in our circular buffer
                int curIdx = getCurrentIndexOfSecond(second);

                //Update the statistics for that particular second
                statDeque[curIdx].addAmount(amount);
                statDeque[curIdx].addCount();

                //Check and update local minimum/maximums for that particular second
                if (isALessThanB(amount, statDeque[curIdx].getMinimum())) {
                    statDeque[curIdx].setMinimum(amount);
                }
                if (isAGreaterThanB(amount, statDeque[curIdx].getMaximum())) {
                    statDeque[curIdx].setMaximum(amount);
                }


                //Update global statistics for the last 60 seconds
                totalAmount = totalAmount.add(amount);
                totalCount++;

                //Check and update global minimum and maximum for the last 60 seconds
                if (isALessThanB(amount, overallMinimum)) {
                    overallMinimum = amount;
                }
                if (isAGreaterThanB(amount, overallMaximum)) {
                    overallMaximum = amount;
                }
            }
            //Successfully registered the transaction in the statistics
            return true;
        } else {
            //Transaction is very old (older than 60 seconds), that's why it was not registered
            return false;
        }
    }

    /**
     * Return statistics dto with current statistics
     * @return 5 kinds of statistics in StatisticsDTO
     */
    public synchronized StatisticsDto getStatistics() {
        return new StatisticsDto(totalAmount, totalCount, overallMinimum, overallMaximum);
    }


    //Helper methods for comparing BigDecimal objects, they would be better off in separate file
    //However let's just keep them here for this coding task
    private boolean isAGreaterThanB(BigDecimal a, BigDecimal b) {
        return (a != null && (b == null || a.compareTo(b) > 0));
    }

    private boolean isALessThanB(BigDecimal a, BigDecimal b) {
        return (a != null && (b == null || a.compareTo(b) < 0));
    }

    private boolean isAEqualsToB(BigDecimal a, BigDecimal b) {
        return (a != null && b != null && a.compareTo(b) == 0);
    }
}
