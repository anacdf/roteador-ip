package roteador;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MessageReceiver implements Runnable{
    private TabelaRoteamento tabela;
    
    public MessageReceiver(TabelaRoteamento t){
        tabela = t;
    }
    @Override
    public void run() {
        DatagramSocket serverSocket;
        try {
            serverSocket = new DatagramSocket(5000); /* Inicializa o servidor para aguardar datagramas na porta 5000 */
        } catch (SocketException ex) {
            Logger.getLogger(MessageReceiver.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        byte[] receiveData = new byte[1024];

        while(true){
            System.out.println("-- MSG RECEIVER --");
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length); /* Cria um DatagramPacket */
            try {
                /* Aguarda o recebimento de uma mensagem */
                serverSocket.receive(receivePacket);
            } catch (IOException ex) {
                Logger.getLogger(MessageReceiver.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            /* Transforma a mensagem em string */
            String tabela_string = new String(receivePacket.getData()).trim();
            
            /* Obtem o IP de origem da mensagem */
            InetAddress IPAddress = receivePacket.getAddress();
            try {
                tabela.update_tabela(tabela_string, IPAddress);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            System.out.println("-------------------------------------------------");
            System.out.println("Tabela atualizada: " + tabela_string);
            System.out.println("-------------------------------------------------");
            System.out.println("-------------------------------------------------");
            System.out.println("Tabela Completa: " + tabela.getTabelaCompleta());
            System.out.println("-------------------------------------------------");

        }
    }
    
}
