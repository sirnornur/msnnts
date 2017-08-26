package uz.msnnts.dtos;

import java.math.BigDecimal;

/**
 * Created by sirnornur on 26.08.17.
 */
public class TimeSlotDto {
    //Number of transactions in a particular second
    private long count = 0;

    //Total Amount of transactions in a particular second
    private BigDecimal amount = BigDecimal.ZERO;

    //Local minimum amount of transactions in a particular second
    private BigDecimal minimum = null;

    //Local maximum amount of transactions in a particular second
    private BigDecimal maximum = null;

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void addAmount(BigDecimal amount) {
        this.amount = this.amount.add(amount);
    }

    public void addCount() {
        this.count++;
    }

    public BigDecimal getMinimum() {
        return minimum;
    }

    public void setMinimum(BigDecimal minimum) {
        this.minimum = minimum;
    }

    public BigDecimal getMaximum() {
        return maximum;
    }

    public void setMaximum(BigDecimal maximum) {
        this.maximum = maximum;
    }
}
