package graph3d.elements;

import graph3d.exception.CollisionException;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * This class define a graph. A graph is define by nodes and links and identify
 * by a name. This class is used to define the logical visualization of a graph
 * and also used to define how create the 3D view.
 * 
 * @author Erwan Daubert
 * @version 1.0
 * 
 */
public class GGraph {

	/**
	 * This attributes defines the name of the graph
	 */
	private String name;

	/**
	 * This Hashtable contains all links which are define into the graph
	 */
	private Hashtable<String, GLink> links;

	/**
	 * This Hashtable contains all nodes which are define into the graph
	 */
	private Hashtable<String, GNode> nodes;

	/**
	 * The default constructor. It's use when you don't define a name to a new
	 * graph. The name specify is "default".
	 * 
	 */
	public GGraph() {
		this("default");
	}

	/**
	 * This constructor initialize all attributes of the graph (name, links and
	 * nodes)
	 * 
	 * @param name
	 *            the name of the graph
	 */
	public GGraph(String name) {
		this.name = name;
		this.links = new Hashtable<String, GLink>();
		this.nodes = new Hashtable<String, GNode>();
	}

	/**
	 * This function add a link into the list
	 * 
	 * @param link
	 *            the new link
	 * @return true if the link have a name which not exist to an other link,
	 *         false else.
	 */
	public boolean addLink(GLink link) {
		GLink exist = this.links.get(link.getName());
		if (exist == null) {
			this.links.put(link.getName(), link);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * This function add a node into the list
	 * 
	 * @param node
	 *            the new node
	 * @return true if the node have an name which not exist to an other node,
	 *         false else.
	 */
	public boolean addNode(GNode node) {
		GNode exist = this.nodes.get(node.getName());
		if (exist == null) {
			this.collision(node);
			this.nodes.put(node.getName(), node);
			return true;
		} else {
			//pourquoi pas throw SameNameException?????
			return false;
		}
	}
	
	/**
	 * This function define the coordonates of the node to prevent collision into the 3D view
	 * @param node the node to test his coordonates
	 */
	public void collision(GNode node) {
		boolean newCollision = true;
		Enumeration<String> keys = this.nodes.keys();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			GNode _node = this.nodes.get(key);
			if (!this.distanceBetweenIsGood(node, _node)) {
				if (newCollision) {
					(new CollisionException()).showError();
					newCollision = false;
				}
				this.putDistanceBetween(node, _node);
				this.collision(node);
				break;
			}
		}
	}

	/**
	 * This function test if there are collisions.
	 * @param node the node which potentially cause collision.
	 * @return true if there are collision, false else.
	 */
	public boolean haveCollision(GNode node) {
		Enumeration<String> keys = this.nodes.keys();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			GNode _node = this.nodes.get(key);
			if (!this.distanceBetweenIsGood(node, _node)) {
				return true;
			}
		}
		return false;
	}
	
	private boolean distanceBetweenIsGood(GNode _aNode, GNode _otherNode) {
		return ( GNode.getDISTANCE() < (float) Math.sqrt(Math.pow(_aNode.getCoordonnateX() - _otherNode.getCoordonnateX(), 2) + Math.pow(_aNode.getCoordonnateY() - _otherNode.getCoordonnateY(), 2) + Math.pow(_aNode.getCoordonnateZ() - _otherNode.getCoordonnateZ(), 2)));
	}
	
	private void putDistanceBetween(GNode _aNode, GNode _otherNode) {
		float _add = (float)Math.sqrt(Math.pow(GNode.getDISTANCE(), 2)/2);
		_aNode.setCoordonnateX(_otherNode.getCoordonnateX() + _add);
		_aNode.setCoordonnateY(_otherNode.getCoordonnateY() + _add);
		_aNode.setCoordonnateZ(_otherNode.getCoordonnateZ() + _add);
		
	}
	
	

	/**
	 * This function delete a link which is into the Hashtable links
	 * 
	 * @param name
	 *            the name of the link which you want delete.
	 */
	public void removeLink(String name) {
		this.links.remove(name);
	}

	/**
	 * This function delete a node which is into the Hashtable nodes
	 * 
	 * @param name
	 *            the name of the node which you want delete.
	 */
	public void removeNode(String name) {
		this.nodes.remove(name);
	}

	/**
	 * The getter of the Hashtable which contains all the links
	 * 
	 * @return links
	 */
	public Hashtable<String, GLink> getLinks() {
		return this.links;
	}

	/**
	 * The setter of the Hashtable which contains all the links
	 * 
	 * @param links
	 *            a new Hashtable which define all the link of the graph
	 */
	public void setLinks(Hashtable<String, GLink> links) {
		this.links = links;
	}

	/**
	 * Ther getter of the name
	 * 
	 * @return name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * The setter of the name
	 * 
	 * @param name
	 *            the new name for the graph
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * the getter of the Hashtable which contains all the nodes
	 * 
	 * @return nodes
	 */
	public Hashtable<String, GNode> getNodes() {
		return this.nodes;
	}

	/**
	 * The setter of the Hashtable which contains all the nodes
	 * 
	 * @param nodes
	 *            an Hashtable which define all the nodes of the graph
	 */
	public void setNodes(Hashtable<String, GNode> nodes) {
		this.nodes = nodes;
	}

	/**
	 * The getter of a node in the Hashtable
	 * 
	 * @param name
	 *            the identifier of the node
	 * @return the node which is define the name
	 */
	public GNode getNode(String name) {
		return this.nodes.get(name);
	}

	/**
	 * The getter of a link in the Hashtable
	 * 
	 * @param name
	 *            the identifier of the link
	 * @return the link which is define the name
	 */
	public GLink getLink(String name) {
		return this.links.get(name);
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String toString = "graph :\n";
		toString += this.name + "\n";
		
		Enumeration<GNode> nodes = this.nodes.elements();
		while (nodes.hasMoreElements()) {
			GNode node = nodes.nextElement();
			toString += node.toString();
		}

		Enumeration<GLink> links = this.links.elements();
		while (links.hasMoreElements()) {
			GLink link = links.nextElement();
			toString += link.toString();
		}

		return toString;

	}
}
