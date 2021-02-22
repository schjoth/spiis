<template>
  <div>
    <h1>{{ dinner.title }}</h1>
    <p><b>Antall gjester:</b> {{ dinner.guests }}/{{ dinner.maxGuests }}</p>
    <p><b>Sted:</b> {{ dinner.location }}</p>
    <router-link :to="'/event/' + dinner.id"> Gå til påmelding </router-link>
  </div>
</template>

<script lang="ts">
export default {
  name: "Dinner",
  props: ["dinner"]
};
</script>

<style lang="scss" scoped>
div {
  background-color: rgb(135,62,35, 0.3);
  border-radius: 20%;
  margin: 5%;
  width: 300px;
  height: 300px;
}
</style>
