// jason brownlee, 08/05/2008

package com.humantspsolver;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Arrays;
import java.util.logging.Logger;

import javax.swing.JPanel;


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
public class BaseTSPDisplay extends JPanel
{
	protected static Logger log = Logger.getLogger(BaseTSPDisplay.class.getName());
	
	// colours	
	public static Color COLOUR_BACKGROUND = Color.WHITE;
	public static Color COLOUR_BORDER = Color.BLACK;
	public static Color COLOUR_CITY = new Color(20, 200, 255, 50);		
	public static Color COLOUR_CITY_CENTER = Color.BLACK;
	
	public static double SIZE_CITY_FACTOR = 0.05;
	
	
	// domain state
	protected City [] cityList;
	protected Rectangle2D domainRectangle;
	// view state
	protected Point2D [] viewPoints;
	protected Rectangle2D [] viewRectangles;
	protected Rectangle lastScreenSize;
	
	protected boolean maintainAspectRatio = false;
	
	
	
	public BaseTSPDisplay()
	{
		// background colour
		setBackground(COLOUR_BACKGROUND);		
		setDoubleBuffered(true);
	}	
	
	
	protected void paintComponent(Graphics g)
	{
		// background
		super.paintComponent(g);		
		Graphics2D g2d = (Graphics2D) g;
		// Anti-alias the painting
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		// run main drawing
		drawMain(g2d); // nice and extensible		
	}
	
	protected void drawPreCity(Graphics2D g2d){}
	protected void drawPostCity(Graphics2D g2d){}
	
	protected void drawMain(Graphics2D g2d)
	{
		// pre-drawing calculations
		preDrawCalculations();
		// draw a border 
		drawBorder(g2d);
		// pre
		drawPreCity(g2d);
		// draw all cities
		drawAllCities(g2d);
		// post
		drawPostCity(g2d);
	}
	
	
	protected void preDrawCalculations()
	{
		// calculate city view points
		calculateViewPoints();
	}
	
	protected void drawBorder(Graphics2D g2d)
	{
		g2d.setColor(COLOUR_BORDER);
		g2d.drawRect(0, 0, getWidth()-1, getHeight()-1);
	}
	
	protected void drawAllCities(Graphics2D g2d)
	{	
		// calculate city size
		int citySize = calculateCitySize();
		// draw all cities
		for (int i = 0; i < cityList.length; i++)
		{
			drawCity(g2d, cityList[i], viewPoints[i], viewRectangles[i], citySize);
		}
	}
	
	/*
	protected void fillCityPolygon(Graphics2D g2d)
	{
		Polygon polygon = new Polygon();
		for(Point2D p : viewPoints)
		{
			polygon.addPoint((int)Math.round(p.getX()), (int)Math.round(p.getY()));
		}
		
		Color c = new Color(0, 200, 0, 20);
		g2d.setColor(c);
		g2d.fill(polygon);
	}
	*/
	
	protected Point2D getViewPointForCity(City city)
	{
		for (int i = 0; i < cityList.length; i++)
		{
			if(cityList[i].fastEquals(city))
			{
				return getViewPointForCity(i);
			}
		}
		
		throw new SolverAppletException("Unable to locate city in city list: "+city);
	}
	
	protected Point2D getViewPointForCity(int knownIndex)
	{
		return viewPoints[knownIndex];
	}

	protected Color getCityFillColour(City aCity, Point2D viewPoint, Rectangle2D viewRectangle)
	{
		return COLOUR_CITY;
	}

	protected void drawCity(Graphics2D g2d, City aCity, Point2D viewPoint, Rectangle2D viewRectangle, int citySize)
	{				
		// fill background
		g2d.setColor(getCityFillColour(aCity, viewPoint, viewRectangle));	
		g2d.fillOval((int)viewRectangle.getX(), (int)viewRectangle.getY(), (int)viewRectangle.getWidth(), (int)viewRectangle.getHeight());
		
		// point in the middle
		g2d.setColor(COLOUR_CITY_CENTER);	
		int x = (int) Math.round(viewPoint.getX() - 1);
		int y = (int) Math.round(viewPoint.getY() - 1);	
		g2d.fillOval(x, y, 2, 2);		
	}	
	
	protected Rectangle2D createViewRectangle(Point2D viewPoint)
	{
		int citySize = calculateCitySize();
		int x = (int) Math.round(viewPoint.getX() - ((double)citySize/2.0));
		int y = (int) Math.round(viewPoint.getY() - ((double)citySize/2.0));	
		Rectangle2D rect = new Rectangle2D.Double(x, y, citySize, citySize);
		return rect;
	}

	protected double getCitySizeFactor()
	{
		return SIZE_CITY_FACTOR;
	}
	
