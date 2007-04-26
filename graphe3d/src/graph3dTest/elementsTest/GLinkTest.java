package graph3dTest.elementsTest;

import java.util.Hashtable;
import graph3d.elements.GLink;
import graph3d.elements.GNode;
import graph3d.exception.GException;
import graph3d.exception.MissingAttributeForClassException;
import graph3d.exception.TooMuchAttributesForClassException;
import junit.framework.TestCase;

/**
 * This class test all the methods of the {@link GLink} class.
 * 
 * @author Jerome Catric, Iuliana Popa
 */
public class GLinkTest extends TestCase {

	/**
	 * Test method for {@link graph3d.elements.GLink#GLink(java.lang.String, graph3d.elements.GNode, graph3d.elements.GNode)}.
	 */
	public void testGLinkStringGNodeGNode() {
		// Création des éléments à tester
		GNode node1 = new GNode("node");
		GNode node2 = new GNode("node2");
		GLink lien = new GLink("link", node1, node2);

		// Vérification des éléments d'un lien
		assertEquals(lien.getName(), "link");
		assertEquals(lien.getFirstNode(), node1);
		assertEquals(lien.getSecondNode(), node2);

	}

	/**
	 * Test method for {@link graph3d.elements.GLink#GLink(java.lang.String, java.lang.String, graph3d.elements.GNode, graph3d.elements.GNode)}.
	 */
	public void testGLinkStringStringGNodeGNode() {
		// Création des éléments à tester
		GNode node1 = new GNode("node");
		GNode node2 = new GNode("node2");
		GLink lien = new GLink("link", "red", node1, node2);

		// Vérification des éléments d'un lien
		assertEquals(lien.getName(), "link");
		assertEquals(lien.getColor(), "red");
		assertEquals(lien.getFirstNode(), node1);
		assertEquals(lien.getSecondNode(), node2);
	}

	/**
	 * Test method for {@link graph3d.elements.GLink#GLink(boolean, java.lang.String, graph3d.elements.GNode, graph3d.elements.GNode)}.
	 */
	public void testGLinkBooleanStringGNodeGNode() {
		// Création des éléments à tester
		GNode node1 = new GNode("node");
		GNode node2 = new GNode("node2");
		GLink lien = new GLink(true, "link", node1, node2);

		// Vérification des éléments d'un lien
		assertEquals(lien.getName(), "link");
		assertEquals(lien.isType(), true);
		assertEquals(lien.getFirstNode(), node1);
		assertEquals(lien.getSecondNode(), node2);
	}

	/**
	 * Test method for {@link graph3d.elements.GLink#GLink(boolean, java.lang.String, java.lang.String, graph3d.elements.GNode, graph3d.elements.GNode)}.
	 */
	public void testGLinkBooleanStringStringGNodeGNode() {
		// Création des éléments à tester
		GNode node1 = new GNode("node");
		GNode node2 = new GNode("node2");
		GLink lien = new GLink(true, "link", "red", node1, node2);

		// Vérification des éléments d'un lien
		assertEquals(lien.getName(), "link");
		assertEquals(lien.getColor(), "red");
		assertEquals(lien.isType(), true);
		assertEquals(lien.getFirstNode(), node1);
		assertEquals(lien.getSecondNode(), node2);
	}

	/**
	 * Test method for {@link graph3d.elements.GLink#setAttributes(java.util.Hashtable)}.
	 */
	public void testSetAttributes() {
		// Création des éléments à tester
		GNode node1 = new GNode("node");
		GNode node2 = new GNode("node2");
		GLink lien = new GLink(true, "link", "red", node1, node2);

		// Modification des attributs
		String[] data = new String[] { "ip", "int", "192.168.1.1" };
		Hashtable<String, String[]> attributes = new Hashtable<String, String[]>();
		attributes.put("ip", data);
		
		try {
			lien.setAttributes(attributes);
		}catch(MissingAttributeForClassException e){
			System.err.println(e.getMessage());
		}
		
		// Vérification de la modification des attributs d'un lien.
		assertEquals(lien.getAttributes(), attributes);
	}

	/**
	 * Test method for {@link graph3d.elements.GLink#setFirstNode(graph3d.elements.GNode)}.
	 */
	public void testSetFirstNode() {
		// Création des éléments à tester
		GNode node = new GNode("node");
		GNode node1 = new GNode("node1");
		GNode node2 = new GNode("node2");
		GLink lien = new GLink(true, "link", "red", node1, node2);

		// Modification du premier noeud du lien
		lien.setFirstNode(node);

		// Vérification de la modification du premier noeud du lien
		assertEquals(lien.getFirstNode(), node);
	}

	/**
	 * Test method for {@link graph3d.elements.GLink#setName(java.lang.String)}.
	 */
	public void testSetName() {
		// Création des éléments à tester
		GNode node1 = new GNode("node1");
		GNode node2 = new GNode("node2");
		GLink lien = new GLink(true, "link", "red", node1, node2);

		// Modification du nom d'un lien
		lien.setName("lien");

		// Verification que le nom du lien a changer
		assertEquals(lien.getName(), "lien");

	}

	/**
	 * Test method for {@link graph3d.elements.GLink#setSecondNode(graph3d.elements.GNode)}.
	 */
	public void testSetSecondNode() {
		// Création des éléments à tester
		GNode node = new GNode("node");
		GNode node1 = new GNode("node1");
		GNode node2 = new GNode("node2");
		GLink lien = new GLink(true, "link", "red", node1, node2);

		// Modification du second noeud du lien
		lien.setSecondNode(node);

		// Verification que le second noeud a changer		
		assertEquals(lien.getSecondNode(), node);
	}

