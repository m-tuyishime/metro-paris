import java.util.ArrayList;

public class Station {
	private String nom;
	private int numero;
	private int xPosition;
	private int yPosition;
	private ArrayList<Station> connexions;
	private ArrayList<Integer> temps;
	private boolean enService;

	public Station(int numero, String nom) {
		this.numero = numero;
		this.nom = nom;
		this.connexions = new ArrayList<Station>();
		this.temps = new ArrayList<Integer>();
		this.enService = true;
	}

	public String getNom() {
		return nom;
	}

	public ArrayList<Station> getConnexions() {
		return connexions;
	}

	public Integer getTempsByI(int i) {
		return temps.get(i);
	}

	public boolean getEnService() {
		return enService;
	}

	public void setXPosition(int xPosition) {
		this.xPosition = xPosition;
	}

	public void setYPosition(int yPosition) {
		this.yPosition = yPosition;
	}

	public void setEnService(boolean enService) {
		this.enService = enService;
	}

	public void addConnexion(Station connexion, int temps) {
		this.connexions.add(connexion);
		this.temps.add(temps);
	}
}
