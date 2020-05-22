package infrastructure.input.json;

import com.google.gson.annotations.SerializedName;

public class RowJsonFormat {
    @SerializedName("All")
    private final AllJsonFormat all;

    public RowJsonFormat(AllJsonFormat all) {
        this.all = all;
    }

    public AllJsonFormat getAll() {
        return all;
    }
}
