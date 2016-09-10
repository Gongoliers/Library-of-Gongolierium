package com.thegongoliers.input;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class CSV {

	public String[] colHeaders;
	public String[][] data;

	public CSV(String filename) {
		File mFile = new File(filename);
		String rawData = readFile(mFile);
		String[] rows = rawData.split("\n");
		colHeaders = rows[0].split(",");
		data = new String[rows.length - 1][colHeaders.length];
		for (int i = 1; i < rows.length; i++) {
			data[i - 1] = rows[i].split(",");
		}
	}

	public String get(String property, int row) {
		int index = propertyIndex(property);
		if (index == -1)
			return null;
		return data[row][index];
	}

	public int getInt(String property, int row) {
		return Integer.valueOf(get(property, row));
	}

	public double getDouble(String property, int row) {
		return Double.valueOf(get(property, row));
	}

	public boolean getBoolean(String property, int row) {
		return Boolean.valueOf(get(property, row));
	}

	private int propertyIndex(String property) {
		return find(colHeaders, property);
	}

	private int find(String[] values, String search) {
		for (int i = 0; i < values.length; i++) {
			if (values[i].equals(search)) {
				return i;
			}
		}
		return -1;
	}

	private String readFile(File aFile) {
		BufferedReader br;
		String fileContent = "";
		try {
			br = new BufferedReader(new FileReader(aFile));

			String line = br.readLine();

			while (line != null) {
				fileContent += line;
				fileContent += System.lineSeparator();
				line = br.readLine();
			}
			br.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileContent;
	}
}
