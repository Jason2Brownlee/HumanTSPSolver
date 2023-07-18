// jason brownlee, 12/05/2008

package com.humantspsolver.test;

import java.awt.Dimension;
import java.util.Random;

import com.humantspsolver.City;
import com.humantspsolver.Contribution;
import com.humantspsolver.GUIUtils;
import com.humantspsolver.view.AggregateTSPDisplay;

public class AggregateViewTest
{
	public static void main(String[] args)
	{
		double factor = 1000; // large factor
		int numPoints = 100;
		int numContributions = 50;
		
		// prepare data
		City [] cityList = TSPDisplayTest.generateTestData(numPoints, factor);
		Contribution [] contributionList = generateTestData(cityList, numContributions, 1000);
		// prepare GUI
		AggregateTSPDisplay panel = new AggregateTSPDisplay();
		panel.setCityList(cityList);
		panel.setContributions(contributionList);
		// display
		GUIUtils.testJFrame(panel, "Test TSPDisplayContributions", new Dimension(500, 500));
	}
	
	public static Contribution [] generateTestData(City [] cityList, int numContributions, double multiplicationFactor)
	{		
		Contribution [] contributionList = new Contribution[numContributions];
		Random rand = new Random(System.currentTimeMillis());
		                
		for (int i = 0; i < contributionList.length; i++)
		{
			int from, to;
			
			// must be distinct
			do
			{			
				from = rand.nextInt(cityList.length);
				to = -1;			
				do
				{
					to = rand.nextInt(cityList.length);
				}
				while(from == to);
				
				// must always be small->big
				if(from > to)
				{
					int tmp = from;
					from = to;
					to = tmp;
				}
	
			}
			while(exists(from, to, contributionList));		

			
			long count = Math.round(multiplicationFactor * Math.abs(rand.nextGaussian()));
			
			if(count == 0)
			{
				count = 1;
			}
			else if(count < 0)
			{
				throw new RuntimeException("Generated an invalid count: " + count);
			}
			
			contributionList[i] = new Contribution(cityList[from], cityList[to], count);
			System.out.println("> " + contributionList[i]);
		}
		
		return contributionList;
	}
	
	public  static boolean exists(int from, int to, Contribution [] list)
	{
		for (int i = 0; i < list.length; i++)
		{
			if(list[i] == null)
			{
				break;
			}
			else if(list[i].getFromCity().getCityNumber() == from)
			{
				if(list[i].getToCity().getCityNumber() == to)
				{
					return true;
				}
			}
		}
		
		return false;
	}
}
