package nom.danielmarreco.train;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nom.danielmarreco.train.exception.NoSuchRouteException;

/**
 * Represents a city, or a node in the graph
 */
public class City {
	


	/**
	 * The (unique) name of this city
	 */
    private String name;
    
    /**
     * A map containing all departing routes from this city, indexed by
     * the destination city and evaluated by it's distance.<br/>
     * 
     * In the graph paradigm, it represents all leaving edges from our node.
     * 
     * NOTE: it is assumed that there can be only one route from any city "X" to another city "Y".
     */
    private Map<City, Integer> adjacentCities = new HashMap<City, Integer> ();
	
    /**
     * Constructor
     * @param name The name of the city
     */
	public City (String name)
	{
		this.name = name;
	}
	
	/**
	 * Adds a leaving Route to this city
	 * @param city
	 * @param distance
	 */
	public void addAdjacentCity (City city, Integer distance)
	{
		adjacentCities.put(city, distance);
	}


	/**
	 * Gets the distance from this city to another one
	 * 
	 * @param adjacentCity The city which the distance to is to be obtained
	 * @return The distance to the adjacentCity
	 * @throws NoSuchRouteException If there is no direct route from this city to the adjacent
	 */
	public Integer getDistanceTo(City adjacentCity)  throws NoSuchRouteException {
		Integer res = this.adjacentCities.get(adjacentCity);
		if (res == null) {
			throw new NoSuchRouteException("No route from " + this.name + " to " + adjacentCity.name);
		}
		return res;
	}


	/**
	 * TODO javadoc
	 * @param route
	 * @return
	 */
	public List<Route> addToRouteAsNeeded (Route route) {
		List<Route> res = new ArrayList<Route>();
		for (City city : route.getLast().adjacentCities.keySet()) {
			if (city.equals(this)) {
				route.addLast(city);
				res.add((Route)route.clone());
				route.removeLast();
				break;
			}
		}
		return res;
	}
	

}
