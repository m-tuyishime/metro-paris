import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class Trajet {
	private Station stationDArrivee; // station d'arrivee
	private ArrayList<Station> ListeDesStationsDuTrajet; // liste des stations du trajet
	private HashMap<Station, Integer> tempsAStation; // de la station de depart a la station actuelle
	private LinkedList<Station> stationsAVisiter; // stations non visitees

	public Trajet(Station stationDeDepart, Station stationDArrivee) {
		this.stationDArrivee = stationDArrivee;
		this.ListeDesStationsDuTrajet = new ArrayList<Station>();
		this.tempsAStation = new HashMap<Station, Integer>();
		this.stationsAVisiter = new LinkedList<Station>();

		// ajouter la station de depart a la liste des stations du trajet
		ajouterStationAVisiter(stationDeDepart);
	}

	public ArrayList<Station> getListeDesStationsDuTrajet() {
		return ListeDesStationsDuTrajet;
	}

	// temps requis pour aller de la station de depart a la station d'arrivee
	public int getTempsRequis() {
		return getTempsAStation(stationDArrivee);
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
