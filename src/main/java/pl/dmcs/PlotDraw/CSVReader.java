package pl.dmcs.PlotDraw;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CSVReader {

    private String[] headers;

    public List<double[]> read(String filePath) {
        List<double[]> result = new ArrayList<>();
        String line = "";
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            while ((line = br.readLine()) != null) {
                try {
                    double[] numbers = getDoubleArray(line);
                    result.add(numbers);
                } catch (Exception e) {
                    headers = line.split(",");
                    continue;
                }
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    public String[] getHeaders() {
        return headers;
    }

    private double[] getDoubleArray(String line) {
        return Arrays.stream(line.split(","))
                     .mapToDouble(Double::parseDouble)
                     .toArray();
    }
}
