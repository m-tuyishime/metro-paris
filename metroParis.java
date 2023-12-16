import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

public class metroParis {
	private int nbStations;
	private int nbConnexions;
	private ArrayList<Station> stations;

	public static void main(String[] args) {
		metroParis metro = new metroParis();
		Trajet trajet = metro.trajetLePlusRapideDijkstra("Saint-Jacques", "Porte d'Italie");
		System.out.println(trajet.getTempsRequis());
	}

	public metroParis() {
		try {
			this.stations = new ArrayList<Station>();

			FileReader fileReader = new FileReader("./Metro.txt");
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String data = bufferedReader.readLine();

			String[] line = data.split(" ", 2);
			nbStations = Integer.parseInt(line[0]);
			nbConnexions = Integer.parseInt(line[1]);

			int section = 0;
			while ((data = bufferedReader.readLine()) != null) {
				if (data.contains("$")) {
					section++;
					continue;
				}
				line = data.split(" ");
				Station station;
				switch (section) {
					// ajouter les stations
					case 0:
						line = data.split(" ", 2);
						station = new Station(Integer.parseInt(line[0]), line[1]);
						this.stations.add(station);
						break;
					// ajouter les positions
					case 1:
						station = this.stations.get(Integer.parseInt(line[0]));
						station.setXPosition(Integer.parseInt(line[1]));
						station.setYPosition(Integer.parseInt(line[2]));
						break;
					// ajouter les connexions
					case 2:
						station = this.stations.get(Integer.parseInt(line[0]));
						Station station2 = this.stations.get(Integer.parseInt(line[1]));
						int temps = Integer.parseInt(line[2]);
						station.addConnexion(station2, temps);
						break;
					default:
						break;
				}
			}

			bufferedReader.close();
		} catch (IOException e) {
			System.out.println("Une erreur est survenue lors de la lecture du fichier.");
			e.printStackTrace();
		}
	}

	public void accessible(Station station) {

		ArrayList<Station> connexions = station.getConnexions();
		for (Station connexion : connexions) {

		}
	}

	public Station getStationNom(String nom) {
		for (Station station : stations) {
			if (station.getNom().equals(nom)) {
				return station;
			}
		}
		return null;
	}

	// retourne le trajet le plus rapide entre 2 stations, et le temps requis pour
	// ce trajet.
	public Trajet trajetLePlusRapideDijkstra(String debut, String fin) {
		Station stationDebut = getStationNom(debut);
		Station stationFin = getStationNom(fin);
		Trajet trajet = new Trajet();
		trajet.ajouterStation(stationDebut, 0);
		return recursionDijkstra(trajet, stationFin);
	}

	private Trajet recursionDijkstra(Trajet trajet, Station fin) {
		Station station = trajet.getDerniereStation();
		ArrayList<Station> connexions = station.getConnexions();

		if (station.equals(fin) || connexions.size() == 0) {
			return trajet;
		}

		ArrayList<Trajet> trajets = new ArrayList<Trajet>();
		for (int i = 0; i < connexions.size(); i++) {
			Station connexion = connexions.get(i);
			trajet.ajouterStation(connexion, station.getTempsByI(i));
			trajets.add(recursionDijkstra(trajet, fin));
		}

		Trajet trajetMin = trajets.get(0);
		for (Trajet trajet2 : trajets) {
			if (trajet2.getTempsRequis() < trajetMin.getTempsRequis()) {
				trajetMin = trajet2;
			}
		}
		return trajetMin;
	}

	// //retourne le trajet le plus rapide entre 2 stations, et le temps requis pour
	// ce trajet, avec l'algo de BellManFord.
	// public Trajet trajetLePlusRapideBellManFord (String debut, String fin) {

	// return void;
	// }

	// //retourne la liste des stations qui, si elles sont ferm�s, rendent au moins
	// une station innateignable
	// public ArrayList<Station> stationsCritiques()
	// {

	// }

}
