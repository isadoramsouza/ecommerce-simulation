
import static java.lang.Thread.sleep;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTextMessage;

public class MudarFila {

    private static MessageProducer producer = null;
    private static MessageProducer producer2 = null;
    private static MessageConsumer consumer = null;
    private static MessageConsumer consumer2 = null;
    String url = "tcp://localhost:61616"; //endereco do servidor do activemq
    String queueName = "Pendente"; //define nome da fila Pendente
    String queueName1 = "Processando"; //define nome da fila Processando
    String queueName2 = "Completo"; //define nome da fila Completo
    ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);

    //metodo para criar consumidor na fila Pendente e produtor na fila Processando
    public void metodo1() throws JMSException {
        new Thread() { //cria thread
            public void run() {
                try {
                    while (true) {
                        Connection connection = connectionFactory.createConnection(); //cria conexao com o activemq
                        connection.start();
                        QueueSession session = (QueueSession) connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
                        Queue queue = session.createQueue(queueName); //cria fila Pendente caso n exista
                        Queue queue1 = session.createQueue(queueName1); //cria fila Processando caso n exista
                        QueueBrowser browser = session.createBrowser(queue);
                        Enumeration e = browser.getEnumeration();
                        consumer = session.createConsumer(queue); //cria consumidor na fila pendente

                        TextMessage message = session.createTextMessage();

                        if (e != null) { //verifica se fila Pendente nao esta vazia
                            while (e.hasMoreElements()) {
                                Object msg = e.nextElement();
                                ActiveMQTextMessage m = (ActiveMQTextMessage) msg;
                                Thread.currentThread().sleep(10000); //espera 10 segundos
                                producer = session.createProducer(queue1); //cria produtor na fila Processando
                                message.setText(m.getText()); //pega a primeira mensagem da fila Pendente
                                producer.send(message); //envia o primeiro elemento de Pendente para Processando.
                                consumer.receive(); //retira de Pendente
                            }
                        }
                        if (e == null) { //se Pendente estiver vazia, volta para o inicio.
                            continue;
                        }
                        browser.close(); //fecha conexao e sessao
                        session.close();
                        connection.close();
                    }
                } catch (Exception e) {
                }
            }
        }.start(); //inicia thread
    }

    //metodo para criar consumidor na fila Processando e produtor na fila Completo
    public void metodo2() throws JMSException {
        new Thread() { //cria thread
            public void run() {
                try {
                    while (true) {
                        Connection connection = connectionFactory.createConnection();
                        connection.start();
                        QueueSession session = (QueueSession) connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
                        Queue queue = session.createQueue(queueName1); //cria fila Processando caso n exista
                        Queue queue1 = session.createQueue(queueName2); //cria Fila Completo caso n exista
                        QueueBrowser browser = session.createBrowser(queue);
                        Enumeration e1 = browser.getEnumeration();
                        consumer2 = session.createConsumer(queue); //cria consumidor na fila Processando

                        TextMessage message = session.createTextMessage();

                        if (e1 != null) { //verifica se fila Processando esta vazia
                            while (e1.hasMoreElements()) {
                                Object msg = e1.nextElement();
                                ActiveMQTextMessage m = (ActiveMQTextMessage) msg;
                                Thread.currentThread().sleep(10000); //espera 10 segundos
                                producer2 = session.createProducer(queue1); //cria produtor em Completo
                                message.setText(m.getText()); //pega a primeira mensagem da fila Processando
                                producer2.send(message); //envia de Processando para Completo
                                consumer2.receive(); //retira da fila Processando
                            }
                        }
                        if (e1 == null) { //se Processando estiver vazia, volta para o inicio
                            continue;
                        }
                        browser.close();
                        session.close();
                        connection.close();
                    }
                } catch (Exception e) {
                }
            }
        }.start(); //inicia thread
    }
}
