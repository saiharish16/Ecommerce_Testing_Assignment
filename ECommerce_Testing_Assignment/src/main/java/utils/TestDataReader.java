package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TestDataReader {
    private String csvFile;

    public TestDataReader(String csvFile) {
        this.csvFile = csvFile;
    }

    public List<String[]> readData() {
        List<String[]> data = new ArrayList<>();
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            // Skip the header
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                data.add(values);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    Map<String, String> data = TestDataReader.readTestData("testdata.csv");

	public static Map<String, String> readTestData(String string) {
		return null;
	


	}
}
