package graph3d.universe;

import graph3d.elements.GGraph;
import graph3d.elements.GLink;
import graph3d.elements.GNode;
import graph3d.lists.GAttributesList;
import graph3d.lists.GConnectionsList;
import graph3d.universe.behaviors.SelectionBehavior;

import java.awt.Color;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;

import javax.media.j3d.Background;
import javax.media.j3d.BoundingBox;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.Group;
import javax.media.j3d.Locale;
import javax.media.j3d.VirtualUniverse;

/**
 * This class define a GGrapheUniverse.
 */
public class GGrapheUniverse extends VirtualUniverse{
	
	private Locale locale;
	private GGraph graph;
	private GView view;
	private Hashtable<String, BranchGroup> ComponentsView;
	
	private BranchGroup scene;

	/**
	 * This constructor is used to create a GGrapheUniverse.
	 * @param _graph of type GGraph.
	 */
	public GGrapheUniverse(GGraph _graph){
		this.locale = new Locale(this);
		this.graph = _graph;
		this.ComponentsView = new Hashtable<String, BranchGroup>();
		this.createScene();
		this.createView();
	}
	
	/**
	 * This function is used to create the scene.
	 */
	private void createScene() {
		this.scene = new BranchGroup();
		
		this.scene.setCapability(Group.ALLOW_CHILDREN_EXTEND);
		this.scene.setCapability(Group.ALLOW_CHILDREN_WRITE);
		this.scene.setCapability(Group.ALLOW_CHILDREN_READ);
		
		Hashtable<String, GNode> nodes = this.graph.getNodes();
		Enumeration<String> keys = nodes.keys();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			this.addGNode(this.graph.getNode(key));
		}
		Hashtable<String, GLink> links = this.graph.getLinks();
		keys = links.keys();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			this.addGLink(links.get(key));
		}
	}
	/**
	 * This function is used to create the view.
	 */
	private void createView() {
		this.view = new GView();
		this.createBestView();
		this.view.addMouseListener();
		this.view.addKeyListener();
	}
	
	private void createBestView() {		
		float[] bestPointToSee = new float[3];
		float fieldOfView = this.view.getFieldOfView();
		
		float minX=0, maxX = 0, minY = 0, maxY = 0, minZ = 0, maxZ = 0;
		
		Enumeration<String> keys = this.graph.getNodes().keys();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			GNode node = this.graph.getNode(key);
			
			if (minX > node.getCoordonnateX()) {
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
		}
		
		//construction des points extrèmes qui sont le plus proche de la caméra
		//si ces points passent dans la vue les autres points existant aussi.
		float[] xyZ = new float [] {minX, minY, maxZ};
		float[] xYZ = new float [] {minX, maxY, maxZ};
		//float[] XYZ = new float [] {maxX, maxY, maxZ}; ce point n'est pas utilsé d'où le commentaire
		float[] XyZ = new float [] {maxX, minY, maxZ};
		
		
		//calcul du barycentre de ces points pour connaitre X et Y que l'on recherche pour la caméra
		float[] barycenter = new float[] {(minX + maxX) / 2, (minY + maxY) / 2, maxZ};
		
		//calcul de la distance nécessaire pour voir les 4 points
		//cette distance correspond à la distance entre le barycentre précédemment calculé et le point où doit se situer la caméra.
		// en effet :
		//
		// *    E    D     *
		//	*     C       *
		//	 *           *
		//    * A  M  B *
		//	   *       *
		//	    *     *
		//       *   *
		//        * *
		//		   C
		//
		// si A et B sont contenu dans le champ de vision alors obligatoirement, 
		//les points possédant des coordonnées qui ne sont pas supérieurs seront aussi présent dans le champ de vision
		
		//la base correspond à la longueur entre le barycentre et l'extrémité du champ de vision.
		// cette extrémité se trouve sur l'un des 4 côté que forme les 4 points précédemment calculés.
		
		//sachant que les 4 points qui ont été calculés représente le sommet d'un rectangle
		//la base du triangle n'est pas obligatoirement égale entre tous les côtés.
		//C'est pourquoi il faut calculer deux bases et prendre la plus longue.
		
		//première base
		float[] base1 = new float[3] ;
		//calcul de X de la base
		base1[0] = xyZ[0] + ((xYZ[0] - xyZ[0]) / 2);
		//calcul de Y de la base
		base1[1] = xyZ[1] + ((xYZ[1] - xyZ[1]) / 2);
		//calcul de Z de la base
		base1[2] = xyZ[2] + ((xYZ[2] - xyZ[2]) / 2);
		
		//second base
		float[] base2 = new float[3] ;
		//calcul de X de la base
		base2[0] = xyZ[0] + ((XyZ[0] - xyZ[0]) / 2);
		//calcul de Y de la base
		base2[1] = xyZ[1] + ((XyZ[1] - xyZ[1]) / 2);
		//calcul de Z de la base
		base2[2] = xyZ[2] + ((XyZ[2] - xyZ[2]) / 2);
		
		//calcul de la longueur de la plus longue base
		float lengthBetween = 0;
		if (this.getLengthBetween(barycenter, base1) > this.getLengthBetween(barycenter, base2)) {
			lengthBetween = this.getLengthBetween(barycenter, base1);
		} else {
			lengthBetween = this.getLengthBetween(barycenter, base2);
		}
		
		float length = (float)(lengthBetween / Math.tan(fieldOfView/ 2));
		
		bestPointToSee[0] = barycenter[0];
		bestPointToSee[1] = barycenter[1];
		bestPointToSee[2] = barycenter[2] + length /*+ 1*/;// ajout d'une marge par rapport au rayon des spheres
		
		//définition de la vue
		this.view.putOnBestPointToSee(bestPointToSee);
		
		// définition de la profondeur de la vue avec une marge de 1
		// la profonceur correspond à la distance jusqu'à laquelle les éléments vont apparaître dans la vue.
		// distance entre la caméra et l'élément qui possède le plus petit Z comme coordonnées
		// + une marge de 1 pour que la sphere apparaisse complètement
		this.view.setBackClipDistance(bestPointToSee[2] - minZ + 1);
	}
	
	private float getLengthBetween(float[] one, float[] second) {
		return (float) Math.sqrt(Math.pow(one[0] - second[0], 2) + Math.pow(one[1] - second[1], 2) + Math.pow(one[2] - second[2], 2));
	}
	
	/**
	 * The getter of the Canvas3D which containts the scene compiled.
	 * @return a Canvas3D component.
	 */
	public Canvas3D getCanvas() {
		this.scene.compile();
		
		this.locale.addBranchGraph(this.view);
		this.locale.addBranchGraph(this.scene);
		
		return this.view.getCanvas();
	}
	
	/**
	 * This function is used to add a GNode to the scene.
	 * @param node of type GNode.
	 */
	public void addGNode(GNode node){
		GNodeView nodeView = new GNodeView(node);
		this.scene.addChild(nodeView);
		this.ComponentsView.put(node.getName(), nodeView);
	}

	/**
	 * This function is used to add a GLink (arrow or bridge) to the scene.
	 * @param link of type GLink.
	 */
	public void addGLink(GLink link){
		GLinkView linkView;
		if (link.isType()) {
			linkView = new GArrowView(link);
		} else {
			linkView = new GBridgeView(link);
		}
		this.scene.addChild(linkView);
		this.ComponentsView.put(link.getName(), linkView);
	}
	
	/**
	 * This function is used to delete a GNode to the scene.
	 * @param node of type GNode.
	 */
	public void deleteGNode(String nodeName){
		this.scene.removeChild(this.ComponentsView.get(nodeName));
		this.graph.removeNode(nodeName);
		this.ComponentsView.remove(nodeName);
	}
	
	/**
	 * This function is used to delete a GLink (arrow or bridge) to the scene.
	 * @param link of type GLink.
	 */
	public void deleteGLink(String linkName){
		scene.removeChild(this.ComponentsView.get(linkName));
		this.graph.removeLink(linkName);
		this.ComponentsView.remove(linkName);
	}
	
	public void updateGNode(String nodeName) {
		GNodeView nodeView = (GNodeView) this.ComponentsView.get(nodeName);
		nodeView.update();
		GNode node = this.graph.getNode(nodeName);
		for (Iterator<GLink> i = node.getLinks().iterator(); i.hasNext();) {
			GLink link = i.next();
			if (link.isType()) {
				((GArrowView)this.ComponentsView.get(link.getName())).update();
			} else {
				((GBridgeView)this.ComponentsView.get(link.getName())).update();
			}
		}
		
	}
	
	/**
	 * This function is used to add a background color to the scene.
	 * @param color of type Color.
	 */
	public void addBackground(Color color){
	    Background background = new Background(color.getRed(),color.getGreen(),color.getBlue());
	    background.setApplicationBounds(new BoundingBox());
	    scene.addChild(background);
	}

	public GGraph getGraph() {
		return graph;
	}

	public void setGraph(GGraph graph) {
		this.graph = graph;
	}
	
	public void addSelectionBehavior(GAttributesList _attributesList, GConnectionsList _connectionsList) {
		Enumeration<String> keys = this.ComponentsView.keys();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			BranchGroup componentView = this.ComponentsView.get(key);
			if (componentView instanceof GNodeView) {
				((GNodeView)componentView).addSelectionBehavior(_attributesList, _connectionsList);
			} else {
				((GLinkView)componentView).addSelectionBehavior(_attributesList, _connectionsList);
			}
		}
	}
	
	/**
	 * 
	 */
	/*public void rotateTop(){
		this.view.rotateY(-0.1f);
	}
	
	/**
	 * 
	 */
	/*public void rotateBottom(){
		this.view.rotateY(0.1f);
	}
	
	/**
	 * 
	 */
	/*public void rotateLeft(){
		this.view.rotateX(0.1f);
	}
	
	/**
	 * 
	 */
	/*public void rotateRight(){
		this.view.rotateX(-0.1f);
	}*/	
	
}
