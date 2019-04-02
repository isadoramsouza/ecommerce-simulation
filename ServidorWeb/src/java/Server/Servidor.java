package Server;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import java.rmi.*;
import java.rmi.server.*;
import java.net.MalformedURLException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

//web service com os metodos Comprar e verificar que chamam os metodos compra e Verifica na classe Compracliente (cliente rmi que se comunica com o server rmi))

@WebService(serviceName = "Servidor")
public class Servidor {

    @WebMethod(operationName = "Comprar")
    public int Comprar() {
        CompraCliente c = new CompraCliente();
       int n= c.compra();
       return n;
    }

    @WebMethod(operationName = "Verifica")
    public String Verifica(@WebParam(name = "name") String txt) {
        CompraCliente c = new CompraCliente();
        String retorno  = c.verifica(txt);
        return retorno;
    }
}
