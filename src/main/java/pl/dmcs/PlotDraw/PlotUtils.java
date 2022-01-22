package pl.dmcs.PlotDraw;

import java.util.List;

public class PlotUtils {

    public String getHeader() {
        return "<svg version=\"1.2\" xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" class=\"graph\" aria-labelledby=\"title\" role=\"img\">\r\n";
    }

    public String getStyle() {
        return "    <style>\r\n"
               + "        body {\r\n"
               + "          font-family: 'Open Sans', sans-serif;\r\n"
               + "        }\r\n"
               + "\r\n"
               + "        .graph .labels.x-labels {\r\n"
               + "          text-anchor: middle;\r\n"
               + "        }\r\n"
               + "\r\n"
               + "        .graph .labels.y-labels {\r\n"
               + "          text-anchor: end;\r\n"
               + "        }\r\n"
               + "\r\n"
               + "\r\n"
               + "        .graph {\r\n"
               + "          height: 500px;\r\n"
               + "          width: 1000px;\r\n"
               + "        }\r\n"
               + "\r\n"
               + "        .graph .grid {\r\n"
               + "          stroke: #ccc;\r\n"
               + "          stroke-dasharray: 0;\r\n"
               + "          stroke-width: 1;\r\n"
               + "        }\r\n"
               + "\r\n"
               + "        .labels {\r\n"
               + "          font-size: 13px;\r\n"
               + "        }\r\n"
               + "\r\n"
               + "        .label-title {\r\n"
               + "          font-weight: bold;\r\n"
               + "          text-transform: uppercase;\r\n"
               + "          font-size: 12px;\r\n"
               + "          fill: black;\r\n"
               + "        }\r\n"
               + "\r\n"
               + "        .data {\r\n"
               + "          stroke-width: 1; \r\n"
               + "        }\r\n"
               + "    </style>\n";
    }

    public String getTitle(String title) {
        return "    <g class=\"labels x-labels\">\r\n"
               + "        <text x=\"400\" y=\"20\" class=\"label-title\">"
               + title
               + "</text>\r\n"
               + "    </g>\n";
    }

    public String getFooter() {
        return "</svg>";
    }

    public String getAxis(String styleClass, String id, int x1, int x2, int y1, int y2) {
        return "    <g class=\""
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
    }

    public String getLabels(String axis, String label, int min, int max) {
        if (axis == "x") {
            return "     <g class=\"labels x-labels\">\r\n"
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
        } else {
            return "    <g class=\"labels y-labels\">\r\n"
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
        }
    }

    private String getPoint(double x, double y) {
        return "<circle cx=\"" + x + "\" cy=\"" + y + "\" r=\"4\" />\n";
    }

    public String getPoints(List<double[]> points, String color, int xMin, int xMax, int yMin, int yMax, boolean drawLine) {
        final double originX = 90;
        final double originY = 400;
        final double lengthY = 356;
        final double lengthX = 594;
        double stepX = lengthX / (xMax - xMin);
        double stepY = lengthY / (yMax - yMin);
        String pointsString = "    <g class=\"data\" data-setname=\"Our first data set\" style=\"fill: " + color + ";\">\n";
        String lineString = "    <g>\n";

        double lastX = -1, lastY = -1;
        if (points.size() > 0) {
            lastX = originX + (points.get(0)[0] - xMin) * stepX;
            lastY = originY - (points.get(0)[1] - yMin) * stepY;
            pointsString += "        " + getPoint(Math.round(lastX), Math.round(lastY));
        }
        for (int i = 1; i < points.size(); i++) {
            double x = originX + (points.get(i)[0] - xMin) * stepX;
            double y = originY - (points.get(i)[1] - yMin) * stepY;
            pointsString += "        " + getPoint(Math.round(x), Math.round(y));

            if (drawLine) {
                lineString += "        <line x1=\""
                              + Math.round(lastX)
                              + "\" y1=\""
                              + Math.round(lastY)
                              + "\" x2=\""
                              + Math.round(x)
                              + "\" y2=\""
                              + Math.round(y)
                              + "\" stroke=\""
                              + color
                              + "\" />\n";
            }
            lastX = x;
            lastY = y;
        }

        pointsString += "   </g>\n";
        if (drawLine) {
            lineString += "   </g>\n";
            pointsString += lineString;
        }

        return pointsString;
    }

    public String getLegendItem(String name, String color, int index) {
        final int gapBetweenItems = 20;
        final int startingX = 800;
        final int startingY = 100;
        final int textOffsetX = 15;
        final int textOffsetY = 5;
        int y = startingY + index * gapBetweenItems;
        return "      <circle cx=\""
               + startingX
               + "\" cy=\""
               + y
               + "\" data-value=\"6.7\" r=\"4\" style=\"fill: "
               + color
               + ";\"></circle>\n"
               + "      <text x=\""
               + (startingX + textOffsetX)
               + "\" y=\""
               + (y + textOffsetY)
               + "\">"
               + name
               + "</text>\n";
    }
}
