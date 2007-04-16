
package graph3dTest.elementsTest;

import graph3d.elements.GNode;
import junit.framework.TestCase;

/**
 * Classe de test pour la classe {@link GNode}.
 * A completer
 * 
 * @author Jerome
 */
public class GNodeTest extends TestCase {

	/**
	 * Test du premier constructeur de la classe. 
	 * GNode avec un nom et coordonnées par defaut.
	 */
	public final void testGNode1() {
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

}
