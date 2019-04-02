
package server;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the server package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _VerificaResponse_QNAME = new QName("http://Server/", "VerificaResponse");
    private final static QName _Comprar_QNAME = new QName("http://Server/", "Comprar");
    private final static QName _Verifica_QNAME = new QName("http://Server/", "Verifica");
    private final static QName _ComprarResponse_QNAME = new QName("http://Server/", "ComprarResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: server
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Comprar }
     * 
     */
    public Comprar createComprar() {
        return new Comprar();
    }

    /**
     * Create an instance of {@link VerificaResponse }
     * 
     */
    public VerificaResponse createVerificaResponse() {
        return new VerificaResponse();
    }

    /**
     * Create an instance of {@link ComprarResponse }
     * 
     */
    public ComprarResponse createComprarResponse() {
        return new ComprarResponse();
    }

    /**
     * Create an instance of {@link Verifica }
     * 
     */
    public Verifica createVerifica() {
        return new Verifica();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link VerificaResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Server/", name = "VerificaResponse")
    public JAXBElement<VerificaResponse> createVerificaResponse(VerificaResponse value) {
        return new JAXBElement<VerificaResponse>(_VerificaResponse_QNAME, VerificaResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Comprar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Server/", name = "Comprar")
    public JAXBElement<Comprar> createComprar(Comprar value) {
        return new JAXBElement<Comprar>(_Comprar_QNAME, Comprar.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Verifica }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Server/", name = "Verifica")
    public JAXBElement<Verifica> createVerifica(Verifica value) {
        return new JAXBElement<Verifica>(_Verifica_QNAME, Verifica.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ComprarResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Server/", name = "ComprarResponse")
    public JAXBElement<ComprarResponse> createComprarResponse(ComprarResponse value) {
        return new JAXBElement<ComprarResponse>(_ComprarResponse_QNAME, ComprarResponse.class, null, value);
    }

}
