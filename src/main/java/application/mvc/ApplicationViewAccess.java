package application.mvc;

import application.core.Row;

import java.util.List;

public interface ApplicationViewAccess {
    List<Row> getAdjustedRows();

    Integer getCursor();
}
