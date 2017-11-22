<template>
  <body class="navi">
    <div class="panel panel-default">
      <div class="panel-heading">
        <h2><i class="fa fa-bar-chart-o fa-fw"></i> ESTADISTICA POR AÑO</h2>
        <hr>
        <div class="pull-right row" style="margin-right:40px">
          <div class="dropdown">
            <button class="btn btn-info dropdown-toggle" type="button" id="menuAnio" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
              {{this.mAnio}}
            </button>
            <div class="dropdown-menu" aria-labelledby="dropdownMenuButton1"style="margin">
              <tr v-for="u,i in anios">
                <button class="dropdown-item" type="button" v-on:click="cambiar(mDelito,u[1])">{{u[1]}}</button>
              </tr>
              <div class="dropdown-divider">
              </div>
              <button class="dropdown-item" type="button" v-on:click="cambiar(mDelito,'AÑOS')">AÑOS</button>
            </div>
          </div>
          <div class="dropdown">
            <button class="btn btn-info dropdown-toggle" type="button" id="menuDelito" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
              {{this.mDelito}}
            </button>
            <div class="dropdown-menu" aria-labelledby="dropdownMenuButton2">
              <tr v-for="u,i in delitos">
                <button class="dropdown-item" type="button" id="i" v-on:click="cambiar(u.crimeName,mAnio)">{{u.crimeName}}</button>
              </tr>
              <div class="dropdown-divider">
              </div>
              <button class="dropdown-item" type="button" v-on:click="cambiar('DELITOS',mAnio)">DELITOS
              </button>
            </div>
          </div>
        </div>
      </div>
    	<svg class="grafico" width="1000" height="500" ></svg>
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
      anios:[],
      mDelito:'DELITOS',
      mAnio:'AÑOS',
      original:[]
      }
    },
    methods:{
        cambiar:function(delito,anio){
          if (this.mDelito==delito && this.mAnio==anio){
            console.log("no se hizo nada");
            return ;
          }

          this.mDelito=delito;
          this.mAnio=anio;
          var datos;
          var id;
          if(this.mDelito=='DELITOS' && this.mAnio=='AÑOS'){
            this.recargar(this.original);
            //console.log("cargar el original");

            //cargar original
          }
          else if (this.mDelito=='DELITOS'){
            //significa que cambio el año
            datos=this.crearDataAnio(this.RespuestaAnio,this.mAnio);
            this.recargar(datos);
          }
          else if (this.mAnio=='AÑOS'){
           //significa que cambio de delito

            for (var i = this.delitos.length - 1; i >= 0; i--) {
              if(this.delitos[i].crimeName==delito){
                var id=this.delitos[i].idCrime;
                break;
              }
            }
            datos=this.crearDataCrimen(this.RespuestaCrimen,id);
            this.recargar(datos);
          }
          else{

            for (var i = this.delitos.length - 1; i >= 0; i--) {
              if(this.delitos[i].crimeName==delito){
                var id=this.delitos[i].idCrime;
                break;
              }
            }
            datos=this.crearDataAnioCrimen(this.GranRespuestaCrimenAnio,this.mAnio,id);
            this.recargar(datos);
          }


        },
        cargar:function(data){
        var svg = d3.select(".grafico"),
        margin = {top: 20, right: 5, bottom: 30, left: 80},
        width = +svg.attr("width") - margin.left - margin.right,
        height = +svg.attr("height") - margin.top - margin.bottom;

        var x = d3.scaleBand().rangeRound([0, width]).padding(0.2),
  			y = d3.scaleLinear().rangeRound([height, 0]);

        var g = svg.append("g")
            .attr("class", "g")
  			    .attr("transform", "translate(" + margin.left + "," + margin.top + ")");

        x.domain(data.map(function(d) { return d.month; }));
        y.domain([0, d3.max(data, function(d) { return d.frequency; })]);//([0, 1]); // eje y en rangos de 0% hasta el 100%

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

        g.append("text")
            .attr("transform", "rotate(-90)")
            .attr("y", 0 - margin.left)
            .attr("x",0 - (height / 2))
            .attr("dy", "1em")
            .style("text-anchor", "middle")
            .text("% Registrado");

        g.selectAll(".bar")
          .data(data)
          .enter().append("rect")
          .attr("class", "bar")
          .attr("x", function(d) { return x(d.month); })
          .attr("y", function(d) { return y(d.frequency); })
          .attr("width", x.bandwidth())
          .attr("height", function(d) { return height - y(d.frequency); });
      },
      recargar:function(data){
        var svg = d3.select(".grafico"),
        margin = {top: 20, right: 5, bottom: 30, left: 80},
        width = +svg.attr("width") - margin.left - margin.right,
        height = +svg.attr("height") - margin.top - margin.bottom;

        var x = d3.scaleBand().rangeRound([0, width]).padding(0.2),
  			y = d3.scaleLinear().rangeRound([height, 0]);

        // Scale the range of the data again
        x.domain(data.map(function(d) { return d.month; }));
        y.domain([0, d3.max(data, function(d) { return d.frequency; })]);//([0, 1]); // eje y en rangos de 0% hasta el 100%


        g = d3.select(".g");
        var bar = g.selectAll(".bar").remove().exit().data(data);
        // Select the section we want to apply our changes to
        var g = d3.select(".g").transition();

        // Make the changes
        g.select(".axis.axis--x") // change the x axis
            .duration(750)
            .call(d3.axisBottom(x));

        g.select(".axis.axis--y") // change the y axis
            .duration(750)
            .call(d3.axisLeft(y).ticks(20, "%"));

        bar.enter()
          .append("rect")
          .attr("class", "bar")
          .attr("x", function(d) { return x(d.month); })
          .transition().duration(750)
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
          this.original=this.data;
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
