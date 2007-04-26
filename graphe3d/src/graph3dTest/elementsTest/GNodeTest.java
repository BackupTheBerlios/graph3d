package graph3dTest.elementsTest;

import java.util.Hashtable;
import graph3d.elements.GLink;
import graph3d.elements.GNode;
import graph3d.exception.GException;
import graph3d.exception.GLinkAlreadyExistException;
import graph3d.exception.MissingAttributeForClassException;
import graph3d.exception.TooMuchAttributesForClassException;
import junit.framework.TestCase;

/**
 * This class test all the methods of the {@link GNode} class.
 * 
 * @author Jerome Catric, Iuliana Popa
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

		// Verification de l'initialisation de la Liste de lien
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

		// Verification de l'initialisation de la Liste de lien
		assertNotNull(node1.getLinks());
	}

	/**
	 * Test method for {@link graph3d.elements.GNode#GNode(java.lang.String, float, float, float, float)}.
	 */
	public void testGNodeStringFloatFloatFloatFloat() {
		// Création des éléments à tester :
		GNode node1 = new GNode("node two", 2, 4, 5, 3);

		// Verification du nom du noeud
		assertNotNull(node1.getName());
		assertEquals(node1.getName(), "node two");

		// Verification des coordonnees
		assertEquals(node1.getCoordonates()[0], new Float(2));
		assertEquals(node1.getCoordonates()[1], new Float(4));
		assertEquals(node1.getCoordonates()[2], new Float(5));

		// Verification de l'initialisation de la table de hachage pour les  attributs
		assertNotNull(node1.getAttributes());

		// Verification de l'initialisation de la Liste de lien
		assertNotNull(node1.getLinks());

		// Verification du rayon de la sphere
		assertEquals(node1.getRadius(), new Float(3));
	}

	/**
	 * Test method for {@link graph3d.elements.GNode#setAttributes(java.util.Hashtable)}.
	 */
	public void testSetAttributes() {
		// Création des éléments à tester :
		GNode node = new GNode("node");

		// Modification des attributs
		String[] data = new String[] { "ip", "int", "192.168.1.1" };
		Hashtable<String, String[]> attributes = new Hashtable<String, String[]>();
		attributes.put("ip", data);
		
		try {
			node.setAttributes(attributes);
		} catch (MissingAttributeForClassException e) {
			System.err.println(e.getMessage());
		}

		//Verification que l'attribut est bien présent.
		assertEquals(node.getAttributeByName("ip"), data);
	}

	/**
	 * Test method for {@link graph3d.elements.GNode#setCoordonates(float[])}.
	 */
	public void testSetCoordonates() {
		// Création des éléments à tester :
		GNode node = new GNode("node");

		//Modification des coordonnées du noeud.
		node.setCoordonates(new float[] { 2, 3, 4 });

		//Verification de la modification.
		assertEquals(node.getCoordonates()[0], new Float(2));
		assertEquals(node.getCoordonates()[1], new Float(3));
		assertEquals(node.getCoordonates()[2], new Float(4));
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
		// Création des éléments à tester :
		GNode node = new GNode("node");

		// Ajout des attributs au noeud
		String[] data = new String[] { "wifi", "boolean", "false" };
		Hashtable<String, String[]> attributes = new Hashtable<String, String[]>();
		attributes.put("wifi", data);
		
		try {
			node.setAttributes(attributes);
		} catch (MissingAttributeForClassException e) {
			System.err.println(e.getMessage());
		}
		
		try {
			// Modification d'un attribut du noeud
			node.setAttributeByName("wifi", "boolean", "true");
		} catch (TooMuchAttributesForClassException e) {
			System.err.println(e.getMessage());
		} catch (GException e) {
			System.err.println(e.getMessage());
		}
		
		//Verification que l'attribut est bien présent.
		assertEquals(node.getAttributeByName("wifi")[0], "wifi");
		assertEquals(node.getAttributeByName("wifi")[1], "boolean");
		assertEquals(node.getAttributeByName("wifi")[2], "true");
	}

	/**
	 * Test method for {@link graph3d.elements.GNode#addLink(graph3d.elements.GLink)}.
	 */
	public void testAddLink() {
		// Création des éléments à tester :
		GNode node1 = new GNode("node1");
		GNode node2 = new GNode("node2");
		GLink link = new GLink("link", node2, node2);

		try {
			//Ajout du lien au noeud
			node1.addLink(link);
		} catch (GLinkAlreadyExistException e) {
			System.err.println(e.getMessage());
		}
		
		//Vérification que l'ajout d'un lien s'est bien passé.
		assertEquals(node1.getLinks().getFirst(), link);
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
		assertEquals(node.getCoordonnateX(), 3.5f);
	}

	/**
	 * Test method for {@link graph3d.elements.GNode#setCoordonnateY(float)}.
	 */
	public void testSetCoordonnateY() {
		// Création des éléments à tester :
		GNode node = new GNode("node");

		// Modification de la coordonnée Y
		node.setCoordonnateY(3.5f);

		// Vérification de la modification
		assertEquals(node.getCoordonnateY(), 3.5f);
	}

	/**
	 * Test method for {@link graph3d.elements.GNode#setCoordonnateZ(float)}.
	 */
	public void testSetCoordonnateZ() {
		// Création des éléments à tester :
		GNode node = new GNode("node");

		// Modification de la coordonnée Z
		node.setCoordonnateZ(3.5f);

		// Vérification de la modification
		assertEquals(node.getCoordonnateZ(), 3.5f);
	}

	/**
	 * Test method for {@link graph3d.elements.GNode#setRadius(float)}.
	 */
	public void testSetRadius() {
		// Création des éléments à tester :
		GNode node = new GNode("node");

		// Modification du rayon
		node.setRadius(3.5f);

		// Vérification de la modification
		assertEquals(node.getRadius(), 3.5f);
	}

	/**
	 * Test method for {@link graph3d.elements.GNode#getAttributes()}.
	 */
	public void testGetAttributes() {
		// Création des éléments à tester :
		GNode node = new GNode("node");

		// Ajout des attributs au noeud
		String[] data = new String[] { "wifi", "boolean", "false" };
		Hashtable<String, String[]> attributes = new Hashtable<String, String[]>();
		attributes.put("wifi", data);
		
		try {
			node.setAttributes(attributes);
		} catch (MissingAttributeForClassException e) {
			System.err.println(e.getMessage());
		}

		//Récuperation des attributs et vérification
		assertEquals(node.getAttributes(), attributes);
	}

	/**
	 * Test method for {@link graph3d.elements.GNode#getCoordonates()}.
	 */
	public void testGetCoordonates() {
		// Création des éléments à tester :
		GNode node = new GNode("node two", 2, 4, 5);

		//Récuperation des coordonnées et vérification
		assertEquals(node.getCoordonates()[0], new Float(2));
		assertEquals(node.getCoordonates()[1], new Float(4));
		assertEquals(node.getCoordonates()[2], new Float(5));
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
		// Création des éléments à tester :
		GNode node = new GNode("node");

		// Ajout des attributs au noeud
		String[] data = new String[] { "wifi", "boolean", "false" };
		Hashtable<String, String[]> attributes = new Hashtable<String, String[]>();
		attributes.put("wifi", data);
		
		try {
			node.setAttributes(attributes);
		} catch (MissingAttributeForClassException e) {
			System.err.println(e.getMessage());
		}

		// Récuperation des données sur un attribut d'un noeud et vérification des valeurs.
		assertEquals(node.getAttributeByName("wifi"), data);
	}

	/**
	 * Test method for {@link graph3d.elements.GNode#getLinks()}.
	 */
	public void testGetLinks() {
		// Création des éléments à tester :
		GNode node1 = new GNode("node1");
		GNode node2 = new GNode("node2");
		GLink link = new GLink("link", node1, node2);

		//Vérification que l'ajout d'un lien s'est bien passé.
		assertEquals(node1.getLinks().getFirst(), link);
		assertEquals(node2.getLinks().getFirst(), link);
	}

	/**
	 * Test method for {@link graph3d.elements.GNode#getCoordonnateX()}.
	 */
	public void testGetCoordonnateX() {
		// Création des éléments à tester :
		GNode node = new GNode("node", 2.5f, 3.4f, 6.4f);

		// Verification de la valeur de la coordonnee X
		assertEquals(node.getCoordonnateX(), 2.5f);
	}

	/**
	 * Test method for {@link graph3d.elements.GNode#getCoordonnateY()}.
	 */
	public void testGetCoordonnateY() {
		// Création des éléments à tester :
		GNode node = new GNode("node", 2.5f, 3.4f, 6.4f);

		// Verification de la valeur de la coordonnee Y
		assertEquals(node.getCoordonnateY(), 3.4f);
		;
	}

	/**
	 * Test method for {@link graph3d.elements.GNode#getCoordonnateZ()}.
	 */
	public void testGetCoordonnateZ() {
		// Création des éléments à tester :
		GNode node = new GNode("node", 2.5f, 3.4f, 6.4f);

		// Verification de la valeur de la coordonnee Z
		assertEquals(node.getCoordonnateZ(), 6.4f);
	}

	/**
	 * Test method for {@link graph3d.elements.GNode#getRadius()}.
	 */
	public void testGetRadius() {
		// Création des éléments à tester :
		GNode node = new GNode("node", 2.5f, 3.4f, 6.4f, 5f);

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
