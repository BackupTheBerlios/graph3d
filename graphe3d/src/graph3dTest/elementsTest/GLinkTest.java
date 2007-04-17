/**
 * 
 */
package graph3dTest.elementsTest;

import graph3d.elements.GLink;
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
public class GLinkTest extends TestCase {

	/**
	 * Test method for {@link graph3d.elements.GLink#GLink(java.lang.String, graph3d.elements.GNode, graph3d.elements.GNode)}.
	 */
	public void testGLinkStringGNodeGNode() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link graph3d.elements.GLink#GLink(java.lang.String, java.lang.String, graph3d.elements.GNode, graph3d.elements.GNode)}.
	 */
	public void testGLinkStringStringGNodeGNode() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link graph3d.elements.GLink#GLink(boolean, java.lang.String, graph3d.elements.GNode, graph3d.elements.GNode)}.
	 */
	public void testGLinkBooleanStringGNodeGNode() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link graph3d.elements.GLink#GLink(boolean, java.lang.String, java.lang.String, graph3d.elements.GNode, graph3d.elements.GNode)}.
	 */
	public void testGLinkBooleanStringStringGNodeGNode() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link graph3d.elements.GLink#setAttributes(java.util.Hashtable)}.
	 */
	public void testSetAttributes() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link graph3d.elements.GLink#setFirstNode(graph3d.elements.GNode)}.
	 */
	public void testSetFirstNode() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link graph3d.elements.GLink#setName(java.lang.String)}.
	 */
	public void testSetName() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link graph3d.elements.GLink#setSecondNode(graph3d.elements.GNode)}.
	 */
	public void testSetSecondNode() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link graph3d.elements.GLink#setType(boolean)}.
	 */
	public void testSetType() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link graph3d.elements.GLink#setAttributeByName(java.lang.String, java.lang.String, java.lang.String)}.
	 */
	public void testSetAttributeByName() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link graph3d.elements.GLink#setColor(java.lang.String)}.
	 */
	public void testSetColor() {
		fail("Not yet implemented");
	}
	
	/**
	 * Test method for {@link graph3d.elements.GLink#getAttributes()}.
	 */
	public void testGetAttributes() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link graph3d.elements.GLink#getFirstNode()}.
	 */
	public void testGetFirstNode() {
		GNode node1 = new GNode("node1");
		GLink link = new GLink("link",node1,new GNode("node2"));
		assertEquals(link.getFirstNode(), node1);
	}

	/**
	 * Test method for {@link graph3d.elements.GLink#getName()}.
	 */
	public void testGetName() {
		GLink link = new GLink("link",new GNode("node1"),new GNode("node2"));
		assertEquals(link.getName(), "link");
	}

	/**
	 * Test method for {@link graph3d.elements.GLink#getSecondNode()}.
	 */
	public void testGetSecondNode() {
		GNode node2 = new GNode("node2");
		GLink link = new GLink("link",new GNode("node1"),node2);
		assertEquals(link.getSecondNode(), node2);
	}

	/**
	 * Test method for {@link graph3d.elements.GLink#getAttributeByName(java.lang.String)}.
	 */
	public void testGetAttributeByName() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link graph3d.elements.GLink#getColor()}.
	 */
	public void testGetColor() {
		fail("Not yet implemented");
	}

}
