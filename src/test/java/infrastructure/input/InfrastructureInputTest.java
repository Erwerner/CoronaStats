package infrastructure.input;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static application.core.RowContent.DE;
import static application.core.RowType.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class InfrastructureInputTest {
    private InfrastructureInput cut;
    @Mock
    private API mockApi;

    @Before
    public void setUp() {
        mockApi = mock(API.class);
        cut = new InfrastructureInput(mockApi);
    }

    @Test
    public void testNew(){
        when(mockApi.getPointsFromApi(DE, CFM)).thenReturn(
                new Double[]{
                        1500.0,1400.0,1000.0,800.0
                }
        );
        Double[] act = cut.readPoints(NEW, DE);
        assertEquals(3,act.length);
        assertEquals(new Double(100.0),act[0]);
        assertEquals(new Double(400.0),act[1]);
        assertEquals(new Double(200.0),act[2]);
    }
//    @Test
//    public void whenRIsCalculatedWithNoIncreaseThenResultsIsOne() {
//        when(mockApi.getPointsFromApi(DE, CFM)).thenReturn(
//                new Double[]{
//                        9000.0,8000.0,7000.0,6000.0,
//                        5000.0,4000.0,3000.0,2000.0,1000.0
//                }
//        );
//        Double[] act = cut.readPoints(R, DE);
//        assertEquals(1, act.length);
//        assertEquals(new Double(1.0), act[0]);
//    }
//    @Test
//    public void whenRIsCalculatedWithDoubleIncreaseThenResultsIsTwo() {
//        when(mockApi.getPointsFromApi(DE, CFM)).thenReturn(
//                new Double[]{
//                        13.0,11.0,9.0,7.0,
//                        5.0,4.0,3.0,2.0,1.0
//                }
//        );
//        Double[] act = cut.readPoints(R, DE);
//        assertEquals(1, act.length);
//        assertEquals(new Double(2.0), act[0]);
//    }
}
