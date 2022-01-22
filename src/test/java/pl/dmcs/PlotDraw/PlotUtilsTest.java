package pl.dmcs.PlotDraw;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class PlotUtilsTest {

    private PlotUtils plotUtils = new PlotUtils();

    @Test
    void testBasicUtils() {
        testGetTitle("title1");
        testGetTitle("Exemplary title");
        testGetTitle("Title 3");

        testGetAxis("grid x-grid", "xGrid", 100, 100, 50, 500);
        testGetAxis("grid y-grid", "yGrid", 20, 1000, 300, 300);
        testGetAxis("grid y-grid", "y", 0, 200, 150, 150);

        testGetLabelsX("xLabel", 0, 100);
        testGetLabelsX("x-label", -200, 200);
        testGetLabelsX("Label_X", 200, 300);

        testGetLabelsY("yLabel", -50, 50);
        testGetLabelsY("y-label", -1000, -200);
        testGetLabelsY("Label_Y", -900, 1000);
    }

    @Test
    void testAddingPoints() {

        testGetPoints(Arrays.asList(new double[][] {{100, 100}, {200, 200}, {300, 300}, {400, 400}, {500, 500}}), "red", 0, 500, -50, 500,
                false,
                "    <g class=\"data\" data-setname=\"Our first data set\" style=\"fill: red;\">\n"
                       + "        <circle cx=\"209.0\" cy=\"303.0\" r=\"4\" />\n"
                       + "        <circle cx=\"328.0\" cy=\"238.0\" r=\"4\" />\n"
                       + "        <circle cx=\"446.0\" cy=\"173.0\" r=\"4\" />\n"
                       + "        <circle cx=\"565.0\" cy=\"109.0\" r=\"4\" />\n"
                       + "        <circle cx=\"684.0\" cy=\"44.0\" r=\"4\" />\n"
                       + "   </g>\n");

        testGetPoints(Arrays.asList(
                new double[][] {{2, 3}, {5, 5}}), "blue", 1, 10, -1, 11, false,
                "    <g class=\"data\" data-setname=\"Our first data set\" style=\"fill: blue;\">\n"
                                                                                + "        <circle cx=\"156.0\" cy=\"281.0\" r=\"4\" />\n"
                                                                                + "        <circle cx=\"354.0\" cy=\"222.0\" r=\"4\" />\n"
                                                                                + "   </g>\n");

        testGetPoints(
                Arrays.asList(new double[][] {{100, 0},
                        {0, 500}}),
                "green", 0, 600, 0, 600, false,
                "    <g class=\"data\" data-setname=\"Our first data set\" style=\"fill: green;\">\n"
                                                + "        <circle cx=\"189.0\" cy=\"400.0\" r=\"4\" />\n"
                                                + "        <circle cx=\"90.0\" cy=\"103.0\" r=\"4\" />\n"
                                                + "   </g>\n");

        testGetPoints(Arrays.asList(new double[][] {{100, 150},
                {200, 200}}), "red", 0, 200, 0, 200, true,
                "    <g class=\"data\" data-setname=\"Our first data set\" style=\"fill: red;\">\n"
                                                           + "        <circle cx=\"387.0\" cy=\"133.0\" r=\"4\" />\n"
                                                           + "        <circle cx=\"684.0\" cy=\"44.0\" r=\"4\" />\n"
                                                           + "   </g>\n"
                                                           + "    <g>\n"
                                                           + "        <line x1=\"387\" y1=\"133\" x2=\"684\" y2=\"44\" stroke=\"red\" />\n"
                                                           + "   </g>\n");

    }

    @Test
    void testAddingLegendItems() {
        testGetLegendItem("plot1", "red", 0,
                "      <circle cx=\"800\" cy=\"100\" data-value=\"6.7\" r=\"4\" style=\"fill: red;\"></circle>\n"
                                             + "      <text x=\"815\" y=\"105\">plot1</text>\n");
        testGetLegendItem("plot2", "blue", 1,
                "      <circle cx=\"800\" cy=\"120\" data-value=\"6.7\" r=\"4\" style=\"fill: blue;\"></circle>\n"
                                              + "      <text x=\"815\" y=\"125\">plot2</text>\n");

        testGetLegendItem("plot3", "yellow", 2,
                "      <circle cx=\"800\" cy=\"140\" data-value=\"6.7\" r=\"4\" style=\"fill: yellow;\"></circle>\n"
                                                + "      <text x=\"815\" y=\"145\">plot3</text>\n");
    }

    private void testGetTitle(String title) {
        String expected = "    <g class=\"labels x-labels\">\r\n"
                          + "        <text x=\"400\" y=\"20\" class=\"label-title\">"
                          + title
                          + "</text>\r\n"
                          + "    </g>\n";
        assertEquals(expected, plotUtils.getTitle(title));
    }

    private void testGetAxis(String styleClass, String id, int x1, int x2, int y1, int y2) {
        String expected = "    <g class=\""
                          + styleClass
                          + "\" id=\""
                          + id
                          + "\">\r\n"
                          + "      <line x1=\""
                          + x1
                          + "\" x2=\""
                          + x2
                          + "\" y1=\""
                          + y1
                          + "\" y2=\""
                          + y2
                          + "\"></line>\r\n"
                          + "    </g>\n";
        assertEquals(expected, plotUtils.getAxis(styleClass, id, x1, x2, y1, y2));
    }

    private void testGetLabelsX(String label, int min, int max) {
        String expected = "     <g class=\"labels x-labels\">\r\n"
                          + "      <text x=\"100\" y=\"430\">"
                          + min
                          + "</text>\r\n"
                          + "      <text x=\"684\" y=\"430\">"
                          + max
                          + "</text>\r\n"
                          + "      <text x=\"400\" y=\"470\" class=\"label-title\">"
                          + label
                          + "</text>\r\n"
                          + "    </g>\n";
        assertEquals(expected, plotUtils.getLabels("x", label, min, max));
    }

    private void testGetLabelsY(String label, int min, int max) {
        String expected = "    <g class=\"labels y-labels\">\r\n"
                          + "      <text x=\"80\" y=\"45\">"
                          + max
                          + "</text>\r\n"
                          + "      <text x=\"80\" y=\"403\">"
                          + min
                          + "</text>\r\n"
                          + "      <text x=\"50\" y=\"230\" class=\"label-title\" style=\"writing-mode: sideways-lr;\">"
                          + label
                          + "</text>\r\n"
                          + "    </g>\n";
        assertEquals(expected, plotUtils.getLabels("y", label, min, max));
    }

    private void testGetPoints(List<double[]> points, String color, int xMin, int xMax, int yMin, int yMax, boolean drawLine,
            String expected) {
        assertEquals(expected, plotUtils.getPoints(points, color, xMin, xMax, yMin, yMax, drawLine));
    }

    private void testGetLegendItem(String name, String color, int index, String expected) {
        assertEquals(expected, plotUtils.getLegendItem(name, color, index));
    }

}
