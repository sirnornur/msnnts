package uz.msnnts.dtos;

/**
 * Created by sirnornur on 26.08.17.
 */
public class StatisticsDto {

    private double sum;

    private double avg;

    private double max;

    private double min;

    private int count;

    public StatisticsDto(double amount, int tCount, double minimum, double maximum) {
        this.sum = amount;
        this.count = tCount;
        this.avg = this.sum / this.count;
        this.min = minimum;
        this.max = maximum;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public double getAvg() {
        return avg;
    }

    public void setAvg(double avg) {
        this.avg = avg;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
