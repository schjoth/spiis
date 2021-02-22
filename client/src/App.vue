<template>

  <div>
      <navbar />
      <img id="bakgrunn" src="@/assets/middagsdeling_bakgrunnsbilde.jpg" alt="bakgrunn"/>
      <router-view/>
  </div>

</template>

<script>
import Navbar from "@/components/Navbar";
import { onMounted } from "vue";
import { tryReusingToken } from "@/api/login";
export default {
  components: {
    Navbar
  },
  setup() {
    onMounted(() => {
      // If we have logged on previously, we already have a token in localStorage
      tryReusingToken();
    });
  }
};


</script>

<style lang="scss">

body{
    margin: 0 0 0 0;
    height: 100vh;
    display: flex;
    justify-content: center;
}

#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #ffffff;
  z-index: 0;
  height: 100%;
}


#bakgrunn{
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  //height: 100wh; --media query? 975px
  z-index: -1;
}

</style>
