package graph3d.parserXML;

import graph3d.elements.GGraph;
import graph3d.elements.GLink;
import graph3d.elements.GNode;
import graph3d.exception.GException;
import graph3d.exception.SameNameException;
import graph3d.exception.XMLDefinitionException;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Enumeration;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * This class define a parser to save or get a graph.
 * @author Erwan Daubert
 * @version 1.0
 */
public class GParser {

	private DocumentBuilder db;

	private Document document;

	private String XMLFileName;

	private GGraph graph;

	/**
	 * This constructor is used to create a parser to read a XML File.
	 * @param _XMLFileName the path of the XML File.
	 */
	public GParser(String _XMLFileName) {
		this(new GGraph(), _XMLFileName);
	}

	/**
	 * This constructor is used to create a parser to write a XML file.
	 * @param _graph the graph to write.
	 * @param _XMLFileName the path of the XML file
	 */
	public GParser(GGraph _graph, String _XMLFileName) {
		this.graph = _graph;
		this.XMLFileName = _XMLFileName;
		this.createDocumentBuilder();
	}

	/**
	 * This function allow you to get a GGraph define in a XML file.
	 * @return a GGraph component which represents the graph define in the XML file
	 * @throws GException
	 */
	public GGraph getGraph() throws GException {
		this.readFile();
	
		Element root = this.document.getDocumentElement();
		this.graph = new GGraph(root.getAttribute("name"));
	
		NodeList list = root.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			Node child = list.item(i);
			if (child != null && child instanceof Element) {
				String nodeName = child.getNodeName();
				if (nodeName.equals("node")) {
					GNode node = this.createNode((Element) child);
					if (!this.graph.addNode(node)) {
						new SameNameException(node.getName());
					}
				} else if (nodeName.equals("link")) {
					GLink link = this.createLink((Element) child);
					if (!this.graph.addLink(link)) {
						new SameNameException(link.getName());
					}
				}
			}
		}
		// traitement des frères
		Node next = root.getNextSibling();
		// normalement pas de frère dans le cas de la racine
		if (next != null) {
			// Exception permettant de signaler à l'utilisateur que la suite
			// n'est pas pris en compte
			throw new GException(
					"The first graph is define but you don't define many graphs with one XML file.\nThe others graphs are not define.");
		}
		return this.graph;
	}

	/**
	 * This functions is used to save the graph in a XML file.
	 *
	 */
	public void saveGraph() {
	
		this.createDocument(this.graph);
	
		File XMLFile = new File(this.XMLFileName);
		Source source = new DOMSource(this.document);
	
		Result resultat = new StreamResult(XMLFile);
		try {
			Transformer transformer = TransformerFactory.newInstance()
					.newTransformer();
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			transformer
					.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
			transformer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.transform(source, resultat);
		} catch (TransformerConfigurationException e) {
			System.err.println("Impossible de créer un transformateur XML.");
	
			System.exit(1);
		} catch (TransformerException e) {
	
			e.printStackTrace();
		}
	
	}

	/**
	 * The getter of the XML file path
	 * @return XMLFileName
	 */
	public String getXMLFileName() {
		return this.XMLFileName;
	}

	/**
	 * The setter of the XML file path
	 * @param fileName the new path for the XML file
	 */
	public void setXMLFileName(String fileName) {
		this.XMLFileName = fileName;
	}

	private void createDocumentBuilder() {
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setIgnoringComments(true);
			dbf.setNamespaceAware(true);
			dbf.setValidating(true);
			dbf.setAttribute("http://xml.org/sax/features/validation", true);
			dbf.setAttribute("http://apache.org/xml/features/validation/schema",
					true);

			dbf.setIgnoringElementContentWhitespace(true);
			this.db = dbf.newDocumentBuilder();

			GErrorHandler handler = new GErrorHandler();

			this.db.setErrorHandler(handler);
			this.document = this.db.newDocument();
		} catch (ParserConfigurationException e) {
			System.out
					.println("the XML parser used  doesn't support feature :");
			System.out.println(e.getMessage());
			System.out.println("Maybe your Java API is not a 1.5");
			System.out.println("Update your JVM if it's necessary.");
			// throw
		}

	}

	private void readFile() {
		try {
			this.document = this.db.parse(this.XMLFileName);
		} catch (SAXException e) {
			e.printStackTrace();
			// throw
		} catch (IOException e) {
			e.printStackTrace();
			// throw new FileNotFoundException();
		}

		// faire remonter les exceptions???
	}

	private GNode createNode(Element element) {
		GNode node = null;
		String name = element.getAttribute("name");

		try {
			Class<?> classe = Class.forName(element.getAttribute("class"));
			java.lang.reflect.Constructor constructeur = classe
					.getConstructor(new Class[] { java.lang.String.class });
			node = (GNode) constructeur.newInstance(new Object[] { name });
			String radius = element.getAttribute("radius");
			if (radius != null) {
				node.setRadius(Float.parseFloat(radius));
			}

			NodeList list = element.getChildNodes();
			for (int i = 0; i < list.getLength(); i++) {
				Node child = list.item(i);
				if (child instanceof Element) {
					if (child.getNodeName().equals("enum-attr")) {
						String attrName = ((Element) child)
								.getAttribute("attr-name");
						String attrType = ((Element) child)
								.getAttribute("attr-type");
						String attrValue = ((Element) child)
								.getAttribute("attr-value");

						node.setAttributeByName(attrName, attrType, attrValue);
					} else if (child.getNodeName().equals("coordonates")) {
						try {
							float x = Float.parseFloat(((Element) child)
									.getAttribute("x"));
							float y = Float.parseFloat(((Element) child)
									.getAttribute("y"));
							float z = Float.parseFloat(((Element) child)
									.getAttribute("z"));
							node.setCoordonates(new float[] { x, y, z });
						} catch (NumberFormatException e) {
							throw new GException(
									"Valeur des coordonées invalides.\n les coordonées sont des floats.");
						}
					} else {
						// normalement géré par le XSD
						System.out.println(child.getNodeName());
					}
				}
			}

		} catch (SecurityException e) {

			e.printStackTrace();
			// throw
		} catch (NoSuchMethodException e) {

			e.printStackTrace();
			// throw
		} catch (IllegalArgumentException e) {

			e.printStackTrace();
			// throw
		} catch (InstantiationException e) {

			e.printStackTrace();
			// throw
		} catch (IllegalAccessException e) {

			e.printStackTrace();
			// throw
		} catch (InvocationTargetException e) {

			e.printStackTrace();
			// throw
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		}
		return node;
	}

	private GLink createLink(Element element) {
		GLink link = null;
		boolean type;
		if (element.getAttribute("type").equals("arrow")) {
			type = true;
		} else {
			type = false;
		}
		String name = element.getAttribute("name");
		GNode first = this.graph.getNode(element.getAttribute("link_start"));
		GNode second = this.graph.getNode(element.getAttribute("link_end"));
		try {
			Class<?> classe = Class.forName(element.getAttribute("class"));
			java.lang.reflect.Constructor constructeur = classe
					.getConstructor(new Class[] { boolean.class,
							java.lang.String.class,
							graph3d.elements.GNode.class,
							graph3d.elements.GNode.class });
			link = (GLink) constructeur.newInstance(new Object[] { type, name, first, second });


			String color = element.getAttribute("color");
			if (color != null) {
				link.setColor(color);
			}
	
			NodeList list = element.getChildNodes();
			for (int i = 0; i < list.getLength(); i++) {
				Node child = list.item(i);
				if (child instanceof Element) {
					if (child.getNodeName().equals("enum-attr")) {
						String attrName = ((Element) child)
								.getAttribute("attr-name");
						String attrType = ((Element) child)
								.getAttribute("attr-type");
						String attrValue = ((Element) child)
								.getAttribute("attr-value");

						link.setAttributeByName(attrName, attrType, attrValue);
					} else {
						// normalement géré par le XSD
						System.out.println(child.getNodeName());
					}
				}
			}
			return link;

		} catch (SecurityException e) {

			e.printStackTrace();
			// throw
		} catch (NoSuchMethodException e) {

			e.printStackTrace();
			// throw
		} catch (IllegalArgumentException e) {

			e.printStackTrace();
			// throw
		} catch (InstantiationException e) {

			e.printStackTrace();
			// throw
		} catch (IllegalAccessException e) {

			e.printStackTrace();
			// throw
		} catch (InvocationTargetException e) {

			e.printStackTrace();
			// throw
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		}
		return link;

	}

	private void createDocument(GGraph graphe) {

		Element racine = this.document.createElement("graph");
		racine.setAttribute("name", graphe.getName());

		String xsd;
		URL url = getClass().getResource("/xml/schema.xsd");
		if (url == null) {
			xsd = new File("xml/schema.xsd").getAbsolutePath();
		} else {
			xsd = url.toString();
		}
		racine.setAttribute("xmlns:xsi",
				"http://www.w3.org/2001/XMLSchema-instance");
		racine.setAttribute("xsi:noNamespaceSchemaLocation", xsd);

		// création des nodes
		Enumeration<GNode> nodes = graphe.getNodes().elements();
		while (nodes.hasMoreElements()) {
			racine.appendChild(createElement(nodes.nextElement()));
		}

		// création des links
		Enumeration<GLink> links = graphe.getLinks().elements();
		while (links.hasMoreElements()) {
			racine.appendChild(createElement(links.nextElement()));
		}
		this.document.appendChild(racine);
	}

	private Element createElement(GNode node) {
		Element element = this.document.createElement("node");
		element.setAttribute("name", node.getName());
		element.setAttribute("class", node.getClass().getName());
		element.setAttribute("radius", "" + node.getRadius());
		
		// gestion du coordonates
		Element coordonates = this.document.createElement("coordonates");
		coordonates.setAttribute("x", node.getCoordonnateX() + "");
		coordonates.setAttribute("y", node.getCoordonnateY() + "");
		coordonates.setAttribute("z", node.getCoordonnateZ() + "");
		element.appendChild(coordonates);
		// les attributs
		Enumeration<String> keys = node.getAttributes().keys();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			String[] datas = node.getAttributeByName(key);

			Element attribute = this.document.createElement("enum-attr");
			attribute.setAttribute("attr-name", key);
			attribute.setAttribute("attr-type", datas[1]);
			attribute.setAttribute("attr-value", datas[2]);
			element.appendChild(attribute);
		}
		return element;
	}

	private Element createElement(GLink link) {
		Element element = this.document.createElement("link");
		element.setAttribute("name", link.getName());
		element.setAttribute("class", link.getClass().getName());
		element.setAttribute("color", link.getColor());
		if (link.isType()) {
			element.setAttribute("type", "arrow");
		} else {
			element.setAttribute("type", "bridge");
		}
		element.setAttribute("link_start", link.getFirstNode().getName());
		element.setAttribute("link_end", link.getSecondNode().getName());

		// les attributs
		Enumeration<String> keys = link.getAttributes().keys();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			String[] datas = link.getAttributeByName(key);

			Element attribute = this.document.createElement("enum-attr");
			attribute.setAttribute("attr-name", key);
			attribute.setAttribute("attr-type", datas[1]);
			attribute.setAttribute("attr-value", datas[2]);
			element.appendChild(attribute);
		}
		return element;
	}

	class GErrorHandler implements ErrorHandler {

		public void error(SAXParseException exception)
				throws XMLDefinitionException {
			System.err.println("error : ");
			exception.printStackTrace();
		}

		public void fatalError(SAXParseException exception) throws SAXException {
			System.err.println("fatal error :");
			exception.printStackTrace();
		}

		public void warning(SAXParseException exception) throws SAXException {
			System.err.println("warning :");
			exception.printStackTrace();
		}

	}
}
