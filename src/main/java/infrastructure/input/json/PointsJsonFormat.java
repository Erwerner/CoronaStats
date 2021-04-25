package infrastructure.input.json;

public class PointsJsonFormat {
    private final Double[] dates;

    public PointsJsonFormat(Double[] dates) {
        this.dates = dates;
    }

    public Double[] getDates() {
        return dates;
    }
}
