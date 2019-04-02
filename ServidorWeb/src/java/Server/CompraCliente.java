package Server;

import Interface.CompraInterface;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

//cliente RMI
public class CompraCliente {

    int n; //guarda o numero do pedido retornado com o metodo realizaPedido
    String retorno; //guarda o nome da fila retornado com o metodo Verifica

    public int compra() {
        CompraInterface compra;
        try {
            Registry reg = LocateRegistry.getRegistry("192.168.208.98", 1099); //obtem referencia de servicos registrados no endereco e porta
            compra = (CompraInterface) reg.lookup("server"); //procura o registro com nome de "server" (setado no CompraServer)

            n = compra.realizaPedido(); //chama o metodo remoto realizaPedido

        } catch (Exception e) {
            System.out.println("HelloClient exception: " + e);
        }
        return n; //retorna o numero do pedido gerado
    }

    public String verifica(String pedido) {
        CompraInterface verifica;

        try {
            Registry reg = LocateRegistry.getRegistry("192.168.208.98", 1099);//ip do servidor rmi

            verifica = (CompraInterface) reg.lookup("server");

            retorno = verifica.Verificar(pedido); //chama o metodo remoto verifica, eviando o numero do pedido.
        } catch (Exception e) {
            System.out.println("HelloClient exception: " + e);
        }
        return retorno; //retorna o nome da fila, ou se nao existe pedido.
    }
}
