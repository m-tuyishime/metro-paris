import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class metroParis {
	private int nbStations;
	private int nbConnexions;
	private HashMap<Integer, Station> stations;

	public static void main(String[] args) {
		metroParis metro = new metroParis();

		Trajet trajet = metro.trajetLePlusRapideDijkstra("Abbesses", "Porte d'Italie");
		System.out.println(trajet.getTempsRequis());
	}

	public metroParis() {
		try {
			this.stations = new HashMap<Integer, Station>();

			FileReader fileReader = new FileReader("./Metro.txt");
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			// lire la premiere ligne
			String data = bufferedReader.readLine();
			// separer le nombre de stations et de connexions
			String[] line = data.split(" ", 2);
			nbStations = Integer.parseInt(line[0]);
			nbConnexions = Integer.parseInt(line[1]);

			// lire le reste du fichier
			int section = 0;
			while ((data = bufferedReader.readLine()) != null) {
				// si on arrive a la fin d'une section
				if (data.contains("$")) {
					section++;
					continue;
				}

				// separer les donnees
				line = data.split(" ");

				switch (section) {
					// ajouter les stations
					case 0: {
						line = data.split(" ", 2);
						int numero = Integer.parseInt(line[0]);
						String nom = line[1];
						Station station = new Station(numero, nom);
						this.stations.put(numero, station);
						break;
					}
					// ajouter les positions
					case 1: {
						int numero = Integer.parseInt(line[0]);
						int x = Integer.parseInt(line[1]);
						int y = Integer.parseInt(line[2]);
						Station station = this.stations.get(numero);
						Position position = new Position(x, y);
						station.setPosition(position);
						break;
					}
					// ajouter les connexions
					case 2: {
						Station depart = this.stations.get(Integer.parseInt(line[0]));
						Station arrivee = this.stations.get(Integer.parseInt(line[1]));
						int temps = Integer.parseInt(line[2]);
						depart.addConnexion(arrivee, temps);
						break;
					}
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

		ArrayList<Connection> connexions = station.getConnexions();
		for (Connection connexion : connexions) {

		}
	}

	public Station getStation(String nom) {
		for (Station station : stations.values()) {
			if (station.getNom().equals(nom)) {
				return station;
			}
		}
		return null;
	}

	// retourne le trajet le plus rapide entre 2 stations, et le temps requis pour
	// ce trajet.
	public Trajet trajetLePlusRapideDijkstra(String debut, String fin) {
		Station stationDebut = getStation(debut);
		Station stationFin = getStation(fin);
		Dijkstra dijkstra = new Dijkstra(stationDebut, stationFin, stations);
		return dijkstra.getTrajet();
	}

	// //retourne le trajet le plus rapide entre 2 stations, et le temps requis pour
	// ce trajet, avec l'algo de BellManFord.
	public Trajet trajetLePlusRapideBellManFord(String debut, String fin) {
		Station stationDebut = getStation(debut);
		Station stationFin = getStation(fin);
		BellManFord bellManFord = new BellManFord(stationDebut, stationFin, stations);
		bellManFord.checkCycleNegatif();
		return bellManFord.getTrajet();
	}

	// //retourne la liste des stations qui, si elles sont ferm�s, rendent au moins
	// une station innateignable
	// public ArrayList<Station> stationsCritiques()
	// {

	// }

}
