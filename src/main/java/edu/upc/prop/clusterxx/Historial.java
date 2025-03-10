public class Historial {
    private List<Jugada> jugades;

    public Historial() {
        this.jugades = new ArrayList<>();
    }

    public void afegirJugada(Jugada jugada) {
        this.jugades.add(jugada);
    }

    public void treureUltimaJugada() {
        if (!this.jugades.isEmpty()) {
            this.jugades.remove(this.jugades.size() - 1);
        }
    }

    public List<Jugada> obtenirJugades() {
        return new ArrayList<>(this.jugades);
    }
}