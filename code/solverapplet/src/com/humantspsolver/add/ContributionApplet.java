// jason brownlee, 08/05/2008

package com.humantspsolver.add;

import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.logging.Logger;

import javax.swing.JApplet;

import com.humantspsolver.AppletUtilities;
import com.humantspsolver.City;
import com.humantspsolver.Contribution;
import com.humantspsolver.EdgeDefinitionListener;
import com.humantspsolver.SolverAppletException;

public class ContributionApplet extends JApplet
	implements EdgeDefinitionListener
{	
	protected static Logger log = Logger.getLogger(ContributionApplet.class.getName());	
		
	protected ArrayList edges;
	protected City [] cityList;
	protected ContributionTSPDisplay map;
	
	public ContributionApplet()
	{}
	
	public void init() 
	{
		try
		{
			// load the problem
			loadProblemDefinition();
			// prepare the GUI
			buildGui();
		}
		catch(SolverAppletException se)
		{
			log.severe("A fatal error has occured: "+ se.getMessage());
			log.throwing(this.getClass().getName(), "init", se);
		}
	}
	
	protected void loadProblemDefinition()
	{		
		cityList = AppletUtilities.loadCoordinateData(this);
		edges = new ArrayList(cityList.length);
	}
	
	protected void buildGui()
	{
		// prepare the interface
		map = new ContributionTSPDisplay();
		map.addEdgeDefinitionListener(this);
		map.setCityList(cityList);
		// add to the applet		
		setLayout(new BorderLayout());
		add(map, BorderLayout.CENTER);		
	}	
	
	
	public void addEdge(Contribution contrib)
	{
		if(edges.size() > cityList.length)
		{
			throw new SolverAppletException("Unable to add edge, already exceeded quota " + cityList.length);
		}
		edges.add(contrib);
	}
	
	protected Contribution retrieveRequestedContribution(int aEdgeNumber)
	{
		if(aEdgeNumber < 0 || aEdgeNumber > cityList.length)
		{
			throw new SolverAppletException("Unable to retrieve edge information, city number out of bounds: "+aEdgeNumber+", length="+cityList.length);
		}
		
		Contribution contrib = null;
		
		// check that that requested edge number exists
		if(edges.size() > aEdgeNumber)
		{
			contrib = (Contribution) edges.get(aEdgeNumber);			
		}
		
		return contrib;
	}
	
	public String getEdgeFromCity(int aEdgeNumber)
	{
		String response = ""; // empty by default
		
		// get the edge
		Contribution contrib = retrieveRequestedContribution(aEdgeNumber);
		if(contrib != null)
		{
			int num = contrib.getFromCity().getCityNumber();
			response = Integer.toString(num);
		}
		
		return response;
	}
	
	public String getEdgeToCity(int aEdgeNumber)
	{
		String response = ""; // empty by default
		
		// get the edge
		Contribution contrib = retrieveRequestedContribution(aEdgeNumber);
		if(contrib != null)
		{
			int num = contrib.getToCity().getCityNumber();
			response = Integer.toString(num);
		}
		
		return response;
	}	
}


