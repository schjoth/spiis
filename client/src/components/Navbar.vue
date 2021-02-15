<template>
  <div id="nav">
    <router-link to="/">Middager</router-link> |
    <router-link to="/CreateNewDinner">Ny Middag</router-link> |
    <router-link to="/MyProfile">Profil</router-link>
  </div>
</template>

<script>
export default {
  name: "Navbar"
};
</script>

<style lang="scss">
#nav {
  padding: 30px;

  a {
    font-weight: bold;
    color: #2c3e50;

    &.router-link-exact-active {
      color: #42b983;
    }
  }
}
</style>
