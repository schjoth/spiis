<template>
  <div>
    <h1>{{ dinner.title }}</h1>
    <p><b>Antall gjester:</b> {{ dinner.guests }}/{{ dinner.maxGuests }}</p>
    <p><b>Sted:</b> {{ dinner.location }}</p>
    <p><b>Beskrivelse:</b> {{ dinner.description }}</p>
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
  background-color: #ffffff;
  border-radius: 10px;
  padding: 30px;
  margin: 0 2% 5% 2%;
  width: 100%;
  min-height: 200px;
  color: black;
}
</style>
