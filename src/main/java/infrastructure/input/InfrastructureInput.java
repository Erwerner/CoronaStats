package infrastructure.input;

import application.core.RowContent;
import application.core.RowType;
import application.service.ApplicationInput;

import java.util.ArrayList;

import static application.core.RowType.*;

public class InfrastructureInput implements ApplicationInput {

    private final API API;

    public InfrastructureInput(API api) {
        API = api;
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
            default:
                return API.getPointsFromApi(rowContent, rowType);
        }
    }

    private Double[] getDeathOfPopulationFromAPI(RowContent rowContent) {
        ArrayList<Double> points = new ArrayList<>();
        Double[] deaths = API.getPointsFromApi(rowContent, DTH);
        for (int i = 0; i <= deaths.length - 1; i++) {
            points.add(deaths[i] / rowContent.getPopulation());
        }
        return points.toArray(new Double[0]);
    }

    private Double[] getDeathFromConfirmedFromAPI(RowContent country) {
        Double[] confirmed = API.getPointsFromApi(country, CFM);
        Double[] deaths = API.getPointsFromApi(country, DTH);
        ArrayList<Double> points = new ArrayList<>();
        for (int i = 0; i <= confirmed.length - 1; i++) {
            points.add(deaths[i] / confirmed[i]);
        }
        return points.toArray(new Double[0]);
    }

    private Double[] getDeathToRecoveredFromAPI(RowContent country) {
        Double[] recovered = API.getPointsFromApi(country, RCV);
        Double[] deaths = API.getPointsFromApi(country, DTH);
        ArrayList<Double> points = new ArrayList<>();
        for (int i = 0; i <= recovered.length - 1; i++) {
            points.add(deaths[i] / (deaths[i] + recovered[i]));
        }
        return points.toArray(new Double[0]);
    }

    private Double[] getActiveFromAPI(RowContent country) {
        Double[] confirmed = API.getPointsFromApi(country, CFM);
        Double[] deaths = API.getPointsFromApi(country, DTH);
        Double[] recovered = API.getPointsFromApi(country, RCV);
        ArrayList<Double> points = new ArrayList<>();
        for (int i = 0; i <= confirmed.length - 1; i++)
            points.add(confirmed[i] - recovered[i] - deaths[i]);
        return points.toArray(new Double[0]);
    }
}
