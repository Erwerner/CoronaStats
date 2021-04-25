package infrastructure.input;

import com.google.gson.Gson;
import infrastructure.input.json.PointsJsonFormat;
import infrastructure.input.json.RowJsonFormat;

public class InputMapper {
    public Double[] mapJsonToDomainObject(String json) {
        RowJsonFormat rowJsonFormat = new Gson().fromJson(json, RowJsonFormat.class);
        String dates = rowJsonFormat.getAll().getDates().toString();
        dates = dates.replace("{","{ \"dates\" : [\"");
        dates = dates.replace("}","\"]}");
        dates = dates.replace(", ","\",\"");
        dates = dates.replaceAll("202[0-1]-[0-9]{2}-[0-9]{2}=","");
        PointsJsonFormat datesJson = new Gson().fromJson(dates, PointsJsonFormat.class);
        return datesJson.getDates();
    }
}
