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

import java.io.IOException;

public class API {
    public final InputMapper mapper = new InputMapper();

    public Double[] getPointsFromApi(RowContent country, RowType rowType) {
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
            default:
                throw new RuntimeException("Can't read from API: " + rowType);
        }
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
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getName(RowContent country) {
        String country1 = "";
        switch (country) {
            case DE:
                country1 = "Germany";
                break;
            case SZ:
                country1 = "Switzerland";
                break;
            case SP:
                country1 = "Spain";
                break;
            case SW:
                country1 = "Sweden";
                break;
            case TK:
                country1 = "Turkey";
                break;
            case GR:
                country1 = "Greece";
                break;
            case TC:
                country1 = "Czechia";
                break;
            case UK:
                country1 = "United+Kingdom";
                break;
            case US:
                country1 = "US";
                break;
            case SK:
                country1 = "Korea,+South";
                break;
            case BZ:
                country1 = "Brazil";
                break;
            case IT:
                country1 = "Italy";
                break;
            case NL:
                country1 = "Netherlands";
                break;
            case FR:
                country1 = "France";
                break;
            case BL:
                country1 = "Belgium";
                break;
            case OE:
                country1 = "Austria";
                break;
            case FN:
                country1 = "Finland";
                break;
        }
        return country1;
    }
}
