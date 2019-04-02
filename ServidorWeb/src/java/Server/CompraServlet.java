package Server;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//Servlet para comunicacao com cliente, pega os dados de index.html realiza chamada ao webservice e devolve resultados para Compra.jsp e Veriica.jsp)
public class CompraServlet extends HttpServlet {
    //metodo post (parametros nao aparecem para o usuario na barra de endereco do navegador)

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String acao = request.getParameter("acao"); //pega valor do parametro acao da barra do navegador
        if ((acao).toString().compareTo("compra") == 0) { //se acao for compra
            server.Servidor_Service service = new server.Servidor_Service();//realiza chamada aos metodos do web service
            server.Servidor port = service.getServidorPort();
            int result = port.comprar(); //chama o metodo compra e guarda numero do pedido gerado
            request.setAttribute("result", result);
            request.getRequestDispatcher("Compra.jsp").forward(request, response); //chama o jsp enviando o valor obtido
        
        } else if ((acao).toString().compareTo("verifica") == 0) { //se acao for verifica

            String txt = request.getParameter("textform"); //recupera valor de parametro txtform com o numero de pedido para verificacao
            server.Servidor_Service service = new server.Servidor_Service(); //realiza chamada aos metodos do web service
            server.Servidor port = service.getServidorPort();
            String result = port.verifica(txt); //realiza metodo, e guarda retorno com nome da fila
            request.setAttribute("result", result); //seta atributos para devolver ao jsp
            request.setAttribute("txt", txt);
            request.getRequestDispatcher("Verifica.jsp").forward(request, response); //chama o jsp enviando os valores obtidos
        }

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
