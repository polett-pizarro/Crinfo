<!-- Template es la parte de la vista-->
<template>
<div class="center">
  <h1><font color="black">{{title}}</font></h1>
    <p>
      CRINFO - Crime Information
    </p>
</div>
</template>
<!-- Scrip vendria siendo de la parte logica-->
<script>
export default{
  data(){
    return{
      title:'CRINFO'
    }
  },
  mounted:function(){
    console.log('Crinfo.vue')
  }
}
</script>
