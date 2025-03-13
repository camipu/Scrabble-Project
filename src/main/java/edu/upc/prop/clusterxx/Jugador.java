//Classe Jugador

public class Jugador {
    String nom;
    int punts;
    Faristol faristol;


    public Jugador() {}
    public Jugador(String nom, int punts, Faristol faristol) {
        this.nom = nom;
        this.punts = punts;
        this.faristol = faristol;
    }

    public String getNom() {
        return nom;
    }

    public int getPunts() {
        return punts;
    }

    public Faristol getFaristol() {
        return faristol;
    }





}
