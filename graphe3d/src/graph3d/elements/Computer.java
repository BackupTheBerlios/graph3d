package graph3d.elements;

public class Computer extends GNode {

	public Computer(String name) {
		super(name);
		createAttributes();
	}
	
	public Computer(String _name, float _x, float _y, float _z){
		super(_name, _x, _y, _z);
		createAttributes();
	}
	
	private void createAttributes(){
		attributes.put("entier", new String[]{"entier","int","2345"});
		attributes.put("booléen", new String[]{"booléen","boolean","true"});
		attributes.put("flottant SP", new String[]{"flottant SP","float","4.5"});
		attributes.put("flottant DP", new String[]{"flottant DP","double","5.4"});
	}
		
}
