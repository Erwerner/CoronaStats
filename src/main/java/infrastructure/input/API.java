package infrastructure.input;

import application.core.RowContent;
import application.core.RowType;
import helper.IO;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.HttpClients;

import java.util.HashMap;
import java.util.Map;

public class API {
    public final InputMapper mapper = new InputMapper();
    private final Map<RowContent, Map<RowType, Double[]>> apiPoints = new HashMap<>();

    public Double[] getPointsFromApi(RowContent country, RowType rowType) {
        String status;
        apiPoints.computeIfAbsent(country, k -> new HashMap<>());
        Map<RowType, Double[]> countryPoints = apiPoints.get(country);
        if (countryPoints.get(rowType) == null) {
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
                default:
                    throw new RuntimeException("Can't read from API: " + rowType);
            }
            countryPoints.put(rowType, readApi(country, status));
        }
        return countryPoints.get(rowType);
    }

    private Double[] readApi(RowContent country, String status) {
        HttpClient client = HttpClients.custom().build();
        String uri = "https://covid-api.mmediagroup.fr/v1/history?country=" + getName(country) + "&status=" + status;
        HttpUriRequest request = RequestBuilder.get()
                .setUri(uri)
                .setHeader(HttpHeaders.ACCEPT, "*/*")
                .setHeader(HttpHeaders.USER_AGENT, "PostmanRuntime/7.24.0")
                .build();

        try {
            HttpResponse response = client.execute(request);
            String json = IO.readFromInputStream(response.getEntity().getContent());
            return mapper.mapJsonToDomainObject(json);
        } catch (Exception e) {
            System.out.println(uri);
            e.printStackTrace();
            return null;
        }
    }

    private String getName(RowContent country) {
        String name = "";
        switch (country) {
            case DE:
                name = "Germany";
                break;
            case SZ:
                name = "Switzerland";
                break;
            case SP:
                name = "Spain";
                break;
            case SW:
                name = "Sweden";
                break;
            case TC:
                name = "Czechia";
                break;
            case UK:
                name = "United+Kingdom";
                break;
            case US:
                name = "US";
                break;
            case SK:
                name = "Korea,+South";
                break;
            case BZ:
                name = "Brazil";
                break;
            case IT:
                name = "Italy";
                break;
            case NL:
                name = "Netherlands";
                break;
            case FR:
                name = "France";
                break;
            case BL:
                name = "Belgium";
                break;
            case OE:
                name = "Austria";
                break;
            case FN:
                name = "Finland";
                break;
            case DN:
                name = "Denmark";
                break;
            case IS:
                name = "Israel";
                break;
            case UI:
                name = "Ukraine";
                break;
            case PL:
                name = "Poland";
                break;
            case PT:
                name = "Portugal";
                break;
            case NW:
                name = "Norway";
                break;
        }
        return name;
    }
}
