package pl.dmcs.PlotDraw;

import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class PlotCreatorTest {

    private PlotUtils mockedPlotUtils = mock(PlotUtils.class);

    @Test
    void runDrawTestAllArgumentsLine() throws IOException {
        testDraw("target/test-classes/file1.csv", "target/test-classes/test_chart1", "ExChart", "xAxis", "yAxis", 0, 500, -50, 500, "line");
    }

    @Test
    void runDrawTestAllArgumentsDots() throws IOException {
        testDraw("target/test-classes/plot1.csv,target/test-classes/plot2.csv,target/test-classes/plot3.csv",
                "target/test-classes/test_chart2", "ExChart2", "x", "y", 0, 500, -50, 500, "dots");
    }

    @Test
    void runDrawTestYMinYMaxMissing() throws IOException {
        testDraw("target/test-classes/plot4.csv", "target/test-classes/test_chart3", "ExChart3", "x axis", "y axis", 0, 500, null, null,
                "line");
    }

    @Test
    void runDrawTestRangesMissing() throws IOException {
        testDraw("target/test-classes/plot4.csv", "target/test-classes/test_chart4", "ExChart4", "X", "Y", null, null, null, null, "dots");
    }

    private void testDraw(String data, String chart, String title, String xLabel, String yLabel, Integer xMin, Integer xMax, Integer yMin,
            Integer yMax, String style) throws IOException {

        Parameters parameters = new Parameters(data, chart, title, xLabel, yLabel, xMin, xMax, yMin, yMax, style);
        PlotCreator plotCreator = new PlotCreator(parameters, mockedPlotUtils);
        plotCreator.draw();

        int numberOfPlots = data.split(",").length;

        verify(mockedPlotUtils, times(1)).getHeader();
        verify(mockedPlotUtils, times(1)).getStyle();
        verify(mockedPlotUtils, times(1)).getFooter();
        verify(mockedPlotUtils, times(1)).getTitle(anyString());
        verify(mockedPlotUtils, times(2)).getAxis(anyString(), anyString(), anyInt(), anyInt(), anyInt(), anyInt());
        verify(mockedPlotUtils, times(2)).getLabels(anyString(), anyString(), anyInt(), anyInt());
        verify(mockedPlotUtils, times(numberOfPlots)).getPoints(Mockito.<double[]> anyList(), anyString(), anyInt(), anyInt(), anyInt(),
                anyInt(), anyBoolean());
        verify(mockedPlotUtils, times(numberOfPlots)).getLegendItem(anyString(), anyString(), anyInt());

    }
}
