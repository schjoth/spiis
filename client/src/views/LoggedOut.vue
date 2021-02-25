<template>
  <article class="box max-600 has-text-centered">
    <h1>Du er nå logget ut!</h1>
    <router-link to="/login">Logg inn igjen</router-link><br>
    <router-link to="/">Tilbake til forsiden</router-link>
  </article>
</template>

<script lang="ts">
export default {
  name: "LogoutView",
}
</script>