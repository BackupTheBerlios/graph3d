package graph3dTest.elementsTest;

import java.util.Hashtable;
import graph3d.elements.GGraph;
import graph3d.elements.GLink;
import graph3d.elements.GNode;
import junit.framework.TestCase;

/**
 * This class test all the methods of the {@link GGraph} class.
 * 
 * @author Jerome Catric, Iuliana Popa
 */
public class GGraphTest extends TestCase {

	/**
	 * Test method for {@link graph3d.elements.GGraph#GGraph()}.
	 */
	public void testGGraph() {
		// Création des éléments à tester
		GGraph g = new GGraph();

		// Vérification des élément d'un graphe
		assertEquals(g.getName(), "default");
		assertNotNull(g.getLinks());
		assertNotNull(g.getNodes());
	}

	/**
	 * Test method for {@link graph3d.elements.GGraph#GGraph(java.lang.String)}.
	 */
	public void testGGraphString() {
		// Création des éléments à tester
		GGraph g = new GGraph("graph");

		// Vérification des élément d'un graphe
		assertEquals(g.getName(), "graph");
		assertNotNull(g.getLinks());
		assertNotNull(g.getNodes());
	}

	/**
	 * Test method for {@link graph3d.elements.GGraph#addLink(graph3d.elements.GLink)}.
	 */
	public void testAddLink() {
		// Création des éléments à tester
		GNode node1 = new GNode("node1");
		GNode node2 = new GNode("node2");
		GLink lien = new GLink("link", node1, node2);
		GGraph g = new GGraph("graph");

		// Ajout d'un lien dans le graphe
		g.addLink(lien);

		// Verification que l'ajout s'est bien passé
		assertEquals(g.getLink("link"), lien);
	}

	/**
	 * Test method for {@link graph3d.elements.GGraph#addNode(graph3d.elements.GNode)}.
	 */
	public void testAddNode() {
		// Création des éléments à tester
		GNode node1 = new GNode("node");
		GGraph g = new GGraph("graph");

		// Ajout d'un lien dans le graphe
		g.addNode(node1);

		// Verification que l'ajout s'est bien passé
		assertEquals(g.getNode("node"), node1);
	}

	/**
	 * Test method for {@link graph3d.elements.GGraph#collision(graph3d.elements.GNode)}.
	 */
	public void testCollision() {
		// Création des éléments à tester
		GNode node1 = new GNode("node1");
		GNode node2 = new GNode("node2");
		GGraph g = new GGraph("graph");
		g.addNode(node1);

		// Verification des coordonnees
		assertEquals(node2.getCoordonates()[0], new Float(0));
		assertEquals(node2.getCoordonates()[1], new Float(0));
		assertEquals(node2.getCoordonates()[2], new Float(0));

		// Modification des coordonnées lors de la collision
		g.collision(node2);

		// Verification qu'il n'y a plus de collision.
		assertFalse(g.haveCollision(node2));

		// Vérification que les coordonnées du noeud 2 ont été modifié.
		assertNotSame(node2.getCoordonates()[0], new Float(0));
		assertNotSame(node2.getCoordonates()[1], new Float(0));
		assertNotSame(node2.getCoordonates()[2], new Float(0));
	}

	/**
	 * Test method for {@link graph3d.elements.GGraph#haveCollision(graph3d.elements.GNode)}.
	 */
	public void testHaveCollision() {
		// Création des éléments à tester
		GNode node1 = new GNode("node1");
		GNode node2 = new GNode("node2");
		GGraph g = new GGraph("graph");
		g.addNode(node1);

		// Vérification qu'il y a collision avec le node2
		assertTrue(g.haveCollision(node2));
	}

	/**
	 * Test method for {@link graph3d.elements.GGraph#removeLink(java.lang.String)}.
	 */
	public void testRemoveLink() {
		// Création des éléments à tester
		GNode node1 = new GNode("node1");
		GNode node2 = new GNode("node2");
		GLink lien = new GLink("link", node1, node2);
		GGraph g = new GGraph("graph");
		g.addLink(lien);

		// Verification que le graph contient bien un lien avant la suppression
		assertEquals(g.getLink("link"), lien);

		// Suppression du lien du graphe
		g.removeLink("link");

		// Verification que le lien a bien été suprimé
		assertEquals(g.getLinks().size(), 0);
	}

	/**
	 * Test method for {@link graph3d.elements.GGraph#removeNode(java.lang.String)}.
	 */
	public void testRemoveNode() {
		// Création des éléments à tester		
		GNode node1 = new GNode("node1");
		GGraph g = new GGraph("graph");
		g.addNode(node1);
		
		// vérification de la présence du noeud dans le graphe
		assertEquals(g.getNode("node1"), node1);
		
		// suppression du noeud du graphe
		g.removeNode("node1");
		
		// verification que le noeud n'est plus dans le graphe
		assertEquals(g.getNodes().size(), 0);
	}

