package nom.danielmarreco.train.test;

import static org.junit.Assert.*;
import nom.danielmarreco.train.City;
import nom.danielmarreco.train.Route;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests the {@link Route} class
 * 
 * @author Daniel Marreco
 *
 */
public class RouteTest {
	
	private Route route;
	
	@Before
	public void setup ()
	{
		route = new Route ();
		City city1 = new City("city1");
		City city2 = new City("city2");
		City city3 = new City("city3");
		city1.addAdjacentCity(city2, 100);
		city2.addAdjacentCity(city3, 10);
		route.add(city1);
		route.add(city2);
		route.add(city3);
	}
	
	/**
	 * Tests {@link Route#toString()}
	 */
	@Test
	public void testToString ()
	{
		assertEquals(route.toString(), "city1 -> city2 -> city3");
	}
	
	/**
	 * Tests {@link Route#getLenght()}
	 */
	@Test
	public void testGetLenght ()
	{
		assertEquals(route.getLenght().intValue(), 110);
	}

}
