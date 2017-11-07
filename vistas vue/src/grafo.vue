<template>
  <body class="navi">
    <h1 class="page-header"><font color="#0e1a35"><i class="fa fa-info"></i> ¿DE QUÉ SE ESTÁ HABLANDO?</font></h1>
    <svg class="grafo" width="1100" height="900"></svg>
    <h1 class="page-header"><font color="#0e1a35"><i class="fa fa-trophy" aria-hidden="true"></i> TWITTEROS CON MÁS INFLUENCIA</font></h1>
    <div class="panel panel-info col-lg-6">
      <div class="panel-heading">
          <i class="fa fa-bell fa-fw"></i> Top 10 Twitteros
      </div>
      <!-- /.panel-heading -->
      <div class="panel-body">
            <div class="table-responsive">
                  <table class="table table-bordered table-hover table-striped">
                    <thead>
                        <tr>
                            <th>Pos.</th>
                            <th>Nombre</th>
                            <th>Influencia</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr v-for="u,i in top">
                            <td>{{i+1}}.-</td>
                            <td>{{u.row[0]}}</td>
                            <td>{{u.row[1]}}</td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <!-- /.table-responsive -->
      </div>
    </div>
  </body>

</template>
<script>
  export default{
  	data(){
        var TopCommune;
        var topInfluencia;
        var top;
        return{
            TopCommune:[],
            topInfluencia:[[5249, "Tele13"],[5034,"BioBioChile"],[3402, "La Tercera"],[3298, "Emol.com"],[3041, "Radio ADN"],[2972, "The Clinic Online"],[2311, "Carabineros de Chile"],[1607, "Publimetro"],[1244, "Marco Enríquez-O"],[438, "LaSerenaOnline"]],
            top:[]
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
					//console.log(dato);
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
					//console.log(dato);
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
					//console.log(link);
		      }
		      //console.log(nodos);
			});
		Promise.all([primerConsulta,segundaConsulta]).
			then(values=>{
				//console.log(nodos);
        //
        var svg = d3.select(".grafo"),
            width = +svg.attr("width"),
            height = +svg.attr("height");

        var color = d3.scaleOrdinal(d3.schemeCategory20);

        var simulation = d3.forceSimulation()
            .force("link", d3.forceLink().distance(10).strength(0.5))
            .force("charge", d3.forceManyBody())
            .force("center", d3.forceCenter(width / 2, height / 2));

          var graph = nodos;
          var nodes = graph.nodes,
              nodeById = d3.map(nodes, function(d) { return d.name; }),
              links = graph.links,
              bilinks = [];

          links.forEach(function(link) {
            var s = link.source = nodeById.get(link.source),
                t = link.target = nodeById.get(link.target),
                i = {}; // intermediate node
            nodes.push(i);
            links.push({source: s, target: i}, {source: i, target: t});
            bilinks.push([s, i, t]);
          });

          var link = svg.selectAll(".link")
            .data(bilinks)
            .enter().append("path")
              .attr("class", "link");

          var node = svg.selectAll(".node")
            .data(nodes.filter(function(d) { return d.name; }))
            .enter().append("circle")
              .attr("class", "node")
              .attr("r", 5)
              .attr("fill", function(d) { return color(d.group); })
              .call(d3.drag()
                  .on("start", dragstarted)
                  .on("drag", dragged)
                  .on("end", dragended));

          node.append("title")
              .text(function(d) { return d.name; });

          node.append("text")
              .attr("dx", 10)
              .attr("dy", ".35em")
              .text(function(d) { return d.name });

          simulation
              .nodes(nodes)
              .on("tick", ticked);

          simulation.force("link")
              .links(links);

          function ticked() {
            link.attr("d", positionLink);
            node.attr("transform", positionNode);
            d3.selectAll("text").attr("x", function (d) {
                return d.x;
            })
                .attr("y", function (d) {
                return d.y;
            });
          }

        function positionLink(d) {
          return "M" + d[0].x + "," + d[0].y
               + "S" + d[1].x + "," + d[1].y
               + " " + d[2].x + "," + d[2].y;
        }

        function positionNode(d) {
          return "translate(" + d.x + "," + d.y + ")";
        }

        function dragstarted(d) {
          if (!d3.event.active) simulation.alphaTarget(0.3).restart();
          d.fx = d.x, d.fy = d.y;
        }

        function dragged(d) {
          d.fx = d3.event.x, d.fy = d3.event.y;
        }

        function dragended(d) {
          if (!d3.event.active) simulation.alphaTarget(0);
          d.fx = null, d.fy = null;
        }
        //
			});
      

      var top = this.$http.post('http://localhost:7474/db/data/transaction/commit',
        {"statements" : [ {
      "statement" : "MATCH (n:Person) MATCH (c:Person) return c.name, (c.followers + c.likes + c.mention + c.retweets)/count(n) as metrica order by metrica desc Limit 10"
       }]},{
              headers: {'Authorization': 'Basic ' + btoa("neo4j" + ':' + "123456789"),
              }
      }).
    then(response=>{
       // get body data
      console.log(response);
      this.top= JSON.parse(response.bodyText);
      //console.log(response.bodyText);
      //console.log(top);
      this.top=this.top.results[0].data;
      console.log(this.top);
      //[]. row [0] y el [1]
    }, response=>{
       // error callback
       console.log('error cargando lista');
    });


    }
  }
</script>
