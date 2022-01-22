package pl.dmcs.PlotDraw;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class RangeCalculatorTest {

    @Test
    void testCalculatingRanges() {
        List<List<double[]>> allPoints = new ArrayList<>();
        allPoints.add(Arrays.asList(new double[][] {{-50, 100}, {10, 20}, {30, 12}, {40, 99}, {50, 200}}));
        allPoints.add(Arrays.asList(new double[][] {{-91, 100}, {-50, 5}, {-60, 11}}));

        testCalculateRanges(allPoints, null, 100, null, null, -100, 100, 0, 200);

        allPoints = new ArrayList<>();
        allPoints.add(Arrays.asList(new double[][] {{0.326, 5.5}, {14.5, 10.5}, {25.678, 15.5}, {49, 16.5}, {58.821, 17.5}}));

        testCalculateRanges(allPoints, null, null, null, null, 0, 60, 0, 20);

        allPoints = new ArrayList<>();
        allPoints.add(Arrays.asList(new double[][] {{0, 10}, {10, 20}, {20, 30.9}, {-10, 15}}));

        testCalculateRanges(allPoints, 0, null, 0, null, 0, 20, 0, 40);
    }

    private void testCalculateRanges(List<List<double[]>> allPoints, Integer xMin, Integer xMax, Integer yMin, Integer yMax,
            Integer expectedMinX, Integer expectedMaxX, Integer expectedMinY, Integer expectedMaxY) {
        Map<String, Integer> expected = new HashMap<>();
        expected.put("xMin", expectedMinX);
        expected.put("xMax", expectedMaxX);
        expected.put("yMin", expectedMinY);
        expected.put("yMax", expectedMaxY);

        Map<String, Integer> actual = RangeCalculator.calculateRanges(allPoints, xMin, xMax, yMin, yMax);
        assertTrue(expected.equals(actual));
    }
}
