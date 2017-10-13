<template>
  <div>
    <!-- Navigation -->
    <nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="index.html">CRINFO</a>
        </div>
        <!-- /.navbar-top-links -->
        </nav>

        <div class="navbar-default sidebar" role="navigation">
            <div class="sidebar-nav navbar-collapse">
                <ul class="nav" id="side-menu">
                    <li>
                        <a href="#/graph"><i class="fa fa-tachometer" aria-hidden="true"></i> VISTA PRINCIPAL</a>
                    </li>
                    <li>
                        <a href="#/"><i class="fa fa-bar-chart" aria-hidden="true"></i> GRAFICOS</a>
                    </li>
                    <li>
                        <a href="#/"><i class="fa fa-map-marker" aria-hidden="true"></i> MAPA DE CALOR</a>
                    </li>
                    <li>
                        <a href="#/"><i class="fa fa-pie-chart" aria-hidden="true"></i> MAS COSAS</a>
                    </li>
                </ul>
            </div>
            <!-- /.sidebar-collapse -->
        </div>
        <!-- /.navbar-static-side -->




    <div class="content-wrapper">
      <div class="container-fluid">
        <main class="container">
          <router-view></router-view>
        </main>
      </div>
    </div>

    <footer>
      Footer
    </footer>
</div>
</template>

<script>
export default {

}
</script>
