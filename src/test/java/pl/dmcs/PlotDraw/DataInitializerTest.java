package pl.dmcs.PlotDraw;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class DataInitializerTest {

    private DataInitializer dataInitializer = new DataInitializer();

    @Test
    void testGetCLIParameters() throws Exception {
        Parameters expectedParameters = new Parameters("target/test-classes/file1.csv", "chart1", "ExChart", "xAxis", "yAxis", 0, 500, -50,
                null, "dots");
        testDataInitializer(new String[] {"-data", "target/test-classes/file1.csv", "-chart", "chart1", "-title", "ExChart", "-xlabel",
                "xAxis", "-ylabel", "yAxis", "-xmin", "0", "-xmax", "500", "-ymin", "-50"}, expectedParameters);

        expectedParameters = new Parameters("target/test-classes/file2.csv", "Chart", "Title", "x", "y", 0, 1000, 0, 1000, "line");
        testDataInitializer(new String[] {"-data", "target/test-classes/file2.csv", "-chart", "Chart", "-title", "Title", "-xlabel", "x",
                "-ylabel", "y", "-xmin", "0", "-xmax", "1000", "-ymin", "0", "-ymax", "1000", "-style", "line"}, expectedParameters);

        expectedParameters = new Parameters("target/test-classes/file3.csv", "velocity_chart", "V(t)", "time", "speed", null, null, null,
                null, "dots");
        testDataInitializer(new String[] {"-data", "target/test-classes/file3.csv", "-chart", "velocity_chart", "-title", "V(t)", "-xlabel",
                "time", "-ylabel", "speed"}, expectedParameters);
    }

    @Test
    void testMissedParametersException() throws Exception {
        testDataInitializerException(new String[] {"-data", "target/test-classes/file1.csv", "-chart", "chart1", "-xlabel", "xAxis",
                "-ylabel", "yAxis", "-xmin", "0", "-xmax", "500", "-ymin", "-50"}, "Missing required option: title");

        testDataInitializerException(new String[] {"-data", "target/test-classes/file2.csv", "-xlabel", "x_labe;", "-ylabel", "y_label",
                "-xmin", "-200", "-xmax", "1000", "-ymin", "-500", "-ymax", "400", "-style", "line"},
                "Missing required options: chart, title");

        testDataInitializerException(new String[] {""}, "Missing required options: data, chart, title, xlabel, ylabel");
    }

    @Test
    void testFormatException() throws Exception {
        testDataInitializerException(new String[] {"-data", "target/test-classes/file1.csv", "-chart", "chart1", "-title", "ExChart",
                "-xlabel", "xAxis", "-ylabel", "yAxis", "-xmin", "0", "-xmax", "500", "-ymin", "-50", "-ymax", "aa"},
                "For input string: \"aa\"");

        testDataInitializerException(new String[] {"-data", "target/test-classes/file2.csv", "-chart", "chart2", "-title", "ExChart2",
                "-xlabel", "x-label", "-ylabel", "y-label", "-xmin", "53e", "-xmax", "500", "-ymin", "-50", "-ymax", "aa"},
                "For input string: \"53e\"");

        testDataInitializerException(new String[] {"-data", "target/test-classes/file3.csv", "-chart", "chart3", "-title", "Chart3",
                "-xlabel", "xlabel", "-ylabel", "ylabel", "-xmin", "?", "-xmax", "?", "-ymin", "-100", "-ymax", "100"},
                "For input string: \"?\"");

    }

    @Test
    void testStyleFormatException() throws Exception {
        testDataInitializerException(
                new String[] {"-data", "target/test-classes/file1.csv", "-chart", "chart1", "-title", "ExChart", "-xlabel", "xAxis",
                        "-ylabel", "yAxis", "-xmin", "0", "-xmax", "500", "-ymin", "-50", "-ymax", "100", "-style", "barchart"},
                "Wrong format of style parameter");
    }

    private void testDataInitializer(String[] s, Parameters expected) throws Exception {
        Parameters actual = dataInitializer.getCLIParameters(s);
        assertEquals(actual.data, expected.data);
        assertEquals(actual.chart, expected.chart);
        assertEquals(actual.title, expected.title);
        assertEquals(actual.xLabel, expected.xLabel);
        assertEquals(actual.yLabel, expected.yLabel);
        assertEquals(actual.xMin, expected.xMin);
        assertEquals(actual.xMax, expected.xMax);
        assertEquals(actual.yMin, expected.yMin);
        assertEquals(actual.yMax, expected.yMax);
        assertEquals(actual.style, expected.style);
    }

    private void testDataInitializerException(String[] s, String message) {
        Exception exception = assertThrows(Exception.class, () -> dataInitializer.getCLIParameters(s));
        assertEquals(message, exception.getMessage());
    }
}
