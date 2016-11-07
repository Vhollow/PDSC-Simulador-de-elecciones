<%-- 
    Document   : index
    Created on : 07-nov-2016, 11:21:08
    Author     : Vicente
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Inicio</title>
        <!-- Latest compiled and minified CSS -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" >

        <!-- Optional theme -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css" >
        <link href="https://fonts.googleapis.com/css?family=Lobster" rel="stylesheet" type="text/css">

        <link rel="stylesheet" href="mycss.css">

        <!-- Latest compiled and minified JavaScript -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.2.2/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" ></script>
    </head>
    <body style="background-image: url('./img/fondo-elec.jpg')">
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-6 col-md-offset-3 rect-central">
                    <div class="col-md-6 text-center mit-iz">
                        <button type="button" class="btn btn-primary btn-grande interior">Version Libre</button>
                        <p class="sep">(No podra guardar su trabajo en este modo)</p>
                    </div>
                    <div class="col-md-6 text-center">
                        <form  class="interior" name="login" action="Login" method="POST">
                                <input type="text" class="input-login sep" name="usuario"  maxlength="50">
                                <input type="password" class="input-login sep" name="contrasena" maxlength="50"><br>
                                <input class="btn-primary sep" type="submit" value="Login"  name="enviar" />
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
