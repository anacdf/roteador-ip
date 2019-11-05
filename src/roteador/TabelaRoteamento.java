package roteador;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.util.ArrayList;

public class TabelaRoteamento {
      private ArrayList<Linha> linhas;

    public TabelaRoteamento(){
        this.linhas = new ArrayList<>();
    }

    public void update_tabela(String tabela_s,  InetAddress IPAddress) throws UnsupportedEncodingException {
        System.out.println("-- UPDATE TABELA ROTEAMENTO --");
        tabela_s.trim();

        System.out.println("Tabela recebida: " + tabela_s);
        System.out.println("IP recebido: " + IPAddress.toString());

        String IPrecebido = IPAddress.toString().replaceAll("/", "");

        String[] linhasDaMensagem = tabela_s.split("\\*"); // Extraindo as linhas da tabela, separadas por asteriscos
        for (String linhaDaMensagem : linhasDaMensagem) {
            if (linhaDaMensagem.isEmpty()) { // Ignorando o primeiro resultado, porque nao ha nada a esquerda do primeiro asterisco
                continue;
            }

            String[] colunasDaLinha = linhaDaMensagem.split(";"); // Extraindo as colunas da linha, separadas pelo ponto e vírgula
            String ip = colunasDaLinha[0];
            int metrica = Integer.parseInt(colunasDaLinha[1]);

            if (!ip.equals("!")) {
                Linha linhaExiste = new Linha(ip, metrica, "saída direto");
                linhas.add(linhaExiste);
            }

            Linha linhaNova = new Linha(IPrecebido, 1, IPAddress.getHostAddress());
            linhas.add(linhaNova);

            for (Linha linha : linhas) {
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

    public String getTabelaCompleta() {
        String tabela_string = "";
        for (Linha linha : linhas) {
            tabela_string += linha.printTabelaCompleta();
        }
        return tabela_string;
    }
}
