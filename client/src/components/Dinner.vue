<template>
  <div>
    <h1>{{ dinner.title }}</h1>
    <p>
      <b>Antall gjester:</b> {{ dinner.guests.length }}/{{ dinner.maxGuests }}
    </p>
    <p><b>Sted:</b> {{ dinner.postCode }}, {{ dinner.city }}</p>
    <router-link :to="'/event/' + dinner.id"> Gå til påmelding </router-link>
  </div>
</template>

<script lang="ts">
export default {
  name: "Dinner",
  props: {
    dinner: Object
  }
};
</script>

<style lang="scss" scoped>
div {
  width: 40%;
  height: 300px;
  background-color: white;
  color: black;
  border-radius: 20px;

  padding: 30px;
  margin: 2%;

  h1{
    color: inherit;
  }
}




</style>
