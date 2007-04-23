package graph3dTest.parserXMLTest;

import java.io.File;

import graph3d.elements.GGraph;
import graph3d.elements.GLink;
import graph3d.elements.GNode;
import graph3d.parserXML.GParser;
import junit.framework.TestCase;

/**
 * This class test all the methods of the GParser class.
 * 
 * @author Jerome Catric, Iuliana Popa
 */
public class GParserTest extends TestCase {

	/** 1)
	 * Test method for {@link graph3d.parserXML.GParser#GParser(java.lang.String)}.
	 */
	public void testGParserString() {
		GParser p= new GParser("test2.xml");
		assertEquals(p.getXMLFileName(), "test2.xml");
	}

	/**
	 * Test method for {@link graph3d.parserXML.GParser#GParser(graph3d.elements.GGraph, java.lang.String)}.
	 */
	public void testGParserGGraphString() {
		GGraph g = new GGraph("graph");
		GNode node = new GNode("node1");
		g.addNode(node);
		GParser p= new GParser(g,"test3.xml");
		assertEquals(p.getXMLFileName(), "test3.xml");
		assertNotNull(p.getGraph());
	}

	/**
	 * Test method for {@link graph3d.parserXML.GParser#getGraph()}.
	 */
	public void testGetGraph() {
		GParser p= new GParser("test3.xml");
		GGraph g = p.getGraph();
		assertNotNull(p.getGraph());
	}

	/**
	 * Test method for {@link graph3d.parserXML.GParser#saveGraph()}.
	 */
	public void testSaveGraph() {
		GGraph graph = new GGraph("Mon Graph");
		
		GNode node1 = new GNode("node two", 2, 2, 2);
		GNode node2 = new GNode("node two", 3, 3, 3);
		GNode node3 = new GNode("node two", 4, 4, 4);
		GNode node4 = new GNode("node two", 5, 5, 5);
		GNode node5 = new GNode("node two", 6, 6, 6);
		
		graph.addNode(node1);
		graph.addNode(node2);
		graph.addNode(node3);
		graph.addNode(node4);
		graph.addNode(node5);
		
		GLink link = new GLink("lien1",node1,node2);
		GLink link2 = new GLink("lien2",node2,node5);
		GLink link3 = new GLink("lien3",node4,node3);
		
		graph.addLink(link);
		graph.addLink(link2);
		graph.addLink(link3);
		
		GParser p= new GParser(graph, "test3.xml");
		p.saveGraph();
		
		assertTrue((new File(p.getXMLFileName())).exists());
		
	}

	/**
	 * Test method for {@link graph3d.parserXML.GParser#getXMLFileName()}.
	 */
	public void testGetXMLFileName() {
		GParser p= new GParser("test3.xml");
		assertEquals(p.getXMLFileName(), "test3.xml");
	}

	/**
	 * Test method for {@link graph3d.parserXML.GParser#setXMLFileName(java.lang.String)}.
	 */
	public void testSetXMLFileName() {
		GParser p= new GParser("test3.xml");
		p.setXMLFileName("test4.xml");
		assertEquals(p.getXMLFileName(), "test4.xml");
		
	}

}
