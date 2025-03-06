//Classe casella

public class Casella {
    int x;
    int y;
    Fitxa fitxa;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Fitxa getFitxa() {
        return fitxa;
    }

    public boolean esBuida() {
        return fitxa == null;
    }

    public void colocarFitxa(Fitxa fitxa) {
        if (esBuida()) {
            this.fitxa = fitxa;
        }
        else {
            throw new IllegalStateException("La casella ja està ocupada."); //??
        }
    }
}
