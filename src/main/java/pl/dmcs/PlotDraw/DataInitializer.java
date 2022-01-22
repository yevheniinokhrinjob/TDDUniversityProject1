package pl.dmcs.PlotDraw;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

class Parameters {

    Parameters() {}

    Parameters(String data, String chart, String title, String xLabel, String yLabel, Integer xMin, Integer xMax, Integer yMin,
            Integer yMax, String style) {
        this.data = data;
        this.chart = chart;
        this.title = title;
        this.xLabel = xLabel;
        this.yLabel = yLabel;
        this.xMin = xMin;
        this.xMax = xMax;
        this.yMin = yMin;
        this.yMax = yMax;
        this.style = style;
    }

    public String data;
    public String chart;
    public String title;
    public String xLabel;
    public String yLabel;
    public Integer xMin;
    public Integer xMax;
    public Integer yMin;
    public Integer yMax;
    public String style;
}

public class DataInitializer {

    private CommandLine cmd;

    public Parameters getCLIParameters(String[] args) throws Exception {

        Options options = setOptions();

        CommandLineParser parser = new DefaultParser();

        cmd = parser.parse(options, args);

        Parameters parameters = new Parameters();

        parameters.data = cmd.getOptionValue("data");
        parameters.chart = cmd.getOptionValue("chart");
        parameters.title = cmd.getOptionValue("title");
        parameters.xLabel = cmd.getOptionValue("xlabel");
        parameters.yLabel = cmd.getOptionValue("ylabel");

        parameters.xMin = getOptionalRangeParameter("xmin");
        parameters.xMax = getOptionalRangeParameter("xmax");
        parameters.yMin = getOptionalRangeParameter("ymin");
        parameters.yMax = getOptionalRangeParameter("ymax");

        parameters.style = "dots";
        if (cmd.hasOption("style")) {
            String tempStyle = cmd.getOptionValue("style");
            if (tempStyle.equals("line") || tempStyle.equals("dots")) {
                parameters.style = tempStyle;
            } else {
                throw new Exception("Wrong format of style parameter");
            }
        }

        return parameters;

    }

    private Option getOption(String name, String desc, boolean required) {
        Option option = new Option(name, true, desc);
        option.setRequired(required);
        return option;
    }

    private Options setOptions() {
        Options options = new Options();

        options.addOption(getOption("data", "data file path", true));
        options.addOption(getOption("chart", "output file path", true));
        options.addOption(getOption("title", "chart title", true));
        options.addOption(getOption("xlabel", "label for x", true));
        options.addOption(getOption("ylabel", "label for y", true));
        options.addOption(getOption("xmin", "minimal value of x", false));
        options.addOption(getOption("xmax", "maximal value of x", false));
        options.addOption(getOption("ymin", "minimal value of y", false));
        options.addOption(getOption("ymax", "maximal value of y", false));
        options.addOption(getOption("style", "style of chart - dots or line", false));

        return options;
    }

    private Integer getOptionalRangeParameter(String parameter) {
        if (cmd.hasOption(parameter)) {
            return Integer.parseInt(cmd.getOptionValue(parameter));
        } else {
            return null;
        }
    }
}
