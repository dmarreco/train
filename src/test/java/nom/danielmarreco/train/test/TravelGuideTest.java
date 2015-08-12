package nom.danielmarreco.train.test;

import static org.junit.Assert.assertEquals;

import java.util.Set;

import nom.danielmarreco.train.Route;
import nom.danielmarreco.train.TravelGuide;
import nom.danielmarreco.train.World;
import nom.danielmarreco.train.WorldBuilder;
import nom.danielmarreco.train.exception.InvalidArgumentException;
import nom.danielmarreco.train.exception.NoSuchRouteException;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for the TravelGuide class
 *
 * @see TravelGuide
 */
public class TravelGuideTest 
{
	
	/**
	 * The test mass shared by the many scenarios
	 */
	private World world; 
	
	/**
	 * An instance of the subject class for this test
	 */
	private TravelGuide guide;

	@Before
	public void setup () throws InvalidArgumentException
	{
		world = new WorldBuilder()
				.addRoute("AB5")
				.addRoute("BC4")
				.addRoute("CD8")
				.addRoute("DC8")
				.addRoute("DE6")
				.addRoute("AD5")
				.addRoute("CE2")
				.addRoute("EB3")
				.addRoute("AE7")
				.build();
		
		guide = new TravelGuide(world);
	}


	/**
	 * Tests findRoute() method
	 * @throws NoSuchRouteException 
	 * 
	 * @see TravelGuide#findRoute(String...)
	 */
	@Test
	public void testFindRoute() throws NoSuchRouteException
	{
		assertEquals(9, guide.findRoute("A", "B", "C").getLenght().intValue());
		assertEquals(5, guide.findRoute("A", "D").getLenght().intValue());
		assertEquals(13, guide.findRoute("A", "D", "C").getLenght().intValue());
		assertEquals(22, guide.findRoute("A", "E", "B", "C", "D").getLenght().intValue());
	}

	@Test(expected=NoSuchRouteException.class)
	public void testFindRouteError () throws NoSuchRouteException
	{
		guide.findRoute("A", "E", "D"); 
	}

	/**
	 * Tests findPossibleRoutes() method
	 * 
	 * @see TravelGuide#findPossibleRoutes(String, String, Integer)
	 */
	@Test
	public void testFindPossibleRoutes()
	{
		assertEquals(2, guide.findPossibleRoutes("C", "C", 3).size());
		assertEquals(3, guide.findPossibleRoutes("A", "C", 4, 4).size());
	}

	/**
	 * Tests findShortestRoute() method
	 * @see TravelGuide#findShortestRoute(String, String)
	 */
	@Test
	public void testFindShortestRoute() throws NoSuchRouteException
	{
		assertEquals(9, guide.findShortestRoute("A", "C").getLenght().intValue());
		assertEquals(9, guide.findShortestRoute("A", "C").getLenght().intValue());
	}
	
	@Test
	public void testFindRoutesShorterThan()
	{
		Set<Route> r = guide.findPossibleRoutes("C", "C", 30);
		int i = 0;
		for (Route ro : r) {
			if (ro.getLenght() < 30) {
				System.out.println(++i + ":" + ro + " : " + ro.getLenght());
			}
		}
		assertEquals(7, guide.findPossibleRoutesShorterThan("C", "C", 29).size());
	}

}
