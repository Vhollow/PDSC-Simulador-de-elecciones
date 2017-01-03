/* VARIABLES */
var propMinRepresentacion   = 0,
    circunscripciones       = [],
    votosACandidatura       = [],
    candidaturas            = [],
    datos                   = [];

/* FUNCIONES */
function actualizaGraficos() {
    var tabla   = document.getElementById("tabla-votos");
    
    
}


function actualizarTablaVotos(){
    
    // Header
    var head    = document.createElement("thead");
    var row     = document.createElement("tr");
    row.appendChild(document.createElement("td"));
    for (var j = 0 ; j < candidaturas.length ; j++){
        var th = document.createElement("th");
        th.innerHTML = candidaturas[j];
        row.appendChild(th);
    }
    head.appendChild(row);

    // Body
    var body    = document.createElement("tbody");
    for (var i = 0; i < circunscripciones.length; i++){
        row = document.createElement("tr");
        
        var th = document.createElement("th");
        th.innerHTML = circunscripciones[i];
        row.appendChild(th);
        
        for (j = 0; j < candidaturas.length; j++){
            var v   = document.createElement("input");
            v.name  = "votos" + i + j;
            v.type  = "text";
            v.value = 0;
            
            var td  = document.createElement("td");
            td.appendChild(v);
            
            row.appendChild(td);
        }
        
        body.appendChild(row);
    }
    
    // Table
    var tabla   = document.getElementById("tabla-votos");
    while(tabla.hasChildNodes()){
        tabla.removeChild(tabla.firstChild);	
    }
    tabla.appendChild(head);
    tabla.appendChild(body);
}


function añadirTablaCircunscripcion(){
    
    var c   = document.createElement("th");
    c.innerHTML = circunscripciones[circunscripciones.length - 1];

    var v   = document.createElement("input");
    v.name  = "votos" + (circunscripciones.length - 1);
    v.type  = "text";
    v.value = 0;
    v.class = "form-control";
    
    var col1 = document.createElement("td");
    col1.appendChild(v);
    
    var r   = document.createElement("input");
    r.name  = "repre" + (circunscripciones.length - 1);
    r.type  = "text";
    r.value = 0;
    r.class = "form-control";
    
    var col2 = document.createElement("td");
    col2.appendChild(r);
    
    var row = document.createElement("tr");
    row.appendChild(c);
    row.appendChild(col1);
    row.appendChild(col2);

    var tabla = document.getElementById("tabla-circunscripciones-body");
    tabla.appendChild(row);
}


function inicio(){
    circunscripciones       = new Array();
    votosACandidatura       = new Array();
    repreCircunscripcion    = new Array();
    candidaturas            = new Array();
    datos                   = new Array();
    
    actualizarTablaVotos();
}


function nuevaCircunscripcion(){
    var circunscripcion = document.getElementById("circunscripcion").value;
    if(circunscripciones.lenght === 0){
        circunscripciones = [circunscripcion];
    } else {
        circunscripciones.push(circunscripcion);
    }
    votosACandidatura.push(0);
    repreCircunscripcion.push(0);

    var div = document.createElement("div");
    div.className = "circuns";
    div.innerHTML = circunscripcion + "  ";
    var icon = document.createElement("span");
    icon.className = "glyphicon glyphicon-remove";
    div.appendChild(icon);

    document.getElementById("elemCirc").appendChild(div);

    añadirTablaCircunscripcion();
    actualizarTablaVotos();
}


function nuevoPartido(){
    var partido = document.getElementById("partido").value;
    if(candidaturas.lenght === 0){
        candidaturas = [partido];
    }
    else candidaturas.push(partido);

    var div = document.createElement("div");
    div.className = "circuns";
    div.innerHTML = partido + "  ";
    var icon = document.createElement("span");
    icon.className = "glyphicon glyphicon-remove";
    div.appendChild(icon);

    document.getElementById("elemPartido").appendChild(div);

    actualizarTablaVotos();
}
