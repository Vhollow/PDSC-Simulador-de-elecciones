function hexToR(h) {return parseInt(h.substring(0,2),16)}
function hexToG(h) {return parseInt(h.substring(2,4),16)}
function hexToB(h) {return parseInt(h.substring(4,6),16)}

/*
 * Dibuja el grafico de sectores con los escaños de cada candidatura en la
 * circunscripcion dada
 */
function dibujaGraficoPequeño(circunscripcion, candidaturas, votosCircunscripcion, propMinRepresentacion) {
    
    var datos = calculaEscaños(circunscripcion, candidaturas, votosCircunscripcion, propMinRepresentacion);
    
    // Dibujamos el gráfico pequeño
    var contenedor = document.getElementById("charts1");
    while(contenedor.hasChildNodes()){
        contenedor.removeChild(contenedor.firstChild);
    }
    
    var divPequeño = d3.select("#charts1");
    var dimensionesDivPequeño = divPequeño.node().getBoundingClientRect(),
        centro = {x: dimensionesDivPequeño.width/2, y: dimensionesDivPequeño.width/2},
        radioExterior = dimensionesDivPequeño.width / 2,
        radioInterior = radioExterior - 50;

    var svg = divPequeño.append("svg")
        .attr("width", dimensionesDivPequeño.width)
        .attr("height", dimensionesDivPequeño.width/2);

    var groupArcos = svg.append("g")
        .attr("transform", "translate(" + centro.x + ", " + centro.y + ")");

    var ultimoAngulo = -Math.PI/2;
    for (var j in datos) {
        var anguloFinal = ultimoAngulo + datos[j].numeroEscaños * Math.PI / circunscripcion.numeroRepresentantes;

        var arco = d3.svg.arc()
            .outerRadius(radioExterior)
            .innerRadius(radioInterior)
            .startAngle(ultimoAngulo)
            .endAngle(anguloFinal);

        var rgbColor = d3.rgb(
                hexToR(datos[j].candidatura.color),
                hexToG(datos[j].candidatura.color),
                hexToB(datos[j].candidatura.color)
        );

        groupArcos.append("path")
            .style("fill", rgbColor)
            .attr("d", arco);
    
        ultimoAngulo = anguloFinal;
    }
}

/*
 * Dibuja el grafico de sectores con los escaños de cada candidatura en cada una
 * de las circunscripciones dadas
 */
function dibujaGraficoGrande(indice, circunscripciones, candidaturas, votosCircunscripciones, propMinRepresentacion) {

    var numRepresentantes = 0;
    for(var i = 0; i <= indice; i++){
        numRepresentantes += circunscripciones[i].numeroRepresentantes;
    }
    
    var datos = new Array(candidaturas.length);
    for (var i = 0; i < candidaturas.length; i++) {
        datos[i] = { candidatura: candidaturas[i], numeroEscaños: 0 };
    }
    
    for(var i = 0 ; i <= indice; i++){
        // Calculamos los escaños en la circunscripcion actual
        var datosTemp = calculaEscaños(circunscripciones[i], candidaturas, votosCircunscripciones[i], propMinRepresentacion);
        
        // Añadimos los escaños de la circunscripcion a su respectiva candidatura
        for (var j = 0; j < datos.length; j++) {
            for (var k = 0; k < datosTemp.length; k++) {
                if (datos[j].candidatura === datosTemp[k].candidatura) {
                    datos[j].numeroEscaños += datosTemp[k].numeroEscaños;
                    k = datos.length;
                }
            }
        }
    }
    
    datos = ordenaPorEscaños(datos);
    
    // Dibujamos el gráfico grande
    var contenedor = document.getElementById("charts2");
    while(contenedor.hasChildNodes()){
        contenedor.removeChild(contenedor.firstChild);
    }
    
    var divGrande = d3.select("#charts2");
    var dimensionesDivGrande = divGrande.node().getBoundingClientRect(),
        centro = {x: dimensionesDivGrande.width/2, y: dimensionesDivGrande.width/2},
        radioExterior = dimensionesDivGrande.width / 2,
        radioInterior = radioExterior - 50;

    var svg = divGrande.append("svg")
        .attr("width", dimensionesDivGrande.width)
        .attr("height", dimensionesDivGrande.width/2);

    var groupArcos = svg.append("g")
        .attr("transform", "translate(" + centro.x + ", " + centro.y + ")");

    var ultimoAngulo = -Math.PI/2;
    for (var j in datos) {
        var anguloFinal = ultimoAngulo + datos[j].numeroEscaños * Math.PI / numRepresentantes;

        var arco = d3.svg.arc()
            .outerRadius(radioExterior)
            .innerRadius(radioInterior)
            .startAngle(ultimoAngulo)
            .endAngle(anguloFinal);

        var rgbColor = d3.rgb(
                hexToR(datos[j].candidatura.color),
                hexToG(datos[j].candidatura.color),
                hexToB(datos[j].candidatura.color)
        );

        groupArcos.append("path")
            .style("fill", rgbColor)
            .attr("d", arco);
    
        ultimoAngulo = anguloFinal;
    }
}
