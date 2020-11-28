package infrastructure.input;

import application.core.RowContent;
import application.core.RowType;
import application.service.ApplicationInput;

import java.util.ArrayList;

import static application.core.RowType.*;

public class InfrastructureInput implements ApplicationInput {

    private final API api;

    public InfrastructureInput(API api) {
        this.api = api;
    }

    @Override
    public Double[] readPoints(RowType rowType, RowContent rowContent) {
        switch (rowType) {
            case ACT:
                return getActiveFromAPI(rowContent);
            case DTH_OF_CFM:
                return getDeathFromConfirmedFromAPI(rowContent);
            case DTH_TO_RCV:
                return getDeathToRecoveredFromAPI(rowContent);
            case DTH_OF_POP:
                return getDeathOfPopulationFromAPI(rowContent);
            case NEW:
                return getNewFromAPI(rowContent);
            case R:
                return getRFromAPI(rowContent);
            default:
                return api.getPointsFromApi(rowContent, rowType);
        }
    }

    private Double[] getRFromAPI(RowContent rowContent) {
        ArrayList<Double> points = new ArrayList<>();
        Double[] news = getNewFromAPI(rowContent);
        for (int i = 0; i <= news.length - 16; i++) {
            double r =
                    (news[i] + news[i + 1] + news[i + 2] + news[i + 3] + news[i + 4] + news[i + 5] + news[i + 6] + news[i + 7]) /
                            (news[i + 8] + news[i + +9] + news[i + 10] + news[i + 11] + news[i + 12] + news[i + 13] + news[i + 14] + news[i + 15]);
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

    private Double[] getDeathOfPopulationFromAPI(RowContent rowContent) {
        ArrayList<Double> points = new ArrayList<>();
        Double[] deaths = api.getPointsFromApi(rowContent, DTH);
        for (int i = 0; i <= deaths.length - 1; i++) {
            points.add(deaths[i] / rowContent.getPopulation());
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
