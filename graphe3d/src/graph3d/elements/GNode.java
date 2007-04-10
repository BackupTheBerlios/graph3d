package graph3d.elements;

import graph3d.exception.GException;
import graph3d.exception.GLinkAlreadyExistException;
import graph3d.exception.MissingAttributeForClassException;
import graph3d.exception.ToMuchAttributesForClassException;
import graph3d.exception.UnvalidAttributeTypeException;

import java.util.Enumeration;
import java.util.Hashtable;

/**
 * This class define a node into a graph. A node is defined by a name,
 * coordonates and attributes. The coordonates are X, Y, Z which define the
 * location in a 3D view. In this class, there are any attributes. To define a
 * node with attributes, you must create a class which inherite of this class.
 * 
 * @author Erwan Daubert
 * @version 1.0
 * 
 */
public class GNode {

	/**
	 * It's the identifier of an object GNode.
	 */
	private String name;

	/**
	 * This float[] contains X, Y, Z value which define the coordonates into a
	 * 3D view.
	 */
	private float[] coordonates = new float[3];

	/**
	 * This Hashtable contains all attributes for a node. <b>Be careful</b> : A
	 * GNode can't contains attributes. If you want declare attributes for a
	 * node, you must create another class which inherite of this class.
	 */
	private Hashtable<String, String[]> attributes;

	/**
	 * This Hashtable contains links which is connected to a node.
	 */
	private Hashtable<String, GLink> links;

	/**
	 * The default constructor You must specify the name. Coordonates are define
	 * by 0, 0, 0. <b>Be careful</b> : when the 3D view is created, it's
	 * impossible that many nodes have the same coordonates or coordonates too
	 * near. The nodes are automatically moved on the 3D view and the
	 * coordonates change.
	 * 
	 * @param _name
	 *            the identifier of the GNode.
	 */
	public GNode(String _name) {
		this(_name, 0, 0, 0);
	}

	/**
	 * The complete constructor of GNode
	 * 
	 * @param _name
	 *            the identifier of the node
	 * @param _x
	 *            coordonate X on the 3D view
	 * @param _y
	 *            coordonate Y on the 3D view
	 * @param _z
	 *            coordonate Z on the 3D view
	 */
	public GNode(String _name, float _x, float _y, float _z) {
		this.name = _name;
		this.coordonates[0] = _x;
		this.coordonates[1] = _y;
		this.coordonates[2] = _z;
		this.attributes = new Hashtable<String, String[]>();
	}

	/**
	 * The getter of the attributes
	 * 
	 * @return attributes
	 */
	public Hashtable<String, String[]> getAttributes() {
		return attributes;
	}

	/**
	 * This function allow you to modify all node 's attributes. 
	 * @param _attributes the Hashtable which contains all attributes.
	 */
	public void setAttributes(Hashtable<String, String[]> _attributes) {
		Enumeration<String> keyAttributes = this.attributes.keys();
		Enumeration<String> key_Attributes = _attributes.keys();
		
		while (keyAttributes.hasMoreElements()) {
			String key = keyAttributes.nextElement();
			boolean sameKey = false;
			while (key_Attributes.hasMoreElements()) {
				String key_ = key_Attributes.nextElement();
				if (key.equals(key_)) {
					sameKey = true;
					break;
				}
			}
			if (sameKey == false) {
				throw new MissingAttributeForClassException(this);
			}
		}
		
		this.attributes = _attributes;
	}

	/**
	 * The getter of the coordonates
	 * 
	 * @return coordonates
	 */
	public float[] getCoordonates() {
		return coordonates;
	}

	/**
	 * The setter of the coordonates for this node.
	 * @param _coordonates the new coordonates
	 */
	public void setCoordonates(float[] _coordonates) {
		this.coordonates = _coordonates;
	}

	/**
	 * The getter of the name
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * The setter of the name
	 * 
	 * @param _name
	 *            thenew value for the attribute name.
	 */
	public void setName(String _name) {
		this.name = _name;
	}

