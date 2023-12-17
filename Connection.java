public class Connection {
    private Station destination;
    private int temps;

    public Connection(Station destination, int temps) {
        this.destination = destination;
        this.temps = temps;
    }

    public Station getDestination() {
        return destination;
    }

    public int getTemps() {
        return temps;
    }
}
