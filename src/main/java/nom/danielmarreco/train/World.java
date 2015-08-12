package nom.danielmarreco.train;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


/**
 * A representation of a set of cities and the available routes between them.
 * 
 * Can be seen as a graph, where cities are vertices and routes are the edges.
 */
public class World {

	/**
	 * A set of cities in this world, mapped by their names
	 */
	private Map<String, City> citiesInTheWorld = new HashMap<String, City> ();

	/**
	 * Adds a {@link City} to the World with the given name if it does not already exists.
	 * If it does already exists, the city will be replaced.
	 * 
	 * @param name The name of the new city
	 * @return An instance of the City just added
	 */
    public City addCity (String name)
    {
    	City newCity = new City(name);
    	citiesInTheWorld.put(name, newCity);
    	return newCity;
    }
    
    /**
     * @param name The name of the city to be obtained
     * @return The city in the world with the given name, or null if it does not exists
     */
    public City getCity (String name)
    {
    	return citiesInTheWorld.get(name);
    }
    
    //TODO javadoc
    public Set<City> getCities () {
    	return new HashSet<City>(this.citiesInTheWorld.values());
    }



}
