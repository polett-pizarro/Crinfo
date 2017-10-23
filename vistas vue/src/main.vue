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
    text-anchor: middle;
  }
</style>
<template>
    <div id="wrapper">
        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header"><font color="white">Vista Principal</font></h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-8">
                    <!-- /.panel -->
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <i class="fa fa-bar-chart-o fa-fw"></i> MISION Y VISION
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <div class="row">
                                <p>Para el gobierno, las municipalidades, la Paz Ciudadana y/o algún político, quienes tienen la necesidad de saber la apreciación que tiene la sociedad respecto de la delincuencia, el CRINFO (Cime Information) es una aplicación web que brinda estadísticas de las reacciones que tiene la sociedad respecto de la delincuencia. </p>                             </div>
                            </div>
                            <!-- /.row -->
                        </div>
                        <!-- /.panel-body -->
                    </div>
                </div>
                <!-- /.col-lg-8 -->
                <div class="col-lg-4">
                    <div class="panel panel-default">
                      <div class="panel-heading">
                          <i class="fa fa-bell fa-fw"></i> Top 3 Delitos en Santiago
                      </div>
                      <!-- /.panel-heading -->
                      <div class="panel-body">
                            <div class="table-responsive">
                                  <table class="table table-bordered table-hover table-striped">
                                    <thead>
                                        <tr>
                                            <th>Pos.</th>
                                            <th>Delito</th>
                                            <th>#Tweets</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr v-for="u,i in TopCrime">
                                            <td>{{i+1}}.-</td>
                                            <td>{{u[0]}}</td>
                                            <td>{{u[1]}}</td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                            <!-- /.table-responsive -->
                      </div>
                    </div>

                    <div class="panel panel-default">
                      <div class="panel-heading">
                          <i class="fa fa-bell fa-fw"></i> Top 3 Comunas con más delitos
                      </div>
                      <!-- /.panel-heading -->
                      <div class="panel-body">
                            <div class="table-responsive">
                                <table class="table table-bordered table-hover table-striped">
                                    <thead>
                                        <tr>
                                            <th>Pos.</th>
                                            <th>Nombre</th>
                                            <th>#Tweets</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr v-for="u,i in TopCommune">
                                            <td>{{i+1}}.-</td>
                                            <td>{{u[0]}}</td>
                                            <td>{{u[1]}}</td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                            <!-- /.table-responsive -->
                      </div>
                    </div>

                    <!-- /.panel -->
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <i class="fa fa-bar-chart-o fa-fw"></i> Delitos en Santiago
                        </div>
                        <div class="panel-body">
                            <p class="chart">Algun tipo de grafico </p>
                            <a href="#" class="btn btn-default btn-block">Ver Detalles</a>
                        </div>
                        <!-- /.panel-body -->
                    </div>
                </div>
                <!-- /.col-lg-4 -->
            </div>
            <!-- /.row -->
        </div>
        <!-- /#page-wrapper -->

    </div>
    <!-- /#wrapper -->
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

        this.$http.get('http://localhost:8082/crinfo/TopCommunes')
    .then(response=>{
       // get body data
      this.TopCommune = response.body;
      console.log(this.TopCommune);
    }, response=>{
       // error callback
       console.log('error cargando lista');
    });
    this.$http.get('http://localhost:8082/crinfo/TopCrime')
    .then(response=>{
       // get body data
      this.TopCrime = response.body;
      console.log(this.TopCrime);
    }, response=>{
       // error callback
       console.log('error cargando lista');
    });

      var month = ["Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre",""]
      var data = [30, 86, 168, 281, 303, 365];

      var sum = data.reduce((acc, item) => acc + item, 0);

      d3.select(".chart")
        .selectAll("div")
        .data(data)
          .enter()
          .append("div")
          .style("width", function(d) { return (d/sum)*100 + "%"; })
          .text(function(d) { return d; });
    }
}
</script>
