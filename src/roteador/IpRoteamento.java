package roteador;

public class IpRoteamento {

    private String ipDestino;
    private int metrica;
    private String ipSaida;
    private String ip;

    public String getIpDestino() {
        return ipDestino;
    }

    public void setIpDestino(String ipDestino) {
        this.ipDestino = ipDestino;
    }

    public int getMetrica() {
        return metrica;
    }

    public void setMetrica(int metrica) {
        this.metrica = metrica;
    }

    public String getIpSaida() {
        return ipSaida;
    }

    public void setIpSaida(String ipSaida) {
        this.ipSaida = ipSaida;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public String toString() {
        return  "*" + ipDestino + ';' + metrica;
    }
}
