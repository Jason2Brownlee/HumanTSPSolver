// jason brownlee, 12/05/2008

package com.humantspsolver.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

import com.humantspsolver.BaseTSPDisplay;
import com.humantspsolver.Contribution;

public class AggregateTSPDisplay extends BaseTSPDisplay
{	
	// contributions count
	protected Contribution [] contributions;
	protected long maximumContributionsCount;
	
	public AggregateTSPDisplay()
	{}
	
	protected void drawPreCity(Graphics2D g2d)
	{
		drawAllEdges(g2d);
	}
	
	protected void drawAllEdges(Graphics2D g2d)
	{
		for (int i = 0; i < contributions.length; i++)
		{
			// calculate ratio
			double ratio = (double)contributions[i].getCount() / (double)maximumContributionsCount;
			// draw edge
			drawEdge(g2d, contributions[i], ratio);
		}
	}
			
	protected void drawEdge(Graphics2D g2d, Contribution contribution, double ratio)
	{
		// get the view points
		Point2D from = getViewPointForCity(contribution.getFromCity());
		Point2D to = getViewPointForCity(contribution.getToCity());
		// prepare the line
		Line2D edgeLine = new Line2D.Double(from, to);		
		// prepare the stroke
		//Stroke stroke = contributionRatioToStroke(ratio);
		Stroke stroke = new BasicStroke(1.5f);		
		
		double darkness = 50.0+(ratio*205); // 50 -> 255
		
		Color c = new Color(0, 0, 255, (int)darkness);
		g2d.setColor(c);
		
		Shape strokedLine = stroke.createStrokedShape(edgeLine);
		g2d.fill(strokedLine); 
	}	
	
	protected void calculateMaximumContributionCount()
	{
		maximumContributionsCount = 0;
		for (int i = 0; i < contributions.length; i++)
		{
			if(contributions[i].getCount() > maximumContributionsCount)
			{
				maximumContributionsCount = contributions[i].getCount();
			}
		}
	}
	
	
	protected double getCitySizeFactor()
	{
		return 0.02;
	}
	

	public Contribution[] getContributions()
	{
		return contributions;
	}

	public void setContributions(Contribution[] contributions)
	{
		this.contributions = contributions;
		calculateMaximumContributionCount();
	}	
}
