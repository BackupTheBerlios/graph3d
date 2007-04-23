package graph3d.elements;

public class EthernetLink extends GLink {

	public EthernetLink(String _name, GNode _first, GNode _second){
		super(_name, _first, _second);
	}
	
	public EthernetLink(String _name, String _color, GNode _first, GNode _second){
		super(_name, _color, _first, _second);
	}
	
	public EthernetLink(boolean _type, String _name, GNode _first, GNode _second) {
		super(_type, _name, _first, _second);
	}
	
	public EthernetLink(boolean _type, String _name, String _color, GNode _first, GNode _second) {
		super(_type, _name, _color, _first, _second);
	}
}
