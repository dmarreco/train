package nom.danielmarreco.train;

import java.io.PrintStream;

/**
 * The TRAIN system bootstrap entry point.<br/>
 * 
 * Input:  A directed graph where a node represents a town and 
 * an edge represents a route between two towns.  The weighting 
 * of the edge represents the distance between the two towns.  
 * A given route will never appear more than once, and for a given 
 * route, the starting and ending town will not be the same town.
 */
public class Main {

	private static final PrintStream OUT = System.out;

	public static void main (String[] args) {

		try {
			// builds Kiwiland
			WorldBuilder kiwilandBuilder = new WorldBuilder();
			for (String arg : args) {
				kiwilandBuilder.addRoute(arg);
			}
			World kiwiland = kiwilandBuilder.build();

			// created a travel guide to help us on our way through Kiwiland
			TravelGuide kiwilandGuide = new TravelGuide (kiwiland);

			// prints the results
			OUT.println("1. The distance of the route A-B-C is: " + 
					kiwilandGuide.findRoute("A", "B", "C").getLenght());

			OUT.println("2. The distance of the route A-D is: " + 
					kiwilandGuide.findRoute("A", "D").getLenght());

			OUT.println("3. The distance of the route A-D-C is: " + 
					kiwilandGuide.findRoute("A", "D", "C").getLenght());

			OUT.println("4. The distance of the route A-E-B-C-D is: " + 
					kiwilandGuide.findRoute("A", "E", "B", "C", "D").getLenght());

			OUT.println("5. The distance of the route A-E-D is: " + 
					kiwilandGuide.findRoute("A", "E", "D").getLenght());

			OUT.println("6. The number of trips starting at C and ending at C with a maximum of 3 stops is: " + 
					kiwilandGuide.findPossibleRoutes("C", "C", 3).size());

			OUT.println("7. The number of trips starting at A and ending at C with exactly 4 stops is: " + 
					kiwilandGuide.findPossibleRoutes("A", "C", 4).size());

			OUT.println("8. The length of the shortest route from A to C is: " + 
					kiwilandGuide.findShortestRoute("A", "C").getLenght());

			OUT.println("9. The length of the shortest route from B to B is: " + 
					kiwilandGuide.findShortestRoute("A", "C").getLenght());

			OUT.println("10. The number of different routes from C to C with a distance of less than 30 is: " +       
					kiwilandGuide.findPossibleRoutesShorterThan("C", "C", 29)); //less than 30 => max = 29. a matter of notation...

		}
		catch (Exception e) {
			OUT.println("An error has occured: " + e.getMessage() + "\n--Details:");
			e.printStackTrace();
		}

	}


}
