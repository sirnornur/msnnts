package uz.msnnts.dtos;

import java.math.BigDecimal;

/**
 * Created by sirnornur on 26.08.17.
 */
public class TimeSlotDto {
    private long count = 0;
    private BigDecimal amount = BigDecimal.ZERO;

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
}
