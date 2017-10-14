
<template>

<body>

<div>
	<svg width="960" height="500"></svg>
	<svg class="chart"></svg>

	<body></body>
</div>

</body>




</template>
<script>
export default{
  data: function(){
    return {
      data : [13,11,30,23,19,17,15,14,14,12],
      data2 : [
      			{letter:'A', frequency: .33},
      			{letter:'b', frequency: .12},
      			{letter:'c', frequency: .13},
      			{letter:'d', frequency: .15},
      			{letter:'e', frequency: .78},
      			{letter:'f', frequency: .13},
      			{letter:'g', frequency: .56},
      			{letter:'h', frequency: .52},
      			{letter:'i', frequency: .98},
      			{letter:'j', frequency: .32},
      			{letter:'k', frequency: .51},
      			{letter:'l', frequency: .35},
      			{letter:'m', frequency: .77},
      			{letter:'n', frequency: .52},
      			{letter:'o', frequency: .50},
      			{letter:'p', frequency: .59},
      			{letter:'q', frequency: .06}
      ],
      data3 : [
      		{date:'30-May-12', close: 23.33},
  			{date:'23-May-12', close: 23.12},
  			{date:'19-May-12', close: 3.13},
  			{date:'10-May-12', close: 23.15},
  			{date:'1-May-12', close: 4.78},
  			{date:'29-Apr-12', close: 55.13},
  			{date:'20-Apr-12', close: 66.56},
  			{date:'10-Apr-12', close: 76.52},
  			{date:'2-Apr-12', close: 43.98},
  			{date:'30-Mar-12', close: 33.32},
  			{date:'26-Mar-12', close: 2.51},
  			{date:'22-Mar-12', close: 33.35},
  			{date:'20-Mar-12', close: 23.77},
  			{date:'16-Mar-12', close: 45.52},
  			{date:'12-Mar-12', close: 5.50},
  			{date:'9-Mar-12', close: 5.59},
  			{date:'2-Mar-12', close: 44.06}

      ]
    }
  },
  methods:{
    loadGraph:function(data){

      	var svg = d3.select("svg"),
		    margin = {top: 20, right: 20, bottom: 30, left: 40},
		    width = +svg.attr("width") - margin.left - margin.right,
		    height = +svg.attr("height") - margin.top - margin.bottom;

		var x = d3.scaleBand().rangeRound([0, width]).padding(0.2),
		    y = d3.scaleLinear().rangeRound([height, 0]);

		var g = svg.append("g")
		    .attr("transform", "translate(" + margin.left + "," + margin.top + ")");



		  x.domain(data.map(function(d) { return d.letter; }));
		  //y.domain([0, d3.max(data, function(d) { return d.frequency; })]);
		  y.domain([0, 1]); // eje y en rangos de 0% hasta el 100%

		  g.append("g")
		      .attr("class", "axis axis--x")
		      .attr("transform", "translate(0," + height + ")")
		      .call(d3.axisBottom(x));

		  g.append("g")
		      .attr("class", "axis axis--y")
		      .call(d3.axisLeft(y).ticks(20, "%")) // ticks indica la cantidad de indices del eje y
		    .append("text")
		      .attr("transform", "rotate(-90)")
		      .attr("y", 6)
		      .attr("dy", "0.71em")
		      .attr("text-anchor", "end")
		      .text("Frequency");

		  g.selectAll(".bar")
		    .data(data)
		    .enter().append("rect")
		      .attr("class", "bar")
		      .attr("x", function(d) { return x(d.letter); })
		      .attr("y", function(d) { return y(d.frequency); })
		      .attr("width", x.bandwidth())
		      .attr("height", function(d) { return height - y(d.frequency); });

    },
    cosa:function(data){


		var width = 420,
		    barHeight = 20;

		var x = d3.scaleLinear()
		    .domain([0, d3.max(data)])
		    .range([0, width]);

		var chart = d3.select(".chart")
		    .attr("width", width)
		    .attr("height", barHeight * data.length);

		var bar = chart.selectAll("g")
		    .data(data)
		  .enter().append("g")
		    .attr("transform", function(d, i) { return "translate(0," + i * barHeight + ")"; });

		bar.append("rect")
		    .attr("width", x)
		    .attr("height", barHeight - 1);

		bar.append("text")
		    .attr("x", function(d) { return x(d) - 3; })
		    .attr("y", barHeight / 2)
		    .attr("dy", ".35em")
		    .text(function(d) { return d; });

    },
    cosa2:function(data){
    	var margin = {top: 20, right: 20, bottom: 30, left: 50},
		    width = 960 - margin.left - margin.right,
		    height = 500 - margin.top - margin.bottom;

		// parse the date / time
		var parseTime = d3.timeParse("%d-%b-%y");

		// set the ranges
		var x = d3.scaleTime().range([0, width]);
		var y = d3.scaleLinear().range([height, 0]);

		// define the line
		var valueline = d3.line()
		    .x(function(data) { return x(data.date); })
		    .y(function(data) { return y(data.close); });

		// append the svg obgect to the body of the page
		// appends a 'group' element to 'svg'
		// moves the 'group' element to the top left margin
		var svg = d3.select("body").append("svg")
		    .attr("width", width + margin.left + margin.right)
		    .attr("height", height + margin.top + margin.bottom)
		  .append("g")
		    .attr("transform",
		          "translate(" + margin.left + "," + margin.top + ")");

		// gridlines in x axis function
		function make_x_gridlines() {
		    return d3.axisBottom(x)
		        .ticks(5)
		}

		// gridlines in y axis function
		function make_y_gridlines() {
		    return d3.axisLeft(y)
		        .ticks(5)
		}

		// format the data
		  data.forEach(function(data) {
		      data.date = parseTime(data.date);
		      data.close = +data.close;
		  });



		  // Scale the range of the data
		  x.domain(d3.extent(data, function(d) { return d.date; }));
		  y.domain([0, d3.max(data, function(d) { return d.close; })]);

		  // add the X gridlines
		  svg.append("g")
		      .attr("class", "grid")
		      .attr("transform", "translate(0," + height + ")")
		      .call(make_x_gridlines()
		          .tickSize(-height)
		          .tickFormat("")
		      )

		  // add the Y gridlines
		  svg.append("g")
		      .attr("class", "grid")
		      .call(make_y_gridlines()
		          .tickSize(-width)
		          .tickFormat("")
		      )

		  // add the valueline path.
		  svg.append("path")
		      .data([data])
		      .attr("class", "line")
		      .attr("d", valueline);

		  // add the X Axis
		  svg.append("g")
		      .attr("transform", "translate(0," + height + ")")
		      .call(d3.axisBottom(x));

		  // add the Y Axis
		  svg.append("g")
		      .call(d3.axisLeft(y));

		}
  },
  mounted:function(){

     this.loadGraph(this.data2);
     //this.cosa(this.data);
     //this.cosa2(this.data3);

  }
}
</script>
<style>
	.chart div {
	  font: 10px sans-serif;
	  background-color: steelblue;
	  text-align: right;
	  padding: 3px;
	  margin: 1px;
	  color: white;
	}

	.chart rect {
	  fill: steelblue;
	}

	.chart text {
	  fill: white;
	  font: 10px sans-serif;
	  text-anchor: end;
	}


	.bar {
	  fill: steelblue;
	}

	.bar:hover {
	  fill: brown;
	}

	.axis--x path {
	  display: block;
	}

	.line {
	  fill: none;
	  stroke: steelblue;
	  stroke-width: 2px;
	}

	.grid line {
	  stroke: lightgrey;
	  stroke-opacity: 0.7;
	  shape-rendering: crispEdges;
	}

	.grid path {
	  stroke-width: 0;
	}

</style>
