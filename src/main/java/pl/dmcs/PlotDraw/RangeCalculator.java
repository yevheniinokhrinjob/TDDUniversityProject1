package pl.dmcs.PlotDraw;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RangeCalculator {

    public static Map<String, Integer> calculateRanges(List<List<double[]>> allPoints, Integer xMin, Integer xMax, Integer yMin,
            Integer yMax) {
        int dataMinX = Integer.MAX_VALUE;
        int dataMaxX = Integer.MIN_VALUE;
        int dataMinY = Integer.MAX_VALUE;
        int dataMaxY = Integer.MIN_VALUE;
        for (int i = 0; i < allPoints.size(); i++) {
            List<double[]> points = allPoints.get(i);
            for (double[] point : points) {
                if (point[0] < dataMinX) {
                    dataMinX = (int) Math.floor(point[0]);
                } else if (point[0] > dataMaxX) {
                    dataMaxX = (int) Math.ceil(point[0]);
                }
                if (point[1] < dataMinY) {
                    dataMinY = (int) Math.floor(point[1]);
                } else if (point[1] > dataMaxY) {
                    dataMaxY = (int) Math.ceil(point[1]);
                }
            }
        }

        if (xMin == null) {
            xMin = (int) Math.floor(dataMinX / 10.0) * 10;
        }
        if (xMax == null) {
            xMax = (int) Math.ceil(dataMaxX / 10.0) * 10;
        }
        if (yMin == null) {
            yMin = (int) Math.floor(dataMinY / 10.0) * 10;
        }
        if (yMax == null) {
            yMax = (int) Math.ceil(dataMaxY / 10.0) * 10;
        }
        Map<String, Integer> ranges = new HashMap<>();
        ranges.put("xMin", xMin);
        ranges.put("xMax", xMax);
        ranges.put("yMin", yMin);
        ranges.put("yMax", yMax);
        return ranges;
    }
}
