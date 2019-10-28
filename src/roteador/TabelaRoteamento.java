package roteador;

import java.net.InetAddress;
import java.util.*;

public class TabelaRoteamento {
    /*Implemente uma estrutura de dados para manter a tabela de roteamento. 
     * A tabela deve possuir: IP Destino, Métrica e IP de Saída.
    */
    private String tabela;

    public TabelaRoteamento(){
        tabela = "";
    }

    void updateTabela(String tabelaS, InetAddress IPAddress){
        /* Atualize a tabela de rotamento a partir da string recebida. */
        String ipRecebido = IPAddress.getHostAddress();
        int metrica = 1;
        Map<String, Integer> ipMetrica = extrairIpsTabela(tabelaS);

        //se não tem o Ip recebido, ele inclui novo com metrica 1
        if (!ipMetrica.containsKey(ipRecebido)) {
           tabela = tabelaS + "*" + ipRecebido + ";" + metrica;
        } else { //se já tem, incrementa a metrica
            ipMetrica.remove(ipRecebido);
            tabela = tabelaS + "*" + ipRecebido + ";" + metrica+1; //vai pro final da fila, revisar.
        }
        //como incluir IP de saída?
    }

    private Map<String, Integer> extrairIpsTabela(String tabelaS) {
        //separa a sting entre * (ip e métrica) em uma lista
        String[] arrOfStr = tabelaS.split("\\*");
        List<String> iPsRecebidosComMeticas = new ArrayList<>();
        Collections.addAll(iPsRecebidosComMeticas, arrOfStr);

        //separa o ip e a métrica, entre ;, em uma lista de chave/valor
        Map<String, Integer> ipMetrica = new HashMap<>();
        for (String ips : iPsRecebidosComMeticas) {
            String[] metrica = ips.split(";");
            ipMetrica.put(metrica[0], Integer.valueOf(metrica[1]));
        }
        return ipMetrica;
    }

    String getTabelaString() {
        /* Tabela de roteamento vazia conforme especificado no protocolo */
        /* Converta a tabela de rotamento para string, conforme formato definido no protocolo . */
        if (tabela.isEmpty()) {
         tabela = "!";
        }
        return tabela;
    }
}
