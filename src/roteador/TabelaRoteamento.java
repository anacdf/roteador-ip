package roteador;

import sun.security.x509.IPAddressName;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashSet;

public class TabelaRoteamento {
    /*Implemente uma estrutura de dados para manter a tabela de roteamento. 
     * A tabela deve possuir: IP Destino, Métrica e IP de Saída.
    */

    private ArrayList<Linha> tabela;

    public TabelaRoteamento(){
        this.tabela = new ArrayList<>();
    }

    public void update_tabela(String tabelaRecebida,  InetAddress IPAddress) throws UnsupportedEncodingException {
        ArrayList<Linha> tabelaNova = interpretaTabela(tabelaRecebida, IPAddress);

        selecionaRotasNovas(tabelaNova);
    }

    private ArrayList<String> selecionaRotasNovas(ArrayList<Linha> tabelaNova) {
        HashSet<String> rotasNovas = new HashSet<>();

        for (int i = 0; i < tabelaNova.size(); i++) {
           rotasNovas.add(tabelaNova.get(i).getIpEntrada());
        }

        return
    }

    private ArrayList<Linha> interpretaTabela(String tabelaString, InetAddress ipSaida){
        String[] linhas = tabelaString.split("\\*");
        ArrayList<Linha> tabelaNova = new ArrayList<>();

        for (String linha: linhas) {
            String[] colunas = linha.split(";");
            String ipDestino = colunas[0];
            int metrica = Integer.parseInt(colunas[1]);

            tabelaNova.add(new Linha(ipDestino, metrica, ipSaida.getHostAddress()));
        }

        return tabelaNova;
    }

    
    public String get_tabela_string(){
        System.out.println("-- GET TABELA ROTEAMENTO --");

        if (linhas.isEmpty()) {
            return "!"; /* Tabela de roteamento vazia conforme especificado no protocolo */
        }
        /* Converta a tabela de rotamento para string, conforme formato definido no protocolo . */
        String tabela_string = "";
        for (Linha linha : linhas) {
            tabela_string += linha.toString();
        }
        return tabela_string;
    }

    public String getTabelaCompleta() {
        String tabela_string = "";
        for (Linha linha : linhas) {
            tabela_string += linha.printTabelaCompleta();
        }
        return tabela_string;
    }
}