	/**
	 * The setter of an attribute define by a name.
	 * 
	 * @param _name
	 *            the name of the attribute
	 * @param _type
	 *            the type which is short, byte, int, long, float, double,
	 *            boolean, char or String
	 * @param _value
	 *            the value of this attribute
	 * @throws UnvalidAttributeTypeException
	 *             if the parameter type is not <b>short, byte, int, long,
	 *             float, double, char or String</b>
	 * @throws ToMuchAttributesForClassException
	 *             if the name of the parameter doesn't exist in the Hashtable.
	 *             It's not an attribute.
	 * @throws GException
	 *             if the parameter value is not valid for the parameter type.
	 */
	public void setAttributeByName(String _name, String _type, String _value)
			throws UnvalidAttributeTypeException,
			ToMuchAttributesForClassException, GException {
		if (this.attributes.containsKey(_name)) {
			if (_type.equals("short")) {
				try {
					Short.parseShort(_value);
				} catch (NumberFormatException e) {
					throw new GException(
							"Valeur du paramètre invalide.\n un short attendu.");
				}
			} else if (_type.equals("byte")) {
				try {
					Byte.parseByte(_value);
				} catch (NumberFormatException e) {
					throw new GException(
							"Valeur du paramètre invalide.\n un byte attendu.");
				}
			} else if (_type.equals("int")) {
				try {
					Integer.parseInt(_value);
				} catch (NumberFormatException e) {
					throw new GException(
							"Valeur du paramètre invalide.\n un int attendu.");
				}
			} else if (_type.equals("long")) {
				try {
					Long.parseLong(_value);
				} catch (NumberFormatException e) {
					throw new GException(
							"Valeur du paramètre invalide.\n un long attendu.");
				}
			} else if (_type.equals("float")) {
				try {
					Float.parseFloat(_value);
				} catch (NumberFormatException e) {
					throw new GException(
							"Valeur du paramètre invalide.\n un float attendu.");
				}
			} else if (_type.equals("double")) {
				try {
					Double.parseDouble(_value);
				} catch (NumberFormatException e) {
					throw new GException(
							"Valeur du paramètre invalide.\n un double attendu.");
				}
			} else if (_type.equals("boolean")) {
				try {
					Boolean.parseBoolean(_value);
				} catch (NumberFormatException e) {
					throw new GException(
							"Valeur du paramètre invalide.\n un boolean attendu.");
				}
			} else if (_type.equals("char")) {
				if (_value.toCharArray().length != 1) {
					throw new GException(
							"Valeur du paramètre invalide.\n un char attendu.");
				}
				// } else if (type.equals("String")) {

			} else {
				// type is undefine
				throw new UnvalidAttributeTypeException(_type);
			}
		} else {
			// attribute doesn't exist
			throw new ToMuchAttributesForClassException(this, _name);
		}
		this.attributes.put(_name, new String[] { _name, _type, _value });
	}

	/**
	 * The getter of an attribute
	 * 
	 * @param _name
	 *            the identifier which define an attribute
	 * @return a String[] which contains all datas of the attribute (name, type,
	 *         value)
	 */
	public String[] getAttributeByName(String _name) {
		return this.attributes.get(_name);
	}

	/**
	 * The getter of the links which are connected with this node.
	 * 
	 * @return links
	 */
	public Hashtable<String, GLink> getLinks() {
		return links;
	}

	/**
	 * This function add a link into the Hashtable which contains all links which are connected with this node.
	 * @param link the link which you want add.
	 */
	public void addLink(GLink link) {
		if (!this.links.containsKey(link.getName())) {
			this.links.put(link.getName(), link);
		} else {
			throw new GLinkAlreadyExistException(this, link); 
		}
	}

	/**
	 * the getter of the X coordonate
	 * 
	 * @return coordonates[0]
	 */
	public float getCoordonnateX() {
		return this.getCoordonates()[0];
	}

	/**
	 * the getter of the Y coordonate
	 * 
	 * @return coordonates[1]
	 */
	public float getCoordonnateY() {
		return this.getCoordonates()[1];
	}

	/**
	 * the getter of the Z coordonate
	 * 
	 * @return coordonates[2]
	 */
	public float getCoordonnateZ() {
		return this.getCoordonates()[2];
	}

	/**
	 * The setter of the coordonate X
	 * 
	 * @param _x
	 *            the new value of the attribute X
	 */
	public void setCoordonnateX(float _x) {
		this.coordonates[0] = _x;
	}

	/**
	 * The setter of the coordonate Y
	 * 
	 * @param _y
	 *            the new value of the attribute Y
	 */
	public void setCoordonnateY(float _y) {
		this.coordonates[1] = _y;
	}

	/**
	 * The setter of the coordonate Z
	 * 
	 * @param _z
	 *            the new value of the attribute Z
	 */
	public void setCoordonnateZ(float _z) {
		this.coordonates[2] = _z;
	}

	/**
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String toString = "node :\n";
		toString += name + "\n";
		toString += "\tcoordonates :\n";
		toString += "\t\tX = " + this.getCoordonnateX() + "\n";
		toString += "\t\tY = " + this.getCoordonnateY() + "\n";
		toString += "\t\tZ = " + this.getCoordonnateZ() + "\n";
		toString += "\n\tattributes :\n";
		Enumeration<String> keys = this.attributes.keys();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			String[] datas = this.attributes.get(key);
			toString += "\t\tkey : " + key + "\t\t\ttype : " + datas[1]
					+ "\t\t\tvalue : " + datas[2];
		}

		return toString;

	}
}