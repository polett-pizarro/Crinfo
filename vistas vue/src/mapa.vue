<template >
  <div >
    <div>
      <br>
      <h2 class = "navi">
        <i class="fa fa-map"></i>
        MAPA
      </h2>
    </div>
    <hr>
    <h4 ALIGN="justify"> En el mapa de calor se puede observar cuanto hablan los tuiteros, en cada communa sobre los delitos, representando con colores verdes los porcentajes mas bajos, con amarillos los intermedios y rojos los las altos: </h4>
    <br>
    <div>
      <div class="pull-right row" style="margin-right:0px">
        <div class="dropdown">
          <button class="btn btn-info dropdown-toggle" type="button" id="menuAnio" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
            {{this.mAnio}}
          </button>
          <div class="dropdown-menu" aria-labelledby="dropdownMenuButton1"style="margin">
            <tr v-for="u,i in anios">
              <button class="dropdown-item" type="button" v-on:click="cambiar(mDelito,u[1])">{{u[1]}}</button>
            </tr>
            <div class="dropdown-divider"></div>
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
            <div class="dropdown-divider"></div>
            <button class="dropdown-item" type="button" v-on:click="cambiar('DELITOS',mAnio)">DELITOS
            </button>
          </div>
        </div>
      </div>
    </div>
    <table style = "width: 79%; border-collapse: collapse; ">
      <tr>
        <td style="border-radius: 20px 0px 0px 20px; background: #FF0000; text-align: center; padding: 11px;"> >=50% </td>
        <td style="background: #FF4900; text-align: center; padding: 11px;"> 45% </td>
        <td style="background: #FF7400; text-align: center; padding: 11px;"> 40% </td>
        <td style="background: #FF9E00; text-align: center; padding: 11px;"> 35% </td>
        <td style="background: #FFCD00; text-align: center; padding: 11px;"> 30% </td>
        <td style="background: #FFF700; text-align: center; padding: 11px;"> 25% </td>
        <td style="background: #D8FF00; text-align: center; padding: 11px;"> 20% </td>
        <td style="background: #AEFF00; text-align: center; padding: 11px;"> 15% </td>
        <td style="background: #80FF00; text-align: center; padding: 11px;"> 10% </td>
        <td style="background: #51FF00; text-align: center;  padding: 11px;"> 5% </td>
        <td style=" border-radius: 0px 20px 20px 0px; background: #2AFF00; text-align: center;  padding: 11px;"> >=1% </td>
      </tr>
    </table>
    <br>
    <div>
      <div class="contentMapa">
        <div class = "mapa" id="mapid" style="height: 600px; width: 100%;" >
        </div>
      </div>
    </div>
  </div>
  
</template>


<script>


