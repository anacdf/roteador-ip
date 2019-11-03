package roteador;

public class Linha {

    private String ipEntrada;
    private int metrica;
    private String ipSaida;

    public Linha (String ipEntrada, int metrica, String ipSaida) {
        this.ipEntrada = ipEntrada;
        this.metrica = metrica;
        this.ipSaida = ipSaida;
    }

    public String getIpEntrada() {
        return this.ipEntrada;
    }

    public void incrementaMetrica() {
        this.metrica += 1;
    }

    public String toString() {
        return "*" + ipEntrada + ";" + metrica;
    }
}
