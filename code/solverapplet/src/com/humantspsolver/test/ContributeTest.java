// jason brownlee, 12/05/2008

package com.humantspsolver.test;

import java.awt.Dimension;
import java.util.Random;

import com.humantspsolver.City;
import com.humantspsolver.GUIUtils;
import com.humantspsolver.add.ContributionTSPDisplay;

public class ContributeTest
{
	public static void main(String[] args)
	{
		double factor = 10000; // large factor
		int numPoints = 30;
		
		City [] cityList = generateTestData(numPoints, factor); 
		ContributionTSPDisplay panel = new ContributionTSPDisplay();
		panel.setCityList(cityList);
		// display
		GUIUtils.testJFrame(panel, "Test TSPUserDefinedEdges", new Dimension(500, 500));
	}
	
	public static City [] generateTestData(int numPoints, double multiplicationFactor)
	{
		City [] cityList = new City[numPoints];
		Random rand = new Random(System.currentTimeMillis());
		                
		for (int i = 0; i < cityList.length; i++)
		{
			double x = multiplicationFactor * rand.nextDouble();
			double y = multiplicationFactor * rand.nextDouble();
			cityList[i] = new City(x, y, i);
		}
		
		return cityList;
	}
}
