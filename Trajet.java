import java.util.ArrayList;

public class Trajet {

	private ArrayList<Station> ListeDesStationsDuTrajet;
	private int tempsRequis;

	public Trajet() {
		ListeDesStationsDuTrajet = new ArrayList<Station>();
	}

	public ArrayList<Station> getListeDesStationsDuTrajet() {
		return ListeDesStationsDuTrajet;
	}

	public Station getDerniereStation() {
		return ListeDesStationsDuTrajet.get(ListeDesStationsDuTrajet.size() - 1);
	}

	public int getTempsRequis() {
		return tempsRequis;
	}

	public void ajouterStation(Station station, int temps) {
		tempsRequis += temps;
		ListeDesStationsDuTrajet.add(station);
	}

}
