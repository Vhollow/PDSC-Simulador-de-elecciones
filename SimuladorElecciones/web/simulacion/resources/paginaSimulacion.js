/* VARIABLES */
var propMinRepresentacion   = 0,
    circunscripciones       = [],
    votos                   = [],
    candidaturas            = [],
    indiceSimulacion        = 0;

/* FUNCIONES */
function actualizaPropMinRepresentacion(elemento) {
    propMinRepresentacion = parseInt(elemento.value) / 100;
}


function actualizaTablaCircunscripcion() {
    
    var tabla = document.getElementById("tabla-circunscripciones-body");
    while(tabla.hasChildNodes()){
        tabla.removeChild(tabla.firstChild);
    }
    
    for (var i = 0; i < circunscripciones.length; i++){
        var indiceCircunscripcion = i;

        var nombre = document.createElement("th");
        nombre.innerHTML = circunscripciones[indiceCircunscripcion].nombre;

        var input_vn = document.createElement("input");
        input_vn.name  = "input-circunscripcion-voto-nulo" + (indiceCircunscripcion);
        input_vn.type  = "text";
        input_vn.value = circunscripciones[indiceCircunscripcion].votoNulo;
        input_vn.class = "form-control";
        input_vn.setAttribute("indiceCircunscripcion", indiceCircunscripcion);
        input_vn.onchange = function(){
            circunscripciones[this.getAttribute("indiceCircunscripcion")].votoNulo = parseInt(this.value);
        };

        var col1 = document.createElement("td");
        col1.appendChild(input_vn);

        var input_vb = document.createElement("input");
        input_vb.name  = "input-circunscripcion-voto-en-blanco" + indiceCircunscripcion;
        input_vb.type  = "text";
        input_vb.value = circunscripciones[indiceCircunscripcion].votoEnBlanco;
        input_vb.class = "form-control";
        input_vb.setAttribute("indiceCircunscripcion", indiceCircunscripcion);
        input_vb.onchange = function(){
            circunscripciones[this.getAttribute("indiceCircunscripcion")].votoEnBlanco = parseInt(this.value);
        };

        var col2 = document.createElement("td");
        col2.appendChild(input_vb);

        var input_nr = document.createElement("input");
        input_nr.name  = "input-circunscripcion-numero-representantes" + indiceCircunscripcion;
        input_nr.type  = "text";
        input_nr.value = circunscripciones[indiceCircunscripcion].numeroRepresentantes;
        input_nr.class = "form-control";
        input_nr.setAttribute("indiceCircunscripcion", indiceCircunscripcion);
        input_nr.onchange = function(){
            circunscripciones[this.getAttribute("indiceCircunscripcion")].numeroRepresentantes = parseInt(this.value);
        };

        var col3 = document.createElement("td");
        col3.appendChild(input_nr);

        var row = document.createElement("tr");
        row.appendChild(nombre);
        row.appendChild(col1);
        row.appendChild(col2);
        row.appendChild(col3);

        tabla.appendChild(row);
    }
}


function actualizaTablaVotos() {
    // Header
    var head = document.createElement("thead");
    var row = document.createElement("tr");
    row.appendChild(document.createElement("td"));
    for (var j = 0 ; j < candidaturas.length ; j++){
        var th = document.createElement("th");
        th.innerHTML = candidaturas[j].nombreCorto;
        th.style = "background-color: #" + candidaturas[j].color;
        row.appendChild(th);
    }
    head.appendChild(row);

    // Body
    var body = document.createElement("tbody");
    for (var i = 0; i < circunscripciones.length; i++){
        row = document.createElement("tr");
        
        var th = document.createElement("th");
        th.innerHTML = circunscripciones[i].nombre;
        row.appendChild(th);
        
        for (j = 0; j < candidaturas.length; j++){
            var input_v = document.createElement("input");
            input_v.name  = "input-votos" + i + "" + j;
            input_v.type  = "text";
            input_v.value = votos[i][j];
            input_v.setAttribute("indiceCircunscripcion", i);
            input_v.setAttribute("indiceCandidatura", j);
            input_v.onchange = function(){
                votos[this.getAttribute("indiceCircunscripcion")][this.getAttribute("indiceCandidatura")] = parseInt(this.value);
            };
            
            var td  = document.createElement("td");
            td.appendChild(input_v);
            
            row.appendChild(td);
        }
        
        body.appendChild(row);
    }
    
    // Table
    var tabla = document.getElementById("tabla-votos");
    while(tabla.hasChildNodes()){
        tabla.removeChild(tabla.firstChild);	
    }
    tabla.appendChild(head);
    tabla.appendChild(body);
}


