package nom.danielmarreco.train;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import nom.danielmarreco.train.exception.NoSuchRouteException;

/**
 * This class provides a facade to the system results.
 * 
 * It provides the client with Routes as desired
 * 
 * @author Daniel Marreco
 *
 */
public class TravelGuide
{
	
	/**
	 * Used to define the recursion stop criteria of the BFS search
	 */
	private enum RouteLimitStrategy {
		/**
		 * Limits the Route by a number of nodes
		 */
		LIMIT_BY_STOPS,
		/**
		 * Limits the Route by it's distance
		 */
		LIMIT_BY_DISTANCE
	}

	/**
	 * The world for which this guide is made for
	 */
	World world;
	
	
	public TravelGuide(World world)
	{
		this.world = world;
	}

	/**
	 * Finds a {@link Route} passing by all the given cities, in order
	 * 
	 * @param citiesNames the names of the cities, in order, the route should visit
	 * @return One Route that visits all given cities, in the given order
	 * @throws NoSuchRouteException If there is no route between some of the given cities
	 */
	public Route findRoute (String... citiesNames) throws NoSuchRouteException
	{
		Route route = new Route();
		for (String cityName : citiesNames) {
			City city = world.getCity(cityName);
			if (city == null) {
				throw new NoSuchRouteException("No such city: " + cityName);
			}

			route.addLast(city);
		}
		return route;
	}

	/**
	 * Finds all possible routes between two given cities with a maximum number of stops
	 * 
	 * @param source The name of the city the routes should start from
	 * @param dest The name of the city the routes should end at
	 * @param maxHops The maximum number of cities the routes can visit
	 * @return A set containing all possible routes between the two given cities with a maximum of maxHops cities
	 */
	public Set<Route> findPossibleRoutes (String source, String dest, Integer maxHops)
	{
		Route route = new Route();
		route.addLast(world.getCity(source));
		Set<Route> possibleRoutes = new HashSet<Route>();
		findPossibleRoutes(route, possibleRoutes, world.getCity(dest), maxHops, RouteLimitStrategy.LIMIT_BY_STOPS);
		return possibleRoutes;
	}
	
	/**
	 * Finds all possible routes between two given cities within a defined maximum and minimum number of stops.
	 * 
	 * @param source The name of the city the routes should start from
	 * @param dest The name of the city the routes should end at
	 * @param minHops The minimum number of cities the route can visit
	 * @param maxHops The maximum number of cities the routes can visit
	 * @return A set containing all possible routes between the two given cities with a maximum of maxHops cities
	 */
	public Set<Route> findPossibleRoutes (String source, String dest, Integer minHops, Integer maxHops)
	{
		/*
		 * There is no processing overhead in finding all routes w a maximum number of stops and filtering, since BFS would have to
		 * visit them all and discard the smaller routes anyway 
		 */
		Set<Route> routes = new HashSet<Route>();
		for (Route route : findPossibleRoutes(source, dest, maxHops)) {
			if (route.size() <= minHops) {
				routes.add(route);
			}
		}
		return routes;
	}
	
	public Set<Route> findPossibleRoutesShorterThan (String source, String dest, Integer maxDistance) 
	{
		Route route = new Route();
		route.addLast(world.getCity(source));
		Set<Route> possibleRoutes = new HashSet<Route>();
		findPossibleRoutes(route, possibleRoutes, world.getCity(dest), maxDistance, RouteLimitStrategy.LIMIT_BY_DISTANCE);
		return possibleRoutes;
	}
	
	/**
	 * Internally used for recursive evaluation of the result presented by {@link TravelGuide#findPossibleRoutes(String, String, Integer)}
	 * using a breadth-first-search approach
	 * 
	 * @param route A route currently being tested. In the first call, should be a route containing only the source city
	 * @param allRoutes The return value containing all routes successfully found so far
	 * @param dest the destination city
	 * @param counter a counter used as stop criteria to the recursion
	 * @param limitStrategy the {@link RouteLimitStrategy} chosen to limit the returned routes
	 */
	private void findPossibleRoutes (Route route, Set<Route> allRoutes, City dest, Integer counter, RouteLimitStrategy limitStrategy)
	{
		if (counter <= 0) {
			return;
		}
		
		for (City city : route.getLast().getAdjacentCitites()) {
			if (city.equals(dest)) {
				route.addLast(city);
				allRoutes.add((Route)route.clone());
				route.removeLast();
				break;
			}
		}
		
		for (City nextCity : route.getLast().getAdjacentCitites()) {
			
			if (limitStrategy.equals(RouteLimitStrategy.LIMIT_BY_STOPS)) {
				counter -= 1;
			}
			else if (limitStrategy.equals(RouteLimitStrategy.LIMIT_BY_DISTANCE)) {
				counter -= route.getLast().getDistanceTo(nextCity);
			}
			else {
				assert false; //should never get here
			}
			route.addLast(nextCity);
			findPossibleRoutes(route, allRoutes, dest, counter, limitStrategy);
			route.removeLast();
			
		}
		
	}

	/**
	 * Finds the shortest path between two cities using Dijkstra's algorithm
	 * 
	 * @param sourceName The name of the source city
	 * @param destName The name of the destination city
	 * @return
	 * @throws
	 */
	public Route findShortestRoute(String sourceName, String destName) throws NoSuchRouteException
	{
		City source = world.getCity(sourceName);
		City dest = world.getCity(destName);
		
		/*
		 * one could decide to encapsulate the distance from source value in the city object, 
		 * but it would be of very specific usage; so it's kept in a local map
		 */
		Map<City, Integer> distancesFromSource = new HashMap<City, Integer> ();
		Map<City, City> prev = new HashMap<City, City>();
		Set<City> cities = world.getCities();
		// set all nodes distance to infinity
		for (City city : cities) {
			distancesFromSource.put(city, Integer.MAX_VALUE);
		}
		// replace the root node distance (from itself) to zero
		distancesFromSource.put(source, 0);
		
		mainLoop : while (!cities.isEmpty()) {
			City city = findClosest (cities, distancesFromSource);
			if (distancesFromSource.get(city).equals(Integer.MAX_VALUE)) {
				if (cities.contains(dest)) {
					throw new NoSuchRouteException ("No route found from " + sourceName + " to " + destName);
				}
				break mainLoop; // all remaining cities are inaccessible
			}
			cities.remove(city);
			innerLoop: for (City neighbor : city.getAdjacentCitites()) {
				if (!cities.contains(neighbor))
					continue innerLoop;
				// in this iteration we find it is possible to arrive to neighbor from this path with a new cost, given by:
				Integer newPossibleDistance = distancesFromSource.get(city) + city.getDistanceTo(neighbor);
				// so, we keep the best option so far:
				if (newPossibleDistance < distancesFromSource.get(neighbor)) {
					distancesFromSource.put(neighbor, newPossibleDistance);
					prev.put(neighbor, city);
				}
			}
		}
		
		Route route = new Route();
		do {
			route.addFirst(dest);
		} while ((dest = prev.get(dest)) != null);
		return route;
	}
	
	/**
	 * Finds the city with the minimal distance from the root node
	 * 
	 * @param distancesFromSource A map mapping cities and their respective distance from source
	 * @return The city (key) in the map with the smallest distance from source (value)
	 */
	private City findClosest (Set<City> cities, Map<City, Integer> distancesFromSource) 
	{
		City res = null;
		int minVal = Integer.MAX_VALUE;
		int dist;
		for (City city : cities) {
			if ((dist = distancesFromSource.get(city)) <= minVal) {
				minVal = dist;
				res = city;
			}
		}
		return res;
	}

}
