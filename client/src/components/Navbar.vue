<template>
  <nav>
    <router-link to="/">Middager</router-link> |
    <router-link to="/CreateNewDinner">Ny Middag</router-link> |
    <router-link to="/MyProfile">Profil</router-link> |
    <router-link to="/Login">Login</router-link>
  </nav>
</template>

<script>
export default {
  name: "Navbar"
};
</script>

<style lang="scss" scoped>
nav {
  padding: 30px 0;
  display: flex;
  flex-direction: row;
  justify-content: center;

  a {
    font-weight: bold;
    color: #2c3e50;
    margin: 0 10px;

    &.router-link-exact-active {
      color: #42b983;
    }
  }
}
</style>
