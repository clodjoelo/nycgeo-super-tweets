import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Borough {
String name;
int landShapes;
ArrayList<Vector>[] boundaryCoOrds;
int countOfShapes = 0;
float shapeId = -1;
	
Borough(String _name,int _lS){
		name = _name;
		landShapes= _lS;
		boundaryCoOrds = new ArrayList[landShapes];
	}



public boolean testUserLocation(String l) {
	boolean found = false;
	Pattern pattern = Pattern.compile("(\\s|^)" + name + "(\\s|$)", Pattern.CASE_INSENSITIVE);
	Matcher matcher = pattern.matcher(l);
	if (matcher.find()) {
		found = true;
	}
	return found;
}

public void loadBoundaries(float thisId, double la, double lo) {
	 if(shapeId!= thisId){
	      boundaryCoOrds[countOfShapes] = new ArrayList<Vector>();
	      countOfShapes++;  
	    }
	 boundaryCoOrds[countOfShapes-1].add(new Vector(la,lo));
	 shapeId = thisId;
}


boolean testGeoInside(Vector v) {
	double la = v.getLat();
	double lo = v.getLong();
	boolean oddNodes = false;
	for(int x = 0; x< boundaryCoOrds.length; x++){
	    	   int i, j = boundaryCoOrds[x].size() - 1;
	   		   int sides = boundaryCoOrds[x].size();
	   		for (i = 0; i < sides; i++) {
				if ((boundaryCoOrds[x].get(i).lat < la && boundaryCoOrds[x].get(j).lat >= la
						|| boundaryCoOrds[x].get(j).lat < la && boundaryCoOrds[x].get(i).lat >= la)
						&& (boundaryCoOrds[x].get(i).lon <= lo || boundaryCoOrds[x].get(j).lon <= lo)) {
					oddNodes ^= (boundaryCoOrds[x].get(i).lon + (la - boundaryCoOrds[x].get(i).lat)
							/ (boundaryCoOrds[x].get(j).lat - boundaryCoOrds[x].get(i).lat)
							* (boundaryCoOrds[x].get(j).lon - boundaryCoOrds[x].get(i).lon) < lo);
				}
				j = i;
			}
	   }
	return oddNodes;
}

public String getName() {
		return name;
	}

}


