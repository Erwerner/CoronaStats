package application.service;

import application.core.ApplicationData;
import application.core.Row;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class ApplicationServiceTest {
    ApplicationService cut;
    private final ApplicationInput mockInput = mock(ApplicationInput.class);

    @Before
    public void setup() {
        cut = new ApplicationService(mockInput);
    }

    @Test
    public void when_sync_is_called_for_two_identical_rows_then_data_is_not_adjusted() {
        ApplicationData data = new ApplicationData();
        ArrayList<Double> points1 = new ArrayList<>();
        ArrayList<Double> points2 = new ArrayList<>();

        points1.add(0.1);
        points2.add(0.1);
        points1.add(0.3);
        points2.add(0.3);
        points1.add(0.2);
        points2.add(0.2);

        data.addRow(new Row(null, null, points1));
        data.addRow(new Row(null, null, points2));

        cut.syncRows(data);

        assertEquals(0, data.getCutEnd());
        assertEquals(0, data.getCutStart());
        for (int i = 0; i <= data.getRowCount() - 1; i++) {
            assertEquals(new Integer(0), data.getShift(i));
            assertEquals(new Double(1.0), data.getScale(i));
        }
    }

    @Test
    public void when_sync_is_called_for_different_rows_then_shift_is_adjusted() {
        ApplicationData data = new ApplicationData();
        ArrayList<Double> points1 = new ArrayList<>();
        ArrayList<Double> points2 = new ArrayList<>();
        ArrayList<Double> points3 = new ArrayList<>();

        points3.add(0.0);
        points1.add(0.1);
        points3.add(0.1);
        points1.add(0.3);
        points2.add(0.3);
        points3.add(0.3);
        points1.add(0.2);
        points2.add(0.2);
        points3.add(0.2);

        data.addRow(new Row(null, null, points1));
        data.addRow(new Row(null, null, points2));
        data.addRow(new Row(null, null, points3));

        cut.syncRows(data);

        assertEquals(new Integer(1), data.getShift(1));
        assertEquals(new Integer(-1), data.getShift(2));
    }

    @Test
    public void when_sync_is_called_for_different_rows_then_scale_is_adjusted() {
        ApplicationData data = new ApplicationData();
        ArrayList<Double> points1 = new ArrayList<>();
        ArrayList<Double> points2 = new ArrayList<>();
        ArrayList<Double> points3 = new ArrayList<>();

        points1.add(0.1);
        points2.add(0.0);
        points3.add(0.1);

        points1.add(3.0);
        points2.add(0.3);
        points3.add(1.5);

        points1.add(0.2);
        points2.add(0.2);
        points3.add(0.2);

        data.addRow(new Row(null, null, points1));
        data.addRow(new Row(null, null, points2));
        data.addRow(new Row(null, null, points3));

        cut.syncRows(data);

        assertEquals(new Double(10), data.getScale(1));
        assertEquals(new Double(2), data.getScale(2));
    }

    @Test
    public void when_adjust_is_called_for_row_with_scale_then_result_is_adjusted() {
        ApplicationData data = new ApplicationData();
        ArrayList<Double> points1 = new ArrayList<>();

        points1.add(0.1);
        points1.add(0.5);

        data.addRow(new Row(null, null, points1));
        data.setScale(0, 2.0);

        List<Row> act = cut.calcAdjustedRows(data);

        assertEquals(new Double(0.2), act.get(0).getPoints().get(0));
        assertEquals(new Double(1.0), act.get(0).getPoints().get(1));
    }
}
