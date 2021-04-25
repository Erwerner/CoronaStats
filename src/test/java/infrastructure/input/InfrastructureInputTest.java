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
    public void testNew() {
        when(mockApi.getPointsFromApi(DE, CFM)).thenReturn(
                new Double[]{
                        1500.0, 1400.0, 1000.0, 800.0
                }
        );
        Double[] act = cut.readPoints(NEW, DE);
        assertEquals(3, act.length);
        assertEquals(new Double(100.0), act[0]);
        assertEquals(new Double(400.0), act[1]);
        assertEquals(new Double(200.0), act[2]);
    }

    @Test
    public void getDeathOf10PopulationMid20FromAPI() {
        when(mockApi.getPointsFromApi(DE, DTH)).thenReturn(
                new Double[]{
                        DE.getPopulation() * 1111.0,
                        DE.getPopulation() * 111.0,
                        DE.getPopulation() * 11.0,
                        DE.getPopulation() * 1.0
                }
        );
        Double[] act = cut.getDeathOf10PopulationMid20FromAPI(DE, 2);
        assertEquals(2, act.length);
        assertEquals(new Double(1100.0), act[0]);
    }
}
