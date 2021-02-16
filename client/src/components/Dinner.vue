<template>
  <div>
    <h1>{{ dinner.title }}</h1>
    <p><b>Antall gjester:</b> {{ dinner.guests }}/{{ dinner.maxGuests }}</p>
    <p><b>Sted:</b> {{ dinner.location }}</p>
    <router-link :to="'/DinnerEvent/' + dinner.id">
      Gå til påmelding
    </router-link>
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
  width: 300px;
  height: 300px;
  display: block;
}
</style>
