<template>
  <div>
    <h1>{{ dinner.title }}</h1>
    <p><b>Antall gjester:</b> {{ dinner.guests }}/{{ dinner.maxGuests }}</p>
    <p><b>Sted:</b> {{ dinner.location }}</p>
  </div>
</template>

<script>
export default {
  name: "Dinner",
  props: ["dinner"]
};
</script>
<style scoped>
div {
  width: 100%;
  display: block;
  position: normal;
  background-color: rgb(135,62,35, 0.3);
  border-radius: 20%;
  margin: 5%
}
</style>
