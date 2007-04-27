package graph3dTest.universeTest;

import javax.media.j3d.TransformGroup;
import graph3d.elements.GNode;
import graph3d.universe.GNodeView;
import junit.framework.TestCase;

/**
 * This class test all the methods of the {@link GNodeView} class.
 * 
 * @author Jerome Catric, Iuliana Popa
 */
public class GNodeViewTest extends TestCase {

	/**
	 * Test method for {@link graph3d.universe.GNodeView#GNodeView(graph3d.elements.GNode)}.
	 */
	public void testGNodeView() {
		GNode node = new GNode("node");
		GNodeView nv = new GNodeView(node);
		assertEquals(nv.getNode(), node);
		assertNotNull(nv.getTransformGroup());
	}

	/**
	 * Test method for {@link graph3d.universe.GNodeView#update()}.
	 */
	public void testUpdate() {
	}

	/**
	 * Test method for {@link graph3d.universe.GNodeView#getNode()}.
	 */
	public void testGetNode() {
		GNode node = new GNode("node");
		GNodeView nv = new GNodeView(node);
		assertEquals(nv.getNode(), node);
	}

	/**
	 * Test method for {@link graph3d.universe.GNodeView#setNode(graph3d.elements.GNode)}.
	 */
	public void testSetNode() {
		GNode node = new GNode("node");
		GNodeView nv = new GNodeView(node);
		assertEquals(nv.getNode(), node);
		GNode node2 = new GNode("node2");
		nv.setNode(node2);
		assertEquals(nv.getNode(), node2);
	}

	/**
	 * Test method for {@link graph3d.universe.GNodeView#getTransformGroup()}.
	 */
	public void testGetTransformGroup() {
		GNode node = new GNode("node");
		GNodeView nv = new GNodeView(node);
		assertNotNull(nv.getTransformGroup());
	}

	/**
	 * Test method for {@link graph3d.universe.GNodeView#setTransformGroup(javax.media.j3d.TransformGroup)}.
	 */
	public void testSetTransformGroup() {
		GNode node = new GNode("node");
		GNodeView nv = new GNodeView(node);
		assertNotNull(nv.getTransformGroup());
		TransformGroup transformGroup = new TransformGroup();
		nv.setTransformGroup(transformGroup);
		assertEquals(nv.getTransformGroup(), transformGroup);
	}

}
