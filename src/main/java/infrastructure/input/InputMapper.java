package infrastructure.input;

import com.google.gson.Gson;
import infrastructure.input.json.DatesJsonFormat;
import infrastructure.input.json.RowJsonFormat;

public class InputMapper {
    public Double[] mapJsonToDomainObject(String json) {
        RowJsonFormat rowJsonFormat = new Gson().fromJson(json, RowJsonFormat.class);
        String dates = rowJsonFormat.getAll().getDates().toString();
        dates = dates.replace("{","{ \"dates\" : [\"");
        dates = dates.replace("}","\"]}");
        dates = dates.replace(", ","\",\"");
        dates = dates.replaceAll("2020-[0-9]{2}-[0-9]{2}=","");
        DatesJsonFormat datesJson = new Gson().fromJson(dates, DatesJsonFormat.class);
        return datesJson.getDates();
    }

}
