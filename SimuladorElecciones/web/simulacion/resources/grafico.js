/** DATOS **/
// Dimensiones del grafico
var margen          = { superior: 75, derecho: 50, inferior: 75, izquierdo: 50 },
    ancho           = 650,
    alto            = 650,
    centro          = {x: ancho/2, y: alto},
    radioExterior   = Math.min(ancho, alto) / 2,
    radioInterior   = radioExterior - 50;


/** FUNCIONES **/
/*
 * Dibuja el grafico de sectores con los escaños de cada candidatura en cada una
 * de las circunscripciones dadas
 */
function dibujaGrafico(circunscripciones, candidaturas, votosCircunscripciones, propMinRepresentacion) {

    for (var i in circunscripciones) {
	var datos = calculaEscaños(circunscripciones[i], candidaturas, votosCircunscripciones[i], propMinRepresentacion);
        if (datos === null) { continue; }

	var svg = d3.select(".charts").append("svg")
            .attr("width", ancho)
            .attr("height", alto);
	var groupArcos = svg.append("g")
            .attr("transform", "translate(" + ancho/2 + ", " + alto/2 + ")");

	ultimoAngulo = -Math.PI/2;
	for (var j in datos) {
            var anguloFinal = ultimoAngulo + datos[j].numeroEscaños * Math.PI / circunscripciones[i].numeroRepresentantes;

            var arco = d3.svg.arc()
                .outerRadius(radioExterior)
                .innerRadius(radioInterior)
                .startAngle(ultimoAngulo)
                .endAngle(anguloFinal);

            groupArcos.append("path")
                .style("fill", datos[j].candidatura.color)
                .attr("d", arco);

            ultimoAngulo = anguloFinal;
	}
    }
    
}

function dibujaGraficoPequeño(circunscripcion, candidaturas, votosCircunscripcion, propMinRepresentacion) {
    
    var datos = calculaEscaños(circunscripcion, candidaturas, votosCircunscripcion, propMinRepresentacion);
    
    var contenedor = document.getElementById("charts1");
    while(contenedor.hasChildNodes()){
        contenedor.removeChild(contenedor.firstChild);
    }
    
    var svg = d3.select("#charts1").append("svg")
        .attr("width", ancho/2)
        .attr("height", alto/2);
    var groupArcos = svg.append("g")
        .attr("transform", "translate(" + ancho/4 + ", " + alto/4 + ")");

    ultimoAngulo = -Math.PI/2;
    for (var j in datos) {
        var anguloFinal = ultimoAngulo + datos[j].numeroEscaños * Math.PI / circunscripcion.numeroRepresentantes;

        var arco = d3.svg.arc()
            .outerRadius(radioExterior)
            .innerRadius(radioInterior)
            .startAngle(ultimoAngulo)
            .endAngle(anguloFinal);

        groupArcos.append("path")
            .style("fill", datos[j].candidatura.color)
            .attr("d", arco);
    
        ultimoAngulo = anguloFinal;
    }
}

function dibujaGraficoGrande(indice, circunscripciones, candidaturas, votosCircunscripciones, propMinRepresentacion) {
    
    var contenedor = document.getElementById("charts2");
    while(contenedor.hasChildNodes()){
        contenedor.removeChild(contenedor.firstChild);
    }
    
    var datos = [];
    var numRepresentantes = 0;
    
    for(var i in circunscripciones){
        numRepresentantes += circunscripciones[i].numeroRepresentantes;
    }
    
    for(var i = 0 ; i <= indice ; i++){
        var datosTemp = calculaEscaños(circunscripciones[i], candidaturas, votosCircunscripciones[i], propMinRepresentacion);
        
        if (i === 0) {
            datos = datosTemp;
        } else {
            for(var j = 1 ; j <= datosTemp.length ; j++){
                datos[j].numeroEscaños += datosTemp[j].numeroEscaños;
            }
        }
    }
    
    var svg = d3.select("#charts2").append("svg")
        .attr("width", ancho)
        .attr("height", alto);
    var groupArcos = svg.append("g")
        .attr("transform", "translate(" + ancho/2 + ", " + alto/2 + ")");

    ultimoAngulo = -Math.PI/2;
    for (var j in datos) {
        var anguloFinal = ultimoAngulo + datos[j].numeroEscaños * Math.PI / numRepresentantes;

        var arco = d3.svg.arc()
            .outerRadius(radioExterior)
            .innerRadius(radioInterior)
            .startAngle(ultimoAngulo)
            .endAngle(anguloFinal);

        groupArcos.append("path")
            .style("fill", datos[j].candidatura.color)
            .attr("d", arco);
    
        ultimoAngulo = anguloFinal;
    }
}
