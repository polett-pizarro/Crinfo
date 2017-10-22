<style>
	.chart div {
	  font: 10px sans-serif;
	  background-color: steelblue;
	  text-align: right;
	  padding: 3px;
	  margin: 1px;
	  color: white;
	}

	.chart rect {
	  fill: steelblue;
	}

	.chart text {
	  fill: white;
	  font: 10px sans-serif;
	  text-anchor: end;
	}


	.bar {
	  fill: steelblue;
	}

	.bar:hover {
	  fill: brown;
	}

	.axis--x path {
	  display: block;
	}

	.line {
	  fill: none;
	  stroke: steelblue;
	  stroke-width: 2px;
	}

	.grid line {
	  stroke: lightgrey;
	  stroke-opacity: 0.7;
	  shape-rendering: crispEdges;
	}

	.grid path {
	  stroke-width: 0;
	}
</style>

<template>

  <body>
    <div class="panel panel-default">
      <div class="panel-heading">
          <i class="fa fa-bar-chart-o fa-fw"></i> ESTADISTICA POR AÑO
          <div class="pull-right">
              <div class="btn-group">
                  <button type="button" class="btn btn-default btn-xs dropdown-toggle" data-toggle="dropdown">
                      AÑOS
                      <span class="caret"></span>
                  </button>
                  <ul class="dropdown-menu pull-right" role="menu">
                      <li><a href="Graph/{2017}"> 2017</a>
                      </li>
                      <li><a href="#"> 2016</a>
                      </li>
                      <li><a href="#"> 2015</a>
                      </li>
                      <li class="divider"></li>
                      <li><a href="#">Separated link</a>
                      </li>
                  </ul>
              </div>
          </div>
					<div class="pull-right">
          		<div class="btn-group">
                  <button type="button" class="btn btn-default btn-xs dropdown-toggle" data-toggle="dropdown">
                      DELITOS
                      <span class="caret"></span>
                  </button>
                  <ul class="dropdown-menu pull-right" role="menu">

                      <li><a href="Graph/{2017}"> Delito 1</a>
                      </li>
                      <li><a href="#"> Delito 2</a>
                      </li>
                      <li><a href="#"> Delito 3</a>
                      </li>
                      <li class="divider"></li>
                      <li><a href="#">Todos los delitos </a>
                      </li>
                  </ul>
              </div>
          </div>
      </div>
    	<svg width="1000" height="500"></svg>
    </div>
  </body>

</template>

