/**
 * 
 */
package graph3dTest.elementsTest;

import graph3d.elements.GNode;
import junit.framework.TestCase;

/**
 * A Completer
 * 
 * Remplacer la ligne :  fail("Not yet implemented");
 * Par le code approprié
 * 
 * @author Jerome
 *
 */
public class GNodeTest extends TestCase {

	/**
	 * Test method for {@link graph3d.elements.GNode#GNode(java.lang.String)}.
	 */
	public void testGNodeString() {
		// Création des éléments à tester :
		GNode node1 = new GNode("node one");

		// Verification du nom du noeud
		assertNotNull(node1.getName());
		assertEquals(node1.getName(), "node one");

		// Verification des coordonnees
		assertEquals(node1.getCoordonates()[0], new Float(0));
		assertEquals(node1.getCoordonates()[1], new Float(0));
		assertEquals(node1.getCoordonates()[2], new Float(0));

		// Verification de l'initialisation de la table de hachage pour les  attributs
		assertNotNull(node1.getAttributes());

		// Verification de linitiation de la Liste de lien
		assertNotNull(node1.getLinks());
	}

	/**
	 * Test method for {@link graph3d.elements.GNode#GNode(java.lang.String, float, float, float)}.
	 */
	public void testGNodeStringFloatFloatFloat() {
		// Création des éléments à tester :
		GNode node1 = new GNode("node two", 2, 4, 5);

		// Verification du nom du noeud
		assertNotNull(node1.getName());
		assertEquals(node1.getName(), "node two");

		// Verification des coordonnees
		assertEquals(node1.getCoordonates()[0], new Float(2));
		assertEquals(node1.getCoordonates()[1], new Float(4));
		assertEquals(node1.getCoordonates()[2], new Float(5));

		// Verification de l'initialisation de la table de hachage pour les  attributs
		assertNotNull(node1.getAttributes());

		// Verification de linitiation de la Liste de lien
		assertNotNull(node1.getLinks());
	}

	/**
	 * Test method for {@link graph3d.elements.GNode#GNode(java.lang.String, float, float, float, float)}.
	 */
	public void testGNodeStringFloatFloatFloatFloat() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link graph3d.elements.GNode#setAttributes(java.util.Hashtable)}.
	 */
	public void testSetAttributes() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link graph3d.elements.GNode#setCoordonates(float[])}.
	 */
	public void testSetCoordonates() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link graph3d.elements.GNode#setName(java.lang.String)}.
	 */
	public void testSetName() {
		// Création des éléments à tester :
		GNode node3 = new GNode("node three");

		// Modification du nom du noeud
		node3.setName("Node 3");

		// Verification du nom du noeud
		assertNotNull(node3.getName());
		assertEquals(node3.getName(), "Node 3");
	}

	/**
	 * Test method for {@link graph3d.elements.GNode#setAttributeByName(java.lang.String, java.lang.String, java.lang.String)}.
	 */
	public void testSetAttributeByName() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link graph3d.elements.GNode#getAttributeByName(java.lang.String)}.
	 */
	public void testGetAttributeByName() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link graph3d.elements.GNode#addLink(graph3d.elements.GLink)}.
	 */
	public void testAddLink() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link graph3d.elements.GNode#setCoordonnateX(float)}.
	 */
	public void testSetCoordonnateX() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link graph3d.elements.GNode#setCoordonnateY(float)}.
	 */
	public void testSetCoordonnateY() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link graph3d.elements.GNode#setCoordonnateZ(float)}.
	 */
	public void testSetCoordonnateZ() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link graph3d.elements.GNode#setRadius(float)}.
	 */
	public void testSetRadius() {
		fail("Not yet implemented");
	}

}
