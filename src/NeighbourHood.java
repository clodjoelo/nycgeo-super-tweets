import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NeighbourHood {
	String name;
	int boroughIndex;

	NeighbourHood(String _N, int _iD) {
		name = _N;
		boroughIndex = _iD;
	}

	boolean testUserLocation(String loc) {
		boolean foundInFunction = false;
		String[] userLoc = loc.split(" ");
		String[] thisLoc = name.split(" ");

		if (userLoc.length < thisLoc.length) {
			foundInFunction = false;
		} else if (userLoc.length == thisLoc.length) {
			foundInFunction = singularComparison(loc);
		} else if (userLoc.length > 3 && thisLoc.length == 3) {
			foundInFunction = threeWordComparison(loc);
		} else if (userLoc.length > 2 && thisLoc.length == 2) {
			foundInFunction = twoWordComparison(loc);
		} else if (userLoc.length > 1 && thisLoc.length == 1) {
			foundInFunction = oneWordComparison(loc);
		}
		return foundInFunction;
	}

	boolean singularComparison(String loc) {
		boolean found = false;
		Pattern pattern = Pattern.compile("(\\s|^)" + name + "(\\s|$)", Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(loc);
		if (matcher.matches()) {
			found = true;
		}
		return found;
	}

	boolean oneWordComparison(String loc) {
		boolean found = false;
		String[] splitted = loc.split(" ");
		for (int i = 0; i < splitted.length; i++) {
			String splittedLoc = splitted[i];
			Pattern pattern = Pattern.compile("(\\s|^)" + name + "(\\s|$)", Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(splittedLoc);
			if (matcher.matches()) {
				found = true;
				break;
			}
		}
		return found;
	}

	boolean twoWordComparison(String loc) {
		boolean found = false;
		String[] userSplitted = loc.split(" ");
		for (int i = 0; i < userSplitted.length - 1; i++) {
			String splittedLoc = userSplitted[i] + " " + userSplitted[i + 1];
			Pattern pattern = Pattern.compile("(\\s|^)" + name + "(\\s|$)", Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(splittedLoc);
			if (matcher.matches()) {
				found = true;
				break;
			}
		}
		return found;
	}

	boolean threeWordComparison(String loc) {
		boolean found = false;
		String[] userSplitted = loc.split(" ");
		for (int i = 0; i < userSplitted.length - 2; i++) {
			String splittedLoc = userSplitted[i] + " " + userSplitted[i + 1] + " " + userSplitted[i + 2];
			Pattern pattern = Pattern.compile("(\\s|^)" + name + "(\\s|$)", Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(splittedLoc);
			if (matcher.matches()) {
				found = true;
				break;
			}
		}
		return found;
	}

	public String getName() {
		return name;
	}

}

