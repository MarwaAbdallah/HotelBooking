package populator;

import java.util.ArrayList;
import java.util.List;

import model.Location;

/**
 * Initial population of locations that will be used throough the projects
 */
public class PopulateLocations {
	
	public static List<Location> generateLocations() {
		List<Location> locs = new ArrayList<>();
		
		locs.add(new Location("Paris","France"));
		locs.add(new Location("Addis Abeba","Ethiopia"));
		locs.add(new Location("Tokyo","Japan"));
		locs.add(new Location("Toronto","Canada"));
		locs.add(new Location("New York","USA"));
		return locs;
		
	}

}
