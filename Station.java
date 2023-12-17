import java.util.ArrayList;

public class Station {
	private int numero;
	private String nom;
	private Position position;
	private ArrayList<Connection> connexions;
	private boolean enService;

	public Station(int numero, String nom) {
		this.numero = numero;
		this.nom = nom;
		this.connexions = new ArrayList<Connection>();
		this.enService = true;
	}

	public int getNumero() {
		return numero;
	}

	public String getNom() {
		return nom;
	}

	public ArrayList<Connection> getConnexions() {
		return connexions;
	}

	public boolean getEnService() {
		return enService;
	}

	public Position getPosition() {
		return position;
	}

	public void setEnService(boolean enService) {
		this.enService = enService;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public void addConnexion(Station destination, int temps) {
		Connection connexion = new Connection(destination, temps);
		this.connexions.add(connexion);
	}
}
