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
</style>
