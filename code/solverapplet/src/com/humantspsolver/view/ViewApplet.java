// jason brownlee, 12/05/2008

package com.humantspsolver.view;

import java.awt.BorderLayout;
import java.util.logging.Logger;

import javax.swing.JApplet;

import com.humantspsolver.AppletUtilities;
import com.humantspsolver.City;
import com.humantspsolver.Contribution;
import com.humantspsolver.SolverAppletException;

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
public class ViewApplet extends JApplet
{
	protected static Logger log = Logger.getLogger(ViewApplet.class.getName());	

	// state
	protected City [] cityList;
	protected Contribution [] contributionList;
	
	public void init()
	{
		try
		{
			// collect problem details
			initialiseProblemDefinition();
			// build the GUI
			buildGui();
		}
		catch(SolverAppletException se)
		{
			log.severe("A fatal error has occured: "+ se.getMessage());
			log.throwing(this.getClass().getName(), "init", se);
		}			
	}
	
	protected void initialiseProblemDefinition()
	{
		// load coordinates
		cityList = AppletUtilities.loadCoordinateData(this);
		// load contributions
		contributionList = AppletUtilities.loadContributionData(this, cityList);
	}
	
	protected void buildGui()
	{
		AggregateTSPDisplay panel = new AggregateTSPDisplay();
		panel.setCityList(cityList);
		panel.setContributions(contributionList);
		
		setLayout(new BorderLayout());
		add(panel, BorderLayout.CENTER);
	}	
}
