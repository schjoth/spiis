<template>
  <navbar />
  <main>
    <router-view />
  </main>
  <footer class="footer">
    <div class="content has-text-centered">
      <strong>Spiis</strong> er laget av gruppe 53 i TDT4140, våren 2021.
    </div>
  </footer>
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
@import "src/assets/main.scss";

main{
  margin-top: 140px;
  padding: 0;
}

#bakgrunn {
  position: fixed;
  top: 0;
  left: 0;
  min-width: 100%;
  min-height: 100%;
  z-index: -1;
}

@media only screen and (max-width: 980px) {
  #bakgrunn {
    min-width: 980px;
  }
 }

footer{
  margin-top: 5%;
}


</style>
