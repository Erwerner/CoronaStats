package application.core;

import java.util.List;

public class ApplicationData {
    private List<Row> rows;
    private List<Double> scales;
    private List<Integer> shifts;
    int cutStart;
    int cutEnd;

    public int getRowCount() {
        return rows.size();
    }

    public Double getScale(Integer index) {
        return scales.get(index);
    }

    public Integer getShift(Integer index) {
        return shifts.get(index);
    }

    public int getCutStart() {
        return cutStart;
    }

    public int getCutEnd() {
        return cutEnd;
    }

    public void setCutStart(int cutStart) {
        this.cutStart = cutStart;
    }

    public void setCutEnd(int cutEnd) {
        this.cutEnd = cutEnd;
    }

    public void setShift(Integer index, int shift) {
        shifts.set(index, shift);
    }

    public void setScale(Integer index, Double faktor) {
        scales.set(index, faktor);
    }

    public void removeIndex(Integer index) {
        rows.remove(index);
        scales.remove(index);
        shifts.remove(index);
    }

    public void reset() {
        cutEnd = 0;
        cutStart = 0;
        scales.clear();
        shifts.clear();
        for (int i = 0; i <= getRowCount(); i++) {
            scales.add(0.0);
            shifts.add(0);
        }
    }

}
