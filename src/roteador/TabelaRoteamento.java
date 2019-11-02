package roteador;

import java.net.InetAddress;
import java.util.ArrayList;

public class TabelaRoteamento {
    /*Implemente uma estrutura de dados para manter a tabela de roteamento. 
     * A tabela deve possuir: IP Destino, Métrica e IP de Saída.
    */

    private ArrayList<LinhaTabela> linhas;

    public TabelaRoteamento(ArrayList<LinhaTabela> linhas){
        this.linhas = linhas;
    }

    public void update_tabela(String tabela_s,  InetAddress IPAddress){
        /* Atualize a tabela de rotamento a partir da string recebida. */
        
        System.out.println( IPAddress.getHostAddress() + ": " + tabela_s);
    
    }
    
    public String get_tabela_string(){
        String tabela_string = "!"; /* Tabela de roteamento vazia conforme especificado no protocolo */
        
        /* Converta a tabela de rotamento para string, conforme formato definido no protocolo . */
        
        return tabela_string;
    }
    

    
}
