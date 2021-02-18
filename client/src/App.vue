<template>
  <navbar />
  <router-view />
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
body,
html {
  margin: 0;
  padding: 0;
}

#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
  display: flex;
  flex-direction: column;
  align-items: center;
  min-height: 1vh;
}

main {
  width: 100%;
  padding: 0 10px;
  box-sizing: border-box;
  max-width: 700px;
}
</style>
