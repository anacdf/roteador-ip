package roteador;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MessageReceiver implements Runnable{
    private TabelaRoteamento tabela;

    //usar para ver se os ips da tabela sao ativos
    private Map<String, Integer> mapIPsAtivos;

    //tabela de roteamento
    private List<IpRoteamento> tabelaDeRoteamento;
    
    public MessageReceiver(TabelaRoteamento t){
        tabela = t;
    }
    
    @Override
    public void run() {
        DatagramSocket serverSocket = null;
        
        try {
            
            /* Inicializa o servidor para aguardar datagramas na porta 5000 */
            serverSocket = new DatagramSocket(5000);
        } catch (SocketException ex) {
            Logger.getLogger(MessageReceiver.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        
        byte[] receiveData = new byte[1024];
        
        while(true){
            
            /* Cria um DatagramPacket */
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            
            try {
                /* Aguarda o recebimento de uma mensagem */
                serverSocket.receive(receivePacket);
            } catch (IOException ex) {
                Logger.getLogger(MessageReceiver.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            /* Transforma a mensagem em string */
            String tabela_string = new String(receivePacket.getData());
            
            /* Obtem o IP de origem da mensagem */
            InetAddress IPAddress = receivePacket.getAddress();

            interpretaMensagem(tabela_string, IPAddress);
            tabela.update_tabela(tabela_string, IPAddress);
        }
    }

    private void interpretaMensagem(String mensagemRecebida, InetAddress ipOrigem) {
        parseMensagem(mensagemRecebida);
        //ver se é !
    }

    private List<IpRoteamento> parseMensagem(String mensagemRecebida) {
        return null;
    }

}

//qnd recebe a msg, fazer parse, verificar

/*
for recebido um IP de Destino não presente na tabela local. Neste caso a rota deveser adicionada, a Métrica deve ser incrementada em 1 e o IP de Saída deve ser o endereço doroteador que ensinou esta informação;
 ●for recebida  uma Métrica menor para um IP Destino presente na tabela local.Neste caso, a Métrica e o IP de Saída devem ser atualizadas;
 ●um IP Destino deixar de ser divulgado. Neste caso, a rota deve ser retirada databela de roteamento = metodo q vai ficar 8/8 seg aumentando o valor dos IPs no map até chegar em 4, qnd tiver 4 limpar o map
 e retirar tudo q tiver relacionado c ele na tabela de roteamento. (vai ser uma outra thread)
 */

//