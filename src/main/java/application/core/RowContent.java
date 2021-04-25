package application.core;

public enum RowContent {
    DE, SZ, OE, NL,
    BZ, US, PL, SW,
    IT, FR, SP,
    BL, TC, PT, UK,
    IS, UI,
    DN, FN, NW, SK;

    public int getPopulation() {
        switch (this) {
            case DE:
                return 83000000;
            case SZ:
                return 8500000;
            case SW:
                return 10300000;
            case SP:
                return 47000000;
            case TC:
                return 10500000;
            case UK:
                return 68000000;
            case US:
                return 328000000;
            case SK:
                return 51700000;
            case BZ:
                return 212000000;
            case IT:
                return 60000000;
            case FR:
                return 67000000;
            case NL:
                return 17300000;
            case BL:
                return 11600000;
            case OE:
                return 8900000;
            case FN:
                return 5500000;
            case DN:
                return 5900000;
            case IS:
                return 9100000;
            case UI:
                return 41800000;
            case PL:
                return 38300000;
            case PT:
                return 10300000;
            case NW:
                return 5400000;
            default:
                throw new RuntimeException("No population for " + this);
        }
    }
}
