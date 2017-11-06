<template>
        <div class="container-fluid display-table">
            <div class="row display-table-row">
                <div class="col-md-2 col-sm-1 hidden-xs display-table-cell v-align box" id="navigation">
                    <div class="logo">
                        <a hef="#/Main"><img src="./images/crinfo.png" class="hidden-xs hidden-sm"></a>
                    </div>
                    <div class="navi">
                        <ul>
                            <li><a href="#Main"><i class="fa fa-home" aria-hidden="true"></i><span class="hidden-xs hidden-sm">Home</span></a></li>
                            <li><a href="#/Grafo"><i class="fa fa-tasks" aria-hidden="true"></i><span class="hidden-xs hidden-sm">Grafos</span></a></li>
                            <li><a href="#/Grafico"><i class="fa fa-bar-chart" aria-hidden="true"></i><span class="hidden-xs hidden-sm">Graficos</span></a></li>
                            <li><a href="#"><i class="fa fa-user" aria-hidden="true"></i><span class="hidden-xs hidden-sm">Calender</span></a></li>
                            <li><a href="#"><i class="fa fa-calendar" aria-hidden="true"></i><span class="hidden-xs hidden-sm">Users</span></a></li>
                            <li><a href="#"><i class="fa fa-cog" aria-hidden="true"></i><span class="hidden-xs hidden-sm">Setting</span></a></li>
                        </ul>
                    </div>
                </div>
                <div class="col-md-10 col-sm-11 display-table-cell v-align">
                    <div class="user-dashboard">
                            <router-view></router-view>
                    </div>
                </div>
            </div>

        </div>
</template>

<script>
export default {

}
</script>