	/**
	 * Test method for {@link graph3d.elements.GLink#setType(boolean)}.
	 */
	public void testSetType() {
		// Création des éléments à tester
		GNode node1 = new GNode("node1");
		GNode node2 = new GNode("node2");
		GLink lien = new GLink(true, "link", "red", node1, node2);

		// Modification du type du lien
		lien.setType(false);

		// Verifie que le type du lien a bien changer
		assertEquals(lien.isType(), false);
	}

	/**
	 * Test method for {@link graph3d.elements.GLink#setAttributeByName(java.lang.String, java.lang.String, java.lang.String)}.
	 */
	public void testSetAttributeByName() {
		// Création des éléments à tester :
		GNode node1 = new GNode("node1");
		GNode node2 = new GNode("node2");
		GLink lien = new GLink(true, "link", "red", node1, node2);

		// Ajout des attributs au lien
		String[] data = new String[] { "wifi", "boolean", "false" };
		Hashtable<String, String[]> attributes = new Hashtable<String, String[]>();
		attributes.put("wifi", data);

		try {
			lien.setAttributes(attributes);
		} catch (MissingAttributeForClassException e) {
			System.err.println(e.getMessage());
		}

		try {
			// Modification d'un attribut du lien
			lien.setAttributeByName("wifi", "boolean", "true");
		} catch (TooMuchAttributesForClassException e) {
			System.err.println(e.getMessage());
		} catch (GException e) {
			System.err.println(e.getMessage());
		}

		// Verification des nouvelles valeurs de l'attribut.
		assertEquals(lien.getAttributeByName("wifi")[0], "wifi");
		assertEquals(lien.getAttributeByName("wifi")[1], "boolean");
		assertEquals(lien.getAttributeByName("wifi")[2], "true");
	}

	/**
	 * Test method for {@link graph3d.elements.GLink#setColor(java.lang.String)}.
	 */
	public void testSetColor() {
		// Création des éléments à tester :
		GNode node1 = new GNode("node1");
		GNode node2 = new GNode("node2");
		GLink lien = new GLink(true, "link", "red", node1, node2);

		// Modification de la couleur du lien
		lien.setColor("blue");

		// Verification que la couleur du lien a bien changer
		assertEquals(lien.getColor(), "blue");
	}

	/**
	 * Test method for {@link graph3d.elements.GLink#getAttributes()}.
	 */
	public void testGetAttributes() {
		// Création des éléments à tester
		GNode node1 = new GNode("node1");
		GNode node2 = new GNode("node2");
		GLink lien = new GLink(true, "link", "red", node1, node2);

		// Ajout des attributs au lien
		String[] data = new String[] { "ip", "int", "192.168.1.1" };
		Hashtable<String, String[]> attributes = new Hashtable<String, String[]>();
		attributes.put("ip", data);
		
		try {
			lien.setAttributes(attributes);
		} catch (MissingAttributeForClassException e) {
			System.err.println(e.getMessage());
		}

		// Verifie que la récuperation des attributs du lien est ok
		assertEquals(lien.getAttributes(), attributes);
	}

	/**
	 * Test method for {@link graph3d.elements.GLink#getFirstNode()}.
	 */
	public void testGetFirstNode() {
		// Création des éléments à tester
		GNode node1 = new GNode("node1");
		GLink link = new GLink("link", node1, new GNode("node2"));

		// Verifie que la récuperation du premier noeud d'un lien est ok
		assertEquals(link.getFirstNode(), node1);
	}

	/**
	 * Test method for {@link graph3d.elements.GLink#getName()}.
	 */
	public void testGetName() {
		// Création des éléments à tester		
		GLink link = new GLink("link", new GNode("node1"), new GNode("node2"));

		// Verifie que le nom d'un lien est le bon	
		assertEquals(link.getName(), "link");
	}

	/**
	 * Test method for {@link graph3d.elements.GLink#getSecondNode()}.
	 */
	public void testGetSecondNode() {
		// Création des éléments à tester			
		GNode node2 = new GNode("node2");
		GLink link = new GLink("link", new GNode("node1"), node2);

		// Verifie que la récuperation du second noeud d'un lien est ok		
		assertEquals(link.getSecondNode(), node2);
	}

	/**
	 * Test method for {@link graph3d.elements.GLink#getAttributeByName(java.lang.String)}.
	 */
	public void testGetAttributeByName() {
		// Création des éléments à tester
		GNode node1 = new GNode("node1");
		GNode node2 = new GNode("node2");
		GLink lien = new GLink(true, "link", "red", node1, node2);

		// Ajout des attribues au lien
		String[] data = new String[] { "wifi", "boolean", "false" };
		Hashtable<String, String[]> attributes = new Hashtable<String, String[]>();
		attributes.put("wifi", data);
		
		try {
			lien.setAttributes(attributes);
		} catch (MissingAttributeForClassException e) {
			System.err.println(e.getMessage());
		}

		// Récuperation des données sur un attribut d'un lien et vérification
		// des valeurs.
		assertEquals(lien.getAttributeByName("wifi"), data);
	}

	/**
	 * Test method for {@link graph3d.elements.GLink#getColor()}.
	 */
	public void testGetColor() {
		// Création des éléments à tester
		GNode node1 = new GNode("node1");
		GNode node2 = new GNode("node2");
		GLink lien = new GLink(true, "link", "red", node1, node2);

		// Verification de la couleur d'un lien
		assertEquals(lien.getColor(), "red");
	}

}
