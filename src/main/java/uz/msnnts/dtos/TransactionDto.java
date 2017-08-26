package uz.msnnts.dtos;

import java.math.BigDecimal;

/**
 * Created by sirnornur on 26.08.17.
 */
public class TransactionDto {

    private long timestamp;

    private BigDecimal amount;

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
