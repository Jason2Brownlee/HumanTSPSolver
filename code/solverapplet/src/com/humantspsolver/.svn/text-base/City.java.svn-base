// jason brownlee, 10/05/2008

package com.humantspsolver;

import java.awt.geom.Point2D;


/**
 *
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
public class City
{
	protected Point2D point;
	protected int cityNumber;
	
	public City()
	{}
	
	public City(double x, double y, int aCityNumber)
	{
		point = new Point2D.Double(x, y);
		cityNumber = aCityNumber;		
	}	

	public Point2D getPoint()
	{
		return point;
	}

	public int getCityNumber()
	{
		return cityNumber;
	}	
	
	
	public void setPoint(Point2D point)
	{
		this.point = point;
	}

	public void setCityNumber(int cityNumber)
	{
		this.cityNumber = cityNumber;
	}
	
	public String toString()
	{
		return "x="+point.getX()+", y="+point.getY()+", city="+cityNumber+"";
	}
	
	public boolean fastEquals(City c)
	{
		return cityNumber == c.cityNumber;
	}
	
	
	public boolean equals(Object o)
	{
		if(o instanceof City)
		{
			City c = (City) o;
			return point.equals(c.point) && cityNumber == c.cityNumber;
		}
		return false;
	}
}
