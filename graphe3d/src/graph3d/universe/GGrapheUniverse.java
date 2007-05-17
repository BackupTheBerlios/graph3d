package graph3d.universe;

import graph3d.elements.GGraph;
import graph3d.elements.GLink;
import graph3d.elements.GNode;
import graph3d.lists.GAttributesList;
import graph3d.universe.behaviors.GBehaviors;
import graph3d.universe.behaviors.PickSelectionBehavior;

import java.awt.Color;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;

import javax.media.j3d.Background;
import javax.media.j3d.BoundingBox;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.Group;
import javax.media.j3d.Transform3D;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.behaviors.vp.OrbitBehavior;
import com.sun.j3d.utils.universe.SimpleUniverse;

/**
 * This class define a GGrapheUniverse.
 */
public class GGrapheUniverse extends SimpleUniverse {
	
	//private Locale locale;
	private GGraph graph;
	private Canvas3D canvas3d;
	private GBehaviors behavior;
	//private GView view;
	private Hashtable<String, BranchGroup> ComponentsView;
	
	private BranchGroup scene;

	private GGrapheUniverse(Canvas3D canvas) {
		super(canvas);
		this.canvas3d = canvas;
	}
	
	/**
	 * This constructor is used to create a GGrapheUniverse.
	 * @param _graph of type GGraph.
	 */
	public GGrapheUniverse(GGraph _graph){
		this(new Canvas3D(SimpleUniverse.getPreferredConfiguration()));
		this.getViewingPlatform().setNominalViewingTransform();
		this.graph = _graph;
		this.ComponentsView = new Hashtable<String, BranchGroup>();
		this.createScene();
		this.addListener();
		
	} 
	
	/**
	 * This function is used to load a new graph into the 3D view
	 * @param _graph the new graph
	 */
	private void loadGraph(GGraph _graph) {
		this.graph = _graph;
		this.removeAll();
		this.loadAll();
		this.addListener();
	}
	
	public void removeAll() {
		Enumeration<String> keys = this.ComponentsView.keys();
		while (keys.hasMoreElements()) {
			this.ComponentsView.remove(keys.nextElement());
		}
		Enumeration children = this.scene.getAllChildren();
		while (children.hasMoreElements()) {
			Object child = children.nextElement();
			if (child instanceof BranchGroup) {
				this.scene.removeChild((BranchGroup) child);
			}
		}

	}
	
	public void loadAll() {
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
	 * This function is used to create the scene.
	 */
	private void createScene() {
		this.scene = new BranchGroup();
		this.scene.setCapability(Group.ALLOW_CHILDREN_EXTEND);
		this.scene.setCapability(Group.ALLOW_CHILDREN_WRITE);
		this.scene.setCapability(Group.ALLOW_CHILDREN_READ);
		
		this.loadAll();
	}
	
	/**
	 * The getter of the Canvas3D which containts the scene compiled.
	 * @return a Canvas3D component.
	 */
	public Canvas3D getCanvas() {
		this.scene.compile();
		
		this.addBranchGraph(this.scene);
		return this.canvas3d;
	}
	
	/**
	 * This function is used to add a GNode to the scene.
	 * @param node of type GNode.
	 */
	public void addGNode(GNode node){
		GNodeView nodeView = new GNodeView(node);
		this.graph.addNode(node);
		this.scene.addChild(nodeView);
		this.ComponentsView.put(node.getName(), nodeView);
		this.addListener();
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
		this.graph.addLink(link);
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
	
	public void updateGNode(String /*old*/nodeName/*, GNode node*/) {
		GNodeView nodeView = (GNodeView) this.ComponentsView.get(/*oldN*/nodeName);
		nodeView.update();
		GNode node = this.graph.getNode(nodeName);
		/*if (!node.getName().equals(oldNodeName)) {
			BranchGroup bg = this.ComponentsView.get(oldNodeName);
			this.ComponentsView.remove(oldNodeName);
			this.ComponentsView.put(node.getName(), bg);
		}*/
		for (Iterator<GLink> i = node.getLinks().iterator(); i.hasNext();) {
			GLink link = i.next();
			if (link.isType()) {
				((GArrowView)this.ComponentsView.get(link.getName())).update();
			} else {
				((GBridgeView)this.ComponentsView.get(link.getName())).update();
			}
		}
		
	}
	
	public void updateGLink(String linkName) {
		
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

	public void setGraph(GGraph _graph) {
		this.loadGraph(_graph);
	}
	
	public void addSelectionBehavior(GAttributesList _attributesList) {
		PickSelectionBehavior selectionBehavior = new PickSelectionBehavior(this.scene, this.canvas3d, new BoundingSphere(), _attributesList);
		this.scene.addChild(selectionBehavior);
	}
	
	public void addListener() {
		this.behavior = new GBehaviors(this.canvas3d, this);
		float[] barycenter = BasicFunctions.calcBarycenter(this.graph);
		this.behavior.setRotationCenter(new Point3d(barycenter[0], barycenter[1], barycenter[2]));
		BoundingSphere bounds = new BoundingSphere(new Point3d(), 100.0);
		this.behavior.setSchedulingBounds(bounds);
		this.getViewingPlatform().setViewPlatformBehavior(this.behavior);
	}

	public GBehaviors getBehavior() {
		return behavior;
	}
}
