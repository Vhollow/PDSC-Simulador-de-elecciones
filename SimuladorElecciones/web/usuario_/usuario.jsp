<%-- 
    Document   : usuario
    Created on : 07-nov-2016, 14:33:04
    Author     : Vicente
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="utils.Usuario"%>
<%@page import="utils.Eleccion"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Area personal</title>
        <!-- Latest compiled and minified CSS -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" >

        <!-- Optional theme -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css" >
        <link href="https://fonts.googleapis.com/css?family=Lobster" rel="stylesheet" type="text/css">

        <link rel="stylesheet" href="./mycss.css">

        <!-- Latest compiled and minified JavaScript -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.2.2/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
    </head>
    <body style="background-image: url('./img/fondo-elec.jpg')">
        <%
            Usuario usuarioActual = (Usuario)request.getAttribute("usuarioActual");
            List<Eleccion> eleccionesUsuario = (List<Eleccion>)request.getAttribute("eleccionesUsuario");
        %>
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-10 col-md-offset-1 zona-principal">
                    <div class="sep-2"></div>
                    <div class="col-md-10 col-md-offset-1">
                        <p class="titulo-grande">Simulaciones guardadas</p>
                    </div>
                    <%
                        for (Eleccion eleccionActual : eleccionesUsuario) {
                    %>
                    <div class="col-md-10 sep rect-simul">
                        <p class="titulo"><%= eleccionActual.getTipoEleccion() %>   <%= eleccionActual.getFecha() %></p>
                    </div>
                    <%
                        }
                    %>
                    <div class="col-md-10 col-md-offset-1   ">
                        <button type="button" class="btn btn-primary btn-lg" onclick="location.href='./simulacion'">Nueva simulacion</button>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
