package ui.console;

import application.core.RowContent;
import application.core.RowType;
import helper.IO;
import ui.template.Model;

public class ConsoleControllerFactory {

    public ConsoleController initSelectController(Model model) {
        return new ConsoleController(model) {
            @Override
            public void execute() {
                int cursor = Integer.parseInt(IO.readInputFor("Select Row"));
                getModel().setCursor(cursor);
            }
        };
    }

    public ConsoleController initAddController(Model model) {
        return new ConsoleController(model) {
            @Override
            public void execute() {
                RowType typeSelection = (RowType) IO.getEnumByInput("Choose RowType", RowType.values());
                RowContent contentSelection = (RowContent) IO.getEnumByInput("Choose ContentType", RowContent.values());
                getModel().addRow(typeSelection, contentSelection);
            }
        };
    }

    public ConsoleController initSyncController(Model model) {
        return new ConsoleController(model) {
            @Override
            public void execute() {
                getModel().syncRows();
            }
        };
    }

    public ConsoleController initShiftController(Model model) {
        return new ConsoleController(model) {
            @Override
            public void execute() {
                int shift = Integer.parseInt(IO.readInputFor("Set Shift"));
                getModel().shiftCursor(shift);
            }
        };
    }

    public ConsoleController initCutStartController(Model model) {
        return new ConsoleController(model) {
            @Override
            public void execute() {
                int cut = Integer.parseInt(IO.readInputFor("Set Cut"));
                getModel().cutRowsStart(cut);
            }
        };
    }

    public ConsoleController initCutEndController(Model model) {
        return new ConsoleController(model) {
            @Override
            public void execute() {
                int cut = Integer.parseInt(IO.readInputFor("Set Cut"));
                getModel().cutRowsEnd(cut);
            }
        };
    }

    public ConsoleController initRemoveController(Model model) {
        return new ConsoleController(model) {
            @Override
            public void execute() {
                getModel().removeCursor();
            }
        };
    }

    public ConsoleController initResetController(Model model) {
        return new ConsoleController(model) {
            @Override
            public void execute() {
                getModel().resetRows();
            }
        };
    }

    public ConsoleController initScaleController(Model model) {
        return new ConsoleController(model) {
            @Override
            public void execute() {
                Double faktor = Double.valueOf(IO.readInputFor("Set faktor"));
                getModel().scaleCursor(faktor);
            }
        };
    }

    public ConsoleController initExportController(Model model) {
        return new ConsoleController(model) {
            @Override
            public void execute() {
                getModel().export();
            }
        };
    }

    public ConsoleController initScaleLastController(Model model) {
        return new ConsoleController(model) {
            @Override
            public void execute() {
                getModel().scaleLast();
            }
        };
    }
}
