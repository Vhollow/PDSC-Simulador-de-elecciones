<!-- 
    Document   : index
    Created on : 07-nov-2016, 11:21:08
    Author     : Vicente
-->

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Inicio</title>
        <!-- Latest compiled and minified CSS -->
        <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" >

        <!-- Optional theme -->
        <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css">
        <link rel="stylesheet" type="text/css" href="https://fonts.googleapis.com/css?family=Lobster">

        <link rel="stylesheet" type="text/css" href="mycss.css">

        <!-- Latest compiled and minified JavaScript -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.2.2/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
    </head>
    <body style="background-image: url('./img/fondo-elec.jpg')">
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-6 col-md-offset-3 rect-central">
                    <div class="col-md-6 text-center mit-iz">
                        <button type="button" class="btn btn-primary btn-lg interior" onclick="location.href='./simulacion'">Versión Libre</button>
                        <br>
                        <br>
                        <b class="sep">*En este modo podrá realizar las simulaciones pero no podrá guardar su trabajo</b>
                    </div>
                    <div class="col-md-6 text-center">
                        <p class="titulo interior">Versión usuario</p>
                        <form class="sep" name="./" action="./" method="POST">
                            <input type="text" class="sep" name="correoElectronico" maxlength="50">
                            <input type="password" class="sep" name="clave" maxlength="50"><br>
                            <input class="btn btn-lg btn-primary sep" type="submit" value="Login" name="enviar"/>
                        </form>
                        <p>¿No tienes una cuenta? <a href="./registro">Registrate</a></p>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
