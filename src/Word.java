import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;

public class Word {
	ArrayList<NeighbourHood> hoods = new ArrayList<NeighbourHood>();
	ArrayList<Borough> boroughs = new ArrayList<Borough>();
	String foundHood;
	String foundBorough;

	public void setBorough() throws IOException {
		CSVReader reader = new CSVReader(new FileReader("src/data/burough.csv"));
		List<String[]> all = reader.readAll();
		for (String[] s : all) {
			String name = s[0];
			boroughs.add(new Borough(name, 0));
		}
		reader.close();
	}

	public void setNeighbourHoods() throws IOException {
		CSVReader reader = new CSVReader(new FileReader("src/data/neighbourhoods.csv"));
		List<String[]> all = reader.readAll();
		for (String[] s : all) {
			String name = s[0];
			int id = Integer.parseInt(s[1]);
			hoods.add(new NeighbourHood(name, id));
		}

		reader.close();
	}

	public String filterNonAlphaNumeric(String t) {
		t = t.replaceAll("[^A-Za-z0-9\\s]", "");
		t = t.trim();
		return t;
	}

	public String filterEmoji(String t) {
		t = t.replaceAll("https?://\\S+\\s?", "");
		t = t.replaceAll("[^\\x00-\\x7f-\\x80-\\xad]", "");
		t = t.replace("[^\\x00-\\x7f-\\x80-\\xad]", "");
		t = t.trim();
		return t;
	}

	public boolean isNeighbourHood(String s) {
		boolean testNeighbourhood = false;
		for (NeighbourHood n : hoods) {
			if (n.testUserLocation(s)) {
				testNeighbourhood = true;
				foundHood = n.getName();
				break;
			}
		}
		return testNeighbourhood;
	}

	public boolean isBorough(String s) {
		boolean testBorough = false;
		for (Borough b : boroughs) {
			if (b.testUserLocation(s)) {
				testBorough = true;
				foundBorough = b.getName();
				break;
			}
		}
		return testBorough;
	}

	public String getHood() {
		return foundHood;
	}

	public String getBorough() {
		return foundBorough;
	}
}

