/** DATOS **/
// Datos del grafico
var numEscaños	= 50,
	abstencion	= 20,
	votosNulos	= 5,
	votosBlanco	= 10,
	propMinimoRepresentacion = 0.03,
	candidaturas = [ {nombre: "PSOE", color: "red", votosValidos: 15},
					{nombre: "PP", color: "blue", votosValidos: 20},
					{nombre: "ERC", color: "yellow", votosValidos: 5},
					{nombre: "Unidos Podemos", color: "purple", votosValidos: 15},
					{nombre: "Ciudadanos", color: "orange", votosValidos: 10} ];

// Dimensiones del grafico
var margen			= { superior: 75, derecho: 50, inferior: 75, izquierdo: 50 },
	ancho 			= 650,
	alto			= 650,
	centro			= {x: ancho/2, y: alto},
	radioExterior	= Math.min(ancho, alto) / 2,
	radioInterior	= radioExterior - 50;


/** FUNCIONES **/
function calculaDatos() {
	var totalVotosValidos = votosBlanco;
	for (var i = 0; i < candidaturas.length; i++) {
		totalVotosValidos += candidaturas[i].votosValidos;
	}

	var candidaturasFiltradas = filtraMinimo(candidaturas, totalVotosValidos, propMinimoRepresentacion);
	return calculaDHondt(candidaturasFiltradas, numEscaños);
}


function dibuja() {

	var datos = calculaDatos();

	var svg = d3.select(".chart").append("svg")
		.attr("width", ancho)
		.attr("height", alto);
	var groupArcos = svg.append("g")
		.attr("transform", "translate(" + ancho/2 + ", " + alto/2 + ")");

	ultimoAngulo = -Math.PI/2;
	for (var i in datos) {
		var anguloFinal = ultimoAngulo + datos[i].numEscaños * Math.PI / numEscaños;
		console.log(datos[i].numEscaños);
		console.log(anguloFinal);

		var arco = d3.svg.arc()
			.outerRadius(radioExterior)
			.innerRadius(radioInterior)
			.startAngle(ultimoAngulo)
			.endAngle(anguloFinal);

		groupArcos.append("path")
			.style("fill", datos[i].candidatura.color)
			.attr("d", arco);

		ultimoAngulo = anguloFinal;
	}
}
