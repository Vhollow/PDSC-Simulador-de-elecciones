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
