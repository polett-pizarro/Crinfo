<template >
  <div >
    <br>
    <h2 class = "navi">
      <i class="fa fa-map"></i>
      MAPA

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
    </h2>
    
    <hr>
    <div class="contentMapa">
    
    <div class = "mapa" id="mapid" style="height: 600px; width: 100%;" >
      
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
          map
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
     var aux=this.max/51;
    for (var i = this.data.length - 1; i >= 0; i--) {
      //console.log(i);
      if(this.data[i].comuna==comuna){
        d=this.data[i].tweet;
        //console.log(d);
        var a=d >= aux+aux*50   ? '#FF0000' :
           d > aux+aux*49   ? '#FF0A00' :
            d > aux+aux*48 ? '#FF1400' :
           d > aux+aux*47 ? '#FF1E00' :
           d > aux+aux*46  ? '#FF2800' :
           d > aux+aux*45  ? '#FF3200' :
           d > aux+aux*44  ? '#FF3C00' :
           d > aux+aux*43  ? '#FF4600' :
           d > aux+aux*42 ? '#FF5000' :
           d > aux+aux*41  ? '#FF5A00' :
           d > aux+aux*40  ? '#FF6400' :
           d > aux+aux*39  ? '#FF6E00' :
           d > aux+aux*38   ? '#FF7800' :
           d > aux+aux*37   ? '#FF8C00' :
           d > aux+aux*36 ? '#FF9600' :
           d > aux+aux*35  ? '#FFA000' :
           d > aux+aux*34  ? '#FFAA00' :
           d > aux+aux*33  ? '#FFB400' :
           d > aux+aux*32   ? '#FFBE00' :
           d > aux+aux*31   ? '#FFC800' :
           d > aux+aux*30 ? '#FFD200' :
           d > aux+aux*29  ? '#FFDC00' :
           d > aux+aux*28  ? '#FFE600' :
           d > aux+aux*27  ? '#FFF000' :
           d > aux+aux*26   ? '#FFFA00' :
           d > aux+aux*25   ? '#FAFF00' :
           d > aux+aux*24 ? '#F0FF00' :
           d > aux+aux*23  ? '#e6ff00' :
           d > aux+aux*22  ? '#dcff00' :
           d > aux+aux*21  ? '#d2ff00' :
           d > aux+aux*20   ? '#c8ff00' :
           d > aux+aux*19   ? '#beff00' :
           d > aux+aux*18 ? '#b4ff00' :
           d > aux+aux*17  ? '#aaff00' :
           d > aux+aux*16  ? '#a0ff00' :
           d > aux+aux*15  ? '#96ff00' :
           d > aux+aux*14   ? '#8cff00' :
           d > aux+aux*13   ? '#82ff00' :
          d > aux+aux*12 ? '#78ff00' :
           d > aux+aux*11  ? '#6eff00' :
           d > aux+aux*10  ? '#64ff00' :
           d > aux+aux*9  ? '#5aff00' :
           d > aux+aux*8   ? '#50ff00' :
           d > aux+aux*7   ? '#46ff00' :
          d > aux+aux*6 ? '#3cff00' :
           d > aux+aux*5  ? '#32ff00' :
           d > aux+aux*4  ? '#28ff00' :
           d > aux+aux*3  ? '#1eff00' :
           d > aux+aux*2   ? '#14ff00' :
           d > aux+aux*1   ? '#0aff00' :
           d > aux   ? '#00ff00' :
                      '#00ff00';
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