package Interface;

import java.rmi.*;

public interface CompraInterface extends Remote {

    public int realizaPedido() throws RemoteException;

    public String Verificar(String pedido) throws RemoteException;

}
