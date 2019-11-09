package roteador;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

public class TabelaRoteamento {
    /*Implemente uma estrutura de dados para manter a tabela de roteamento. 
     * A tabela deve possuir: IP Destino, Métrica e IP de Saída.
    */

    private ArrayList<Rota> tabelaAtual;

    public TabelaRoteamento(){
        tabelaAtual = new ArrayList<>();
    }

    public void update_tabela(String tabelaRecebida,  InetAddress IPAddress) throws UnsupportedEncodingException {
        tabelaRecebida.trim();
        if (tabelaRecebida.equals("!")) { //verifica se a tabela é vazia e inclui um novo Ip, com os valores recebidos.
            String IPrecebido = IPAddress.toString().replaceAll("/", "");
            tabelaAtual.add(new Rota(IPrecebido, 1, IPAddress.getHostAddress()));
            return;
        }

        ArrayList<Rota> tabelaNova = interpretaTabela(tabelaRecebida, IPAddress);  //lista com IPs e métricas recebidas

        selecionaRotasComNovosIPsDeEntrada(tabelaNova)
                .forEach(rotaNova -> tabelaAtual.add(rotaNova)); //inclui Ips novos na Tabela

        selecionaRotasComIPdeSaidaIgualAoIPAddress(tabelaNova) //adiciona na tabela se IP de entrada não está na tabela e coloca a metrica
                .forEach(rotaNova -> {
                    encontraRotaAtual(rotaNova.getIpEntrada())
                            .ifPresent(rotaAtual -> rotaAtual.setMetrica(rotaNova.getMetrica()));
                });

        atualizaRotasComMetricaNovaMenor(tabelaNova); //atualiza a métrica
    }

    private ArrayList<Rota> interpretaTabela(String tabelaString, InetAddress ipSaida){
        //faz a quebra da string para ter IPs e métricas separadas em uma lista
        ArrayList<Rota> tabelaNova = new ArrayList<>();

        String[] linhas = tabelaString.split("\\*");

        for (String linha : linhas) {
            if (linha.isEmpty()) continue; // Ignora a primeira linha que é vazia por causa do split

            String[] colunas = linha.split(";");
            String ipDestino = colunas[0];
            int metrica = Integer.parseInt(colunas[1]);

            tabelaNova.add(new Rota(ipDestino, metrica, ipSaida.getHostAddress()));
        }

        return tabelaNova;
    }

    private ArrayList<Rota> selecionaRotasComNovosIPsDeEntrada(ArrayList<Rota> tabelaNova) {
        //verifica se os IPs recebidos não estão na tabela e retorna eles.
        ArrayList<String> listaDeIPsDeEntradaAtuais = selecionaIPsDeEntradaAtuais();

        return (ArrayList<Rota>) tabelaNova.stream()
                .filter(rota -> !listaDeIPsDeEntradaAtuais.contains(rota.getIpEntrada()))
                .collect(Collectors.toList());
    }

    private ArrayList<Rota> selecionaRotasComIPdeSaidaIgualAoIPAddress(ArrayList<Rota> tabelaNova) {
        //verifica os IPs da tabela que são do mesmo IP vizinho
        ArrayList<String> listaDeIPsDeSaidaAtuais = selecionaIPsDeSaidaAtuais();

        return (ArrayList<Rota>) tabelaNova.stream()
                .filter(rotaNova -> listaDeIPsDeSaidaAtuais.contains(rotaNova.getIpSaida()))
                .collect(Collectors.toList());
    }

    private Optional<Rota> encontraRotaAtual(String ipEntrada) {
        //ve se o IP de entrada é igual ao IP recebido.
        return tabelaAtual.stream()
                .filter(rota -> rota.getIpEntrada().equals(ipEntrada))
                .findFirst();
    }

    public ArrayList<String> selecionaIPsDeEntradaAtuais() {
        return (ArrayList<String>) tabelaAtual.stream()
                .map(Rota::getIpEntrada)
                .collect(Collectors.toList());
    }

    private ArrayList<String> selecionaIPsDeSaidaAtuais() {
        return (ArrayList<String>) tabelaAtual.stream()
                .map(Rota::getIpSaida)
                .collect(Collectors.toList());
    }

    public String get_tabela_string(){
        System.out.println("-- GET TABELA ROTEAMENTO --");

        if (tabelaAtual.isEmpty()) {
            return "!"; /* Tabela de roteamento vazia conforme especificado no protocolo */
        }
        /* Converta a tabela de rotamento para string, conforme formato definido no protocolo . */
        String tabela_string = "";
        for (Rota rota : tabelaAtual) {
            tabela_string += rota.toString();
        }
        return tabela_string;
    }

    public String getTabelaCompleta() {
        //Mostra a tabela com IP de saída
        String tabela_string = "";
        for (Rota rota : tabelaAtual) {
            tabela_string += rota.printTabelaCompleta();
        }
        return tabela_string;
    }

    private void atualizaRotasComMetricaNovaMenor(ArrayList<Rota> tabelaNova) {
        //verifica se a rota nova tem métrica menor que a nossa tabela atual tinha armazenado e caso sim, atualiza ela
        tabelaNova.forEach(rotaNova -> {
            encontraRotaAtual(rotaNova.getIpEntrada())
                    .ifPresent(rotaAtual -> {
                        int metricaNova = rotaNova.getMetrica();

                        if (metricaNova < rotaAtual.getMetrica()) {
                            rotaAtual.setMetrica(metricaNova);
                        }
                    });
        });
    }
}
