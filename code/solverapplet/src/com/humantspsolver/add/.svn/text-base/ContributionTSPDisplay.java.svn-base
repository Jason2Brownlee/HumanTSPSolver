// jason brownlee, 12/05/2008

package com.humantspsolver.add;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import com.humantspsolver.BaseTSPDisplay;
import com.humantspsolver.City;
import com.humantspsolver.Contribution;
import com.humantspsolver.EdgeDefinitionListener;
import com.humantspsolver.TSPUtils;

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
public class ContributionTSPDisplay extends BaseTSPDisplay
{	
	public static Color COLOUR_EDGE_FIXED = new Color(20, 20, 255, 100);
	public static Color COLOUR_EDGE_PARTIAL = new Color(20, 20, 255, 50);
	public static Color COLOUR_CITY_FIXED = new Color(255, 20, 20, 50);
	public static Color COLOUR_CITY_FINISHED = new Color(255, 20, 20, 50);
	
	public static Color COLOUR_EDGE_NN = new Color(255, 0, 0, 100);
	
	protected static NumberFormat format = DecimalFormat.getInstance();
	
	public static Font FONT_BIG = new Font("Helvetica", Font.BOLD, 30);
	public static Font FONT_SMALL = new Font("Helvetica", Font.BOLD, 20);
	
	public static Color COLOR_GOOD = new Color(20, 255, 20);
	public static Color COLOR_BAD = new Color(255, 20, 20);
	
	// basic
	protected LinkedList edgeDefinitionListeners;
	
	// state
	protected boolean cityIsSelected;
	protected City currentlySelectedCity;
	protected Point currentMousePosition;
	protected City cityInRange; 
	protected Random rand;
	
	// edges	
	protected LinkedList edges;
	
	
	// more state	
	protected boolean nnVisible = false;
	
	protected City [] nnSolution;
	protected double nnScore;
	protected double userScore;
	
	
	public ContributionTSPDisplay()
	{
		// basic creation
		edgeDefinitionListeners = new LinkedList();
		rand = new Random(System.currentTimeMillis());
		edges = new LinkedList();			
		// listeners
		prepareListeners();
	}
	
	public void setCityList(City[] cityList)
	{
		super.setCityList(cityList);
		nnSolution = TSPUtils.getBestNNSolution(cityList, rand);
		nnScore = TSPUtils.calculateTourDistance(nnSolution);					
		log.info("Calculated NN Solution, cities: "+nnSolution.length+", tour length: " + nnScore);
	}	
	
	protected void prepareListeners()
	{
		// mouse events
		this.addMouseListener(new MyMouseAdapter());
		this.addMouseMotionListener(new MyMouseMoutionAdapter());
	}
	
	public void addEdgeDefinitionListener(EdgeDefinitionListener l)
	{
		edgeDefinitionListeners.add(l);
	}
	public boolean removeEdgeDefinitionListener(EdgeDefinitionListener l)
	{
		return edgeDefinitionListeners.remove(l);
	}
	protected void triggerEdgeDefinitionListenerEvent(Contribution contrib)
	{
		for (Iterator it = edgeDefinitionListeners.iterator(); it.hasNext();)
		{
			EdgeDefinitionListener l = (EdgeDefinitionListener) it.next();
			l.addEdge(contrib);
		}
	}
	
	
	protected void drawPreCity(Graphics2D g2d)
	{
		// draw NN solution
		drawNNSolution(g2d);
		// draw all edges
		drawAllEdges(g2d);
		// partial edges
		drawPartialEdge(g2d);
	}
	
	protected String getUserScoreString()
	{
		return "Length: " + format.format(userScore);
	}
	protected String getEstimateScoreString()
	{
		return "Estimate: " + format.format(nnScore);
	}
	protected String getComparisonScoreString()
	{
		// negative for better
		double diff = (userScore - nnScore);	
		String diffS = format.format(diff);
		if((userScore - nnScore)>0)
		{
			diffS = "+" + diffS;
		}
		
		return "(" + diffS + ")";
	}
	