	protected int calculateCitySize()
	{		
		// ensure square
		int size = Math.min(getWidth(), getHeight());
		// percentage
		size = (int) Math.round((double)size * getCitySizeFactor());
		// ensure even for nice drawing
		if((size%2)!=0)
		{
			size++;
		}
		// ensure bug enough to see
		if(size < 2)
		{
			size = 2;
		}			
		return size;
	}
	
	protected void calculateViewPoints()
	{
		boolean generatePoints = false;		
		
		if(viewPoints == null)
		{
			viewPoints = new Point2D[cityList.length];
			viewRectangles = new Rectangle2D[cityList.length];
			lastScreenSize = getBounds();
			generatePoints = true;
		}
		else
		{
			// check if the screen has changed since the last time
			Rectangle screenSize = getBounds();		
			if(!screenSize.equals(lastScreenSize))
			{
				lastScreenSize = screenSize;
				Arrays.fill(viewPoints, null);
				Arrays.fill(viewRectangles, null);
				generatePoints = true;
			}
		}
		
		// generate the points
		if(generatePoints)
		{
			// prepare required scaling
			double scaleX = (lastScreenSize.getWidth() / domainRectangle.getWidth());
			double scaleY = (lastScreenSize.getHeight() / domainRectangle.getHeight());
			
			if(maintainAspectRatio)
			{
				// only use one scale factor to maintain the aspect ratio
				double factor = Math.min(scaleX, scaleY);
				// conform
				scaleX = scaleY = factor;
			}			
			
			// prepare the transform
			AffineTransform at = new AffineTransform();
			// scaling from user space to device space, includes inversion
			at.scale(scaleX, -scaleY);
			// offset in user space to 0,0 (with inversion, otherwise use getY())
			at.translate(-domainRectangle.getX(), -domainRectangle.getY()-domainRectangle.getHeight());
			
			// log
			log.info("Prepared view points transform, scaling information: scaleX="+scaleX+", scaleY="+scaleY);
			
			// generate the points
			for (int i = 0; i < cityList.length; i++)
			{
				// transform domain point
				Point2D viewPt = at.transform(cityList[i].getPoint(), null);			
				// store
				viewPoints[i] = viewPt;			
				viewRectangles[i] = createViewRectangle(viewPoints[i]);
				// validation
				if(!lastScreenSize.contains(viewPt))
				{
					throw new RuntimeException("Generated an invalid view point: screen="+lastScreenSize+", point="+viewPt);
				}
			}
		}		
	}
	
	protected void calculateDomainBoundary()
	{	
		double maxX = Double.NEGATIVE_INFINITY;
		double minX = Double.POSITIVE_INFINITY;
		
		double maxY = Double.NEGATIVE_INFINITY;			
		double minY = Double.POSITIVE_INFINITY;
		
		for (int i = 0; i < cityList.length; i++)
		{
			Point2D p = cityList[i].getPoint();
			double x = p.getX();
			if(x > maxX) maxX = x;					
			if(x < minX) minX = x;
			
			double y = p.getY();			
			if(y > maxY) maxY = y;
			if(y < minY) minY = y;	
		}
		
		// hack to provides some space around our points in user space (5% of the domain)
		// also needed for validation of the prepared space (contains check)
		double domainWidthBuffer = (maxX - minX) * 0.05;
		double domainHeightBuffer = (maxX - minX) * 0.05;
		
		maxX += domainWidthBuffer;
		maxY += domainHeightBuffer;
		minX -= domainWidthBuffer;
		minY -= domainHeightBuffer;
		
		double domainWidth = maxX - minX;
		double domainHeight = maxY - minY;		
		
		// prepare the problem space definition
		domainRectangle = new Rectangle2D.Double(minX, minY, domainWidth, domainHeight);
		
		// validate the space
		for (int i = 0; i < cityList.length; i++)
		{
			Point2D p = cityList[i].getPoint();
			if(!domainRectangle.contains(p))
			{
				throw new SolverAppletException("Domain rectangle does not contain a domain point: rect="+domainRectangle+", point="+p);
			}
		}
		
		// log problem space information
		log.info("domain bounds: minX="+minX+", maxX="+maxX+", minY="+minY+", maxY="+maxY);
		log.info("domain rectangle="+domainRectangle);
	}	
	
	public City[] getCityList()
	{
		return cityList;
	}

	public void setCityList(City[] cityList)
	{
		this.cityList = cityList;
		calculateDomainBoundary();
	}

	public boolean isMaintainAspectRatio()
	{
		return maintainAspectRatio;
	}

	public void setMaintainAspectRatio(boolean isMaintainAspectRatio)
	{
		this.maintainAspectRatio = isMaintainAspectRatio;
	}		
}
