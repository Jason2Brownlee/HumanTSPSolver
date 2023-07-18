// jason brownlee, 12/05/2008

package com.humantspsolver;

/**
 * Description: 
 *  
 * Date: 12/05/2008<br/>
 * @author Jason Brownlee 
 *
 * <br/>
 * <pre>
 * Change History
 * ----------------------------------------------------------------------------
 * 
 * </pre>
 */
public class Contribution
{
	protected City fromCity;
	protected City toCity;
	protected long count;
	
	public Contribution()
	{}
	
	public Contribution(City aFromCity, City aToCity, long aCount)
	{
		fromCity = aFromCity;
		toCity = aToCity;
		count = aCount;
	}
	
	
	public String toString()
	{
		//return String.format("from=[%s], to=[%s], count=%s", fromCity, toCity, count);
		return "from=["+fromCity+"], to=["+toCity+"], count="+count+".";
	}

	
	public boolean equals(Object o)
	{
		if(o instanceof Contribution)
		{
			Contribution c = (Contribution) o;
			return 	fromCity.equals(c.fromCity) &&
					toCity.equals(c.toCity) &&
					count == c.count;
		}
		return false;
	}
	
	public City getFromCity()
	{
		return fromCity;
	}

	public void setFromCity(City fromCity)
	{
		this.fromCity = fromCity;
	}

	public City getToCity()
	{
		return toCity;
	}

	public void setToCity(City toCity)
	{
		this.toCity = toCity;
	}

	public long getCount()
	{
		return count;
	}

	public void setCount(long count)
	{
		this.count = count;
	}	
}
