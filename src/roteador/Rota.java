package roteador;

public class Rota {

    private String ipEntrada;
    private int metrica;
    private String ipSaida;

    public Rota(String ipEntrada, int metrica, String ipSaida) {
        this.ipEntrada = ipEntrada;
        this.metrica = metrica;
        this.ipSaida = ipSaida;
    }

    public String getIpEntrada() {
        return this.ipEntrada;
    }

    public int getMetrica() {
        return this.metrica;
    }

    public String getIpSaida() {
        return this.ipSaida;
    }

    public void setMetrica(int metrica) {
        this.metrica = metrica;
    }

    public String toString() {
        return "*" + ipEntrada + ";" + metrica;
    }

    public String printTabelaCompleta() {
        return "* IP Entrada: " + ipEntrada + "; métrica: " + metrica + "; IP Saída: " + ipSaida;
    }
}
