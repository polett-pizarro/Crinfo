<template >
  <div >
    <h2>MAPA</h2>
     <div id="mapid" style="height: 600px; width: 100%;" ></div>
  </div>
</template>


<script>


export default{
  data(){
        
        return{
           
        }
    },
  methods:{
    getColor:function(d) {//cambiar a porcentaje
    return /*d > 1000 ? '#800026' : 
           d > 500  ? '#BD0026' :
           d > 200  ? '#E31A1C' :
           d > 100  ? '#FC4E2A' :
           d > 50   ? '#FD8D3C' :
           d > 20   ? '#FEB24C' :
           d > 10   ? '#FED976' :*/
                      '#FFEDA0';
    },
    style: function(feature) {
    return {
        fillColor: this.getColor(feature.properties.shape_leng),
        weight: 2,
        opacity: 1,
        color: 'white',
        dashArray: '3',
        fillOpacity: 0.7
      };
    },

    highlightFeature: function(e) {
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
    geojson.resetStyle(e.target);
  }
},
  mounted: function(){
    var mymap = L.map('mapid').setView([-33.6058007,-70.771973,9], 8.5);
        L.tileLayer('https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token={accessToken}', {
          attribution: 'Map data &copy; <a href="http://openstreetmap.org">OpenStreetMap</a> contributors, <a href="http://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, Imagery © <a href="http://mapbox.com">Mapbox</a>',
          maxZoom: 18,
          id: 'mapbox.light',
          accessToken: 'pk.eyJ1Ijoiam9yZ2VwYXJlZGVzNzdraWNrIiwiYSI6ImNqYTJxOTJ2cjByZHYzM2xmMmJucnZ6eHYifQ.YQGAKNm1Kk2Nt-4gRuRMQA'
      }).addTo(mymap);
      var mapGeo=require('./comunas_santiago.geo.json');
      L.geoJson(mapGeo).addTo(mymap);
      L.geoJson(mapGeo, {style: this.style}).addTo(mymap);
  }
}
</script>