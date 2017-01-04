/* VARIABLES */
var propMinRepresentacion   = 0,
    circunscripciones       = [],
    votosCircunscripciones  = [],
    candidaturas            = [],
    datos                   = [];

/* FUNCIONES */
function cambioPropMinRepresentacion(elemento) {
    propMinRepresentacion = elemento.value;
    dibujaGrafico(circunscripciones, candidaturas, votosCircunscripciones, propMinRepresentacion);
}


function actualizarTablaVotos(){
    
    // Header
    var head = document.createElement("thead");
    var row = document.createElement("tr");
    row.appendChild(document.createElement("td"));
    for (var j = 0 ; j < candidaturas.length ; j++){
        var th = document.createElement("th");
        th.innerHTML = candidaturas[j].nombreCorto;
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
            input_v.name  = "votos" + i + j;
            input_v.type  = "text";
            input_v.value = votosCircunscripciones[i][j];
            input_v.setAttribute("indiceCircunscripcion", i);
            input_v.setAttribute("indiceCandidatura", j);
            input_v.onchange = function(){
                votosCircunscripciones[this.getAttribute("indiceCircunscripcion")][this.getAttribute("indiceCandidatura")] = this.value;
                dibujaGrafico(circunscripciones, candidaturas, votosCircunscripciones, propMinRepresentacion);
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


function actualizarTablaCircunscripcion(){
    
    
    var tabla = document.getElementById("tabla-circunscripciones-body");
    
    while(tabla.hasChildNodes()){
        tabla.removeChild(tabla.firstChild);
    }
    
    for (i=0; i<circunscripciones.length; i++){
        var indiceCircunscripcion = i;

        var nombre = document.createElement("th");
        nombre.innerHTML = circunscripciones[indiceCircunscripcion].nombre;

        var input_vn = document.createElement("input");
        input_vn.name  = "votos-nulo" + (indiceCircunscripcion);
        input_vn.type  = "text";
        input_vn.value = 0;
        input_vn.class = "form-control";
        input_vn.setAttribute("indiceCircunscripcion", indiceCircunscripcion);
        input_vn.onchange = function(){
            circunscripciones[this.getAttribute("indiceCircunscripcion")].votoNulo = this.value;
            dibujaGrafico(circunscripciones, candidaturas, votosCircunscripciones, propMinRepresentacion);
        };

        var col1 = document.createElement("td");
        col1.appendChild(input_vn);

        var input_vb = document.createElement("input");
        input_vb.name  = "votos-en-blanco" + indiceCircunscripcion;
        input_vb.type  = "text";
        input_vb.value = 0;
        input_vb.class = "form-control";
        input_vb.setAttribute("indiceCircunscripcion", indiceCircunscripcion);
        input_vb.onchange = function(){
            circunscripciones[this.getAttribute("indiceCircunscripcion")].votoEnBlanco = this.value;
            dibujaGrafico(circunscripciones, candidaturas, votosCircunscripciones, propMinRepresentacion);
        };

        var col2 = document.createElement("td");
        col2.appendChild(input_vb);

        var input_nr = document.createElement("input");
        input_nr.name  = "n-representacion" + indiceCircunscripcion;
        input_nr.type  = "text";
        input_nr.value = 0;
        input_nr.class = "form-control";
        input_nr.setAttribute("indiceCircunscripcion", indiceCircunscripcion);
        input_nr.onchange = function(){
            circunscripciones[this.getAttribute("indiceCircunscripcion")].numeroRepresentantes = this.value;
            dibujaGrafico(circunscripciones, candidaturas, votosCircunscripciones, propMinRepresentacion);
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


function inicio(){
    circunscripciones       = new Array();
    votosCircunscripciones  = new Array();
    repreCircunscripcion    = new Array();
    candidaturas            = new Array();
    datos                   = new Array();
    
    actualizarTablaVotos();
}


function nuevaCircunscripcion(){
    var circunscripcion = {
        nombre: document.getElementById("nombre-circunscripcion").value,
        numeroRepresentantes: 0,
        votoNulo: 0,
        votoEnBlanco: 0
    };
    circunscripciones.push(circunscripcion);
    votosCircunscripciones.push(new Array(candidaturas.length).fill(0));

    // Recuadro para eliminar circunscripcion
    var div = document.createElement("div");
    div.className = "circuns";
    div.innerHTML = circunscripcion.nombre + "  ";
    var icon = document.createElement("span");
    icon.className = "glyphicon glyphicon-remove";
    div.appendChild(icon);
    document.getElementById("cuadrados-circunscripciones").appendChild(div);

    actualizarTablaCircunscripcion();
    actualizarTablaVotos();
    
    // Reset del texto por defecto en los botones para añadir una circunscripcion
    document.getElementById("nombre-circunscripcion").value = "";
}


function nuevaCandidatura(){
    var candidatura = {
        nombreCorto: document.getElementById("candidatura").value,
        nombreLargo: "",
        color: document.getElementById("color").value
    }
    candidaturas.push(candidatura);
    for (var i in votosCircunscripciones) { votosCircunscripciones[i].push(0); }

    var div = document.createElement("div");
    div.className = "circuns";
    div.innerHTML = candidatura.nombreCorto + "  ";
    var icon = document.createElement("span");
    icon.className = "glyphicon glyphicon-remove";
    div.appendChild(icon);

    document.getElementById("elemCandidatura").appendChild(div);

    actualizarTablaVotos();

    // Reset del texto por defecto en los botones para añadir una candidatura
    document.getElementById("candidatura").value = "";
    document.getElementById("color").value = "FFFFFF"
}
