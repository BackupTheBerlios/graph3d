package graph3d.elements;

import graph3d.exception.GException;
import graph3d.exception.GLinkAlreadyExistException;
import graph3d.exception.InvalidAttributeTypeException;
import graph3d.exception.MissingAttributeForClassException;
import graph3d.exception.TooMuchAttributesForClassException;

import java.util.Enumeration;
import java.util.Hashtable;

/**
 * 
 * @author Erwan Daubert
 * @version 1.0
 * 
 * This class define a basic link into a graph. This link is potentially
 * oriented or not and doesn't contains attributes To create links which
 * contains attributes, you must define a class which inherited of this.
 * 
 */
public class GLink {

	/**
	 * This attributes identify the link. It's the name of the link
	 */
	private String name;

	/**
	 * If type is true, this link is oriented, it's not else.
	 */
	private boolean type;

	/**
	 * This attribute define the color of the representation of the link
	 */
	private String color;

	/**
	 * The node which is the start of the link if this is oriented. If it's not
	 * oriented, it a node which is to the extremity of the link
	 */
	private GNode firstNode;

	/**
	 * The node which is the end of the link if this is oriented. If it's not
	 * oriented, it a node which is to the extremity of the link
	 */
	private GNode secondNode;

	/**
	 * This Hashtable contains all attributes define in a subclass of GLink. In
	 * this class, there are not attributes. If you define a class inherited of
	 * GLink, you must declare into this Hashtable all attributes which define
	 * this class.
	 * 
	 * See tutorial for the exemple.
	 */
	private Hashtable<String, String[]> attributes;

	/**
	 * This constructor define the identifier of this link and his extremities.
	 * By default, a link is not oriented and his color is black.
	 * 
	 * @param _name
	 *            the identifier of this link
	 * @param _first
	 *            a node which is an extrenity of this link
	 * @param _second
	 *            a node which is another extremity.
	 * @throws GLinkAlreadyExistException
	 */
	public GLink(String _name, GNode _first, GNode _second) {
		this(false, _name, "white", _first, _second);
	}

	/**
	 * This constructor define the identifier of this link, his color and his
	 * extremities. By default, a link is not oriented.
	 * 
	 * @param _name
	 *            the identifier of this link
	 * @param _color
	 *            the color which define the color of the link in the 3D view.
	 * @param _first
	 *            a node which is an extrenity of this link.
	 * @param _second
	 *            a node which is another extremity.
	 * @throws GLinkAlreadyExistException
	 */
	public GLink(String _name, String _color, GNode _first, GNode _second) {
		this(false, _name, _color, _first, _second);
	}

	/**
	 * This constructor define the identifier of the link, his type (arrow or
	 * bridge)and his extremities. By default, a link have the white color.
	 * 
	 * @param _type
	 *            true if this link is an arrow, false else
	 * @param _name
	 *            the identifier of this link
	 * @param _first
	 *            a node which is an extrenity of this link. It's the start of
	 *            the link if this link is an arrow.
	 * @param _second
	 *            a node which is another extremity. It's the end of the link if
	 *            this link is an arrow.
	 * @throws GLinkAlreadyExistException
	 */
	public GLink(boolean _type, String _name, GNode _first, GNode _second) {
		this(_type, _name, "white", _first, _second);
	}

	/**
	 * This constructor define a link. With this constructor, you define if the
	 * link is oriented or not.
	 * 
	 * @param _type
	 *            true, if the link is oriented, false else.
	 * @param _name
	 *            the identifier of this link
	 * @param _color
	 *            the color which define the color of the link in the 3D view.
	 * @param _first
	 *            a node which is an extrenity of this link. If type is true,
	 *            this node is the start of the link.
	 * @param _second
	 *            a node which is another extremity. If type is true, this node
	 *            is the end of the link.
	 * @throws GLinkAlreadyExistException
	 */
	public GLink(boolean _type, String _name, String _color, GNode _first,
			GNode _second) {
		this.name = _name;
		this.color = _color;
		this.firstNode = _first;
		this.secondNode = _second;
		this.type = _type;

		this.attributes = new Hashtable<String, String[]>();
		try {
			this.firstNode.addLink(this);
		} catch (GLinkAlreadyExistException e) {
			e.printStackTrace();
			e.showError();
		}
		if (!this.firstNode.getName().equals(this.secondNode.getName())) {
			try {
				this.secondNode.addLink(this);
			} catch (GLinkAlreadyExistException e) {
				e.printStackTrace();
				e.showError();
			}
		}
	}

	/**
	 * The getter of the attributes Hashtable.
	 * 
	 * @return attributes
	 */
	public Hashtable<String, String[]> getAttributes() {
		return this.attributes;
	}

