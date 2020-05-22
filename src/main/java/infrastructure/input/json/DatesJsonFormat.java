package infrastructure.input.json;

public class DatesJsonFormat {
    private final Double[] dates;

    public DatesJsonFormat(Double[] dates) {
        this.dates = dates;
    }

    public Double[] getDates() {
        return dates;
    }
}
