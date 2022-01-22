package pl.dmcs.PlotDraw;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

public class SVGWriterTest {

    private SVGWriter svgWriter = new SVGWriter();
    private static final String TEST_RESOURCES_PATH = "target/test-classes/";

    @Test
    public void writeSVGTest() throws IOException {
        testWrite(getFileContent("chart1"), "svgwriter_test_1");
        testWrite(getFileContent("chart2"), "svgwriter_test_2");
    }

    private void testWrite(String content, String filePath) throws IOException {
        svgWriter.write(TEST_RESOURCES_PATH + filePath, content);

        String actualContent = getFileContent(filePath);

        assertEquals(content, actualContent);
    }

    private String getFileContent(String filePath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(TEST_RESOURCES_PATH + filePath + ".svg")), StandardCharsets.UTF_8);
    }
}
