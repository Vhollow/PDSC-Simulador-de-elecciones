/*
 * A partir de los datos dados, retorna un array con las candidaturas que
 * superan el umbral minimo de representacion y sus respectivos votos
 */
function filtraCandidaturas(circunscripcion, candidaturas, votosACandidatura, propMinRepresentacion) {

    var totalVotosValidos = circunscripcion.votosBlanco;
    for (var i = 0; i < votosACandidatura.length; i++) {
        totalVotosValidos += votosACandidatura[i];
    }

    var copiaCandidaturas = [];
    var copiaVotosACandidatura = [];
    var minimo = propMinRepresentacion * totalVotosValidos;
    for (var i = 0; i < candidaturas.length; i++) {
        if (votosACandidatura[i] >= minimo) {
            copiaCandidaturas.push(candidaturas[i]);
            copiaVotosACandidatura.push(votosACandidatura[i]);
        }
    }
    
    return {
        candidaturas: copiaCandidaturas,
        votosACandidatura: copiaVotosACandidatura
    };
}


/*
 * Ordena el array de candidaturas y el de numero de votospor el numero de
 * votos asignado a cada candidatura de mayor a menor
 */
function ordenaPorVotos(candidaturas, votosACandidatura) {

    for (var i = 0; i < candidaturas.length; i++) {

        var indiceMaximo = i;
        for (var j = indiceMaximo; j < candidaturas.length; j++) {
            if (votosACandidatura[indiceMaximo] < votosACandidatura[j]) {
                indiceMaximo = j;
            }
        }

        var tmp1 = candidaturas[i];
        var tmp2 = votosACandidatura[i];
        candidaturas[i] = candidaturas[indiceMaximo];
        votosACandidatura[i] = votosACandidatura[indiceMaximo];
        candidaturas[indiceMaximo] = tmp1;
        votosACandidatura[indiceMaximo] = tmp2;
    }

    return {
        candidaturas: candidaturas,
        votosACandidatura: votosACandidatura
    };
}


/*
 * Funcion que calcula el numero de escaños que le toca a cada candidatura en
 * base al numero de escaños y su numero de votos. Retorna un array con las
 * candidaturas y sus respectivo numero de escaños.
 */
function calculaDHondt(candidaturas, votosACandidatura, numeroEscaños) {

    if (candidaturas.length <= 0 || numeroEscaños <= 0) { return null; }

    datosOrdenados = ordenaPorVotos(candidaturas, votosACandidatura);

    var divisores           = new Array(datosOrdenados.candidaturas.length).fill(1);
    var escaño              = new Array(datosOrdenados.candidaturas.length);
    var escañosAsignados    = new Array(datosOrdenados.candidaturas.length);
    for (var i = 0; i < datosOrdenados.candidaturas.length; i++) {
        escañosAsignados[i] = {
            candidatura: datosOrdenados.candidaturas[i],
            numeroEscaños: 0
        };
    }

    while (numeroEscaños--) {
        var indiceMaximo = 0;
        for (var j = 0; j < datosOrdenados.candidaturas.length; j++) {
            escaño[j] = datosOrdenados.votosACandidatura[j] / divisores[j];

            if (escaño[j] > escaño[indiceMaximo]) {
                indiceMaximo = j;
            }
        }

        divisores[indiceMaximo]++;
        escañosAsignados[indiceMaximo].numeroEscaños++;
    }

    return escañosAsignados;
}


/*
 * Ordena el array de candidaturas y numero de escaños dado por el numero de
 * escaños asignado a cada candidatura de mayor a menor
 */
function ordenaPorEscaños(escañosAsignados) {

    escañosAsignados.sort(function(a, b){
        if (a.numEscaños < b.numEscaños)
            return 1;
        if (a.numEscaños > b.numEscaños)
            return -1;
        return 0;
    });
    
    return escañosAsignados;
}


/*
 * Calcula los escaños correspondientes a cada candidatura a partir de los datos
 * dados mediante el sistema d´Hondt filtrando las candidaturas que no superen
 * el minimo de representacion, y lo retorna en un array ordenado de mayor a
 * menor por el numero de escaños
 */
function calculaEscaños(circunscripcion, candidaturas, votosACandidatura, propMinRepresentacion) {

    var datosFiltrados = filtraCandidaturas(circunscripcion, candidaturas, votosACandidatura, propMinRepresentacion);
    
    return ordenaPorEscaños(
        calculaDHondt(
            datosFiltrados.candidaturas,
            datosFiltrados.votosACandidatura,
            circunscripcion.numeroRepresentantes
        )
    );
}