	protected void drawPostCity(Graphics2D g2d)
	{
		if(!isAdditionalEdgesSupported())
		{
			// prepare string
			String userString = getUserScoreString();
			String estimateString = getEstimateScoreString();
			String diffString = getComparisonScoreString();
			int offset = 0;
			
			g2d.setFont(FONT_BIG);
			FontMetrics fm = g2d.getFontMetrics();						
			// draw user string			
			Rectangle2D rect = fm.getStringBounds(userString, g2d);		
			int x = (int) ((lastScreenSize.getWidth()  - rect.getWidth()) / 2.0);
			int y = (int) ((lastScreenSize.getHeight() - rect.getHeight()) / 2.0  + fm.getAscent());	
			y -= 40;
			offset = fm.getAscent();
			drawString(g2d, Color.BLUE, userString, x, y);
			
			// estimate string
			g2d.setFont(FONT_SMALL);
			fm = g2d.getFontMetrics();
			rect = fm.getStringBounds(estimateString, g2d);		
			x = (int) ((lastScreenSize.getWidth()  - rect.getWidth()) / 2.0);			
			drawString(g2d, Color.DARK_GRAY, estimateString, x, y+offset);			
			
			// difference
			rect = fm.getStringBounds(diffString, g2d);		
			x = (int) ((lastScreenSize.getWidth()  - rect.getWidth()) / 2.0);		
			Color c = ((userScore - nnScore)<=0) ? COLOR_GOOD : COLOR_BAD;			
			drawString(g2d, c, diffString, x, y+offset+(fm.getAscent()+10));	
		}
	}
	
	
	
	
	protected void drawString(Graphics2D g2d, Color c, String s, int x, int y)
	{
		g2d.setColor(c);
		g2d.drawString(s, x, y);  // Draw the string.
	}
	
	protected void drawNNSolution(Graphics2D g2d)
	{
		if(nnVisible)
		{
			for (int i = 1; i <= nnSolution.length; i++)
			{
				if(i == nnSolution.length)
				{
					drawNNEdge(g2d, nnSolution[i-1], nnSolution[0]);
				}
				else
				{
					drawNNEdge(g2d, nnSolution[i-1], nnSolution[i]);
				}
			}
		}
	}
	
	protected void drawNNEdge(Graphics2D g2d, City fromC, City toC)
	{		
		// get the view points
		Point2D from = getViewPointForCity(fromC);
		Point2D to = getViewPointForCity(toC);
		// prepare the line
		Line2D edgeLine = new Line2D.Double(from, to);		
		// prepare the stroke
		Stroke stroke = contributionStroke();
		// draw
		g2d.setColor(COLOUR_EDGE_NN);
		Shape strokedLine = stroke.createStrokedShape(edgeLine);
		g2d.fill(strokedLine); 
	}
	
	
	protected Color getCityFillColour(City aCity, Point2D viewPoint, Rectangle2D viewRectangle)
	{
		if(isCityCurrentlySelected(aCity))
		{
			return COLOUR_CITY_FIXED;
		}
		else if(isCityCurrentlyInRange(aCity))
		{
			return COLOUR_CITY_FIXED;
		}
		else if(!isAdditionalEdgesSupported())
		{
			return COLOUR_CITY_FINISHED;
		}
		
		return super.getCityFillColour(aCity, viewPoint, viewRectangle);
	}
	
	protected void drawPartialEdge(Graphics2D g2d)
	{
		if(cityIsSelected)
		{
			g2d.setColor(COLOUR_EDGE_PARTIAL);
			Point2D position = getViewPointForCity(currentlySelectedCity);
			Line2D line = new Line2D.Double(position, currentMousePosition);			
			g2d.draw(line);
		}
	}
	
	protected void drawAllEdges(Graphics2D g2d)
	{
		for (Iterator it = edges.iterator(); it.hasNext();)
		{
			Contribution c = (Contribution) it.next();
			// draw edge
			drawEdge(g2d, c);
		}
	}
	
	protected void drawEdge(Graphics2D g2d, Contribution contribution)
	{
		// get the view points
		Point2D from = getViewPointForCity(contribution.getFromCity());
		Point2D to = getViewPointForCity(contribution.getToCity());
		// prepare the line
		Line2D edgeLine = new Line2D.Double(from, to);		
		// prepare the stroke
		Stroke stroke = contributionStroke();
		// draw
		g2d.setColor(COLOUR_EDGE_FIXED);
		Shape strokedLine = stroke.createStrokedShape(edgeLine);
		g2d.fill(strokedLine); 
	}
	
	protected Stroke contributionStroke()
	{
		float size = 3.0f;
		Stroke stroke = new BasicStroke(size);
		return stroke;
	}
	
	protected void highlightClosestCity(Point location)
	{
		City closest = getIntersectingCity(location);
		cityInRange = closest;
	}
	
	
	
	protected City getIntersectingCity(Point location)
	{
		if(!lastScreenSize.contains(location))
		{
			return null;
		}
		
		// locate closest city
		int closestCity = -1;
		double distance = Double.POSITIVE_INFINITY;
		for (int i = 0; i < viewPoints.length; i++)
		{
			double dist = TSPUtils.distance(location, viewPoints[i]);
			if(dist < distance)
			{
				distance = dist;
				closestCity = i;
			}
		}
		
		// nothing by default
		City selection = null;
		
		// check if close enough
		if(distance <= (calculateCitySize()/2.0))
		{
			selection = cityList[closestCity]; 
		}
		
		return selection;
	}	
	
