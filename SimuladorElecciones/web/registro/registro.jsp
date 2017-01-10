<%-- 
    Document   : registro
    Created on : 07-nov-2016, 12:51:43
    Author     : Vicente
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Registrate</title>
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
                <div class="col-md-4 col-md-offset-4 rect-central-fino text-center">
                    <p class="titulo-grande sep">Registro</p>
                    <form  class="interior" name="login" action="registrar" method="POST">
                        <b>
                            Nombre de usuario:<br>
                            <input type="text" class="sep" name="usuario"  maxlength="50"><br>
                            Correo electrónico:<br>
                            <input type="text" class="sep" name="correo"  maxlength="50"><br>
                            Contraseña:<br>
                            <input type="password" class="sep" name="contrasena" maxlength="50"><br>
                            Repetir contraseña:<br>
                            <input type="password" class="sep" name="repcontrasena" maxlength="50"><br>
                            <input class="btn btn-lg btn-primary sep" type="submit" value="Registrarse"  name="enviar" />
                        </b>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
