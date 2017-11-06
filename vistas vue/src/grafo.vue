<template>
  <body>
    ACA VA EL GRAFO
  </body>

</template>
<script>
  export default{
  	data(){
        var TopCommune;
        var TopCrime;
        return{
            TopCommune:[],
            TopCrime:[]
        }
    },
    mounted: function(){

    this.$http.post('http://localhost:7474/db/data/transaction/commit',
            {"statements" : [ {
    "statement" : "MATCH p=()-->() RETURN p"
            }]},{
            headers: {'Authorization': 'Basic ' + btoa("neo4j" + ':' + "123456789"),
            }
    }).then(response=>{
    	console.log(response);
    	var j=JSON.parse(response.bodyText);
  		console.log(j.results);
  		console.log(j.results[0]);
  		console.log(j.results[0].data);

  		for (var i = j.results[0].data.length - 1; i >= 0; i--) {
			let dato=j.results[0].data[i];
			console.log("ROW");
			for (var p = dato.row[0].length - 1; p >= 0; p--) {
				console.log(dato.row[0][p]);
			}
			console.log("META");
			for (p = dato.meta[0].length - 1; p >= 0; p--) {
				console.log(dato.meta[0][p]);
			}
			console.log("  ");
			
  		}
  		j.results[0].data.forEach(console.log())
  		//console.log(j.results[0].data[0]);
  		//console.log(j.results[0].data[0].row[0]);
  		//console.log(j.results[0].data[0].meta[0]);
  		//console.log(response.bodyText[0]);

    });
    }
  }
</script>
