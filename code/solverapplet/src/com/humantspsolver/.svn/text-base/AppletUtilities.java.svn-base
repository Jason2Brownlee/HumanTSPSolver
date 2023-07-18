// jason brownlee, 12/05/2008

package com.humantspsolver;

import java.util.logging.Logger;

import javax.swing.JApplet;

public class AppletUtilities
{
	protected static Logger log = Logger.getLogger(AppletUtilities.class.getName());
	
	public static String PARAM_NUM_COORDS = "num_coords";
	public static String PARAM_COORD_DATA = "coord_num_";
	
	
	public static String PARAM_NUM_CONTRIBUTIONS= "num_contributions";
	public static String PARAM_CONTRIBUTION_DATA = "contribution_num_";
	
	
	/**
	 * Load a set of coordinates from an applet context
	 * @param context
	 * @return
	 * @throws SolverAppletException
	 */
	public static City [] loadCoordinateData(JApplet context)
		throws SolverAppletException
	{
		String numCititesString = context.getParameter(PARAM_NUM_COORDS);
		if(numCititesString == null)
		{
			throw new SolverAppletException("Unable to prepare problem definition, parameter not provided"+PARAM_NUM_COORDS);
		}
		
		int numCitites = 0;
		
		try
		{
			numCitites = Integer.parseInt(numCititesString);
		}
		catch(NumberFormatException nfe)
		{
			throw new SolverAppletException("Unable to prepare problem definition, parameter is invalid " +PARAM_NUM_COORDS, nfe);
		}
		
		log.info("Loaded parameter "+PARAM_NUM_COORDS+", expecting "+numCitites+" cities");
		
		// load the cities
		City [] cityList = new City[numCitites];
		for (int i = 0; i < cityList.length; i++)
		{
			// load
			cityList[i] = loadCoordinate(context, i);
		}
		
		return cityList;
	}
	
	/**
	 * Load an city from an applet parameter
	 * @param context
	 * @param aCityNumber
	 * @return
	 * @throws SolverAppletException
	 */
	public static City loadCoordinate(JApplet context, int aCityNumber)
		throws SolverAppletException
	{
		// load the city data
		String cityData = context.getParameter(PARAM_COORD_DATA+aCityNumber);
		if(cityData==null)
		{
			throw new SolverAppletException("Unable to load city "+aCityNumber+", no data provided for parameter: " + PARAM_COORD_DATA+aCityNumber);
		}
		// parse
		String [] parts = cityData.split(":");
		if(parts.length != 3)
		{
			throw new SolverAppletException("Unable to load city "+aCityNumber+", invalid parameter=("+cityData+"), expected=(city:x:y).");
		}
		// extract the parts
		City city = null;
		int cityNumber = 0;
		double x, y = 0;
		try
		{
			cityNumber = Integer.parseInt(parts[0]);
			x = Double.parseDouble(parts[1]);
			y = Double.parseDouble(parts[2]);			
		}
		catch(NumberFormatException e)
		{
			throw new SolverAppletException("Unable to load city "+aCityNumber+", invalid parameter=("+cityData+"), expected=(city:x:y).", e);
		}
		
		// create
		city = new City(x, y, cityNumber);
		log.info("Successfully loaded a city coordinate: "+city);		
		return city;
	}
	
	
	
	
	
	/**
	 * Load a set of contributions from an applet context
	 * @param context
	 * @param cityList
	 * @return
	 * @throws SolverAppletException
	 */
	public static Contribution [] loadContributionData(JApplet context, City [] cityList)
		throws SolverAppletException
	{
		String numContributionsString = context.getParameter(PARAM_NUM_CONTRIBUTIONS);
		if(numContributionsString == null)
		{
			throw new SolverAppletException("Unable to prepare contributions list, parameter not provided "+PARAM_NUM_CONTRIBUTIONS);
		}
		
		int numContributions = 0;		
		try
		{
			numContributions = Integer.parseInt(numContributionsString);
		}
		catch(NumberFormatException nfe)
		{
			throw new SolverAppletException("Unable to prepare contributions list, parameter is invalid "+PARAM_NUM_CONTRIBUTIONS, nfe);
		}
		
		log.info("Loaded parameter "+PARAM_NUM_CONTRIBUTIONS+", expecting "+numContributions+" contributions");
		
		// load the cities
		Contribution [] contributionList = new Contribution[numContributions];
		for (int i = 0; i < contributionList.length; i++)
		{
			// load
			contributionList[i] = loadContribution(context, i, cityList);
		}
		
		return contributionList;
	}
	
	/**
	 * Load a contribution with the specified number
	 * @param context
	 * @param aContributionNumber
	 * @param cityList
	 * @return
	 * @throws SolverAppletException
	 */
	public static Contribution loadContribution(JApplet context, int aContributionNumber, City [] cityList)
		throws SolverAppletException
	{
		// load the city data
		String contributionData = context.getParameter(PARAM_CONTRIBUTION_DATA+aContributionNumber);
		if(contributionData==null)
		{
			throw new SolverAppletException("Unable to load contribution "+aContributionNumber+", no data provided for parameter " + PARAM_CONTRIBUTION_DATA+aContributionNumber);
		}
		// parse [from:to:count]
		String [] parts = contributionData.split(":");
		if(parts.length != 3)
		{
			throw new SolverAppletException("Unable to load contribution "+aContributionNumber+", invalid parameter=("+contributionData+"), expected=(from:to:count).");
		}
		// extract the parts
		Contribution contrib = null;
		int fromCity, toCity = 0;
		long count = 0;
		try
		{
			fromCity = Integer.parseInt(parts[0]);
			toCity = Integer.parseInt(parts[1]);
			count = Long.parseLong(parts[2]);					
		}
		catch(NumberFormatException e)
		{
			throw new SolverAppletException("Unable to load contribution "+aContributionNumber+", invalid parameter=("+contributionData+"), expected=(from:to:count).",  e);
		}
		
		if(fromCity <= 0 || fromCity > cityList.length)
		{
			throw new SolverAppletException("Invalid from city "+fromCity+", must >= 0 && < "+cityList.length);
		}
		else if(toCity <= 0 || toCity > cityList.length)
		{
			throw new SolverAppletException("Invalid to city "+toCity+", must >= 0 && < "+cityList.length);
		}
		else if(toCity == fromCity || fromCity > toCity)
		{
			throw new SolverAppletException("Invalid edge, must have different from and to cities, and be ordered: fromCity < toCity: " + fromCity +" + " + toCity);
		}
		
		contrib = new Contribution(cityList[fromCity-1], cityList[toCity-1], count);		
		log.info("Successfully loaded a contribution coordinate: "+contrib.toString());		
		return contrib;
	}
}
