package graph3d.use;

import graph3d.universe.GGrapheUniverse;

import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class PanelButtonInteraction extends JPanel implements ActionListener {

	private JButton bZoomPlus, bZoomMoins, boutonHaut, boutonGauche,
			boutonDroite, boutonBas, boutonCentrer;

	private GGrapheUniverse universe;

	public PanelButtonInteraction(GGrapheUniverse universe) {

		this.universe = universe;

		// panel interaction
		this.setLayout(new GridLayout(3, 1));
		TitledBorder borderDroiteBas = new TitledBorder(new EtchedBorder(),
				"Interaction");
		this.setBorder(borderDroiteBas);

		// boutons du zoom
		JPanel panelZoom = new JPanel();
		panelZoom.setLayout(new GridLayout(1, 2));
		bZoomPlus = new JButton("+");
		bZoomPlus.addActionListener(this);
		panelZoom.add(bZoomPlus);
		bZoomMoins = new JButton("-");
		bZoomMoins.addActionListener(this);
		panelZoom.add(bZoomMoins);
		this.add(panelZoom);

		// boutons deplacement de la camera
		JPanel panelInteraction = new JPanel();
		panelInteraction.setLayout(new GridLayout(3, 1));

		JPanel panInteracHaut = new JPanel();
		panInteracHaut.setLayout(new GridLayout(1, 3));
		
		panInteracHaut.add(new Label(""));
		boutonHaut = new JButton("^");
		boutonHaut.addActionListener(this);
		panInteracHaut.add(boutonHaut);
		panInteracHaut.add(new Label(""));
		panelInteraction.add(panInteracHaut);
		JPanel panInteracMilieu = new JPanel();
		panInteracMilieu.setLayout(new GridLayout(1, 3));
		boutonGauche = new JButton("<");
		boutonGauche.addActionListener(this);
		panInteracMilieu.add(boutonGauche);
		panInteracMilieu.add(new Label(""));
		boutonDroite = new JButton(">");
		boutonDroite.addActionListener(this);
		panInteracMilieu.add(boutonDroite);
		panelInteraction.add(panInteracMilieu);
		JPanel panInteracBas = new JPanel();
		panInteracBas.setLayout(new GridLayout(1, 3));
		panInteracBas.add(new Label(""));
		boutonBas = new JButton("v");
		boutonBas.addActionListener(this);
		panInteracBas.add(boutonBas);
		panInteracBas.add(new Label(""));
		panelInteraction.add(panInteracBas);

		this.add(panelInteraction);

		// bouton centrer la vue
		boutonCentrer = new JButton("Centrer la vue");
		boutonCentrer.addActionListener(this);
		this.add(boutonCentrer);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == bZoomPlus) {
			universe.zoomMore();
		}
		if (e.getSource() == bZoomMoins) {
			universe.zoomLess();
		}
		if (e.getSource() == boutonHaut) {
			universe.rotateTop();
		}
		if (e.getSource() == boutonBas) {
			universe.rotateBottom();
		}
		if (e.getSource() == boutonGauche) {
			universe.rotateLeft();
		}
		if (e.getSource() == boutonDroite) {
			universe.rotateRight();
		}
		if (e.getSource() == boutonCentrer) {
			universe.centerView();
		}
	}
}
