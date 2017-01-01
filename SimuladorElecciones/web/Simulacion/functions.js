
/*
 * Retorna un array con las candidaturas dadas que superan el umbral minimo de
 * representacion
 */
function filtraMinimo(candidaturas, totalVotosValidos, propMinimoRepresentacion) {

	var minimo = propMinimoRepresentacion * totalVotosValidos;

	var ret = new Array();
	for (var i = 0; i < candidaturas.length; i++) {
		if (candidaturas[i].votosValidos > minimo) {
			ret.push(candidaturas[i]);
		}
	}

	return ret;
}


/*
 * Ordena el array de Candidaturas dado por el numero de votos de la
 * candidaturas
 */
function ordenaPorVotos(candidaturas) {

	// Ordenacion por insercion
	for (var i = 0; i < candidaturas.length; i++) {
		for (var j = 0; j < candidaturas.length; j++) {
			if (candidaturas[j].votosValidos < candidaturas[i].votosValidos) {
				tmp = candidaturas[j];
				candidaturas[j] = candidaturas[i];
				candidaturas[i] = tmp;
				j = candidaturas.length;
			}
		}
	}

}


/*
 * Funcion que calcula el numero de escaños que le toca a cada candidatura en
 * base al numero de escaños y su numero de votos. Retorna un array con las
 * candidaturas y sus respectivo numero de escaños
 */
function calculaDHondt(candidaturas, numEscaños) {

	if (candidaturas.length <= 0 || numEscaños <= 0) { return null; }

	ordenaPorVotos(candidaturas);

	var divisores			= new Array(candidaturas.length).fill(1);
	var escaño				= new Array(candidaturas.length);
	var escañosAsignados	= new Array(candidaturas.length);
	for (var i = 0; i < candidaturas.length; i++) {
		escañosAsignados[i] = {candidatura: candidaturas[i], numEscaños: 0};
	}

	while (numEscaños--) {
		var indiceMaximo = 0;
		for (var j = 0; j < candidaturas.length; j++) {
			escaño[j] = candidaturas[j].votosValidos / divisores[j];

			if (escaño[j] > escaño[indiceMaximo]) {
				indiceMaximo = j;
			}
		}

		divisores[indiceMaximo]++;
		escañosAsignados[indiceMaximo].numEscaños++;
	}

	return escañosAsignados;
}


function calcula() {

	var numEscaños		= 50;
	var abstencion		= 20;
	var votosNulos		= 5;
	var votosBlanco		= 10;
	var propMinimoRepresentacion = 0.03;
	var candidaturas	= [ {nombre: "PSOE", votosValidos: 15}, {nombre: "PP", votosValidos: 20},
							{nombre: "ERC", votosValidos: 5}, {nombre: "Unidos Podemos", votosValidos: 15},
							{nombre: "Ciudadanos", votosValidos: 10} ];

	var totalVotosValidos = votosBlanco;
	for (var i = 0; i < candidaturas.length; i++) {
		totalVotosValidos += candidaturas[i].votosValidos;
	}

	var candidaturasFiltradas = filtraMinimo(candidaturas, totalVotosValidos, propMinimoRepresentacion);
	var res = calculaDHondt(candidaturasFiltradas, numEscaños);
	console.log(res);

}
