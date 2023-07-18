// jason brownlee, 19/05/2008

package com.humantspsolver;

import java.awt.geom.Point2D;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;


/**
 * Description: 
 *  
 * @author Jason Brownlee 
 *
 * <br/>
 * <pre>
 * Change History
 * ----------------------------------------------------------------------------
 * 
 * </pre>
 */
public class TSPUtils
{
	public static int NUM_SAMPLES = 30;
	
	
	
	public static City [] getBestNNSolution(City [] cityList, Random r)
	{
		int attempts = (int) Math.min(cityList.length, NUM_SAMPLES);
		
		double [][] distanceMatrix = calculateDistanceMatrix(cityList);
		double best = Double.POSITIVE_INFINITY;
		City [] nnSolution = null;
		
		for (int i = 0; i < attempts; i++)
		{
			City [] tmpList = null;
			
			if(attempts==cityList.length)
			{
				// enumerate each starting city
				tmpList = calculateNearestNeighbourSolution(cityList, distanceMatrix, i, r);
			}
			else
			{
				// random sample with re-selection
				tmpList = calculateNearestNeighbourSolution(cityList, distanceMatrix, r);
			}			
			
			double tmpScore = calculateTourDistance(tmpList);
			if(tmpScore < best)
			{
				best = tmpScore;
				nnSolution = tmpList;
			}
		}
		
		return nnSolution;
	}
	
	public static double calculateContributionDistance(LinkedList edges)
	{		
		double sum = 0;
		
		for (Iterator it = edges.iterator(); it.hasNext();)
		{
			Contribution c = (Contribution) it.next();
			sum += distance(c.getFromCity(), c.getToCity());
		}
		
		return sum;
	}
	
	public static double calculateTourDistance(City [] cityList)
	{
		double sum = 0;
		
		for (int i = 1; i <= cityList.length; i++)
		{
			// check for last step
			if(i == cityList.length)
			{
				City from = cityList [i-1];
				City to = cityList [0];
				sum += distance(from, to);
			}
			else
			{
				City from = cityList [i-1];
				City to = cityList [i];
				sum += distance(from, to);
			}
		}
		
		return sum;
	}
	
	public static City [] calculateNearestNeighbourSolution(City [] cityList, double [][] distanceMatrix, Random r)
	{        
		int start = r.nextInt(cityList.length);
		return calculateNearestNeighbourSolution(cityList, distanceMatrix, start, r);
	}
	
	public static City [] calculateNearestNeighbourSolution(			
			City [] cityList, 
			double [][] distanceMatrix, 
			int startingCity,
			Random r)
	{        
        City [] permutation = new City[cityList.length];
        HashSet set = new HashSet();
        
        int fromCity = startingCity;
        permutation[0] = cityList[fromCity];
        set.add(new Integer(fromCity));
        
        for (int i = 1; i < permutation.length; i++)
        {        	
        	int toCity = -1;
        	double toCityDistance = Double.POSITIVE_INFINITY; // minimising 
        	
        	
        	// check all distances from the from city
            for (int j = 0; j < distanceMatrix[fromCity].length; j++)
            {
            	Integer jthCity = new Integer(j);
            	
                if(!set.contains(jthCity))
                {
                	// check for equal distance
                    if(distanceMatrix[fromCity][j] == toCityDistance)
                    {
                    	// random conflict resolution
                    	if(r.nextBoolean())
                    	{
                    		toCityDistance = distanceMatrix[fromCity][j];
                    		toCity = j;
                    	}
                    }
                    // check for smaller distance
                    else if(distanceMatrix[fromCity][j] < toCityDistance)
                    {
                    	toCityDistance = distanceMatrix[fromCity][j];
                    	toCity = j;
                    }
                }
            }
            // safety check, ensure that a city was selected
            if(toCity == -1)
            {
                throw new SolverAppletException("Failed to select city in NN solution generation, city["+i+"] of " + permutation.length);
            }
            
            fromCity = toCity;
            permutation[i] = cityList[toCity];
            set.add(new Integer(toCity)); // cannot revisit this city
        }        
        
        return permutation;
	}	
	
	public static double [][] calculateDistanceMatrix(City [] cityList)
	{
		double [][] distanceMatrix = new double[cityList.length][cityList.length];
        
        for (int x = 0; x < cityList.length; x++)
        {
        	// lame
            for (int y = 0; y < cityList.length; y++)
            {
                distanceMatrix[x][y] = distance(cityList[x], cityList[y]);
            }
        }
        
        return distanceMatrix;
	}
	
	public static double distance(City x, City y)
	{
		Point2D p1 = x.getPoint();
		Point2D p2 = y.getPoint();
		
		return distance(p1, p2);
	}
	
	public static double distance(Point2D p1, Point2D p2)
	{		
		double diffX = p1.getX() - p2.getX();
		double diffY = p1.getY() - p2.getY();
		
		return Math.sqrt((diffX*diffX) + (diffY*diffY));
	}	
}
