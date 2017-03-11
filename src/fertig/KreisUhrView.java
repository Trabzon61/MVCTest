package fertig;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;

public class KreisUhrView extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8261528369169273398L;
	private static final String TITEL = "Kreisuhr";
	private static final String INFO = "Tasten: A(usschalten), E(inschalten)";
	private static final int BREITE = 400;
	private static final int HOEHE = 300;
	private static final int ZENTRUM_X = BREITE / 2;
	private static final int ZENTRUM_Y = HOEHE / 2;
	private static final int RADIUS = 100;
	private static final int DURCHMESSER = 2 * RADIUS;
	private static final int POS_INFO_X = 20;
	private static final int POS_INFO_Y = 40;
	private static final int POS_UHRZEIT = 60;
	private static final int[] ZEIGERLAENGE = new int[] { 5 * RADIUS / 10, 6 * RADIUS / 10, 7 * RADIUS / 10 }; // Längen
																												// der
																												// 3
																												// Zeiger
	private static final Color[] ZEIGERFARBE = new Color[] { Color.red, Color.green, Color.blue }; // Farben
																									// der
																									// 3
																									// Zeiger
	private static final Color HINTERGRUND_FARBE = Color.white;
	private static final Color KREIS_FARBE = Color.black;
	// Anfangspunkt für jeden Zeiger ist Mittelpunkt des Kreises
	private static final int[][] endX = new int[3][60];
	private static final int[][] endY = new int[3][60];

	// Endpunkte der 3 Zeiger pro Minute - Jeder Endpunkt wird nur einmal
	// berechnet
	private static final double zweiPi = 2 * Math.PI;
	private static final double[] KONST = new double[] { zweiPi / 12, zweiPi / 60, zweiPi / 60 };

	private UhrModel uhrzeit;

	public KreisUhrView() {
		// Erstellen der Oberflächenelemente:
		setTitle(TITEL);
		setSize(BREITE, HOEHE);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void paint(Graphics g) {
		g.clearRect(0, 0, BREITE, HOEHE);
		g.setColor(HINTERGRUND_FARBE);
		g.fillRect(0, 0, BREITE, HOEHE);
		g.setColor(KREIS_FARBE);
		g.drawOval((BREITE - DURCHMESSER) / 2, (HOEHE - DURCHMESSER) / 2, DURCHMESSER, DURCHMESSER); // Kreis
		final int[] zeit = new int[] { uhrzeit.getStunde(), uhrzeit.getMinute(), uhrzeit.getSekunde() };
		g.drawString(INFO, POS_INFO_X, POS_INFO_Y);
		g.drawString(String.format("%02d:%02d:%02d", zeit[0], zeit[1], zeit[2]), POS_INFO_X, POS_UHRZEIT); // Uhrzeit
																											// digital
		for (int i = 0; i < endX.length; i++) { // für jeden Zeiger
			final int z = zeit[i]; // Stunde, Minute oder Sekunde
			if (endX[i][z] == 0) { // wurde noch nicht berechnet
				final double grad = z * KONST[i];
				endX[i][z] = (int) (ZENTRUM_X + ZEIGERLAENGE[i] * Math.sin(grad));
				endY[i][z] = (int) (ZENTRUM_Y - ZEIGERLAENGE[i] * Math.cos(grad));
			}
			g.setColor(ZEIGERFARBE[i]);
			g.drawLine(ZENTRUM_X, ZENTRUM_Y, endX[i][zeit[i]], endY[i][zeit[i]]);
		}
	}

}
