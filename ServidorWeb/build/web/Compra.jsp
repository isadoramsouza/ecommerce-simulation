<%-- 
    Document   : teste
    Created on : 24/09/2016, 00:24:07
    Author     : isadora
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Realizar Pedido</title>
    </head>
    <body>
        <form>
            <p  style="text-align: center;">
                <%--recupera valor result enviado por post pelo CompraServlet com o numero do pedido gerado--%>
                Pedido realizado com sucesso! Número: 0<%=request.getAttribute("result")%>.
                <br><br>
            </p>
        </form>

        <form style="text-align: center;" action="index.html" method="post">
            <button onclick="location.href = 'index.html'" action="index.html">Voltar</button>
        </form>       

    </body>
</html>
