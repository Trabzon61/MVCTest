package fertig;

import java.util.Calendar;

public class UhrModel {
	private int stunde, minute, sekunde;

	/**
	 * erstellt die Uhr
	 */
    public UhrModel() {
		//Thread starten, um die Uhrzeit laufen zu lassen:
		new Thread() {
			/**
			 * löst jede Sekunde die Aktualisierung der Uhrzeit aus
			 */
			@Override
			public void run() {
				try {
					while (true) {
						laufen();
						Thread.sleep(1000);
					}
				}
				catch (InterruptedException e) {}
			}
		}.start();
    }

	/**
	 * liefert die aktuelle Stunde
	 * 
	 * @return
	 */
	public int getStunde() {
		return stunde;
	}

	/**
	 * liefert die aktuelle Minute
	 * 
	 * @return
	 */
	public int getMinute() {
		return minute;
	}

	/**
	 * liefert die aktuelle Sekunde
	 * 
	 * @return
	 */
	public int getSekunde() {
		return sekunde;
	}
	private void laufen() {
		Calendar kalender = Calendar.getInstance();
		stunde = kalender.get(Calendar.HOUR);
		minute = kalender.get(Calendar.MINUTE);
		sekunde = kalender.get(Calendar.SECOND);
	}
}
