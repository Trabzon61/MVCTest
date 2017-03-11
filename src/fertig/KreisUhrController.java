package fertig;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KreisUhrController {

	private boolean uhrAn;
	private UhrModel uhrzeit;
	private KreisUhrView view;

	/**
	 * erstellt die analoge Uhr und bringt sie auf den Bildschirm
	 */
	public KreisUhrController() {
		uhrAn = true;
		uhrzeit = new UhrModel();
		

		 tick(); // uhrzeit wird initialisiert

		 view = new KreisUhrView();
		// Einrichten des KeyListeners, d.h. die Uhr reagiert auf Tastendruck
		view.addKeyListener(new KeyAdapter() {

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
				view.repaint();
			}
		});
		new Thread() {
			/**
			 * löst jede Sekunde die Aktualisierung der Anzeige aus
			 */
			@Override
			public void run() {
				try {
					while (true) {
						Thread.sleep(1000);
						tick();
					}
				} catch (InterruptedException e) {
				}
			}
		}.start();
	}

	private void tick() {

		if (!uhrAn) {
			return;
		}
		view.repaint();
	}

	public static void main(String[] args) {
		new KreisUhrController();
	}
}
