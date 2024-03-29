import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Calendar;

import javax.swing.JFrame;

/**
 * stellt eine analoge Uhr dar
 * 
 * @author Doro
 *
 */
public class KreisUhr extends JFrame {

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
	private static final int[] ZEIGERLAENGE = new int[] { 5 * RADIUS / 10, 6 * RADIUS / 10, 7 * RADIUS / 10 }; // L�ngen
																												// der
																												// 3
																												// Zeiger
	private static final Color[] ZEIGERFARBE = new Color[] { Color.red, Color.green, Color.blue }; // Farben
																									// der
																									// 3
																									// Zeiger
	private static final Color HINTERGRUND_FARBE = Color.white;
	private static final Color KREIS_FARBE = Color.black;
	
	// Anfangspunkt f�r jeden Zeiger ist Mittelpunkt des Kreises
	private static final int[][] endX = new int[3][60];
	private static final int[][] endY = new int[3][60];

	// Endpunkte der 3 Zeiger pro Minute - Jeder Endpunkt wird nur einmal
	// berechnet
	private static final double zweiPi = 2 * Math.PI;
	private static final double[] KONST = new double[] { zweiPi / 12, zweiPi / 60, zweiPi / 60 };

	private Uhr uhrzeit;
	private boolean uhrAn;

	/**
	 * erstellt die analoge Uhr und bringt sie auf den Bildschirm
	 */
	public KreisUhr() {
		uhrAn = true;
		uhrzeit = new Uhr();

		tick(); // uhrzeit wird initialisiert

		// Erstellen der Oberfl�chenelemente:
		setTitle(TITEL);
		setSize(BREITE, HOEHE);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Einrichten des KeyListeners, d.h. die Uhr reagiert auf Tastendruck
		this.addKeyListener(new KeyAdapter() {

			/**
			 * Taste 'E' schaltet die Uhr ein, 'A' schaltet sie aus, alle
			 * anderen Tasten werden ignoriert
			 */
			@Override
			public void keyPressed(KeyEvent e) {
				switch (Character.toUpperCase(e.getKeyChar())) {
				case 'E':
					uhrAn = true;
					break; // "Ein"
				case 'A':
					uhrAn = false;
					break; // "Aus"
				}
				repaint();
			}
		});

		//Thread starten, um die Uhrzeit laufen zu lassen:
		new Thread() {
			/**
			 * l�st jede Sekunde die Aktualisierung der Anzeige aus
			 */
			@Override
			public void run() {
				try {
					while (true) {
						Thread.sleep(1000);
						tick();
					}
				}
				catch (InterruptedException e) {}
			}
		}.start();
	}

	/**
	 * Holen der aktuellen Uhrzeit und Anzeige, wenn die Uhr angestellt ist
	 */
	private void tick() {

		if (!uhrAn)
		{
			return;
		}
		repaint();
	}


	/**
	 * Analoge Uhr zeichnen
	 */
	@Override 
	public void paint(Graphics g) {
		g.clearRect(0, 0, BREITE, HOEHE);
		g.setColor(HINTERGRUND_FARBE);
		g.fillRect(0, 0, BREITE, HOEHE);
		g.setColor(KREIS_FARBE);
		g.drawOval((BREITE - DURCHMESSER) / 2, (HOEHE - DURCHMESSER) / 2, DURCHMESSER, DURCHMESSER); // Kreis
		final int[] zeit = new int[] { uhrzeit.getStunde(), uhrzeit.getMinute(), uhrzeit.getSekunde() };
		g.drawString(INFO , POS_INFO_X, POS_INFO_Y);
		g.drawString(String.format("%02d:%02d:%02d", zeit[0], zeit[1], zeit[2]), POS_INFO_X, POS_UHRZEIT); // Uhrzeit digital
		for (int i = 0; i < endX.length; i++) { // f�r jeden Zeiger
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

	/**
	 * Analoge Uhr anzeigen
	 * @param args
	 */
	public static void main(String[] args) {
		new KreisUhr();
	}
}