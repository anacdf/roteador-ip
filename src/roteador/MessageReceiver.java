package roteador;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MessageReceiver implements Runnable {
    private TabelaRoteamento tabela;

    public MessageReceiver(TabelaRoteamento t){
        tabela = t;
    }
    
    @Override
    public void run() {
        DatagramSocket serverSocket;
        
        try {
            /* Inicializa o servidor para aguardar datagramas na porta 5000 */
            serverSocket = new DatagramSocket(5000);
        } catch (SocketException ex) {
            Logger.getLogger(MessageReceiver.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        
        byte[] receiveData = new byte[1024];
        
        while (true) {
            /* Cria um DatagramPacket */
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            
            try {
                /* Aguarda o recebimento de uma mensagem */
                serverSocket.receive(receivePacket);
            } catch (IOException ex) {
                Logger.getLogger(MessageReceiver.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            /* Transforma a mensagem em string */
            String tabelaString = new String(receivePacket.getData());
            
            /* Obtem o IP de origem da mensagem */
            InetAddress ipAddress = receivePacket.getAddress();

            //atualiza a tabela
            tabela.updateTabela(tabelaString, ipAddress);
        }
    }
}