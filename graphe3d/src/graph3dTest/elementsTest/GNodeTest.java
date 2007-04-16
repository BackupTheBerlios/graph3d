package graph3dTest.elementsTest;

import graph3d.elements.GNode;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Classe de test pour la classe {@link GNode}
 * A completer
 * 
 * @author Jerome
 */
public class GNodeTest {

	/**
	 * Test du premier constructeur de la classe. 
	 * GNode avec un nom et coordonnées par defaut.
	 */
	@Test public void GNode1() {
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
	 * Test du second constructeur de la classe. 
	 * GNode avec un nom et des coodonnées.
	 */
	@Test public void GNode2() {
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
	 * Test de la méthode permettant de changer le nom d'un noeud.
	 * On vérifie q'un changement effectué par l'appel du modificateur 
	 * est correctement enregistré.
	 */
	@Test public void setName(){
		// Création des éléments à tester :
		GNode node3 = new GNode("node three");

		// Modification du nom du noeud
		node3.setName("Node 3");

		// Verification du nom du noeud
		assertNotNull(node3.getName());
		assertEquals(node3.getName(), "Node 3");
	}

}
