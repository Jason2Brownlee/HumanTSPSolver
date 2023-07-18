// jason brownlee, 12/05/2008

package com.humantspsolver.test;

import java.awt.Dimension;
import java.util.Random;

import com.humantspsolver.BaseTSPDisplay;
import com.humantspsolver.City;
import com.humantspsolver.GUIUtils;

public class TSPDisplayTest
{
	public static void main(String[] args)
	{
		double factor = 1000; // large factor
		int numPoints = 6;
		
		City [] cityList = generateTestData(numPoints, factor); 
		BaseTSPDisplay panel = new BaseTSPDisplay();
		panel.setCityList(cityList);
		// display
		GUIUtils.testJFrame(panel, "Test BaseTSPDisplay", new Dimension(250, 250));
	}
	
	public static City [] generateTestData(int numPoints, double multiplicationFactor)
	{
		City [] cityList = new City[numPoints];
		Random rand = new Random(System.currentTimeMillis());
		                
		for (int i = 0; i < cityList.length; i++)
		{
			double x = multiplicationFactor * rand.nextDouble();
			double y = multiplicationFactor * rand.nextDouble();
			cityList[i] = new City(x, y, 1+i);
		}
		
		return cityList;
	}
}