	protected boolean isCityCurrentlySelected(City aCity)
	{
		if(cityIsSelected)
		{
			if(currentlySelectedCity.fastEquals(aCity))
			{
				return true;
			}
		}
		
		return false;
	}
	
	protected boolean isCityCurrentlyInRange(City aCity)
	{
		if(cityInRange!=null && cityInRange.fastEquals(aCity))
		{
			return true;
		}
		
		return false;
	}
	
	protected void processMouseClick(Point location)
	{		
		// ensure additional edges are supported
		if(!isAdditionalEdgesSupported())
		{
			return;
		}		
		
		// get an intersecting city
		City clickedCity = getIntersectingCity(location);
		
		// a city was clicked and we are currently not processing an edge
		if(clickedCity != null && !cityIsSelected)
		{
			cityIsSelected = true;
			currentlySelectedCity = clickedCity;
		}
		// we are processing an edge
		else if(cityIsSelected)
		{
			// check if a city was clicked
			if(clickedCity != null)
			{
				// ensure the city is not the same city already clicked
				if(!clickedCity.fastEquals(currentlySelectedCity))
				{
					// add the edge
					createEdge(currentlySelectedCity, clickedCity);
					// update position
					currentlySelectedCity = clickedCity;
				}
				// clear the state
				else
				{
					clearCurrentCity();
				}
			}
			// a click was made on something that was not a city
			else
			{
				// clear state
				clearCurrentCity();
			}
		}				
		
		repaint();		
	}	
	
	public void toggleNNVisible()
	{
		nnVisible = !nnVisible;
		repaint();
	}
	
	protected boolean isEdgeDefined(Contribution aEdge)
	{
		for (Iterator it = edges.iterator(); it.hasNext();)
		{
			Contribution c = (Contribution) it.next();
			if(c.equals(aEdge))
			{
				return true;
			}
			
		}
		
		return false;		
	}
	
	protected boolean isAdditionalEdgesSupported()
	{
		return edges.size() < cityList.length;
	}
	
	protected void createEdge(City fromCity, City toCity)
	{		
		// swap as needed (prefer small->big in terms of city numbers)
		if(fromCity.getCityNumber() > toCity.getCityNumber())
		{
			City tmp = fromCity;
			fromCity = toCity;
			toCity = tmp;
		}
		// create a contribution
		Contribution contrib = new Contribution(fromCity, toCity, 1);
		// check if the edge exists
		if(isEdgeDefined(contrib))
		{
			return;
		}		
		
		// store		
		edges.add(contrib);
		// event
		triggerEdgeDefinitionListenerEvent(contrib);
		log.info("A contribution was made, total="+edges.size()+", contrib="+contrib);
		
		// check for complete
		if(!isAdditionalEdgesSupported())
		{
			userScore = TSPUtils.calculateContributionDistance(edges);
			clearCurrentCity();
		}
	}
	
	protected void clearCurrentCity()
	{
		currentlySelectedCity = null;
		cityIsSelected = false;
	}
	
	protected void processMouseMotion(Point aPosition)
	{
		// ensure additional edges are supported
		if(!isAdditionalEdgesSupported())
		{
			return;
		}
		
		currentMousePosition = aPosition;
		highlightClosestCity(aPosition);
		repaint();
	}
	
	protected class MyMouseAdapter extends MouseAdapter
	{
	    public void mousePressed(MouseEvent evt)
		{			
			if(wasLeftClick(evt))
			{
				processMouseClick(evt.getPoint());
			}
			else if(wasRightClick(evt))
			{
				toggleNNVisible();
			}
		}	    
	    
	    public boolean wasLeftClick(MouseEvent evt)
	    {
	    	if ((evt.getModifiers() & InputEvent.BUTTON1_MASK) != 0) 
	    	{
                return true;
            }
	    	
	    	return false;
	    }
	    public boolean wasRightClick(MouseEvent evt)
	    {
	    	if ((evt.getModifiers() & InputEvent.BUTTON3_MASK) != 0) 
	    	{
                return true;
            }
	    	
	    	return false;
	    }
	}
	
	protected class MyMouseMoutionAdapter extends MouseMotionAdapter
	{
		public void mouseMoved(MouseEvent evt)
		{
			processMouseMotion(evt.getPoint());
		}		
	}	
}
