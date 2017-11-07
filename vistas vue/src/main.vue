<template>
    <div id="wrapper" class="navi">
        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-10">
                    <h1 class="page-header"><font color="#0e1a35">Vista Principal</font></h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-10">
                    <!-- /.panel -->
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <h2><i class="fa fa-info"></i> MISION Y VISION</h2>
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <div class="row">
                                <p ALIGN="justify">Para el gobierno, las municipalidades, la Paz Ciudadana y/o algún político, quienes tienen la necesidad de saber la apreciación que tiene la sociedad
                                   respecto de la delincuencia, CRINFO (Crime Information) es un observatorio de Twitter que muestra resultados por medio de una aplicación web que brinda
                                   estadísticas de las reacciones que tiene la sociedad respecto de la delincuencia, clasifica diversos tipos de delitos, muestra informacion por comunas
                                   rankea los diferentes delitos, ademas de la delincuencia registrada en las diversas comunas existentes.
                                </p>
                            </div>
                        </div>
                        <!-- /.row -->
                    </div>
                    <!-- /.panel-body -->
                    <!-- /.panel -->
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <h2><i class="fa fa-lock"></i> INFORMACION RAPIDA </h2>
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <div class="row">
                                <p ALIGN="justify">Mostrar la informacion mas reciente registrada en cuanto a los crimenes ocurridos en chile y hacerlo de una forma amistosa y rapida
                                es el principal enfoque que se busca trabajar, a continuacion se puede apresiar dos ejemplos en donde se muestra algunos de los resultados obtenidos
                                por medio del analisis de <strong><big>CRINFO</big></strong>:</p>
                            </div>
                        </div>
                        <!-- /.row -->
                    </div>
                    <!-- /.panel-body -->

                    <div class="panel panel-info col-lg-6">
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

                    <div class="panel panel-info  col-lg-6">
                      <div class="panel-heading">
                          <i class="fa fa-bell fa-fw"></i> Top 5 Comunas con más delitos
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
                </div>
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

    this.$http.post('http://localhost:7474/db/data/transaction/commit',
            {"statements" : [ {
    "statement" : "MATCH p=()-->() RETURN p"
            }]},{
            headers: {'Authorization': 'Basic ' + btoa("neo4j" + ':' + "123456789"),
            }
    }).then(response=>{
  console.log(response);
    });
    }
}
</script>
