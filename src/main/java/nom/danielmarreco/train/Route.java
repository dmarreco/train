package nom.danielmarreco.train;

import java.util.LinkedList;

/**
 * A Route is a valid path through two or more cities, following existing train lines.
 * <br/>
 * <b>Attention:</b> this implementation does not check for route validity. It must be checked by the client class.
 * 
 * @author Daniel Marreco
 *
 */
public class Route extends LinkedList<City> 
{
	private static final long serialVersionUID = 5799940691485863114L;
	
	/**
	 * Returns the total distance traveled for this route
	 * @return The total distance traveled for this route
	 */
	public Integer getLenght ()
	{
		City previousCity = this.getFirst();
		int res = 0;	
		for (City city : this.subList(1, this.size())) {
			res += previousCity.getDistanceTo(city);
			previousCity = city;
		}
		return res;
	}
	
	/**
	 * Prints this route in the format:<br/>
	 * city1 -> city2 -> city3 -> ... -> cityN
	 */
	public String toString ()
	{
		final String separator = " -> ";
		StringBuffer sb = new StringBuffer();
		for (City city : this) {
			sb.append(city.getName());
			sb.append(separator);
		}
		return new String (sb.substring(0, sb.length() - separator.length()));
	}

}
