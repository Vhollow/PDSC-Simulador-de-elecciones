<%-- 
    Document   : simulacion
    Created on : 13-nov-2016, 22:56:04
    Author     : Vicente
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Simulador de elecciones</title>
        <!-- Latest compiled and minified CSS -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" >

        <!-- Optional theme -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css" >
        <link href="https://fonts.googleapis.com/css?family=Lobster" rel="stylesheet" type="text/css">

        <link rel="stylesheet" href="../mycss.css">

        <!-- Latest compiled and minified JavaScript -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.2.2/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" ></script>
        
        <!-- D3 para graficos -->
        <script type="text/javascript" src="./resources/d3.min.js"></script>
        
        <!-- Javascript propios -->
	<script type="text/javascript" src="./resources/leyDHondt.js"></script>
        <script type="text/javascript" src="./resources/paginaSimulacion.js"></script>
	<script type="text/javascript" src="./resources/grafico.js"></script>
        <script type="text/javascript" src="./resources/jscolor.js"></script>
    </head>
    <body class="container-fluid" onload="inicio()">
        <div class="row">
            <!-- Columna de configuración -->
            <div class="col-md-4" style="background-color: #ddf">
                <div class="sep-2"></div>
                <!-- Cargar archivo -->
                <p class="titulo">Cargar archivo</p>
                <form class="form-horizontal col-md-10">
                    <div class="form-group">
                        <select class="form-control">
                            <option>-</option>
                            <option>Elecciones España junio 2016</option>
                            <option>2</option>
                            <option>3</option>
                            <option>4</option>
                            <option>5</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <button type="submit" class="btn btn-primary">Abrir</button>
                    </div>
                </form>
                <div class="clearflx">&nbsp</div>
                <hr>
                
                <!-- Configurar eleccion -->
                <p class="titulo">Detalles elección</p>
                <form class="form-horizontal col-md-10">
                    <div class="form-group">
                        <label for="tipo" class="col-sm-2 control-label">Tipo</label>
                        <div class="col-sm-10">
                            <select class="form-control" id="tipo">
                                <option>-</option>
                                <option>Congreso</option>
                                <option>Autonómicas</option>
                                <option>Municipales</option>
                                <option>Europeas</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="año" class="col-sm-2 control-label">Año</label>
                        <div class="col-sm-4">
                            <select class="form-control" id="año">
                                <option>-</option>
                                <option>2016</option>
                                <option>2015</option>
                                <option>2014</option>
                                <option>2013</option>
                            </select>
                        </div>
                        <label for="mes" class="col-sm-1 control-label">Mes</label>
                        <div class="col-sm-5">
                            <select class="form-control" id="mes">
                                <option>-</option>
                                <option>Enero</option>
                                <option>Febrero</option>
                                <option>Marzo</option>
                                <option>Abril</option>
                                <option>Mayo</option>
                                <option>Junio</option>
                                <option>Julio</option>
                                <option>Abgosto</option>
                                <option>Septiembre</option>
                                <option>Octubre</option>
                                <option>Noviembre</option>
                                <option>Diciembre</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="n_rep" class="col-sm-4">Numero de representantes</label>
                        <div class="col-sm-8">
                            <input type="text" class="form-control" id="n_rep" placeholder="ej:1000">
                        </div>
                    </div>
                </form>
                <div class="clearflx">&nbsp</div>
                <hr>
                
                <!-- Parametros simulacion -->
                <p class="titulo">Parametros simulación</p>
                <form class="form-horizontal col-md-10">
                    <div class="form-group">
                        <label for="umbral" class="col-sm-2">Umbral minimo</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="umbral" onchange="cambioPropMinRepresentacion()" placeholder="ej:1000">
                        </div>
                    </div>
                </form>
                
                <form class="form-horizontal col-sm-11">
                    <div class="form-group">
                        <label for="circunscripcion" class="col-sm-4">Nueva circunscripción</label>
                        <div class="col-sm-5">
                            <input type="text" class="form-control" id="circunscripcion" placeholder="ej:Madrid">
                        </div>
                    <button type="button" class="btn btn-primary col-sm-2" onclick="nuevaCircunscripcion()">Añadir</button>
                    </div>
                </form>
                
                <div class="clearflx"></div>
                <div id="elemCirc"></div>
                <div class="sep-2"></div>
                
                <form class="form-horizontal col-sm-11">
                    <div class="form-group">
                        <label for="partido" class="col-sm-4">Nuevo partido</label>
                        <div class="col-sm-7">
                            <input type="text" class="form-control" id="partido" placeholder="ej:Partido x">
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-5">
                            <input class="jscolor" id="color" value="">
                        </div>
                        <div class="col-sm-2 col-sm-offset-2">
                            <button type="button" class="btn btn-primary" onclick="nuevoPartido()">Añadir</button>
                        </div>
                    </div>
                </form>
                
                <div class="clearflx"></div>
                <div id="elemPartido"></div>
                <div class="clearflx">&nbsp</div>
                
            </div>

            <!-- Zona principal -->
            <div class="col-md-8">
                <div class="sep">
                    <p class="titulo"> Circunscripciones </p>
                    <div class="clearflx"></div>
                    <table class="table table-bordered" id="tabla-circunscripciones">
                        <thead>
                            <tr>
                                <th> Circunscripcion </th>
                                <th> Total votos </th>
                                <th> Numero representantes </th>
                            </tr>
                        </thead>
                        <tbody id="tabla-circunscripciones-body">
                            
                        </tbody>
                    </table>
                </div>
                <div class="sep">
                    <p class="titulo"> Votos </p>
                    <div class="clearflx"></div>
                    <table class="table table-bordered table-responsive" id="tabla-votos">
                        
                    </table>
                </div>
                
                <div class="sep">
                    <p class="titulo"> Simulacion </p>
                    <div class="clearflx"></div>
                        <!-- Aquí aparecen los gráficos de la simulación -->
                        <div class="charts"></div>
                </div>
            </div>
        </div> 
    </body>
</html>
