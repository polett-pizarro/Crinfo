<template>
  <body class="navi">
    <div class="panel panel-default">
      <div class="panel-heading">
          <h1><i class="fa fa-bar-chart-o fa-fw"></i> ESTADISTICA POR AÑO</h1>
          <div class="dropdown pull-right">
            <button class="btn btn-info dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
              AÑOS
            </button>
            <div class="dropdown-menu" aria-labelledby="dropdownMenuButton1">
              <tr v-for="u,i in anios">
                 <button class="dropdown-item" type="button">{{u[1]}}</button>           
              </tr>
            </div>
          </div>
          <div class="dropdown pull-right">
            <button class="btn btn-info dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
              DELITOS
            </button>
            <div class="dropdown-menu" aria-labelledby="dropdownMenuButton2">
              <tr v-for="u,i in delitos">
                 <button class="dropdown-item" type="button">{{u.crimeName}}</button>           
              </tr>
              
            </div>
          </div>
      </div>
    	<svg class="grafico" width="1000" height="500"></svg>
    </div>
  </body>

</template>

<script>
  export default{
    data: function(){
      return{
        data : [
        			{month:'Enero', frequency: .123},
        			{month:'Febrero', frequency: .321},
        			{month:'Marzo', frequency: .312},
        			{month:'Abril', frequency: .213},
        			{month:'Mayo', frequency: .132},
        			{month:'Junio', frequency: .412},
        			{month:'Julio', frequency: .312},
        			{month:'Agosto', frequency: .231},
        			{month:'Septiembre', frequency: .100},
        			{month:'Octubre', frequency: .200},
        			{month:'Noviembre', frequency: .300},
        			{month:'Diciembre', frequency: .940}
        ],
        totalG:0,
      GranRespuestaCrimenAnio:[],
      RespuestaAnio:[],
      RespuestaCrimen:[],
      delitos:[],
      anios:[]
      }
    },
    methods:{
      cargar:function(data){
        var svg = d3.select(".grafico"),
        margin = {top: 20, right: 5, bottom: 30, left: 80},
        width = +svg.attr("width") - margin.left - margin.right,
        height = +svg.attr("height") - margin.top - margin.bottom;

        var x = d3.scaleBand().rangeRound([0, width]).padding(0.2),
  			y = d3.scaleLinear().rangeRound([height, 0]);

        var g = svg.append("g")
  			    .attr("transform", "translate(" + margin.left + "," + margin.top + ")");

        x.domain(data.map(function(d) { return d.month; }));
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
          .attr("x", function(d) { return x(d.month); })
          .attr("y", function(d) { return y(d.frequency); })
          .attr("width", x.bandwidth())
          .attr("height", function(d) { return height - y(d.frequency); });
      },
      crearDataAnioCrimen:function(GranRespuesta,anio,idCrimen){
           var newData=[
                {month:'Enero', frequency: 0},
                {month:'Febrero', frequency: 0},
                {month:'Marzo', frequency: 0},
                {month:'Abril', frequency: 0},
                {month:'Mayo', frequency: 0},
                {month:'Junio', frequency: 0},
                {month:'Julio', frequency: 0},
                {month:'Agosto', frequency: 0},
                {month:'Septiembre', frequency: 0},
                {month:'Octubre', frequency: 0},
                {month:'Noviembre', frequency: 0},
                {month:'Diciembre', frequency: 0}
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
                {month:'Enero', frequency: 0},
                {month:'Febrero', frequency: 0},
                {month:'Marzo', frequency: 0},
                {month:'Abril', frequency: 0},
                {month:'Mayo', frequency: 0},
                {month:'Junio', frequency: 0},
                {month:'Julio', frequency: 0},
                {month:'Agosto', frequency: 0},
                {month:'Septiembre', frequency: 0},
                {month:'Octubre', frequency: 0},
                {month:'Noviembre', frequency: 0},
                {month:'Diciembre', frequency: 0}
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
                {month:'Enero', frequency: 0},
                {month:'Febrero', frequency: 0},
                {month:'Marzo', frequency: 0},
                {month:'Abril', frequency: 0},
                {month:'Mayo', frequency: 0},
                {month:'Junio', frequency: 0},
                {month:'Julio', frequency: 0},
                {month:'Agosto', frequency: 0},
                {month:'Septiembre', frequency: 0},
                {month:'Octubre', frequency: 0},
                {month:'Noviembre', frequency: 0},
                {month:'Diciembre', frequency: 0}
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
        },
        crearDataAnioCrimen:function(GranRespuesta,anio,idCrimen){
           var newData=[
                {month:'Enero', frequency: 0},
                {month:'Febrero', frequency: 0},
                {month:'Marzo', frequency: 0},
                {month:'Abril', frequency: 0},
                {month:'Mayo', frequency: 0},
                {month:'Junio', frequency: 0},
                {month:'Julio', frequency: 0},
                {month:'Agosto', frequency: 0},
                {month:'Septiembre', frequency: 0},
                {month:'Octubre', frequency: 0},
                {month:'Noviembre', frequency: 0},
                {month:'Diciembre', frequency: 0}
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
                {month:'Enero', frequency: 0},
                {month:'Febrero', frequency: 0},
                {month:'Marzo', frequency: 0},
                {month:'Abril', frequency: 0},
                {month:'Mayo', frequency: 0},
                {month:'Junio', frequency: 0},
                {month:'Julio', frequency: 0},
                {month:'Agosto', frequency: 0},
                {month:'Septiembre', frequency: 0},
                {month:'Octubre', frequency: 0},
                {month:'Noviembre', frequency: 0},
                {month:'Diciembre', frequency: 0}
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
                {month:'Enero', frequency: 0},
                {month:'Febrero', frequency: 0},
                {month:'Marzo', frequency: 0},
                {month:'Abril', frequency: 0},
                {month:'Mayo', frequency: 0},
                {month:'Junio', frequency: 0},
                {month:'Julio', frequency: 0},
                {month:'Agosto', frequency: 0},
                {month:'Septiembre', frequency: 0},
                {month:'Octubre', frequency: 0},
                {month:'Noviembre', frequency: 0},
                {month:'Diciembre', frequency: 0}
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
      /*var totalG=0;
      var GranRespuestaCrimenAnio;
      var RespuestaAnio;
      var RespuestaCrimen;*/
      this.$http.get('http://localhost:8082/crinfo/Crime/conTweets').then(
        respuesta=>{
          this.delitos=respuesta.body;
          //location.reload();
          console.log(this.delitos);
        },error=>{
          console.log(error);
      });

      this.$http.get('http://localhost:8082/crinfo/tweet/year').then(
        respuesta=>{
          this.anios=respuesta.body;
        });
      this.$http.get('http://localhost:8082/crinfo/month').then(
        salida=>{
          let auxs=salida.body;
          for (var i = auxs.length - 1; i >= 0; i--) {
            this.totalG=this.totalG+auxs[i][0];
          }
          for (i = auxs.length - 1; i >= 0; i--) {
            this.data[i].frequency=auxs[i][0]/this.totalG;
          }
          this.cargar(this.data);
          console.log("total listo");
      });
      this.$http.get('http://localhost:8082/crinfo/months/anio/crime').then(
        salida=>{
          console.log("AAAAS");
          this.GranRespuestaCrimenAnio=salida.body;
          //this.crearDataAnioCrimen(GranRespuestaCrimenAnio,'2017',1); //cual quier año e id de crimen
        },error=>{
          console.log("si hay error");
          console.log(error);
        });
      this.$http.get('http://localhost:8082/crinfo/months/anio').then(
        salida=>{
          console.log("AAAAS");
          this.RespuestaAnio=salida.body;
          //this.crearDataAnio(RespuestaAnio,'2013'); cualquier anio
        },error=>{
          console.log("si hay error");
          console.log(error);
        });
      this.$http.get('http://localhost:8082/crinfo/month/crime').then(
        salida=>{
          console.log("AAAAS");
          this.RespuestaCrimen=salida.body;
          //this.crearDataCrimen(RespuestaCrimen,1);
        },error=>{
          console.log("si hay error");
          console.log(error);
        });

    }





  }
</script>
