package pl.dmcs.PlotDraw;

public class PlotDraw {

    public static void main(String[] args) throws Exception {

        DataInitializer dataInitializer = new DataInitializer();
        Parameters parameters = dataInitializer.getCLIParameters(args);

        PlotCreator plotCreator = new PlotCreator(parameters, new PlotUtils());
        plotCreator.draw();
    }
}
