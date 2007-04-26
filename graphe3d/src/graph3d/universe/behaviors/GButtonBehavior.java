package graph3d.universe.behaviors;

import java.util.Enumeration;

import javax.media.j3d.TransformGroup;
import javax.vecmath.Vector3f;

public class GButtonBehavior extends GOneActionBehavior {

	
	public GButtonBehavior (TransformGroup TG, Vector3f _camera, double angleX, double angleY) {
		this.setTransformGroup(TG);
		this.setCamera(_camera);
		this.setAngles(angleX,angleY);
	}
	
	/**
	 * appel de la methode zoom de la classe view
	 * zoom plus de 1
	 *//*
	public void zoomMore(){
		this.zoom(0.95f);
	}
	
	*//**
	 * appel de la methode zoom de la classe view
	 * zoom moins de 1
	 *//*
	public void zoomLess(){
		this.zoom(1.05f);
	}
	
	*//**
	 * 
	 *//*
	public void rotateTop(){
		this.getTransformGroup().setTransform(this.rotateY(0.1f));
	}
	
	*//**
	 * 
	 *//*
	public void rotateBottom(){
		this.getTransformGroup().setTransform(this.rotateY(-0.1f));
	}
	
	*//**
	 * 
	 *//*
	public void rotateLeft(){
		this.getTransformGroup().setTransform(this.rotateX(0.1f));
	}
	
	*//**
	 * 
	 *//*
	public void rotateRight(){
		this.getTransformGroup().setTransform(this.rotateX(-0.1f));
	}*/

	@Override
	public void initialize() {
		
	}

	@Override
	public void processStimulus(Enumeration arg0) {
		
	}
}


