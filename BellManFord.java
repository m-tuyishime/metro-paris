import java.util.HashMap;
import java.util.LinkedList;

public class BellManFord extends Dijkstra {
    public BellManFord(Station depart, Station arrivee, HashMap<Integer, Station> stations) {
        // initialiser les distances aux stations a l'infini
        super(depart, arrivee, stations);
    }

    @Override
    public Trajet getTrajet() {
        // toutes les stations du metro
        LinkedList<Station> stations = new LinkedList<Station>(trajet.getStationsAVisiter());

        // boucler sur toutes les stations
        for (Station station : stations) {
            // boucler sur toutes les connexions de la station
            for (Connection connexion : station.getConnexions()) {
                // la destination de la connexion
                Station destination = connexion.getDestination();
                // le temps pour aller de la station a la destination
                int tempsConnection = connexion.getTemps();
                // le temps actuel pour aller de la station de depart a la destination
                int tempsActuel = trajet.getTempsAStation(destination);
                // le nouveau temps pour aller de la station de depart a la destination
                // en passant par la station
                int tempsNouveau = trajet.getTempsAStation(station) + tempsConnection;

                // si le nouveau temps est plus petit que le temps actuel
                if (tempsNouveau < tempsActuel) {
                    // mettre a jour le temps pour aller de la station de depart a la destination
                    trajet.ajouterTempsAStation(destination, tempsNouveau);
                }
            }
        }

        return trajet;
    }

    // verifier si un cycle negatif est present
    public void checkCycleNegatif() {
        // copier le trajet actuel
        Trajet ancienTrajet = trajet;
        // chercher un nouveau trajet
        Trajet nouveauTrajet = getTrajet();

        // si le temps requis des trajets est different, il y a un cycle negatif
        if (ancienTrajet.getTempsRequis() != nouveauTrajet.getTempsRequis())
            System.out.println("Cycle negatif present");
        else
            System.out.println("Pas de cycle negatif");
    }
}
