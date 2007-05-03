package graph3d.use;

import graph3d.exception.ASCIIFileNotFoundException;
import graph3d.exception.GException;
import graph3d.lists.GAttributesList;
import graph3d.lists.GConnectionsList;
import graph3d.universe.GGrapheUniverse;

import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class PanelCaracteristique extends JPanel{
	
	public PanelCaracteristique(GGrapheUniverse universe){
		this.setLayout(new GridLayout(2,1));
		TitledBorder borderDroiteHaut = new TitledBorder(new EtchedBorder(),"Caractéristiques");
		this.setBorder(borderDroiteHaut);
			
		GAttributesList attributesList;
		GConnectionsList connectionsList;
		try {
			attributesList = new GAttributesList(universe, "");
			universe.addSelectionBehavior(attributesList);
			this.add(attributesList);
			connectionsList = new GConnectionsList();
			attributesList.attachConnectionsList(connectionsList);
			this.add(connectionsList);
		} catch (GException e) {
			e.printStackTrace();
			e.showError();
		}
	
	}

}
