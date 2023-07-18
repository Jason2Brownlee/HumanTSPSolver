// jason brownlee, 12/05/2008

package com.humantspsolver;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.JFrame;
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
public class GUIUtils
{
    /**
     * Centre the provided component, useful for dialogs and frames.
     * @param c
     */
    public static void centerComponent(Component c)
    {
        Dimension dim = c.getToolkit().getScreenSize();
        Rectangle abounds = c.getBounds();
        c.setLocation((dim.width - abounds.width) / 2, (dim.height - abounds.height) / 2);
    }
    
    
    /**
     * Creates a test JFrame for the provided panel
     * @param panel
     * @param name
     * @param size
     */
    public static void testJFrame(JPanel panel, String name)
    {		
		JFrame frame = new JFrame(name);
		frame.add(panel);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		centerComponent(frame);    	
    }
    
    /**
     * Creates a test JFrame for the provided panel
     * @param panel
     * @param name
     * @param size
     */
    public static void testJFrame(JPanel panel, String name, Dimension aSize)
    {		
		JFrame frame = new JFrame(name);
		frame.add(panel);
		frame.setSize(aSize);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		centerComponent(frame);    	
    }
}
