package graph3d.universe;

import graph3d.elements.GGraph;
import graph3d.elements.GNode;

import java.util.Enumeration;

public final class BasicFunctions {
	
	public static float getLengthBetween(float[] one, float[] second) {
		return (float) Math.sqrt(Math.pow(one[0] - second[0], 2) + Math.pow(one[1] - second[1], 2) + Math.pow(one[2] - second[2], 2));
	}
	
	public static float[] calcBarycenter(GGraph graph) {
		float[] barycenter = new float[] {0, 0, 0};
		float radiusSum = 0;
		Enumeration<String> keys = graph.getNodes().keys();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			GNode node = graph.getNode(key);
			barycenter[0] += node.getCoordonnateX() * node.getRadius();
			barycenter[1] += node.getCoordonnateY() * node.getRadius();
			barycenter[2] += node.getCoordonnateZ() * node.getRadius();
			radiusSum += node.getRadius();
		}
		if (barycenter[0] != 0) {
			barycenter[0] /= radiusSum;
		}
		if (barycenter[1] != 0) {
			barycenter[1] /= radiusSum;
		}
		if (barycenter[2] != 0) {
			barycenter[2] /= radiusSum;
		}
		return barycenter;
	}
}
