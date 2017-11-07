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
    methods:{
      obtenerNodos: function(){
      	var nodos={"nodes":[],"links":[]};
		var primerConsulta = this.$http.post('http://localhost:7474/db/data/transaction/commit',
	            {"statements" : [ {
	    "statement" : "MATCH (n) RETURN n"
	            }]},{
	            headers: {'Authorization': 'Basic ' + btoa("neo4j" + ':' + "123456789"),
	            }
	    }).
		then(response=>{
		    	//console.log(response);
		    	var j=JSON.parse(response.bodyText);
		  		//console.log(j.results);
		  		//console.log(j.results[0]);
		  		//console.log(j.results[0].data);
		  		let aux=1;
		  		for (let i = j.results[0].data.length - 1; i >= 0; i--) {

					let dato=j.results[0].data[i].row[0];
					console.log(dato);
					//console.log(dato.likes);
					let nodo={"name":dato.name,"group":0};
					if(dato.likes==null){
						nodo.group=aux;
						aux++;
					}
					nodos.nodes.push(nodo);
					//console.log(dato.row);
					//console.log(dato.meta);
		      }
		      //console.log(nodos);
			});
		var segundaConsulta = this.$http.post('http://localhost:7474/db/data/transaction/commit',
	            {"statements" : [ {
	    "statement" : "MATCH p=()-->() RETURN p"
	            }]},{
	            headers: {'Authorization': 'Basic ' + btoa("neo4j" + ':' + "123456789"),
	            }
	    }).
		then(response=>{
		    	//console.log(response);
		    	var j2=JSON.parse(response.bodyText);
		  		//console.log(j2.results);
		  		//console.log(j2.results[0]);
		  		//console.log(j2.results[0].data);
		  		let aux=1;
		  		for (let k =0 ; k <j2.results[0].data.length; k++) {

					let dato=j2.results[0].data[k].row[0];
					/*for (var p = 0; p<dato.row[0].length ; p++) {
						console.log(dato.row[0][p]);
					}*/
					/*for (p =0; p < dato.meta[0].length; p++) {
						console.log(dato.meta[0][p]);
					}*/
					let link={"source":dato[0].name,"target":dato[2].name,"value":1};
					nodos.links.push(link);
					console.log(link);
		      }
		      //console.log(nodos);
			});
		Promise.all([primerConsulta,segundaConsulta]).
			then(values=>{
				console.log(nodos);
				return nodos;
				
			});
		
  		//return nodos;
  		}

  	},
    mounted: function(){
    	var nodos={"nodes":[],"links":[]};
		var primerConsulta = this.$http.post('http://localhost:7474/db/data/transaction/commit',
	            {"statements" : [ {
	    "statement" : "MATCH (n) RETURN n"
	            }]},{
	            headers: {'Authorization': 'Basic ' + btoa("neo4j" + ':' + "123456789"),
	            }
	    }).
		then(response=>{
		    	//console.log(response);
		    	var j=JSON.parse(response.bodyText);
		  		//console.log(j.results);
		  		//console.log(j.results[0]);
		  		//console.log(j.results[0].data);
		  		let aux=1;
		  		for (let i = j.results[0].data.length - 1; i >= 0; i--) {

					let dato=j.results[0].data[i].row[0];
					console.log(dato);
					//console.log(dato.likes);
					let nodo={"name":dato.name,"group":0};
					if(dato.likes==null){
						nodo.group=aux;
						aux++;
					}
					nodos.nodes.push(nodo);
					//console.log(dato.row);
					//console.log(dato.meta);
		      }
		      //console.log(nodos);
			});
		var segundaConsulta = this.$http.post('http://localhost:7474/db/data/transaction/commit',
	            {"statements" : [ {
	    "statement" : "MATCH p=()-->() RETURN p"
	            }]},{
	            headers: {'Authorization': 'Basic ' + btoa("neo4j" + ':' + "123456789"),
	            }
	    }).
		then(response=>{
		    	//console.log(response);
		    	var j2=JSON.parse(response.bodyText);
		  		//console.log(j2.results);
		  		//console.log(j2.results[0]);
		  		//console.log(j2.results[0].data);
		  		let aux=1;
		  		for (let k =0 ; k <j2.results[0].data.length; k++) {

					let dato=j2.results[0].data[k].row[0];
					/*for (var p = 0; p<dato.row[0].length ; p++) {
						console.log(dato.row[0][p]);
					}*/
					/*for (p =0; p < dato.meta[0].length; p++) {
						console.log(dato.meta[0][p]);
					}*/
					let link={"source":dato[0].name,"target":dato[2].name,"value":1};
					nodos.links.push(link);
					console.log(link);
		      }
		      //console.log(nodos);
			});
		Promise.all([primerConsulta,segundaConsulta]).
			then(values=>{
				console.log(nodos);
				
			});
    }
  }
</script>