	/**
	 * This function allow you to modify all link 's attributes.
	 * 
	 * @param _attributes
	 *            the Hashtable which contains all attributes.
	 * @throws MissingAttributeForClassException
	 */
	public void setAttributes(Hashtable<String, String[]> _attributes)
			throws MissingAttributeForClassException {
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
	 * The getter of the firstNode
	 * 
	 * @return firstNode
	 */
	public GNode getFirstNode() {
		return this.firstNode;
	}

	/**
	 * The setter of the firstNode
	 * 
	 * @param _firstNode
	 *            a new GNode which define the firstNode
	 */
	public void setFirstNode(GNode _firstNode) {
		this.firstNode = _firstNode;
	}

	/**
	 * The getter of the name
	 * 
	 * @return name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * The setter of the name
	 * 
	 * @param _name
	 *            the new String which define the name
	 */
	public void setName(String _name) {
		this.name = _name;
	}

	/**
	 * The getter of the secondNode
	 * 
	 * @return secondNode
	 */
	public GNode getSecondNode() {
		return this.secondNode;
	}

	/**
	 * The setter of the secondNode
	 * 
	 * @param _secondNode
	 *            the new GNode which define the secondNode
	 */
	public void setSecondNode(GNode _secondNode) {
		this.secondNode = _secondNode;
	}

	/**
	 * the getter of type. If type is true, that's mean this link is oriented,
	 * it's not oriented else.
	 * 
	 * @return type
	 */
	public boolean isType() {
		return this.type;
	}

	/**
	 * The setter of the type.
	 * 
	 * @param _type
	 *            a boolean which define the new type.
	 */
	public void setType(boolean _type) {
		this.type = _type;
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
	 * @throws InvalidAttributeTypeException
	 *             if the parameter type is not <b>short, byte, int, long,
	 *             float, double, char or String</b>.
	 * @throws TooMuchAttributesForClassException
	 *             if the name of the parameter doesn't exist in the Hashtable.
	 *             It's not an attribute.
	 * @throws GException
	 *             if the parameter value is not valid for the parameter type.
	 */
	public void setAttributeByName(String _name, String _type, String _value)
			throws InvalidAttributeTypeException,
			TooMuchAttributesForClassException, GException {
		if (this.attributes.containsKey(_name)) {
			if (_type.equals("short")) {
				try {
					Short.parseShort(_value);
				} catch (NumberFormatException e) {
					throw new InvalidAttributeTypeException(this, _name, _type,
							_value);
				}
			} else if (_type.equals("byte")) {
				try {
					Byte.parseByte(_value);
				} catch (NumberFormatException e) {
					throw new InvalidAttributeTypeException(this, _name, _type,
							_value);
				}
			} else if (_type.equals("int")) {
				try {
					Integer.parseInt(_value);
				} catch (NumberFormatException e) {
					throw new InvalidAttributeTypeException(this, _name, _type,
							_value);
				}
			} else if (_type.equals("long")) {
				try {
					Long.parseLong(_value);
				} catch (NumberFormatException e) {
					throw new InvalidAttributeTypeException(this, _name, _type,
							_value);
				}
			} else if (_type.equals("float")) {
				try {
					Float.parseFloat(_value);
				} catch (NumberFormatException e) {
					throw new InvalidAttributeTypeException(this, _name, _type,
							_value);
				}
			} else if (_type.equals("double")) {
				try {
					Double.parseDouble(_value);
				} catch (NumberFormatException e) {
					throw new InvalidAttributeTypeException(this, _name, _type,
							_value);
				}
			} else if (_type.equals("boolean")) {
				try {
					Boolean.parseBoolean(_value);
				} catch (NumberFormatException e) {
					throw new InvalidAttributeTypeException(this, _name, _type,
							_value);
				}
			} else if (_type.equals("char")) {
				if (_value.toCharArray().length != 1) {
					throw new InvalidAttributeTypeException(this, _name, _type,
							_value);
				}
			} else {
				// type is undefine
				throw new InvalidAttributeTypeException(this, _name, _type,
						_value);
			}
		} else {
			// attribute doesn't exist
			throw new TooMuchAttributesForClassException(this, this.name);
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
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String toString = "link :\n";
		toString += this.name + "\n";
		toString += "\t" + ((this.type) ? "oriented" : "non-oriented") + "\n";
		toString += "\tfrom : " + this.firstNode.getName() + "\t\tto : "
				+ this.secondNode.getName() + "\n";
		toString += "\n\tattributes :\n";
		toString += "\t\tcolor  = " + this.color;
		final Enumeration<String> keys = this.attributes.keys();
		while (keys.hasMoreElements()) {
			final String key = keys.nextElement();
			final String[] datas = this.attributes.get(key);
			toString += "\t\tkey : " + key + "\t\t\ttype : " + datas[1]
					+ "\t\t\tvalue : " + datas[2];
		}
		return toString;
	}

	/**
	 * The getter of color
	 * 
	 * @return color
	 */
	public String getColor() {
		return this.color;
	}

	/**
	 * THe setter of color
	 * 
	 * @param _color
	 *            the new value which define the new color.
	 */
	public void setColor(String _color) {
		this.color = _color;
	}
}
