package pl.dmcs.PlotDraw;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SVGWriter {

    public void write(String filePath, String content) throws IOException {
        File file = new File(filePath + ".svg");
        file.createNewFile();
        FileWriter writer = new FileWriter(file);
        writer.write(content);
        writer.close();
    }

}
