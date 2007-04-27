package graph3d.universe.behaviors;

import graph3d.universe.GView;

import java.util.Enumeration;

/**
 * This class is use to call all function define into GOneActionBehavior to rotate or zoom into the view
 * @author Erwan Daubert
 * @version 1.0
 *
 */
public class GButtonBehavior extends GOneActionBehavior {

	/**
	 * The constructor of this class
	 * @param _view the view which you want rotate or zoom
	 * 
	 */
	public GButtonBehavior (GView _view) {
		this.setGview(_view);
	}

	@Override
	public void initialize() {}

	@Override
	public void processStimulus(Enumeration arg0) {}
}


