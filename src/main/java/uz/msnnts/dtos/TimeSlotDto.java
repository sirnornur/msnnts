package uz.msnnts.dtos;

/**
 * Created by sirnornur on 26.08.17.
 */
public class TimeSlotDto {
    private long count = 0;
    private double amount = 0;

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void addSum(double amount) {
        this.amount += amount;
    }

    public void addCount() {
        this.count++;
    }
}