function nuevaCircunscripcion() {
    var formSimulacion = document.getElementById("form-simulacion");
    
    var circunscripcion = {
        nombre: formSimulacion.elements["input-circunscripcion-nombre"].value,
        numeroRepresentantes: 0,
        votoNulo: 0,
        votoEnBlanco: 0
    };
    circunscripciones.push(circunscripcion);
    votos.push(new Array(candidaturas.length).fill(0));

    // Recuadro para eliminar circunscripcion
    var div = document.createElement("div");
    div.className = "circuns";
    div.innerHTML = circunscripcion.nombre + "  ";
    var icon = document.createElement("span");
    icon.className = "glyphicon glyphicon-remove";
    div.appendChild(icon);
    document.getElementById("cuadrados-circunscripciones").appendChild(div);

    actualizaTablaCircunscripcion();
    actualizaTablaVotos();
    
    // Reset del texto por defecto en los botones para añadir una circunscripcion
    formSimulacion.elements["input-circunscripcion-nombre"].value = "";
}


function nuevaCandidatura() {
    var formSimulacion = document.getElementById("form-simulacion");
    
    var candidatura = {
        nombreCorto: formSimulacion.elements["input-candidatura-nombre"].value,
        nombreLargo: "",
        color: formSimulacion.elements["input-candidatura-color"].value
    };
    candidaturas.push(candidatura);
    for (var i in votos) { votos[i].push(0); }

    var div = document.createElement("div");
    div.className = "circuns";
    div.innerHTML = candidatura.nombreCorto + "  ";
    div.style = "background-color: #" + candidatura.color;
    var icon = document.createElement("span");
    icon.className = "glyphicon glyphicon-remove";
    div.appendChild(icon);

    document.getElementById("cuadrados-candidaturas").appendChild(div);

    actualizaTablaVotos();
    
    // Reset del texto por defecto en los botones para añadir una candidatura
    formSimulacion.elements["input-candidatura-nombre"].value = "";
    formSimulacion.elements["input-candidatura-color"].value = "FFFFFF";
}


/*
 * Almacena los datos javascript que no estan todavía en la pagina en hiddens
 * para que los servlets puedan acceder a ellos
 */
function doSave() {
    
    function addHidden(element, key, value) {
        var input = document.createElement("input");
        input.type = "hidden";
        input.name = key;
        input.value = value;
        element.appendChild(input);
    }
    
    var formSimulacion = document.getElementById("form-simulacion");
    
    // Añadimos un div al form donde introduciremos todos los datos
    var almacen = document.createElement("div");
    almacen.id = "almacen";
    formSimulacion.appendChild(almacen);
    
    // Añadimos los hidden al div
    var inputTipoEleccion = formSimulacion.elements["input-tipo-eleccion"];
    addHidden(almacen, "hidden-tipo-eleccion", inputTipoEleccion.value);
    
    addHidden(almacen, "hidden-numero-candidaturas", candidaturas.length);
    for (var i in candidaturas) {
        addHidden(almacen, "hidden-candidatura-nombre-corto" + i, candidaturas[i].nombreCorto);
        addHidden(almacen, "hidden-candidatura-nombre-largo" + i, candidaturas[i].nombreLargo);
        addHidden(almacen, "hidden-candidatura-color" + i, parseInt(candidaturas[i].color, 16));
    }
    
    addHidden(almacen, "hidden-numero-circunscripciones", circunscripciones.length);
    for (var i in candidaturas) {
        addHidden(almacen, "hidden-circunscripcion-nombre" + i, circunscripciones[i].nombre);
    }

    // El resto de datos ya estan en los inputs
    return true;
}


/*
 * Carga los datos javascript que estan en la pagina almacenados en hiddens en
 * sus correspondientes variables
 */
