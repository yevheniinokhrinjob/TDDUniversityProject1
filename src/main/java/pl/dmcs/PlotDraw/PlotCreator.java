package pl.dmcs.PlotDraw;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;

public class PlotCreator {

    private Parameters parameters;
    private PlotUtils plotUtils;
    private CSVReader csvReader;
    private SVGWriter svgWriter;

    public PlotCreator(Parameters parameters, PlotUtils plotUtils) {
        this.parameters = parameters;
        this.plotUtils = plotUtils;
        this.csvReader = new CSVReader();
        this.svgWriter = new SVGWriter();
    }

    public void draw() throws IOException {
        String header = plotUtils.getHeader();
        String plotStyle = plotUtils.getStyle();
        String footer = plotUtils.getFooter();
        String content = header + plotStyle + plotUtils.getTitle(parameters.title);
        content += plotUtils.getAxis("grid x-grid", "xGrid", 90, 90, 35, 401);
        content += plotUtils.getAxis("grid y-grid", "yGrid", 90, 705, 400, 400);

        boolean drawLine = false;
        if (parameters.style.equals("line")) {
            drawLine = true;
        }

        List<String> colors = Arrays.asList("red", "blue", "yellow", "green", "orange", "black", "brown", "purple");

        String[] dataArray = parameters.data.split(",");
        String legend = "   <g>\n";

        List<List<double[]>> allPoints = new ArrayList<>();
        for (int i = 0; i < dataArray.length; i++) {
            allPoints.add(csvReader.read(dataArray[i]));

            String[] dataFileHeader = csvReader.getHeaders();
            if (dataFileHeader != null) {
                parameters.xLabel = dataFileHeader[0];
                parameters.yLabel = dataFileHeader[1];
            }
        }

        Map<String, Integer> ranges = RangeCalculator.calculateRanges(allPoints, parameters.xMin, parameters.xMax, parameters.yMin,
                parameters.yMax);
        parameters.xMin = ranges.get("xMin");
        parameters.xMax = ranges.get("xMax");
        parameters.yMin = ranges.get("yMin");
        parameters.yMax = ranges.get("yMax");

        for (int i = 0; i < allPoints.size(); i++) {
            String color = colors.get(i % 8);

            List<double[]> points = allPoints.get(i);

            content += plotUtils.getPoints(points, color, parameters.xMin, parameters.xMax, parameters.yMin, parameters.yMax, drawLine);
            legend += plotUtils.getLegendItem(FilenameUtils.getBaseName(dataArray[i]), color, i);
        }
        legend += "   </g>\n";
        content += legend;

        content += plotUtils.getLabels("x", parameters.xLabel, parameters.xMin, parameters.xMax);
        content += plotUtils.getLabels("y", parameters.yLabel, parameters.yMin, parameters.yMax);

        content += footer;

        svgWriter.write(parameters.chart, content);
    }
}
