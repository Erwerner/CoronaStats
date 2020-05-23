package infrastructure.input;

import application.core.RowContent;
import application.core.RowType;
import application.service.ApplicationInput;
import helper.IO;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.util.ArrayList;

import static application.core.RowType.*;

public class InfrastructureInput implements ApplicationInput {

    private final InputMapper mapper = new InputMapper();

    @Override
    public Double[] readPoints(RowType rowType, RowContent rowContent) {
        String country = "";
        switch (rowContent) {
            case DE:
                country = "Germany";
                break;
            case SZ:
                country = "Switzerland";
                break;
            case SP:
                country = "Spain";
                break;
            case SW:
                country = "Sweden";
                break;
            case UK:
                country = "United Kingdom";
                break;
            case TK:
                country = "Turkey";
                break;
        }
        if (rowType == ACT)
            return getActiveFromAPI(country);
        else
            return getPointsFromApi(country, rowType);
    }

    private Double[] getActiveFromAPI(String country) {
        Double[] confirmed = getPointsFromApi(country, CFM);
        Double[] deaths = getPointsFromApi(country, DTH);
        Double[] recovered = getPointsFromApi(country, RCV);
        ArrayList<Double> points = new ArrayList<>();
        for (int i = 0; i <= confirmed.length - 1; i++)
            points.add(confirmed[i] - recovered[i] - deaths[i]);
        return points.toArray(new Double[0]);
    }

    private Double[] getPointsFromApi(String country, RowType rowType) {
        String status = "";
        switch (rowType) {
            case RCV:
                status = "Recovered";
                break;
            case DTH:
                status = "Deaths";
                break;
            case CFM:
                status = "Confirmed";
                break;
            case ACT:
                throw new RuntimeException();
        }
        HttpClient client = HttpClients.custom().build();
        HttpUriRequest request = RequestBuilder.get()
                .setUri("https://covid-api.mmediagroup.fr/v1/history?country=" + country + "&status=" + status)
                .setHeader(HttpHeaders.ACCEPT, "*/*")
                .setHeader(HttpHeaders.USER_AGENT, "PostmanRuntime/7.24.0")
                .build();

        try {
            HttpResponse response = client.execute(request);
            String json = IO.readFromInputStream(response.getEntity().getContent());
            return mapper.mapJsonToDomainObject(json);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


}
