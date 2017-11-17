<template >
  <div >
    <h2>MAPA</h2>
     <div id="mapid" style="height: 600px; width: 100%;" ></div>
  </div>
</template>


<script>


export default{
  data(){
        var geoJson;
        var info = L.control();
        return{
           geoJson,
           info
        }
    },
  methods:{
    
},
  mounted: function(){
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
      //L.geoJson(mapGeo, {style: this.style}).addTo(map);
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



  }
}
</script>