<template >
  <div >
    <h2>MAPA</h2>
     <div id="mapid" style="height: 600px; width: 100%;" ></div>
  </div>
</template>


<script>


export default{
  data(){
      var comunas=[];
      var delitos=[]
      var data=[];
      var original=[];
        return{
           comunas,
           data,
           delitos,
           original,
           max,
        }
    },
  methods:{
    filtroAnioDelito:function(original,comunas,anio,delito){
      //var baseAnioDelito={comuna:"",tweet:0, porcentaje:0, delito:""};
      //para años por delito
      var salida=[];
      var sum=0;
      for(var i=0;i<comunas.length;i++){
        var comuna=comunas[i].nameCommune;
        for(var j=0;j<original.length;j++){
          var datAnio=original[j][2];
          var datDelito=parseInt(original[j][3]);
          var datTweet=original[j][1];
          var datComuna=original[j][0];
          if(datAnio==anio && datDelito==delito && datComuna==comuna){
            var baseAnioDelito={'comuna':comuna,'tweet':datTweet, 'porcentaje':0};
            salida.push(baseAnioDelito);
            sum=sum+datTweet;
          }
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
    }
},
  mounted: function(){
      var data;
      var max;
      var p1=this.$http.get('http://localhost:8082/crinfo/Commune').then(
        respuesta=>{
          this.comunas=respuesta.body;
          //location.reload();
          console.log(this.comunas);
        },error=>{
          console.log(error);
      });
      var p2=this.$http.get('http://localhost:8082/crinfo/TuitsCommunes/crime/year').then(
        respuesta=>{
          this.original=respuesta.body;
          //location.reload();
          console.log(this.original);
        },error=>{
          console.log(error);
      });
      var p3=this.$http.get('http://localhost:8082/crinfo/Crime').then(
        respuesta=>{
          this.delitos=respuesta.body;
          //location.reload();
          console.log(this.delitos);
        },error=>{
          console.log(error);
      });


      Promise.all([p1,p2,p3]).then(values=>{
        this.data=this.filtroOriginal(this.original,this.comunas);
        console.log(this.data);
        data=this.data;
        max=this.max;

          map.createPane('labels');
          map.getPane('labels').style.zIndex = 650;
          map.getPane('labels').style.pointerEvents = 'none';

        geojson = L.geoJson(mapGeo, {
        style: style,
        onEachFeature: onEachFeature
         }).addTo(map);

        geojson.eachLayer(function (layer) {
          layer.bindPopup(layer.feature.properties.NOMBRE_DPT);
      });


      map.fitBounds(geojson.getBounds());
geojson.eachLayer(function (layer) {
          layer.bindPopup(layer.feature.properties.shape_area.toString());
      });
        map.fitBounds(geojson.getBounds());


      });


    var map = L.map('mapid').setView([-33.6058007,-70.771973], 8.5)
        L.tileLayer('https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token={accessToken}', {
          attribution: 'Map data &copy; <a href="http://openstreetmap.org">OpenStreetMap</a> contributors, <a href="http://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, Imagery © <a href="http://mapbox.com">Mapbox</a>',
          maxZoom: 11,
          minZoom: 8,
          id: 'mapbox.light',
          accessToken: 'pk.eyJ1Ijoiam9yZ2VwYXJlZGVzNzdraWNrIiwiYSI6ImNqYTJxOTJ2cjByZHYzM2xmMmJucnZ6eHYifQ.YQGAKNm1Kk2Nt-4gRuRMQA'
      }).addTo(map);
        map.doubleClickZoom.disable();
        map.setMinZoom(8.4);
        map.setMaxZoom(11);
      var mapGeo=require('./comunas_santiago.geo.json');
      var geojson=L.geoJson(mapGeo).addTo(map);

     

      /*var positron = L.tileLayer('http://{s}.basemaps.cartocdn.com/light_nolabels/{z}/{x}/{y}.png', {
        attribution: '©OpenStreetMap, ©CartoDB'
      }).addTo(map);

      var positronLabels = L.tileLayer('http://{s}.basemaps.cartocdn.com/light_only_labels/{z}/{x}/{y}.png', {
              attribution: '©OpenStreetMap, ©CartoDB',
              pane: 'labels'
      }).addTo(map);*/

      

  function getColor(comuna) {
        var d;
    console.log(comuna);
     var r;

    for (var i = data.length - 1; i >= 0; i--) {
      //console.log(i);
      if(data[i].comuna==comuna){
        d=data[i].tweet;
        console.log(d);
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
    var aux=max/51;
            
      return a;
      }

      function style(feature) {
    return {
        fillColor: getColor(feature.properties.NOMBRE_DPT),
        weight: 2,
        opacity: 1,
        color: 'white',
        dashArray: '3',
        fillOpacity: 0.7
    };
    }



      function highlightFeature(e) {
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
      }
      function resetHighlight(e) {
        geojson.resetStyle(e.target);
      }

      function onEachFeature(feature,layer){
        layer.on({
          mouseover: highlightFeature,
          mouseout: resetHighlight,
          click: zoomToFeature
          });
        }
      
      function zoomToFeature(e) {
          map.fitBounds(e.target.getBounds());
      }
      console.log(map);

      

  }
}
</script>