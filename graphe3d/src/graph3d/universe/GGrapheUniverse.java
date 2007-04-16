package graph3d.universe;

import graph3d.elements.GGraph;
import graph3d.elements.GLink;
import graph3d.elements.GNode;

import java.util.Enumeration;
import java.util.Hashtable;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.Locale;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.VirtualUniverse;

/*
 * create : GGrapheUniverse():GGrapheUniverse
 * create SceneGraphe():void
 * addGNode():void
 * addGLink():void
 * deleteGNode():void
 * deleteGLink():void
 * zoom():void
 * rotate():void
 * zoom(zoom:float):void
 * rotate(angle:float):void
 * collision():boolean
 * getCanvas():Canvas3D
 * addMouseListener():void
 * addKeyListener():void
 */
public class GGrapheUniverse extends VirtualUniverse{
	
	private Locale locale;
	private GGraph graph;
	private GView view;
	private BranchGroup sceneCompiled;//peut-être pas nécessaire si possibilité de modifier la scene même si elle est compilée
	private Hashtable<String, TransformGroup> ComponentsView;
	
	private BranchGroup scene;


	public GGrapheUniverse(GGraph _graph){
		this.locale = new Locale(this);
		this.graph = _graph;
		this.ComponentsView = new Hashtable<String, TransformGroup>();
		this.createView();
		this.createScene();
	}
	
	private void createScene() {
		this.scene = new BranchGroup();
		
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
	
	private void createView() {
		float[] bestPlaceToSee = this.graph.getBestPlaceToSee();
		this.view = new GView(bestPlaceToSee);
		
		
		// il faut maintenant que à partir du barycentre, je "dézoome pour que l'on puisse voir tous les nodes
		// comment dézoomer???
		// trouver les noeuds extrèmes : plus grand X, plus grand Y, plus petit X, plus petit Y, plus grand Z
		// puis construire un triangle(une pyramide plutôt) qui permettrait d'avoir ces 5 noeuds à l'intérieur
		
		
		
	}
	
	public Canvas3D getCanvas() {
		this.sceneCompiled = this.scene;
		
		this.sceneCompiled.compile();
		
		this.locale.addBranchGraph(this.view);
		this.locale.addBranchGraph(this.sceneCompiled);
		
		return this.view.getCanvas();
	}
	
	public void addGNode(GNode node){
		GNodeView nodeView = new GNodeView(node);
		this.scene.addChild(nodeView);
		this.ComponentsView.put(node.getName(), nodeView);
	}

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
}
