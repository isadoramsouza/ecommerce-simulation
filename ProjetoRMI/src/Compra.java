
import Interface.CompraInterface;
import java.rmi.*;
import java.rmi.server.*;
import java.util.Enumeration;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTextMessage;

public class Compra extends UnicastRemoteObject implements CompraInterface {

    private ConnectionFactory factory = null;
    private Connection connection = null;
    private Session session = null;
    private Destination destination = null;
    private MessageProducer producer = null;
    String url = "tcp://localhost:61616"; //endereco do servidor activemq
    String queueName = "Pendente"; //define nome da fila Pendente
    String queueName1 = "Processando"; //define nome da fila Processando
    String queueName2 = "Completo"; //define nome da fila Completo
    int numero; //numero do pedido gerado que ira retornar na compra
    String retorno; //nome da fila que ira retornar na hora de verificar

    public Compra() throws RemoteException {
    }

    //metodo para colocar pedido na fila Pendente
    @Override
    public int realizaPedido() throws RemoteException {
        try {
            factory = new ActiveMQConnectionFactory(url);
            connection = factory.createConnection(); //cria conexao com o servidor activemq
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            destination = session.createQueue("Pendente"); //cria fila pendente caso n exista
            producer = session.createProducer(destination); //cria um produtor para a fila Pendente

            numero = 1 + (int) (Math.random() * 100); //gera numero aleatorio para o pedido

            TextMessage message = session.createTextMessage(); //cria a mensagem para enviar para a fila Pendente
            System.out.println(message);
            message.setText("0" + String.valueOf(numero)); //transforma para String com um 0 na frente. Ou seja, pedido 1 é pedido 01.
            producer.send(message); //envia para a fila
            System.out.println("Sent: " + message.getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
        return numero; //retorna o numero do pedido que foi gerado para o cliente (no servidor web)
    }

    //metodo para procurar pedido nas filas
    public String Verificar(String pedido) throws RemoteException {
        boolean r = false; //variavel de controle. true se pedido foi encontrado.
        try {
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
            Connection connection = connectionFactory.createConnection(); //cria conexao com activemq
            connection.start();
            QueueSession session = (QueueSession) connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Queue queue = session.createQueue(queueName); //cria filas caso nao existam
            Queue queue1 = session.createQueue(queueName1);
            Queue queue2 = session.createQueue(queueName2);
            QueueBrowser browser = session.createBrowser(queue); //variavel para percorrer fila Pendente
            QueueBrowser browser1 = session.createBrowser(queue1); //variavel para percorrer fila Processando
            QueueBrowser browser2 = session.createBrowser(queue2); //variavel para percorrer fila Completo
            Enumeration e = browser.getEnumeration();
            Enumeration e1 = browser1.getEnumeration();
            Enumeration e2 = browser2.getEnumeration();

            while (r == false) { //enquanto nao encontrou o pedido
                if (e != null) { //se fila Pendente nao estiver vazia
                    while (e.hasMoreElements()) { //passa por todos os elementos
                        Object msg = e.nextElement();
                        ActiveMQTextMessage m = (ActiveMQTextMessage) msg;
                        if (m.getText().compareTo(pedido) == 0) { //compara cada elemento da fila com o pedido
                            retorno = ": Pendente"; //se for igual, seta retorno com o nome da fila
                            r = true; //r vira true e busca para
                        }
                    }
                }

                if (e1 != null && r == false) { //pesquisa na fila Processando, caso nao estiver vazia e r ainda for falso
                    while (e1.hasMoreElements()) {
                        Object msg = e1.nextElement();
                        ActiveMQTextMessage m1 = (ActiveMQTextMessage) msg;
                        if (m1.getText().compareTo(pedido) == 0) {
                            retorno = ": Processando";
                            r = true;
                        }
                    }

                }
                
                if (e2 != null && r == false) {
                    while (e2.hasMoreElements()) {
                        Object msg = e2.nextElement();
                        ActiveMQTextMessage m2 = (ActiveMQTextMessage) msg;
                        if (m2.getText().compareTo(pedido) == 0) {
                            retorno = ": Completo";
                            r = true;
                        }
                    }

                    if (r == false) { //se pesquisar em todas as filas e nao encontrar pedido (r false), retorna que pedido nao existe.
                        return ": Número de pedido não existe!";
                    }
                }

            }
            browser.close();
            browser1.close();
            browser2.close();
            session.close();
            connection.close();
        } catch (JMSException ex) {

        }
        return retorno; //retorna resultado para o cliente (servidor web).
    }

}