function doLoad() {
    
    var formSimulacion = document.getElementById("form-simulacion");
    
    // Actualizamos los datos del Javascript
    var tipoEleccion = formSimulacion.elements["hidden-tipo-eleccion"].value;
    
    propMinRepresentacion = formSimulacion.elements["hidden-prop-min-representacion"].value;
    
    var numeroCircunscripciones = formSimulacion.elements["hidden-numero-circunscripciones"].value;
    circunscripciones = new Array(numeroCircunscripciones);
    for (var i = 0; i < numeroCircunscripciones; i++) {
        var circunscripcion = {
            nombre: formSimulacion.elements["hidden-circunscripcion-nombre" + i].value,
            numeroRepresentantes: parseInt(formSimulacion.elements["hidden-circunscripcion-numero-representantes" + i].value),
            votoNulo: parseInt(formSimulacion.elements["hidden-circunscripcion-voto-nulo" + i].value),
            votoEnBlanco: parseInt(formSimulacion.elements["hidden-circunscripcion-voto-en-blanco" + i].value)
        };
        circunscripciones[i] = circunscripcion;
    }
    
    var numeroCandidaturas = formSimulacion.elements["hidden-numero-candidaturas"].value;
    candidaturas = new Array(numeroCandidaturas);
    for (var i = 0; i < numeroCandidaturas; i++) {
        var candidatura = {
            nombreCorto: formSimulacion.elements["hidden-candidatura-nombre-corto" + i].value,
            nombreLargo: formSimulacion.elements["hidden-candidatura-nombre-largo" + i].value,
            color: parseInt(formSimulacion.elements["hidden-candidatura-color" + i].value).toString(16)
        };
        candidaturas[i] = candidatura;
    }

    votos = new Array(numeroCircunscripciones);
    for (var i = 0; i < numeroCircunscripciones; i++) {
        var fila = new Array(numeroCandidaturas);
        for (var j = 0; j < numeroCandidaturas; j++) {
            fila[j] = formSimulacion.elements["hidden-votos" + i + "" + j].value;
        }
        votos[i] = fila;
    }
    
    // Actualizamos la vista
    formSimulacion.elements["input-tipo-eleccion"].value = tipoEleccion;
    formSimulacion.elements["input-prop-min-representacion"].value = propMinRepresentacion * 100;
    actualizaTablaCircunscripcion();
    actualizaTablaVotos();
}


/*
 * Funciones para controlar el flujo de la simulación: iniciar, avanzar ...
 */
function inicioSimulacion() {
    document.getElementById("boton-inicio").style.display = "none";
    document.getElementById("boton-avance").style.display = "block";
    document.getElementById("boton-avance").disabled = false;
    document.getElementById("boton-retroceso").style.display = "block";
    document.getElementById("boton-retroceso").disabled = true;
    document.getElementById("boton-avance-fin").style.display = "block";
    document.getElementById("boton-avance-fin").disabled = false;
    document.getElementById("boton-detener").style.display = "block";
    document.getElementById("charts1").style.display = "initial";
    document.getElementById("charts2").style.display = "initial";
    
    if (circunscripciones.length === 1) {
        document.getElementById("boton-avance").disabled = true;
        document.getElementById("boton-avance-fin").disabled = true;
    }
    
    dibujaGraficoPequeño(circunscripciones[0],candidaturas, votos[0], propMinRepresentacion);
    dibujaGraficoGrande(0, circunscripciones, candidaturas, votos, propMinRepresentacion);
}

function avanzarSimulacion() {
    indiceSimulacion++;
    document.getElementById("boton-retroceso").disabled = false;
    
    if (indiceSimulacion === circunscripciones.length - 1) {
       document.getElementById("boton-avance").disabled = true;
       document.getElementById("boton-avance-fin").disabled = true;
    }
    
    dibujaGraficoPequeño(circunscripciones[indiceSimulacion],candidaturas, votos[indiceSimulacion], propMinRepresentacion);
    dibujaGraficoGrande(indiceSimulacion, circunscripciones, candidaturas, votos, propMinRepresentacion);
}

function retrocederSimulacion() {
    indiceSimulacion--;
    document.getElementById("boton-avance").disabled = false;
    
    if (indiceSimulacion === 0) {
       document.getElementById("boton-retroceso").disabled = true;
    }
    
    dibujaGraficoPequeño(circunscripciones[indiceSimulacion], candidaturas, votos[indiceSimulacion], propMinRepresentacion);
    dibujaGraficoGrande(indiceSimulacion, circunscripciones, candidaturas, votos, propMinRepresentacion);
}

function avanzarFinSimulacion() {
    indiceSimulacion = circunscripciones.length - 1;
    dibujaGraficoPequeño(circunscripciones[indiceSimulacion],candidaturas, votos[indiceSimulacion], propMinRepresentacion);
    dibujaGraficoGrande(indiceSimulacion, circunscripciones, candidaturas, votos, propMinRepresentacion);
}

function finalizarSimulacion() {
    document.getElementById("boton-inicio").style.display = "block";
    document.getElementById("boton-avance").style.display = "none";
    document.getElementById("boton-retroceso").style.display = "none";
    document.getElementById("boton-avance-fin").style.display = "none";
    document.getElementById("boton-detener").style.display = "none";
    document.getElementById("charts1").style.display = "none";
    document.getElementById("charts2").style.display = "none";
}
