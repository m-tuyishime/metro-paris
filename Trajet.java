import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class Trajet {
	private ArrayList<Station> ListeDesStationsDuTrajet; // liste des stations du trajet
	private int tempsRequis; // temps minimal pour faire le trajet
	private HashMap<Station, Integer> tempsAStation; // de la station de depart a la station actuelle
	private LinkedList<Station> stationsAVisiter; // stations non visitees

	public Trajet() {
		this.ListeDesStationsDuTrajet = new ArrayList<Station>();
		this.tempsRequis = 0;
		this.tempsAStation = new HashMap<Station, Integer>();
		this.stationsAVisiter = new LinkedList<Station>();
	}

	public ArrayList<Station> getListeDesStationsDuTrajet() {
		return ListeDesStationsDuTrajet;
	}

	public int getTempsRequis() {
		return tempsRequis;
	}

	public LinkedList<Station> getStationsAVisiter() {
		return stationsAVisiter;
	}

	public int getTempsAStation(Station station) {
		return tempsAStation.get(station);
	}

	public void ajouterTempsAStation(Station station, int temps) {
		tempsAStation.put(station, temps);
	}

	public void ajouterStationAVisiter(Station station) {
		stationsAVisiter.add(station);
	}

	public void visiterStation(Station station) {
		// enlever la station de la liste des stations a visiter
		stationsAVisiter.remove(station);
		// ajouter la station a la liste des stations du trajet
		ListeDesStationsDuTrajet.add(station);
	}
}