	/**
	 * Test method for {@link graph3d.elements.GGraph#getLinks()}.
	 */
	public void testGetLinks() {
		// création des éléments à tester
		GNode node1 = new GNode("node1");
		GNode node2 = new GNode("node2");
		GLink lien = new GLink("link", node1, node2);
		GLink lien2 = new GLink("link2", node2, node2);
		GGraph g = new GGraph("graph");

		Hashtable<String, GLink> links = new Hashtable<String, GLink>();
		links.put("link", lien);
		links.put("link2", lien2);

		// ajout des liens au graphe
		g.setLinks(links);

		// vérification que la méthode rends tous les liens du graphe.
		assertEquals(g.getLinks(), links);
	}

	/**
	 * Test method for {@link graph3d.elements.GGraph#setLinks(java.util.Hashtable)}.
	 */
	public void testSetLinks() {
		// création des éléments à tester		
		GNode node1 = new GNode("node1");
		GNode node2 = new GNode("node2");
		GLink lien = new GLink("link1", node1, node2);
		GNode node3 = new GNode("node3");
		GNode node4 = new GNode("node4");
		GLink lien2 = new GLink("link2", node3, node4);
		GGraph g = new GGraph("graph");

		Hashtable<String, GLink> links = new Hashtable<String, GLink>();
		links.put("link1", lien);
		links.put("link2", lien2);
		
		// ajout des liens au graphe
		g.setLinks(links);
		
		// vérification que l'ajout des liens au graph s'est bien passé.
		assertEquals(g.getLinks(), links);
	}

	/**
	 * Test method for {@link graph3d.elements.GGraph#getName()}.
	 */
	public void testGetName() {
		// création des éléments à tester	
		GGraph g = new GGraph("graph");
		
		// Vérification du nom du graphe
		assertEquals(g.getName(), "graph");
	}

	/**
	 * Test method for {@link graph3d.elements.GGraph#setName(java.lang.String)}.
	 */
	public void testSetName() {
		// création des éléments à tester
		GGraph g = new GGraph("graph");
		
		// Modification du nom du graphe
		g.setName("graph1");
		
		// Verification que le nom du graphe a bien été modifier.
		assertEquals(g.getName(), "graph1");

	}

	/**
	 * Test method for {@link graph3d.elements.GGraph#getNodes()}.
	 */
	public void testGetNodes() {
		// création des éléments à tester
		GNode node1 = new GNode("node1");
		GNode node2 = new GNode("node2");
		GGraph g = new GGraph("graph");

		Hashtable<String, GNode> nodes = new Hashtable<String, GNode>();
		nodes.put("node1", node1);
		nodes.put("node2", node2);

		// ajout de noeuds au graphe
		g.setNodes(nodes);

		//Récuperation des noeuds du graphe
		assertEquals(g.getNodes(), nodes);
	}

	/**
	 * Test method for {@link graph3d.elements.GGraph#setNodes(java.util.Hashtable)}.
	 */
	public void testSetNodes() {
		// création des éléments à tester
		GNode node1 = new GNode("node1");
		GNode node2 = new GNode("node2");
		GGraph g = new GGraph("graph");

		Hashtable<String, GNode> nodes = new Hashtable<String, GNode>();
		nodes.put("node1", node1);
		nodes.put("node2", node2);
		
		// ajout de noeuds au graph
		g.setNodes(nodes);

		// Vérification que l'ajout des noeuds au graph est ok
		assertEquals(g.getNodes(), nodes);
	}

	/**
	 * Test method for {@link graph3d.elements.GGraph#getNode(java.lang.String)}.
	 */
	public void testGetNode() {
		// création des éléments à tester
		GNode node1 = new GNode("node1");
		GGraph g = new GGraph("graph");
		
		//Ajout du noeud au graphe
		g.addNode(node1);
		
		//vérifie la récuperation du noeud
		assertEquals(g.getNode("node1"), node1);
	}

	/**
	 * Test method for {@link graph3d.elements.GGraph#getLink(java.lang.String)}.
	 */
	public void testGetLink() {
		// création des éléments à tester
		GNode node1 = new GNode("node1");
		GNode node2 = new GNode("node2");
		GLink lien = new GLink("link", "red", node1, node2);
		GGraph g = new GGraph("graph");
		
		//Ajout d'un lien au graph
		g.addLink(lien);
		
		//verifie la récuperation du lien
		assertEquals(g.getLink("link"), lien);
	}
	
}
