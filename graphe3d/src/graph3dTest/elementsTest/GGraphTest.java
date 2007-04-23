package graph3dTest.elementsTest;
import java.util.Enumeration;
import java.util.Hashtable;

import graph3d.elements.GGraph;
import graph3d.elements.GLink;
import graph3d.elements.GNode;
import junit.framework.TestCase;

/** classe GGraphTest
 * @author Jerome,Iuliana
 */
public class GGraphTest extends TestCase {

	/** 1)
	 * Test method for {@link graph3d.elements.GGraph#GGraph()}.
	 */
	public void testGGraph() {
		GGraph g= new GGraph();
		assertEquals(g.getName(), "default");
	
	}

	/** 2)
	 * Test method for {@link graph3d.elements.GGraph#GGraph(java.lang.String)}.
	 */
	public void testGGraphString() {
		GGraph g= new GGraph("gr");
		assertEquals(g.getName(), "gr");
	}

	/** 3)
	 * Test method for {@link graph3d.elements.GGraph#addLink(graph3d.elements.GLink)}.
	 */
	public void testAddLink() {
		GNode node1=new GNode("n");
		GNode node2=new GNode("n2");
		GLink lien =new GLink("l","red", node1,node2);	
		GGraph g= new GGraph("gr");
		g.addLink(lien);
		assertEquals(g.getLink("l"), lien);
	}

	/** 4)
	 * Test method for {@link graph3d.elements.GGraph#addNode(graph3d.elements.GNode)}.
	 */
	public void testAddNode() {
		GNode node1=new GNode("n");
		GGraph g= new GGraph("gr");
		g.addNode(node1);
		assertEquals(g.getNode("n"),node1);
	}

	/** 5)
	 * Test method for {@link graph3d.elements.GGraph#collision(graph3d.elements.GNode)}.
	 */
	public void testCollision() {
		
	}

	/** 6)
	 * Test method for {@link graph3d.elements.GGraph#getBestPlaceToSee()}.
	 */
	public void testGetBestPlaceToSee() {
		
	}
	/** 7)
	 * Test method for {@link graph3d.elements.GGraph#haveCollision(graph3d.elements.GNode)}.
	 */
	public void testHaveCollision() {
		
	}

	/** 8)
	 * Test method for {@link graph3d.elements.GGraph#removeLink(java.lang.String)}.
	 */
	public void testRemoveLink() {
		GNode node1=new GNode("n");
		GNode node2=new GNode("n2");
		GLink lien =new GLink("l","red", node1,node2);	
		GGraph g= new GGraph("gr");
		g.addLink(lien);
		assertEquals(g.getLink("l"), lien);	
		g.removeLink("l");
		assertEquals(g.getLinks().size(), 0);	
	}

	/** 9)
	 * Test method for {@link graph3d.elements.GGraph#removeNode(java.lang.String)}.
	 */
	public void testRemoveNode() {
		GNode node1=new GNode("n");
		GGraph g= new GGraph("gr");
		g.addNode(node1);
		assertEquals(g.getNode("n"),node1);	
		g.removeNode("n");
		assertEquals(g.getNodes().size(), 0);	
	}

	/** 10)
	 * Test method for {@link graph3d.elements.GGraph#getLinks()}.
	 */
	public void testGetLinks() {
		GNode node1=new GNode("n");
		GNode node2=new GNode("n2");
		GLink lien =new GLink(true,"l","red", node1,node2);
		GGraph g= new GGraph("gr");
		
		Hashtable<String, GLink> links = new Hashtable<String, GLink>();
		links.put("l",lien);
		
		g.setLinks(links);
		
		assertEquals(g.getLinks(),links);
	}

	/** 11)
	 * Test method for {@link graph3d.elements.GGraph#setLinks(java.util.Hashtable)}.
	 */
	public void testSetLinks() {
		GNode node1=new GNode("n");
		GNode node2=new GNode("n2");
		GLink lien =new GLink(true,"l","red", node1,node2);
		GNode node3=new GNode("n3");
		GNode node4=new GNode("n4");
		GLink lien2 =new GLink(false,"l2","red", node3,node4);	
		GGraph g= new GGraph("gr");
		
		Hashtable<String, GLink> links = new Hashtable<String, GLink>();
		links.put("l",lien);
		links.put("l2",lien2);
		
		g.setLinks(links);
		
		assertEquals(g.getLinks(),links);
	}

	/** 12)
	 * Test method for {@link graph3d.elements.GGraph#getName()}.
	 */
	public void testGetName() {
		GGraph g= new GGraph("gr");
		assertEquals(g.getName(),"gr");
	}

	/** 13)
	 * Test method for {@link graph3d.elements.GGraph#setName(java.lang.String)}.
	 */
	public void testSetName() {
		GGraph g= new GGraph("gr");
		g.setName("graph");
		assertEquals(g.getName(),"graph");
		
	}

	/** 14)
	 * Test method for {@link graph3d.elements.GGraph#getNodes()}.
	 */
	public void testGetNodes() {
		GNode node1=new GNode("n1");
		GNode node2=new GNode("n2");
		GGraph g= new GGraph("gr");
		
		Hashtable<String, GNode> nodes = new Hashtable<String, GNode>();
		nodes.put("n1",node1);
		nodes.put("n2",node2);
		
		g.setNodes(nodes);
		
		assertEquals(g.getNodes(), nodes);	
	}

	/** 15)
	 * Test method for {@link graph3d.elements.GGraph#setNodes(java.util.Hashtable)}.
	 */
	public void testSetNodes() {
		GNode node1=new GNode("n1");
		GNode node2=new GNode("n2");
		GGraph g= new GGraph("gr");
		
		Hashtable<String, GNode> nodes = new Hashtable<String, GNode>();
		nodes.put("n1",node1);
		nodes.put("n2",node2);
		
		g.setNodes(nodes);
		
		assertEquals(g.getNodes(), nodes);	
	}

	/** 16)
	 * Test method for {@link graph3d.elements.GGraph#getNode(java.lang.String)}.
	 */
	public void testGetNode() {
		GNode node1=new GNode("n");
		GGraph g= new GGraph("gr");
		g.addNode(node1);
		assertEquals(g.getNode("n"),node1);	
	}

	/** 17)
	 * Test method for {@link graph3d.elements.GGraph#getLink(java.lang.String)}.
	 */
	public void testGetLink() {
		GNode node1=new GNode("n");
		GNode node2=new GNode("n2");
		GLink lien =new GLink("l","red", node1,node2);	
		GGraph g= new GGraph("gr");
		g.addLink(lien);
		assertEquals(g.getLink("l"), lien);	
	}
}// fin de la classe GGraphTest
