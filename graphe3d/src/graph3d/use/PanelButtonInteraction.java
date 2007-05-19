package graph3d.use;

import graph3d.universe.GGraphUniverse;

import java.awt.GridLayout;
import java.awt.Label;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class PanelButtonInteraction extends JPanel {

	private JButton bZoomPlus, bZoomMoins, boutonHaut, boutonGauche,
			boutonDroite, boutonBas, boutonCentrer;

	private GGraphUniverse universe;

	public JButton getBoutonBas() {
		return boutonBas;
	}

	public void setBoutonBas(JButton boutonBas) {
		this.boutonBas = boutonBas;
	}

	public JButton getBoutonCentrer() {
		return boutonCentrer;
	}

	public void setBoutonCentrer(JButton boutonCentrer) {
		this.boutonCentrer = boutonCentrer;
	}

	public JButton getBoutonDroite() {
		return boutonDroite;
	}

	public void setBoutonDroite(JButton boutonDroite) {
		this.boutonDroite = boutonDroite;
	}

	public JButton getBoutonGauche() {
		return boutonGauche;
	}

	public void setBoutonGauche(JButton boutonGauche) {
		this.boutonGauche = boutonGauche;
	}

	public JButton getBoutonHaut() {
		return boutonHaut;
	}

	public void setBoutonHaut(JButton boutonHaut) {
		this.boutonHaut = boutonHaut;
	}

	public JButton getBZoomMoins() {
		return bZoomMoins;
	}

	public void setBZoomMoins(JButton zoomMoins) {
		bZoomMoins = zoomMoins;
	}

	public JButton getBZoomPlus() {
		return bZoomPlus;
	}

	public void setBZoomPlus(JButton zoomPlus) {
		bZoomPlus = zoomPlus;
	}

	public PanelButtonInteraction(GGraphUniverse universe) {

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
		bZoomPlus.addActionListener(universe.getBehavior());
		panelZoom.add(bZoomPlus);
		bZoomMoins = new JButton("-");
		bZoomMoins.addActionListener(universe.getBehavior());
		panelZoom.add(bZoomMoins);
		this.add(panelZoom);

		// boutons deplacement de la camera
		JPanel panelInteraction = new JPanel();
		panelInteraction.setLayout(new GridLayout(3, 1));

		JPanel panInteracHaut = new JPanel();
		panInteracHaut.setLayout(new GridLayout(1, 3));

		panInteracHaut.add(new Label(""));
		boutonHaut = new JButton("^");
		boutonHaut.addActionListener(universe.getBehavior());
		panInteracHaut.add(boutonHaut);
		panInteracHaut.add(new Label(""));
		panelInteraction.add(panInteracHaut);
		JPanel panInteracMilieu = new JPanel();
		panInteracMilieu.setLayout(new GridLayout(1, 3));
		boutonGauche = new JButton("<");
		boutonGauche.addActionListener(universe.getBehavior());
		panInteracMilieu.add(boutonGauche);
		panInteracMilieu.add(new Label(""));
		boutonDroite = new JButton(">");
		boutonDroite.addActionListener(universe.getBehavior());
		panInteracMilieu.add(boutonDroite);
		panelInteraction.add(panInteracMilieu);
		JPanel panInteracBas = new JPanel();
		panInteracBas.setLayout(new GridLayout(1, 3));
		panInteracBas.add(new Label(""));
		boutonBas = new JButton("v");
		boutonBas.addActionListener(universe.getBehavior());
		panInteracBas.add(boutonBas);
		panInteracBas.add(new Label(""));
		panelInteraction.add(panInteracBas);

		this.add(panelInteraction);

		// bouton centrer la vue
		boutonCentrer = new JButton("Centrer la vue");
		boutonCentrer.addActionListener(universe.getBehavior());
		this.add(boutonCentrer);
	}
}
