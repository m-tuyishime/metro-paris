import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class metroParis {
	private int nbStations;
	private int nbConnexions;
	private HashMap<Integer, Station> stations;

	public static void main(String[] args) {
		metroParis metro = new metroParis();

		Trajet trajet = metro.trajetLePlusRapideDijkstra("Abbesses", "Porte d'Italie");
		System.out.println(trajet.getTempsRequis());
		System.out.println(metro.stationsCritiques().size());
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
		return bellManFord.getTrajet();
	}

	// determine si le metro est connexe malgre la fermeture d'une station
	public boolean accessible(Station stationFermee) {
		// Marquer toutes les stations comme non visitées
		HashMap<Integer, Boolean> visite = new HashMap<>();
		for (Integer numero : stations.keySet()) {
			visite.put(numero, false);
		}

		// Créer une file d'attente pour le parcours en largeur
		LinkedList<Station> queue = new LinkedList<>();

		// Obtenez n'importe quelle station comme point de départ (qui n'est pas la
		// station fermée)
		Station start = null;
		for (Station station : stations.values()) {
			// Si la station est en service, elle peut être utilisée comme point de départ
			if (station.getEnService()) {
				start = station;
				break;
			}
		}

		// Marquez la station de départ comme visitée et l'ajoutez à la file d'attente
		visite.put(start.getNumero(), true);
		queue.add(start);

		while (queue.size() != 0) {
			// Retirez un élément de la file d'attente
			Station station = queue.poll();

			// Parcourez toutes les stations accessibles à partir de la station actuelle
			for (Connection connection : station.getConnexions()) {
				Station voisine = connection.getDestination();
				// Si la station voisine a deja ete visitee ou si elle est fermee,
				// ignorez-la
				if (visite.get(voisine.getNumero()) || !voisine.getEnService()) {
					continue;
				}

				// Marquez cette station comme visitée et ajoutez-la à la file d'attente
				visite.put(voisine.getNumero(), true);
				queue.add(voisine);
			}
		}

		// Vérifiez si toutes les stations ont été visitées
		for (Station station : stations.values()) {
			if (!visite.get(station.getNumero())) {
				return false;
			}
		}

		return true;
	}

	// //retourne la liste des stations qui, si elles sont ferm�s, rendent au moins
	// une station innateignable
	public ArrayList<Station> stationsCritiques() {
		ArrayList<Station> stationsCritiques = new ArrayList<>();
		// iterer sur toutes les stations
		for (Station station : stations.values()) {
			// fermer la station
			station.setEnService(false);
			// si le metro n'est plus connexe
			if (!accessible(station)) {
				// ajouter la station a la liste des stations critiques
				stationsCritiques.add(station);
			}
			// remettre la station en service
			station.setEnService(true);
		}
		return stationsCritiques;

	}

}
