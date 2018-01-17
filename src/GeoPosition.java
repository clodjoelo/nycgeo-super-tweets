import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;

import twitter4j.GeoLocation;

public class GeoPosition {
	ArrayList<Borough> boroughs = new ArrayList<Borough>();
	int amountOfZones = 100;
	double lon;
	double lat;
	String name;

	public void setBoroughs() throws IOException {
		CSVReader reader = new CSVReader(new FileReader("src/data/burough.csv"));
		List<String[]> all = reader.readAll();
		for (String[] s : all) {
			String name = s[0];
			int id = Integer.parseInt(s[5]);
			boroughs.add(new Borough(name, id));
		}
		reader.close();
	}

	public void setGeoBoroughs() throws IOException {
		CSVReader reader = new CSVReader(new FileReader("src/data/borbounds.csv"));
		List<String[]> all = reader.readAll();
		int current = 0;
		Borough b = boroughs.get(current);
		for (String[] s : all) {
			float shapeId = Float.parseFloat(s[0]);
			double lat = Double.parseDouble(s[1]);
			double lon = Double.parseDouble(s[2]);
			if (current != (int) Math.floor(shapeId)) {
				current = (int) Math.floor(shapeId);
				b = boroughs.get(current);
			}
			b.loadBoundaries(shapeId, lat, lon);
		}
		reader.close();
	}

	public boolean isGeoBounded(GeoLocation g) {
		boolean foundInGeo = false;
		lat = g.getLatitude();
		lon = g.getLongitude();
		Vector tweetLoc = new Vector(lat, lon);
		for (Borough b : boroughs) {
			if (b.testGeoInside(tweetLoc)) {
				name = b.getName();
				foundInGeo = true;
				break;
			}
		}
		return foundInGeo;
	}

	public String getNameMatch() {
		return name;
	}

}