export default{
  data(){
      var comunas=[];
      var delitos=[]
      var data=[];
      var original=[];
      var max;
      var mDelito='DELITOS';
      var mAnio='AÑOS';
      var anios=[];
      var geojson;
      var map;
      var var2=0;
        return{
           comunas,
           data,
           delitos,
           original,
           max,
           mDelito:'DELITOS',
          mAnio:'AÑOS',
          anios,
          geojson,
          map,
          var2
        }
    },
  methods:{
    maxSinSan:function(data){
      for (var i = data.length - 1; i >= 0; i--) {
        if(data[i].comuna!="Santiago"){
          if(data[i].porcentaje>this.var2){
            this.var2=data[i].porcentaje;
          }
        }
      }
      console.log(this.var2);
    },
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
            this.data=this.filtroOriginal(this.original,this.comunas);
            this.recargar(this.data);
            //console.log("cargar el original");

            //cargar original
          }
          else if (this.mDelito=='DELITOS'){
            //significa que cambio el año
            this.data=this.filtroAnio(this.original,this.comunas,this.mAnio);
            this.recargar(this.data);
          }
          else if (this.mAnio=='AÑOS'){
           //significa que cambio de delito

            for (var i = this.delitos.length - 1; i >= 0; i--) {
              if(this.delitos[i].crimeName==delito){
                var id=this.delitos[i].idCrime;
                break;
              }
            }
            this.data=this.filtroDelito(this.original,this.comunas,id);
            this.recargar(this.data);
          }
          else{

            for (var i = this.delitos.length - 1; i >= 0; i--) {
              if(this.delitos[i].crimeName==delito){
                var id=this.delitos[i].idCrime;
                break;
              }
            }
            this.data=this.filtroAnioDelito(this.original,this.comunas,this.mAnio,id);
            this.recargar(this.data);
          }

          
    }
    ,

    filtroAnioDelito:function(original,comunas,anio,delito){
      //var baseAnioDelito={comuna:"",tweet:0, porcentaje:0, delito:""};
      //para años por delito
      var salida=[];
      var sum=0;
      for(var i=0;i<comunas.length;i++){
        var comuna=comunas[i].nameCommune;
        var push=0;
        for(var j=0;j<original.length;j++){
          var datAnio=original[j][2];
          var datDelito=parseInt(original[j][3]);
          var datTweet=original[j][1];
          var datComuna=original[j][0];
          if(datAnio==anio && datDelito==delito && datComuna==comuna){
            var baseAnioDelito={'comuna':comuna,'tweet':datTweet, 'porcentaje':0};
            salida.push(baseAnioDelito);
            sum=sum+datTweet;
            push=1;
          }
        }
        if(push==0){
          var baseAnioDelito={'comuna':comuna,'tweet':0, 'porcentaje':0};
            salida.push(baseAnioDelito);
        }
      }

      for (var i = salida.length - 1; i >= 0; i--) {
        if (sum ==0){
          break;
        }

        salida[i].porcentaje=salida[i].tweet/sum;
      }
      this.maxSinSan(salida);
      return salida;
    },
    filtroAnio:function(original,comunas,anio){
      //var baseAnioDelito={comuna:"",tweet:0, porcentaje:0, delito:""};
      //para años por delito
      var salida=[];
      var sum=0;
      for(var i=0;i<comunas.length;i++){
        var comuna=comunas[i].nameCommune;
        var sumtweet=0;
        for(var j=0;j<original.length;j++){
          var datAnio=original[j][2];
          var datDelito=parseInt(original[j][3]);
          var datTweet=original[j][1];
          var datComuna=original[j][0];
          if(datAnio==anio && datComuna==comuna){
            sumtweet=sumtweet+datTweet;
            sum=sum+datTweet;
          }

        }
        var baseAnio={'comuna':comuna,'tweet':sumtweet, 'porcentaje':0};
        salida.push(baseAnio);
      }

      for (var i = salida.length - 1; i >= 0; i--) {
        if (sum ==0){
          break;
        }

        salida[i].porcentaje=salida[i].tweet/sum;
      }
      this.maxSinSan(salida);
      return salida;
    },
    filtroDelito:function(original,comunas,delito){
      //var baseAnioDelito={comuna:"",tweet:0, porcentaje:0, delito:""};
      //para años por delito
      var salida=[];
      var sum=0;
      for(var i=0;i<comunas.length;i++){
        var comuna=comunas[i].nameCommune;
        var sumtweet=0;
        for(var j=0;j<original.length;j++){
          var datAnio=original[j][2];
          var datDelito=parseInt(original[j][3]);
          var datTweet=original[j][1];
          var datComuna=original[j][0];
          if(datComuna==comuna && datDelito==delito){
            sumtweet=sumtweet+datTweet;
            sum=sum+datTweet;
          }

        }
        var baseDelito={'comuna':comuna,'tweet':sumtweet, 'porcentaje':0};
        salida.push(baseDelito);
      }

      for (var i = salida.length - 1; i >= 0; i--) {
        if (sum ==0){
          break;
        }

        salida[i].porcentaje=salida[i].tweet/sum;
      }
      this.maxSinSan(salida);
      return salida;  
    },
    filtroOriginal:function(original,comunas){
      var salida=[];
      var sum=0;
      for(var i=0;i<comunas.length;i++){
        var comuna=comunas[i].nameCommune;
        var sumtweet=0;
        for(var j=0;j<original.length;j++){
          var datAnio=original[j][2];
          var datDelito=parseInt(original[j][3]);
          var datTweet=original[j][1];
          var datComuna=original[j][0];
          if(datComuna==comuna){
            sumtweet=sumtweet+datTweet;
            sum=sum+datTweet;
          }

        }
        var baseDelito={'comuna':comuna,'tweet':sumtweet, 'porcentaje':0};
        salida.push(baseDelito);
      }

      for (var i = salida.length - 1; i >= 0; i--) {
        if (sum ==0){
          break;
        }
        salida[i].porcentaje=salida[i].tweet/sum;
      }
      this.max=sum;
      this.maxSinSan(salida);
      return salida; 
    },
    recargar:function(data){
      var mapGeo=require('./comunas_santiago.geo.json');
      
      this.map.removeLayer(this.geojson);

      this.geojson = L.geoJson(mapGeo, {
        style: this.style,
        onEachFeature: this.onEachFeature
         });
        this.geojson.addTo(this.map);

        for (var i = this.data.length - 1; i >= 0; i--) {
            console.log(this.data[i].comuna,this.data[i].tweet,this.data[i].porcentaje);
          }
      this.map.fitBounds(this.geojson.getBounds());
      this.geojson.eachLayer(function (layer) {
          layer.bindPopup(cargarText(layer.feature.properties.NOMBRE_DPT));
      });
        this.map.fitBounds(this.geojson.getBounds());
    
      function cargarText(comuna){
        var text="";
        for (var i = data.length - 1; i >= 0; i--) {
          if(data[i].comuna==comuna){
            //console.log("igualdad", comuna);
            text=comuna + "<br> Delitos: " + data[i].tweet.toString() + "<br> Porcentaje: " + Math.round(data[i].porcentaje *100).toString() + "%";
            return text;
          }
          //console.log(data[i].comuna,"!=",comuna);
        }
        //console.log("NO IGUAL", comuna);
        return text;
      }
    },
    getColor:function(comuna) {
        var d;
    //console.log(comuna);
     var r;
     var aux=1;
     var aux2=this.var2/52;
    for (var i = this.data.length - 1; i >= 0; i--) {
      //console.log(i);
      if(this.data[i].comuna==comuna && comuna=="Santiago"){
        d=this.data[i].porcentaje;
        //console.log(d);
        var a=d >= 0.5   ? '#FF0000' :
           //d > aux*0.495   ? '#FF0800' :
           d > aux*0.49  ? '#FF0C00' :
           //d > aux*0.485   ? '#FF0F00' :
           d > aux*0.48   ? '#FF1B00' :
           //d > aux*0.475   ? '#FF2300' :
           d > aux*0.47   ? '#FF2A00' :
           //d > aux*0.465   ? '#FF3200' :
           d > aux*0.46 ? '#FF3A00' :
           //d > aux*0.455   ? '#FF4600' :
           d > aux*0.45  ? '#FF4900' :
           //d > aux*0.445   ? '#FF4D00' :
           d > aux*0.44  ? '#FF5100' :
           //d > aux*0.435   ? '#FF5500' :
           d > aux*0.43  ? '#FF5900' :
           //d > aux*0.425   ? '#FF5D00' :
           d > aux*0.42   ? '#FF6100' :
           //d > aux*0.415   ? '#FF6400' :
           d > aux*0.41   ? '#FF6C00' :
           //d > aux*0.405   ? '#FF7000' :
           d > aux*0.4  ? '#FF7400' :
           //d > aux*0.395   ? '#FF7800' :
           d > aux*0.39  ? '#FF7C00' :
           //d > aux*0.385   ? '#FF8000' :
           d > aux*0.38   ? '#FF8300' :
           //d > aux*0.375   ? '#FF8700' :
           d > aux*0.37   ? '#FF8B00' :
           //d > aux*0.365   ? '#FF8F00' :
           d > aux*0.36 ? '#FF9300' :
           //d > aux*0.355   ? '#FF9B00' :
           d > aux*0.35  ? '#FF9E00' :
           //d > aux*0.345   ? '#FFA200' :
           d > aux*0.34  ? '#FFA600' :
           //d > aux*0.335   ? '#FFAA00' :
           d > aux*0.33  ? '#FFAE00' :
           //d > aux*0.325   ? '#FFB200' :
           d > aux*0.32   ? '#FFB600' :
           //d > aux*0.315   ? '#FFBD00' :
           d > aux*0.31   ? '#FFC500' :
           //d > aux*0.305   ? '#FFC900' :
           d > aux*0.3  ? '#FFCD00' :
           //d > aux*0.295   ? '#FFD100' :
           d > aux*0.29  ? '#FFD500' :
           //d > aux*0.285   ? '#FFD800' :
           d > aux*0.28   ? '#FFDC00' :
           //d > aux*0.275   ? '#FFE000' :
           d > aux*0.27   ? '#FFE400' :
           //d > aux*0.265   ? '#FFE800' :
           d > aux*0.26 ? '#FFF000' :
           //d > aux*0.255   ? '#FFF300' :
           d > aux*0.25  ? '#FFF700' :
           //d > aux*0.245   ? '#FFFB00' :
           d > aux*0.24  ? '#FFFF00' :
           //d > aux*0.235   ? '#FBFF00' :
           d > aux*0.23  ? '#F7FF00' :
           //d > aux*0.225   ? '#F3FF00' :
           d > aux*0.22   ? '#ECFF00' :
           //d > aux*0.215   ? '#E4FF00' :
           d > aux*0.21   ? '#E0FF00' :
           //d > aux*0.205   ? '#DCFF00' :
           d > aux*0.2   ? '#D8FF00' :
           //d > aux*0.195  ? '#D4FF00' :
           d > aux*0.19  ? '#D1FF00' :
           //d > aux*0.185   ? '#CDFF00' :
           d > aux*0.18   ? '#C9FF00' :
           //d > aux*0.175 ? '#C5FF00' :
           d > aux*0.17 ? '#C1FF00' :
           //d > aux*0.165  ? '#B9FF00' :
           d > aux*0.16  ? '#B6FF00' :
           //d > aux*0.155   ? '#B2FF00' :
           d > aux*0.15   ? '#AEFF00' :
           //d > aux*0.145 ? '#AAFF00' :
           d > aux*0.14  ? '#A6FF00' :
           //d > aux*0.135  ? '#A2FF00' :
           d > aux*0.13  ? '#9EFF00' :
           //d > aux*0.125   ? '#97FF00' :
           d > aux*0.12   ? '#8FFF00' :
           //d > aux*0.115 ? '#8BFF00' :
           d > aux*0.11   ? '#87FF00' :
           //d > aux*0.105  ? '#83FF00' :
           d > aux*0.1  ? '#80FF00' :
           //d > aux*0.095   ? '#7CFF00' :
           d > aux*0.09  ? '#78FF00' :
           //d > aux*0.085   ? '#74FF00' :
           d > aux*0.08   ? '#70FF00' :
           //d > aux*0.075   ? '#6CFF00' :
           d > aux*0.07   ? '#64FF00' :
           //d > aux*0.065   ? '#5DFF00' :
           d > aux*0.06 ? '#59FF00' :
           //d > aux*0.055   ? '#55FF00' :
           d > aux*0.05  ? '#51FF00' :
           //d > aux*0.045   ? '#4DFF00' :
           d > aux*0.04  ? '#49FF00' :
           //d > aux*0.035   ? '#42FF00' :
           d > aux*0.03  ? '#3AFF00' :
           //d > aux*0.025   ? '#36FF00' :
           d > aux*0.02   ? '#32FF00' :
           //d > aux*0.015   ? '#2EFF00' :
           d > aux*0.01   ? '#2AFF00' :
           d > aux   ? '#00FF00' :
                      '#00FF00';
          console.log(a);
        break;
      }
      else if(this.data[i].comuna==comuna && comuna!="Santiago"){
        console.log("AQUI")
        d=this.data[i].porcentaje;
        console.log(this.var2);
        var a=d >= aux2*53   ? '#FF0000' :
           //d > aux*0.495   ? '#FF0800' :
           //d > aux2* 49  ? '#FF0C00' :
           //d > aux2* 485   ? '#FF0F00' :
           //d > aux2* 48   ? '#FF1B00' :
           //d > aux2* 475   ? '#FF2300' :
           //d > aux2* 47   ? '#FF2A00' :
           //d > aux2* 465   ? '#FF3200' :
           d > aux2* 46 ? '#FF3A00' :
           //d > aux2* 455   ? '#FF4600' :
           d > aux2* 45  ? '#FF4900' :
           //d > aux2* 445   ? '#FF4D00' :
           d > aux2* 44  ? '#FF5100' :
           //d > aux2* 435   ? '#FF5500' :
           d > aux2* 43  ? '#FF5900' :
           //d > aux2* 425   ? '#FF5D00' :
           d > aux2* 42   ? '#FF6100' :
           //d > aux2* 415   ? '#FF6400' :
           d > aux2* 41   ? '#FF6C00' :
           //d > aux2* 405   ? '#FF7000' :
           d > aux2* 40  ? '#FF7400' :
           //d > aux2* 395   ? '#FF7800' :
           d > aux2* 39  ? '#FF7C00' :
           //d > aux2* 385   ? '#FF8000' :
           d > aux2* 38   ? '#FF8300' :
           //d > aux2* 375   ? '#FF8700' :
           d > aux2* 37   ? '#FF8B00' :
           //d > aux2* 365   ? '#FF8F00' :
           d > aux2* 36 ? '#FF9300' :
           //d > aux2* 355   ? '#FF9B00' :
           d > aux2* 35  ? '#FF9E00' :
           //d > aux2* 345   ? '#FFA200' :
           d > aux2* 34  ? '#FFA600' :
           //d > aux2* 335   ? '#FFAA00' :
           d > aux2* 33  ? '#FFAE00' :
           //d > aux2* 325   ? '#FFB200' :
           d > aux2* 32   ? '#FFB600' :
           //d > aux2* 315   ? '#FFBD00' :
           d > aux2* 31   ? '#FFC500' :
           //d > aux2* 305   ? '#FFC900' :
           d > aux2* 30  ? '#FFCD00' :
           //d > aux2* 295   ? '#FFD100' :
           d > aux2* 29  ? '#FFD500' :
           //d > aux2* 285   ? '#FFD800' :
           d > aux2* 28   ? '#FFDC00' :
           //d > aux2* 275   ? '#FFE000' :
           d > aux2* 27   ? '#FFE400' :
           //d > aux2* 265   ? '#FFE800' :
           d > aux2* 26 ? '#FFF000' :
           //d > aux2* 255   ? '#FFF300' :
           d > aux2* 25  ? '#FFF700' :
           //d > aux2* 245   ? '#FFFB00' :
           d > aux2* 24  ? '#FFFF00' :
           //d > aux2* 235   ? '#FBFF00' :
           d > aux2* 23  ? '#F7FF00' :
           //d > aux2* 225   ? '#F3FF00' :
           d > aux2* 22   ? '#ECFF00' :
           //d > aux2* 215   ? '#E4FF00' :
           d > aux2* 21   ? '#E0FF00' :
           //d > aux2* 205   ? '#DCFF00' :
           d > aux2* 20   ? '#D8FF00' :
           //d > aux2* 195  ? '#D4FF00' :
           d > aux2* 19  ? '#D1FF00' :
           //d > aux2* 185   ? '#CDFF00' :
           d > aux2* 18   ? '#C9FF00' :
           //d > aux2* 175 ? '#C5FF00' :
           d > aux2* 17 ? '#C1FF00' :
           //d > aux2* 165  ? '#B9FF00' :
           d > aux2* 16  ? '#B6FF00' :
           //d > aux2* 155   ? '#B2FF00' :
           d > aux2* 15   ? '#AEFF00' :
           //d > aux2* 145 ? '#AAFF00' :
           d > aux2* 14  ? '#A6FF00' :
           //d > aux2* 135  ? '#A2FF00' :
           d > aux2* 13  ? '#9EFF00' :
           //d > aux2* 125   ? '#97FF00' :
           d > aux2* 12   ? '#8FFF00' :
           //d > aux2* 115 ? '#8BFF00' :
           d > aux2* 11   ? '#87FF00' :
           //d > aux2* 105  ? '#83FF00' :
           d > aux2* 10 ? '#80FF00' :
           //d > aux2* 095   ? '#7CFF00' :
           d > aux2* 9  ? '#78FF00' :
           //d > aux2* 085   ? '#74FF00' :
           d > aux2* 8   ? '#70FF00' :
           //d > aux2* 075   ? '#6CFF00' :
           d > aux2* 7   ? '#64FF00' :
           //d > aux2* 065   ? '#5DFF00' :
           d > aux2* 6 ? '#59FF00' :
           //d > aux2* 055   ? '#55FF00' :
           d > aux2* 5  ? '#51FF00' :
           //d > aux2* 045   ? '#4DFF00' :
           d > aux2* 4  ? '#49FF00' :
           //d > aux2* 035   ? '#42FF00' :
           d > aux2* 3  ? '#3AFF00' :
           //d > aux2* 025   ? '#36FF00' :
           d > aux2* 2   ? '#32FF00' :
           //d > aux2* 015   ? '#2EFF00' :
           d > aux2* 1   ? '#2AFF00' :
           d > aux2   ? '#00FF00' :
                      '#00FF00';
          console.log(a);
        break;
      }
    }        
    return a;
    }
    ,
    style:function(feature) {
    return {
        fillColor: this.getColor(feature.properties.NOMBRE_DPT),
        weight: 2,
        opacity: 1,
        color: 'white',
        dashArray: '3',
        fillOpacity: 0.7
    };
    },
      highlightFeature:function(e) {
        var layer = e.target;

        layer.setStyle({
            weight: 5,
            color: '#666',
            dashArray: '',
            fillOpacity: 0.7
        });

        if (!L.Browser.ie && !L.Browser.opera && !L.Browser.edge) {
            layer.bringToFront();
        }
      },

      resetHighlight:function(e) {
        this.geojson.resetStyle(e.target);
      },

      onEachFeature:function(feature,layer){
        layer.on({
          mouseover: this.highlightFeature,
          mouseout: this.resetHighlight,
          click: this.zoomToFeature
          });
        },
      
      zoomToFeature:function(e) {
          this.map.fitBounds(e.target.getBounds());
      }

},
  mounted: function(){
      var data;
      var mapGeo=require('./comunas_santiago.geo.json');
      
      this.$http.get('http://localhost:8082/crinfo/tweet/year').then(
        respuesta=>{
          this.anios=respuesta.body;
        });
      var p1=this.$http.get('http://localhost:8082/crinfo/Commune').then(
        respuesta=>{
          this.comunas=respuesta.body;
          //location.reload();
          //console.log(this.comunas);
        },error=>{
          console.log(error);
      });
      var p2=this.$http.get('http://localhost:8082/crinfo/TuitsCommunes/crime/year').then(
        respuesta=>{
          this.original=respuesta.body;
          //location.reload();
          //console.log(this.original);
        },error=>{
          console.log(error);
      });
      var p3=this.$http.get('http://localhost:8082/crinfo/Crime').then(
        respuesta=>{
          this.delitos=respuesta.body;
          //location.reload();
          //console.log(this.delitos);
        },error=>{
          console.log(error);
      });


      Promise.all([p1,p2,p3]).then(values=>{
        this.data=this.filtroOriginal(this.original,this.comunas);
        data=this.data;
        //console.log(this.data);

          this.map.createPane('labels');
          this.map.getPane('labels').style.zIndex = 650;
          this.map.getPane('labels').style.pointerEvents = 'none';

        this.geojson = L.geoJson(mapGeo, {
        style: this.style,
        onEachFeature: this.onEachFeature
         });
        this.geojson.addTo(this.map);
    

      this.map.fitBounds(this.geojson.getBounds());
this.geojson.eachLayer(function (layer) {
          layer.bindPopup(cargarText(layer.feature.properties.NOMBRE_DPT));
      });
        this.map.fitBounds(this.geojson.getBounds());


      });


    this.map= L.map('mapid').setView([-33.6058007,-70.771973], 8.5);
        L.tileLayer('https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token={accessToken}', {
          attribution: 'Map data &copy; <a href="http://openstreetmap.org">OpenStreetMap</a> contributors, <a href="http://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, Imagery © <a href="http://mapbox.com">Mapbox</a>',
          maxZoom: 11,
          minZoom: 8,
          id: 'mapbox.light',
          accessToken: 'pk.eyJ1Ijoiam9yZ2VwYXJlZGVzNzdraWNrIiwiYSI6ImNqYTJxOTJ2cjByZHYzM2xmMmJucnZ6eHYifQ.YQGAKNm1Kk2Nt-4gRuRMQA'
      }).addTo(this.map);
        this.map.doubleClickZoom.disable();
        this.map.setMinZoom(8.4);
        this.map.setMaxZoom(11);
      
      //var geojson=L.geoJson(this.mapGeo).addTo(this.map);
      function cargarText(comuna){
      var text="";
      for (var i = data.length - 1; i >= 0; i--) {
        if(data[i].comuna==comuna){
          text=comuna + "<br> Delitos: " + data[i].tweet.toString() + "<br> Porcentaje: " + Math.round(data[i].porcentaje *100).toString() + "%";
        }
      }
      return text;
    }
      
      

  }
}
</script>