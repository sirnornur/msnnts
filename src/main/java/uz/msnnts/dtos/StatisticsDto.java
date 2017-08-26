package uz.msnnts.dtos;

import java.math.BigDecimal;

/**
 * Created by sirnornur on 26.08.17.
 */
public class StatisticsDto {

    private BigDecimal sum;

    private BigDecimal avg = null;

    private BigDecimal max;

    private BigDecimal min;

    private int count;

    public StatisticsDto(BigDecimal amount, int tCount, BigDecimal minimum, BigDecimal maximum) {
        this.sum = amount;
        this.count = tCount;
        if (this.count > 0) {
            this.avg = this.sum.divide(BigDecimal.valueOf(this.count), BigDecimal.ROUND_HALF_UP);
        }
        this.min = minimum;
        this.max = maximum;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }

    public BigDecimal getAvg() {
        return avg;
    }

    public void setAvg(BigDecimal avg) {
        this.avg = avg;
    }

    public BigDecimal getMax() {
        return max;
    }

    public void setMax(BigDecimal max) {
        this.max = max;
    }

    public BigDecimal getMin() {
        return min;
    }

    public void setMin(BigDecimal min) {
        this.min = min;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