<script>
  export default{
    data: function(){
      var dalitos;
      return{
        data : [
				      {letter:'Enero', frequency: 0},
        			{letter:'Febrero', frequency: 0},
        			{letter:'Marzo', frequency: 0},
        			{letter:'Abril', frequency: 0},
        			{letter:'Mayo', frequency: 0},
        			{letter:'Junio', frequency: 0},
        			{letter:'Julio', frequency: 0},
        			{letter:'Agosto', frequency: 0},
        			{letter:'Septiembre', frequency: 0},
        			{letter:'Octubre', frequency: 0},
        			{letter:'Noviembre', frequency: 0},
        			{letter:'Diciembre', frequency: 0}
        ],
        data1 : [
              {letter:'Enero', frequency: 0},
              {letter:'Febrero', frequency: 0},
              {letter:'Marzo', frequency: 0},
              {letter:'Abril', frequency: 0},
              {letter:'Mayo', frequency: 0},
              {letter:'Junio', frequency: 0},
              {letter:'Julio', frequency: 0},
              {letter:'Agosto', frequency: 0},
              {letter:'Septiembre', frequency: 0},
              {letter:'Octubre', frequency: 0},
              {letter:'Noviembre', frequency: 0},
              {letter:'Diciembre', frequency: 0}
        ],
        delitos: []
      }
    },
    methods:{
      cargar:function(data){
        var svg = d3.select("svg"),
        margin = {top: 20, right: 20, bottom: 30, left: 40},
        width = +svg.attr("width") - margin.left - margin.right,
        height = +svg.attr("height") - margin.top - margin.bottom;

        var x = d3.scaleBand().rangeRound([0, width]).padding(0.2),
  			y = d3.scaleLinear().rangeRound([height, 0]);

        var g = svg.append("g")
  			    .attr("transform", "translate(" + margin.left + "," + margin.top + ")");

        x.domain(data.map(function(d) { return d.letter; }));
        y.domain([0, 1]); // eje y en rangos de 0% hasta el 100%

        g.append("g")
			      .attr("class", "axis axis--x")
			      .attr("transform", "translate(0," + height + ")")
			      .call(d3.axisBottom(x));

			  g.append("g")
			      .attr("class", "axis axis--y")
			      .call(d3.axisLeft(y).ticks(20, "%")) // ticks indica la cantidad de indices del eje y
			    .append("text")
			      .attr("transform", "rotate(-90)")
			      .attr("y", 6)
			      .attr("dy", "0.71em")
			      .attr("text-anchor", "end")
			      .text("Frequency");

        g.selectAll(".bar")
          .data(data)
          .enter().append("rect")
          .attr("class", "bar")
          .attr("x", function(d) { return x(d.letter); })
          .attr("y", function(d) { return y(d.frequency); })
          .attr("width", x.bandwidth())
          .attr("height", function(d) { return height - y(d.frequency); });
      },
      crearDataAnioCrimen:function(GranRespuesta,anio,idCrimen){

         var newData=[
              {letter:'Enero', frequency: 0},
              {letter:'Febrero', frequency: 0},
              {letter:'Marzo', frequency: 0},
              {letter:'Abril', frequency: 0},
              {letter:'Mayo', frequency: 0},
              {letter:'Junio', frequency: 0},
              {letter:'Julio', frequency: 0},
              {letter:'Agosto', frequency: 0},
              {letter:'Septiembre', frequency: 0},
              {letter:'Octubre', frequency: 0},
              {letter:'Noviembre', frequency: 0},
              {letter:'Diciembre', frequency: 0}
        ];
        let sum=0;
        let j;
        for (var i =0; i < GranRespuesta.length; i++) {
          if(GranRespuesta[i][0]==anio){
            if(GranRespuesta[i][2]==idCrimen){
              console.log(j, GranRespuesta[i]);
              j=parseInt(GranRespuesta[i][1]);
              newData[j-1].frequency=GranRespuesta[i][3];
              sum=sum+GranRespuesta[i][3];
            }
          }
        }
        console.log("\n");
        for (var i = 0; i < newData.length; i++) {
          if(sum==0){
            break;
          }
          newData[i].frequency=newData[i].frequency / sum;
          console.log(newData[i]);
        }
        console.log(newData);
        return newData;
      },
      crearDataAnio:function(GranRespuesta,anio){
         var newData=[
              {letter:'Enero', frequency: 0},
              {letter:'Febrero', frequency: 0},
              {letter:'Marzo', frequency: 0},
              {letter:'Abril', frequency: 0},
              {letter:'Mayo', frequency: 0},
              {letter:'Junio', frequency: 0},
              {letter:'Julio', frequency: 0},
              {letter:'Agosto', frequency: 0},
              {letter:'Septiembre', frequency: 0},
              {letter:'Octubre', frequency: 0},
              {letter:'Noviembre', frequency: 0},
              {letter:'Diciembre', frequency: 0}
        ];
        let sum=0;
        let j;

        for (var i =0; i < GranRespuesta.length; i++) {
          if(GranRespuesta[i][2]==anio){
              console.log(j, GranRespuesta[i]);
              j=parseInt(GranRespuesta[i][1]);
              newData[j-1].frequency=GranRespuesta[i][0];
              sum=sum+GranRespuesta[i][0];
            }
        }

        for (var i = 0; i < newData.length; i++) {
          if(sum==0){
            break;
          }
          newData[i].frequency=newData[i].frequency / sum;
          console.log(newData[i]);
        }
        console.log(newData);
        return newData;
      },
      crearDataCrimen:function(GranRespuesta,id){
         var newData=[
              {letter:'Enero', frequency: 0},
              {letter:'Febrero', frequency: 0},
              {letter:'Marzo', frequency: 0},
              {letter:'Abril', frequency: 0},
              {letter:'Mayo', frequency: 0},
              {letter:'Junio', frequency: 0},
              {letter:'Julio', frequency: 0},
              {letter:'Agosto', frequency: 0},
              {letter:'Septiembre', frequency: 0},
              {letter:'Octubre', frequency: 0},
              {letter:'Noviembre', frequency: 0},
              {letter:'Diciembre', frequency: 0}
        ];
        let sum=0;
        let j;

        for (var i =0; i < GranRespuesta.length; i++) {
          if(GranRespuesta[i][2]==id){
              console.log(j, GranRespuesta[i]);
              j=parseInt(GranRespuesta[i][1]);
              newData[j-1].frequency=GranRespuesta[i][0];
              sum=sum+GranRespuesta[i][0];
            }
        }

        for (var i = 0; i < newData.length; i++) {
          if(sum==0){
            break;
          }
          newData[i].frequency=newData[i].frequency / sum;
          console.log(newData[i]);
        }
        console.log(newData);
        return newData;
      }



    },
    
    mounted: function(){
      //this.cargar(this.data);
			var totalG=0;
      var GranRespuestaCrimenAnio;
      var RespuestaAnio;
      var RespuestaCrimen;

      this.$http.get('http://localhost:8082/crinfo/Crime/conTweets').then(
        respuesta=>{
          this.delitos=respuesta.body;
          //location.reload();
          console.log("listo delitos");
        },error=>{
          console.log(error);
      });
      this.$http.get('http://localhost:8082/crinfo/month').then(
        salida=>{
          let auxs=salida.body;
          for (var i = auxs.length - 1; i >= 0; i--) {
            totalG=totalG+auxs[i][0];
          }
          for (i = auxs.length - 1; i >= 0; i--) {
            this.data[i].frequency=auxs[i][0]/totalG;
          }
          this.cargar(this.data);
          console.log("total listo");
      });
      this.$http.get('http://localhost:8082/crinfo/months/anio/crime').then(
        salida=>{
          console.log("AAAAS");
          GranRespuestaCrimenAnio=salida.body;
          //this.crearDataAnioCrimen(GranRespuestaCrimenAnio,'2017',1); //cual quier año e id de crimen
        },error=>{
          console.log("si hay error");
          console.log(error);
        });

      this.$http.get('http://localhost:8082/crinfo/months/anio').then(
        salida=>{
          console.log("AAAAS");
          RespuestaAnio=salida.body;
          //this.crearDataAnio(RespuestaAnio,'2013'); cualquier anio
        },error=>{
          console.log("si hay error");
          console.log(error);
        });
      this.$http.get('http://localhost:8082/crinfo/month/crime').then(
        salida=>{
          console.log("AAAAS");
          RespuestaCrimen=salida.body;
          //this.crearDataCrimen(RespuestaCrimen,1); 
        },error=>{
          console.log("si hay error");
          console.log(error);
        });
        
    }




  }
</script>
