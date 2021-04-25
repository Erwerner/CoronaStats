package infrastructure.input;

import application.core.RowContent;
import application.core.RowType;
import application.service.ApplicationInput;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import static application.core.RowType.*;

public class InfrastructureInput implements ApplicationInput {

    private final API api;
    private final int deathSinceCount = 9 * 31;

    public InfrastructureInput(API api) {
        this.api = api;
    }

    @Override
    public Double[] readPoints(RowType rowType, RowContent rowContent) {
        Double[] points;
        switch (rowType) {
            case ACT:
                points = getActiveFromAPI(rowContent);
                break;
            case DTH_OF_CFM:
                points = getDeathFromConfirmedFromAPI(rowContent);
                break;
            case DTH_TO_RCV:
                points = getDeathToRecoveredFromAPI(rowContent);
                break;
            case DTH_OF_POP:
                points = getDeathOfPopulationFromAPI(rowContent);
                break;
            case DTH_OF_10_POP:
                points = getDeathOf10PopulationFromAPI(rowContent);
                break;
            case NEW:
                points = getNewFromAPI(rowContent);
                break;
            case R_NEW:
                points = getRFromAPI(rowContent, this::getNewFromAPI);
                break;
            case INC_7D_100K:
                points = getIncidFromApi(rowContent, this::getNewFromAPI);
                break;
            case R_DTH:
                points = getRFromAPI(rowContent, this::getDeathNewFromAPI);
                break;
            case DTH_7D_100K:
                points = getIncidFromApi(rowContent, this::getDeathNewFromAPI);
                break;
            case DTH_OF_POP_MID_20:
                points = getDeathOf10PopulationMid20FromAPI(rowContent, deathSinceCount);
                break;

            default:
                points = api.getPointsFromApi(rowContent, rowType);
                break;
        }
        return points;
    }

    protected Double[] getDeathOf10PopulationMid20FromAPI(RowContent rowContent, int deathSinceCount) {
        List<Double> doubles = new ArrayList<>();
        Double[] deathOfPopulation = getDeathOfPopulationFromAPI(rowContent);
        Double deathAtStart = deathOfPopulation[deathSinceCount];
        Arrays.stream(Arrays.copyOfRange(deathOfPopulation, 0, deathSinceCount )).forEach(
                it -> {
                    doubles.add(it - deathAtStart);
                }
        );
        Double[] itemsArray = new Double[doubles.size()];
        return doubles.toArray(itemsArray);
    }

    private Double[] getIncidFromApi(RowContent rowContent, Function<RowContent, Double[]> valueFunction) {
        ArrayList<Double> points = new ArrayList<>();
        Double[] news = valueFunction.apply(rowContent);
        for (int i = 0; i <= news.length - 14; i++) {
            double incid7 = 100000 * (news[i] + news[i + 1] + news[i + 2] + news[i + 3] + news[i + 4] + news[i + 5] + news[i + 6]) / rowContent.getPopulation();
            points.add(incid7);
        }
        return points.toArray(new Double[0]);
    }

    private Double[] getRFromAPI(RowContent rowContent, Function<RowContent, Double[]> valueFunnction) {
        ArrayList<Double> points = new ArrayList<>();
        Double[] news = valueFunnction.apply(rowContent);
        for (int i = 0; i <= news.length - 14; i++) {
            double r =
                    (news[i] + news[i + 1] + news[i + 2] + news[i + 3] + news[i + 4] + news[i + 5] + news[i + 6]) /
                            (news[i + 7] + news[i + 8] + news[i + 9] + news[i + 10] + news[i + 11] + news[i + 12] + news[i + 13]);
            points.add(r);
        }
        return points.toArray(new Double[0]);
    }

    private Double[] getNewFromAPI(RowContent rowContent) {
        ArrayList<Double> points = new ArrayList<>();
        Double[] confirmed = api.getPointsFromApi(rowContent, CFM);
        for (int i = 0; i <= confirmed.length - 2; i++) {
            points.add(confirmed[i] - confirmed[i + 1]);
        }
        return points.toArray(new Double[0]);
    }


    private Double[] getDeathNewFromAPI(RowContent rowContent) {
        ArrayList<Double> points = new ArrayList<>();
        Double[] deaths = api.getPointsFromApi(rowContent, DTH);
        for (int i = 0; i <= deaths.length - 2; i++) {
            points.add(deaths[i] - deaths[i + 1]);
        }
        return points.toArray(new Double[0]);
    }

    private Double[] getDeathOfPopulationFromAPI(RowContent rowContent) {
        ArrayList<Double> points = new ArrayList<>();
        Double[] deaths = api.getPointsFromApi(rowContent, DTH);
        for (int i = 0; i <= deaths.length - 1; i++) {
            points.add(deaths[i] / rowContent.getPopulation());
        }
        return points.toArray(new Double[0]);
    }

    private Double[] getDeathOf10PopulationFromAPI(RowContent rowContent) {
        ArrayList<Double> points = new ArrayList<>();
        Double[] deaths = api.getPointsFromApi(rowContent, DTH);
        for (int i = 0; i <= deaths.length - 1; i++) {
            points.add(deaths[i] * 10000 / rowContent.getPopulation());
        }
        return points.toArray(new Double[0]);
    }

    private Double[] getDeathFromConfirmedFromAPI(RowContent country) {
        Double[] confirmed = api.getPointsFromApi(country, CFM);
        Double[] deaths = api.getPointsFromApi(country, DTH);
        ArrayList<Double> points = new ArrayList<>();
        for (int i = 0; i <= confirmed.length - 1; i++) {
            points.add(deaths[i] / confirmed[i]);
        }
        return points.toArray(new Double[0]);
    }

    private Double[] getDeathToRecoveredFromAPI(RowContent country) {
        Double[] recovered = api.getPointsFromApi(country, RCV);
        Double[] deaths = api.getPointsFromApi(country, DTH);
        ArrayList<Double> points = new ArrayList<>();
        for (int i = 0; i <= recovered.length - 1; i++) {
            points.add(deaths[i] / (deaths[i] + recovered[i]));
        }
        return points.toArray(new Double[0]);
    }

    private Double[] getActiveFromAPI(RowContent country) {
        Double[] confirmed = api.getPointsFromApi(country, CFM);
        Double[] deaths = api.getPointsFromApi(country, DTH);
        Double[] recovered = api.getPointsFromApi(country, RCV);
        ArrayList<Double> points = new ArrayList<>();
        for (int i = 0; i <= confirmed.length - 1; i++)
            points.add(confirmed[i] - recovered[i] - deaths[i]);
        return points.toArray(new Double[0]);
    }
}
