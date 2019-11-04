package roteador;

import java.io.UnsupportedEncodingException;
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

    public void update_tabela(String tabela_s,  InetAddress IPAddress) throws UnsupportedEncodingException {
        tabela_s.trim();
        System.out.println("-- UPDATE TABELA ROTEAMENTO --");

        System.out.println("enderecoIP" + IPAddress);

        /* Atualize a tabela de rotamento a partir da string recebida. */
        System.out.println("Tabela recebida: " + tabela_s);
        System.out.println("IP recebido: " + IPAddress.toString());

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

            Linha linhaNova = new Linha(IPAddress.toString().replaceAll("/", ""), 1, IPAddress.getHostAddress());
            linhas.add(linhaNova);

            for (Linha linha : this.linhas) {
                // Incrementa a metrica dos IPs que já existem
                if (linha.getIpEntrada() == ip) {
                    linha.incrementaMetrica();
                }
            }
        }
        System.out.println("IP HostAddress " + IPAddress.getHostAddress() + ": " + tabela_s);
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
}
