package roteador;

import java.net.InetAddress;
import java.util.ArrayList;

public class TabelaRoteamento {
    /*Implemente uma estrutura de dados para manter a tabela de roteamento. 
     * A tabela deve possuir: IP Destino, Métrica e IP de Saída.
    */

    private ArrayList<Linha> linhas;

    public TabelaRoteamento(){
        this.linhas = new ArrayList<>();
    }

    public void update_tabela(String tabela_s,  InetAddress IPAddress){
        /* Atualize a tabela de rotamento a partir da string recebida. */

        // Extraindo as linhas da tabela, separadas por asteriscos
        String[] linhasDaMensagem = tabela_s.split("\\*");

        for (String linhaDaMensagem : linhasDaMensagem) {
            // Ignorando o primeiro resultado, porque nao ha nada a esquerda do primeiro asterisco
            if (linhaDaMensagem.isEmpty()) {
                continue;
            }

            // Extraindo as colunas da linha, separadas pelo ponto e vírgula
            String[] colunasDaLinha = linhaDaMensagem.split(";");
            String ip = colunasDaLinha[0];


            for (Linha linha : this.linhas) {
                // Incrementa a metrica dos IPs que já existem
                if (linha.getIpEntrada() == ip) {
                    linha.incrementaMetrica();
                }
            }
        }

        System.out.println( IPAddress.getHostAddress() + ": " + tabela_s);
    }
    
    public String get_tabela_string(){
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
}
