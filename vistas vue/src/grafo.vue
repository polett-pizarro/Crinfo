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
  		console.log(response.bodyText);
    });
    }
  }
</script>
