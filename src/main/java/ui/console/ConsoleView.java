package ui.console;

import application.core.Row;
import application.core.RowContent;
import application.core.RowType;
import application.mvc.ApplicationControllerAccess;
import application.mvc.ApplicationViewAccess;
import helper.IO;
import ui.template.Model;
import ui.template.View;

import java.util.HashMap;
import java.util.List;

import static application.core.RowContent.*;
import static application.core.RowType.*;
import static ui.console.ConsoleControllerType.*;

public class ConsoleView extends View {
    private HashMap<ConsoleControllerType, ConsoleController> controllers;
    private boolean active = true;

    public ConsoleView(Model model) {
        super(model);
        run();
    }

    private void run() {
        update();
        ApplicationControllerAccess applicationControllerAccess = (ApplicationControllerAccess) this.model;
        applicationControllerAccess.addRow(DTH_OF_CFM, BL);
        applicationControllerAccess.addRow(DTH_OF_CFM, IT);
        applicationControllerAccess.addRow(DTH_OF_CFM, US);
        applicationControllerAccess.addRow(DTH_OF_CFM, TC);
        applicationControllerAccess.addRow(DTH_OF_CFM, BZ);
        applicationControllerAccess.addRow(DTH_OF_CFM, FR);
        applicationControllerAccess.addRow(DTH_OF_CFM, FN);
        applicationControllerAccess.addRow(DTH_OF_CFM, SP);
        applicationControllerAccess.addRow(DTH_OF_CFM, UK);
        applicationControllerAccess.addRow(DTH_OF_CFM, SW);
        applicationControllerAccess.addRow(DTH_OF_CFM, SZ);
        applicationControllerAccess.addRow(DTH_OF_CFM, DE);
        applicationControllerAccess.addRow(DTH_OF_CFM, OE);

        applicationControllerAccess.setCursor(12);
        addCountryStatsPack(SZ);
        addCountryStatsPack(DE);
        addCountryStatsPack(SW);
        addCountryStatsPack(SP);
        addCountryStatsPack(TC);
        addCountryStatsPack(UK);
        addCountryStatsPack(US);
        addCountryStatsPack(SK);
        addCountryStatsPack(BZ);
        addCountryStatsPack(IT);
        addCountryStatsPack(NL);
        addCountryStatsPack(FR);
        addCountryStatsPack(BL);
        addCountryStatsPack(OE);
        addCountryStatsPack(FN);

        applicationControllerAccess.addRow(DTH_OF_POP, SZ);
        applicationControllerAccess.addRow(DTH_OF_POP, DE);
        applicationControllerAccess.addRow(DTH_OF_POP, SW);
        applicationControllerAccess.addRow(DTH_OF_POP, SP);
        applicationControllerAccess.addRow(DTH_OF_POP, TC);
        applicationControllerAccess.addRow(DTH_OF_POP, UK);
        applicationControllerAccess.addRow(DTH_OF_POP, US);
        applicationControllerAccess.addRow(DTH_OF_POP, SK);
        applicationControllerAccess.addRow(DTH_OF_POP, BZ);
        applicationControllerAccess.addRow(DTH_OF_POP, IT);
        applicationControllerAccess.addRow(DTH_OF_POP, NL);
        applicationControllerAccess.addRow(DTH_OF_POP, FR);
        applicationControllerAccess.addRow(DTH_OF_POP, BL);
        applicationControllerAccess.addRow(DTH_OF_POP, OE);
        applicationControllerAccess.addRow(DTH_OF_POP, FN);

        applicationControllerAccess.addRow(R, SZ);
        applicationControllerAccess.addRow(R, DE);
        applicationControllerAccess.addRow(R, SW);
        applicationControllerAccess.addRow(R, SP);
        applicationControllerAccess.addRow(R, TC);
        applicationControllerAccess.addRow(R, UK);
        applicationControllerAccess.addRow(R, US);
        applicationControllerAccess.addRow(R, SK);
        applicationControllerAccess.addRow(R, BZ);
        applicationControllerAccess.addRow(R, IT);
        applicationControllerAccess.addRow(R, NL);
        applicationControllerAccess.addRow(R, FR);
        applicationControllerAccess.addRow(R, BL);
        applicationControllerAccess.addRow(R, OE);
        applicationControllerAccess.addRow(R, FN);
        applicationControllerAccess.export();
        while (active) {
            ConsoleControllerType selection = (ConsoleControllerType) IO.getEnumByInput("Choose Controller", ConsoleControllerType.values());
            controllers.get(selection).execute();
        }
    }

    private void addCountryStatsPack(RowContent country) {
        addCountryPercentageRow(ACT, country);
        addCountryPercentageRow(CFM, country);
        addCountryPercentageRow(RCV, country);
    }

    private void addCountryPercentageRow(RowType act, RowContent country) {
        ((ApplicationControllerAccess) model).addRow(act, country);
        scaleForCountry(1.0 / country.getPopulation());
    }

    private void scaleForCountry(double faktor) {
        Integer nextCursor = ((ApplicationViewAccess) model).getCursor() + 1;
        ((ApplicationControllerAccess) model).setCursor(nextCursor);
        ((ApplicationControllerAccess) model).scaleCursor(faktor);
    }

    @Override
    protected void initController() {
        ConsoleControllerFactory controllerFactory = new ConsoleControllerFactory();
        controllers = new HashMap<>();
        controllers.put(EXIT, initExitController());
        controllers.put(ADD, controllerFactory.initAddController(model));
        controllers.put(SELC, controllerFactory.initSelectController(model));
        controllers.put(SYNC, controllerFactory.initSyncController(model));
        controllers.put(SHFT, controllerFactory.initShiftController(model));
        controllers.put(CUTS, controllerFactory.initCutStartController(model));
        controllers.put(CUTE, controllerFactory.initCutEndController(model));
        controllers.put(REMV, controllerFactory.initRemoveController(model));
        controllers.put(RSET, controllerFactory.initResetController(model));
        controllers.put(SCAL, controllerFactory.initScaleController(model));
        controllers.put(SCLA, controllerFactory.initScaleLastController(model));
        controllers.put(EXP, controllerFactory.initExportController(model));
    }


    private ConsoleController initExitController() {
        return new ConsoleController(model) {
            @Override
            public void execute() {
                active = false;
                System.out.println("Closing View...");
            }
        };
    }

    @Override
    public void update() {
        ApplicationViewAccess model = getModel();
        Integer cursor = model.getCursor();
        System.out.println();
        System.out.println();
        System.out.println("Selected cursor [" + cursor + "]");
        List<Row> rows = model.getAdjustedRows();
        if (rows != null)
            for (Row row : rows) {
                System.out.print(row.getRowContent() + "(" + row.getRowType() + ")");
                for (Double point : row.getPoints()) {
                    System.out.print(point + ",");
                }
                System.out.println();
            }
        else
            System.out.println("[No Rows]");
    }

    private ApplicationViewAccess getModel() {
        return (ApplicationViewAccess) model;
    }
}
