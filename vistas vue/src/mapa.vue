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
      var data=[];
      var anioDelito=[];
      var forAnio=[];
      var forDelito=[];
      var original=[];
        return{
           comunas,
           data,
           anioDelito,
           forAnio,
           forDelito,
           original
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
          if(datComuna=comuna){
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
    }
},
  mounted: function(){
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
      Promise.all([p1,p2]).then(values=>{


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

      map.createPane('labels');
      map.getPane('labels').style.zIndex = 650;
      map.getPane('labels').style.pointerEvents = 'none';

      /*var positron = L.tileLayer('http://{s}.basemaps.cartocdn.com/light_nolabels/{z}/{x}/{y}.png', {
        attribution: '©OpenStreetMap, ©CartoDB'
      }).addTo(map);

      var positronLabels = L.tileLayer('http://{s}.basemaps.cartocdn.com/light_only_labels/{z}/{x}/{y}.png', {
              attribution: '©OpenStreetMap, ©CartoDB',
              pane: 'labels'
      }).addTo(map);*/

      

  function getColor(d) {
    return d > 1000 ? '#800026' :
           d > 500  ? '#BD0026' :
           d > 200  ? '#E31A1C' :
           d > 100  ? '#FC4E2A' :
           d > 50   ? '#FD8D3C' :
           d > 20   ? '#FEB24C' :
           d > 10   ? '#FED976' :
                      '#FFEDA0';
      }

      function style(feature) {
    return {
        fillColor: getColor(feature.properties.dis_elec),
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

     geojson = L.geoJson(mapGeo, {
        style: style,
        onEachFeature: onEachFeature
    }).addTo(map);

     geojson.eachLayer(function (layer) {
          layer.bindPopup(layer.feature.properties.NOMBRE_DPT);
      });

      map.fitBounds(geojson.getBounds());

      this.$http.get('http://localhost:8082/crinfo/Commune').then(
        respuesta=>{
          this.comunas=respuesta.body;
          //location.reload();
          console.log(this.comunas);
        },error=>{
          console.log(error);
      });
      this.$http.get('http://localhost:8082/crinfo/TuitsCommunes/crime/year').then(
        respuesta=>{
          this.original=respuesta.body;
          //location.reload();
          console.log(this.original);
        },error=>{
          console.log(error);
      });

  }
}
</script>