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

public class InfrastructureInput implements ApplicationInput {

    private final InputMapper mapper = new InputMapper();

    @Override
    public Double[] readPoints(RowType rowType, RowContent rowContent) {
        HttpClient client = HttpClients.custom().build();
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
        }
        String country = "";
        switch (rowContent) {
            case DE:
                country = "Germany";
                break;
            case SW:
                country = "Switzerland";
                break;
        }
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
