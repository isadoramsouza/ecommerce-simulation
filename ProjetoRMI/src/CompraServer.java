
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.*;
import javax.jms.JMSException;

public class CompraServer {

    public static void main(String[] argv) throws JMSException {
        try {
            Registry reg = LocateRegistry.createRegistry(1099); //cria registro de servico na jvm na porta 1099 (obtem referencia de servico de registro)
	    System.setProperty("java.rmi.server.hostname","localhost"); //seta ip do servidor (ao inves de 127.0.0.1 para que o cliente possa enxergar
            reg.rebind("server", new Compra()); //inicia um novo servico server" e pode acessar a classe compra
            //  reg.rebind("server1", new Soma());
            System.out.println("compra server started");
        } catch (Exception e) {
        }
        
      
        //chamar threads
         MudarFila m = new MudarFila();
         m.metodo1();
         m.metodo2();
                }
}
