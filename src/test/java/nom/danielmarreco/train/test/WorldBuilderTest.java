package nom.danielmarreco.train.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import nom.danielmarreco.train.City;
import nom.danielmarreco.train.World;
import nom.danielmarreco.train.WorldBuilder;
import nom.danielmarreco.train.exception.InvalidArgumentException;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit Test class for the WorldBuilder class
 * 
 * @see WorldBuilder
 */
public class WorldBuilderTest {
	
	private WorldBuilder worldBuilder;
	
	@Before
	public void setup()
	{
		worldBuilder = new WorldBuilder();
	}
	
	/**
	 * Tests for the correct structure of a graph created by WorldBuilder.build()
	 * @throws InvalidArgumentException 
	 * 
	 * @see WorldBuilder#build()
	 */
	@Test
	public void testBuild() throws InvalidArgumentException 
	{
		World testWorld = worldBuilder
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
		
		assertTrue(testWorld.getCities().size() == 5);
		
		City a, b, c, d, e;
		
		assertNotNull(a = testWorld.getCity("A"));
		assertNotNull(b = testWorld.getCity("B"));
		assertNotNull(c = testWorld.getCity("C"));
		assertNotNull(d = testWorld.getCity("D"));
		assertNotNull(e = testWorld.getCity("E"));
		
		assertTrue(a.getAdjacentCitites().size() == 3);
		assertTrue(b.getAdjacentCitites().size() == 1);
		assertTrue(c.getAdjacentCitites().size() == 2);
		assertTrue(d.getAdjacentCitites().size() == 2);
		assertTrue(e.getAdjacentCitites().size() == 1);
		
		assertTrue(a.getAdjacentCitites().get(b).equals(5));
		assertTrue(b.getAdjacentCitites().get(c).equals(4));
		assertTrue(c.getAdjacentCitites().get(d).equals(8));
		assertTrue(d.getAdjacentCitites().get(c).equals(8));
		assertTrue(d.getAdjacentCitites().get(e).equals(6));
		assertTrue(a.getAdjacentCitites().get(d).equals(5));
		assertTrue(c.getAdjacentCitites().get(e).equals(2));
		assertTrue(e.getAdjacentCitites().get(b).equals(3));
		assertTrue(a.getAdjacentCitites().get(e).equals(7));

	}
	

}
