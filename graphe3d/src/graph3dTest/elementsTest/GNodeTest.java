/**
 * 
 */
package graph3dTest.elementsTest;

import java.util.Hashtable;

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
		// Création des éléments à tester :
		GNode node = new GNode("node");
		
		// Modification des attribues
		String[] data = new String[]{"ip","int","192.168.1.1"};
		Hashtable<String, String[]> attributes = new Hashtable<String, String[]>();
		attributes.put("ip", data);
		node.setAttributes(attributes);	
		
		//Verification que l'attribut est bien présent.
		assertEquals(node.getAttributeByName("ip"),data);	
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
	 * Test method for {@link graph3d.elements.GNode#addLink(graph3d.elements.GLink)}.
	 */
	public void testAddLink() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link graph3d.elements.GNode#setCoordonnateX(float)}.
	 */
	public void testSetCoordonnateX() {
		// Création des éléments à tester :
		GNode node = new GNode("node");
		// Modification de la coordonnée X
		node.setCoordonnateX(3.5f);
		// Vérification de la modification
		assertEquals(node.getCoordonnateX(),3.5f);
	}

	/**
	 * Test method for {@link graph3d.elements.GNode#setCoordonnateY(float)}.
	 */
	public void testSetCoordonnateY() {
		// Création des éléments à tester :
		GNode node = new GNode("node");
		// Modification de la coordonnée X
		node.setCoordonnateY(3.5f);
		// Vérification de la modification
		assertEquals(node.getCoordonnateY(),3.5f);
	}

	/**
	 * Test method for {@link graph3d.elements.GNode#setCoordonnateZ(float)}.
	 */
	public void testSetCoordonnateZ() {
		// Création des éléments à tester :
		GNode node = new GNode("node");
		// Modification de la coordonnée X
		node.setCoordonnateZ(3.5f);
		// Vérification de la modification
		assertEquals(node.getCoordonnateZ(),3.5f);
	}

	/**
	 * Test method for {@link graph3d.elements.GNode#setRadius(float)}.
	 */
	public void testSetRadius() {
		// Création des éléments à tester :
		GNode node = new GNode("node");
		// Modification de la coordonnée X
		node.setRadius(3.5f);
		// Vérification de la modification
		assertEquals(node.getRadius(),3.5f);
	}
	

	/**
	 * Test method for {@link graph3d.elements.GNode#getAttributes()}.
	 */
	public void testGetAttributes() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link graph3d.elements.GNode#getCoordonates()}.
	 */
	public void testGetCoordonates() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link graph3d.elements.GNode#getName()}.
	 */
	public void testGetName() {
		// Création des éléments à tester :
		GNode node = new GNode("node");
		// Verification du nom du noeud
		assertEquals(node.getName(), "node");
	}

	/**
	 * Test method for {@link graph3d.elements.GNode#getAttributeByName(java.lang.String)}.
	 */
	public void testGetAttributeByName() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link graph3d.elements.GNode#getLinks()}.
	 */
	public void testGetLinks() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link graph3d.elements.GNode#getCoordonnateX()}.
	 */
	public void testGetCoordonnateX() {
		// Création des éléments à tester :
		GNode node = new GNode("node",2.5f,3.4f,6.4f);
		// Verification de la valeur de la coordonnee X
		assertEquals(node.getCoordonnateX(), 2.5f);
	}

	/**
	 * Test method for {@link graph3d.elements.GNode#getCoordonnateY()}.
	 */
	public void testGetCoordonnateY() {
		// Création des éléments à tester :
		GNode node = new GNode("node",2.5f,3.4f,6.4f);
		// Verification de la valeur de la coordonnee Y
		assertEquals(node.getCoordonnateY(), 3.4f);;
	}

	/**
	 * Test method for {@link graph3d.elements.GNode#getCoordonnateZ()}.
	 */
	public void testGetCoordonnateZ() {
		// Création des éléments à tester :
		GNode node = new GNode("node",2.5f,3.4f,6.4f);
		// Verification de la valeur de la coordonnee Z
		assertEquals(node.getCoordonnateZ(), 6.4f);
	}

	/**
	 * Test method for {@link graph3d.elements.GNode#getRadius()}.
	 */
	public void testGetRadius() {
		// Création des éléments à tester :
		GNode node = new GNode("node",2.5f,3.4f,6.4f,5f);
		// Verification de la valeur du rayon d'une sphere
		assertEquals(node.getRadius(), 5f);
	}

	/**
	 * Test method for {@link graph3d.elements.GNode#getDISTANCE()}.
	 */
	public void testGetDISTANCE() {
		// Création des éléments à tester :
		GNode node = new GNode("node");
		// Verification de la distance entre 2 spheres
		assertEquals(node.getDISTANCE(), 2f);
	}


}
