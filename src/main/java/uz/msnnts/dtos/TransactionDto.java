package uz.msnnts.dtos;

/**
 * Created by sirnornur on 26.08.17.
 */
public class TransactionDto {

    private long time;

    private double amount;

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
