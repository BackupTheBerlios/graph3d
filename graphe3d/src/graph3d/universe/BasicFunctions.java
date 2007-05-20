package graph3d.universe;

import graph3d.elements.GGraph;
import graph3d.elements.GNode;

import java.util.Enumeration;
import java.util.Hashtable;

import javax.media.j3d.Transform3D;
import javax.vecmath.Vector3f;

public final class BasicFunctions {

	public static float getLength(float[] node) {
		return getLengthBetween(new float[] { 0, 0, 0 }, node);
	}

	public static float getLengthBetween(float[] one, float[] second) {
		return (float) Math.sqrt(Math.pow(one[0] - second[0], 2)
				+ Math.pow(one[1] - second[1], 2)
				+ Math.pow(one[2] - second[2], 2));
	}

	public static float[] calcBarycenter(Hashtable<String, GNode> nodes) {
		float[] barycenter = new float[] { 0, 0, 0 };
		float radiusSum = 0;
		Enumeration<String> keys = nodes.keys();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			GNode node = nodes.get(key);
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

	public static float[] calcBarycenterBetweenToNodes(GNode firstNode,
			GNode secondNode) {

		float[] barycenter = new float[] { 0, 0, 0 };

		// node1
		barycenter[0] += firstNode.getCoordonnateX();
		barycenter[1] += firstNode.getCoordonnateY();
		barycenter[2] += firstNode.getCoordonnateZ();

		// node 2
		barycenter[0] += secondNode.getCoordonnateX();
		barycenter[1] += secondNode.getCoordonnateY();
		barycenter[2] += secondNode.getCoordonnateZ();

		barycenter[0] /= 2;
		barycenter[1] /= 2;
		barycenter[2] /= 2;

		return barycenter;
	}

	public static void createBestView(GGraphUniverse universe) {

		float fieldOfView = (float) universe.getViewingPlatform().getViewers()[0]
				.getView().getFieldOfView();
		float[] eyePosition =getBestPointToSee(fieldOfView, universe.getGraph().getNodes());
		
		// définition de la vue
		Transform3D eye = new Transform3D();
		eye.setTranslation(new Vector3f(eyePosition[0], eyePosition[1], eyePosition[2]));
		universe.getViewingPlatform().getViewPlatformTransform().setTransform(
				eye);
		universe.getViewingPlatform().getViewers()[0].getView()
				.setBackClipDistance(eyePosition[2] - eyePosition[3] + 10);

	}

	public static void createBestView(GGraphUniverse universe,
			Hashtable<String, GNode> nodes) {

		float fieldOfView = (float) universe.getViewingPlatform().getViewers()[0].getView().getFieldOfView();
		
		float[] eyePosition = getBestPointToSee(fieldOfView, nodes);

		// définition de la vue
		Transform3D eye = new Transform3D();
		eye.setTranslation(new Vector3f(eyePosition[0], eyePosition[1], eyePosition[2]));
		universe.getViewingPlatform().getViewPlatformTransform().setTransform(eye);

	}

	private static float[] getBestPointToSee(float fieldOfView,
			Hashtable<String, GNode> nodes) {
	
		float[] eyePosition = new float[] {0, 0, 0, 0};
		
		float minX = 0;
		float maxX = 0;
		float minY = 0;
		float maxY = 0;
		float minZ = 0;
		float maxZ = 0;
		float maxRadius = 0;
	
		Enumeration<String> keys = nodes.keys();
	
		boolean first = true;
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			GNode node = nodes.get(key);
			if (first) { // if it's the first time which the part is
				// executed.
				minX = node.getCoordonnateX();
				maxX = node.getCoordonnateX();
				minY = node.getCoordonnateY();
				maxY = node.getCoordonnateY();
				minZ = node.getCoordonnateZ();
				maxZ = node.getCoordonnateZ();
				first = false;
			} else if (minX > node.getCoordonnateX()) {
				minX = node.getCoordonnateX();
			} else if (maxX < node.getCoordonnateX()) {
				maxX = node.getCoordonnateX();
			}
			if (minY > node.getCoordonnateY()) {
				minY = node.getCoordonnateY();
			} else if (maxY < node.getCoordonnateY()) {
				maxY = node.getCoordonnateY();
			}
			if (minZ > node.getCoordonnateZ()) {
				minZ = node.getCoordonnateZ();
			} else if (maxZ < node.getCoordonnateZ()) {
				maxZ = node.getCoordonnateZ();
			}
			if (maxRadius < node.getRadius()) {
				maxRadius = node.getRadius();
			}
		}
	
		// construction des points extrèmes qui sont le plus proche de la caméra
		// si ces points passent dans la vue les autres points existant aussi.
		float[] xyZ = new float[] { minX, minY, maxZ };
		float[] xYZ = new float[] { minX, maxY, maxZ };
		// float[] XYZ = new float [] {maxX, maxY, maxZ}; ce point n'est pas
		// utilsé d'où le commentaire
		float[] XyZ = new float[] { maxX, minY, maxZ };
	
		// calcul du barycentre de ces points pour connaitre X et Y que l'on
		// recherche pour la caméra
		float[] clippingBarycenter = new float[] { (minX + maxX) / 2,
				(minY + maxY) / 2, maxZ };
	
		// calcul de la distance nécessaire pour voir les 4 points
		// cette distance correspond à la distance entre le barycentre
		// précédemment calculé et le point où doit se situer la caméra.
		// en effet :
		//
		// * E D *
		// * C *
		// * *
		// * A M B *
		// * *
		// * *
		// * *
		// * *
		// C
		//
		// si A et B sont contenu dans le champ de vision alors obligatoirement,
		// les points possédant des coordonnées qui ne sont pas supérieurs
		// seront aussi présent dans le champ de vision
	
		// la base correspond à la longueur entre le barycentre et l'extrémité
		// du champ de vision.
		// cette extrémité se trouve sur l'un des 4 côté que forme les 4 points
		// précédemment calculés.
	
		// sachant que les 4 points qui ont été calculés représente le sommet
		// d'un rectangle
		// la base du triangle n'est pas obligatoirement égale entre tous les
		// côtés.
		// C'est pourquoi il faut calculer deux bases et prendre la plus longue.
	
		// première base
		float[] base1 = new float[3];
		// calcul de X de la base
		base1[0] = xyZ[0] + ((xYZ[0] - xyZ[0]) / 2);
		// calcul de Y de la base
		base1[1] = xyZ[1] + ((xYZ[1] - xyZ[1]) / 2);
		// calcul de Z de la base
		base1[2] = xyZ[2] + ((xYZ[2] - xyZ[2]) / 2);
	
		// second base
		float[] base2 = new float[3];
		// calcul de X de la base
		base2[0] = xyZ[0] + ((XyZ[0] - xyZ[0]) / 2);
		// calcul de Y de la base
		base2[1] = xyZ[1] + ((XyZ[1] - xyZ[1]) / 2);
		// calcul de Z de la base
		base2[2] = xyZ[2] + ((XyZ[2] - xyZ[2]) / 2);
	
		// calcul de la longueur de la plus longue base
		float lengthBetween = 0;
		if (BasicFunctions.getLengthBetween(clippingBarycenter, base1) > BasicFunctions
				.getLengthBetween(clippingBarycenter, base2)) {
			lengthBetween = BasicFunctions.getLengthBetween(clippingBarycenter,
					base1);
		} else {
			lengthBetween = BasicFunctions.getLengthBetween(clippingBarycenter,
					base2);
		}
	
		float length = (float) (lengthBetween / Math.tan(fieldOfView / 2));
	
		eyePosition[0] = clippingBarycenter[0];
		eyePosition[1] = clippingBarycenter[1];
		eyePosition[2] = clippingBarycenter[2] + length + (20 * maxRadius);// ajout d'une marge
		// par rapport au
		// rayon des spheres
		eyePosition[3] = minZ;
		
		return eyePosition;
	
	}
}
