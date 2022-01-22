package pl.dmcs.PlotDraw;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class CSVReaderTest {

    private CSVReader csvReader = new CSVReader();
    private static final String TEST_RESOURCES_PATH = "target/test-classes/";

    @Test
    public void readCSVWithoutHeadersTest() {
        List<double[]> expectedResult = getTestList(new double[][] {{100, 100}, {200, 200}, {300, 300}, {400, 400}, {500, 500}});
        testRead(expectedResult, null, "file1.csv");

        expectedResult = getTestList(new double[][] {{1, 1.5}, {1.5, 2}, {3, 3.99}});
        testRead(expectedResult, null, "file2.csv");
    }

    @Test
    public void readEmptyCSVTest() {
        List<double[]> expectedResult = getTestList(new double[][] {});
        testRead(expectedResult, null, "file3.csv");
    }

    @Test
    public void readCSVWithHeaderTest() {
        List<double[]> expectedResult = getTestList(new double[][] {{1, 10}, {2, 20}});
        testRead(expectedResult, new String[] {"Speed", "Time"}, "file4.csv");

        expectedResult = getTestList(new double[][] {{1, 2}, {3, 4}, {5, 6}, {7, 8}});
        testRead(expectedResult, new String[] {"Force", "Acceleration"}, "file5.csv");
    }

    private List<double[]> getTestList(double[][] array) {
        List<double[]> result = new ArrayList<>();
        for (double[] ds : array) {
            result.add(ds);
        }
        return result;
    }

    private void testRead(List<double[]> expected, String[] expectedHeaders, String filePath) {
        List<double[]> actual = csvReader.read(TEST_RESOURCES_PATH + filePath);
        for (int i = 0; i < expected.size(); i++) {
            assertArrayEquals(expected.get(i), actual.get(i));
        }
        assertArrayEquals(expectedHeaders, csvReader.getHeaders());
    }
}
