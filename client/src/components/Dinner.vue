<template>
  <section class="box">
    <h1>{{ dinner.title }}</h1>
    <p>
      <b>Antall gjester:</b> {{ dinner.guests.length }}/{{ dinner.maxGuests }}
    </p>
    <p><b>Sted:</b> {{ dinner.postCode }}, {{ dinner.city }}</p>
    <router-link :to="'/event/' + dinner.id"> Gå til påmelding </router-link>
  </section>
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
section {
  padding: 30px;
  margin-top: 20px;
  margin-bottom: 20px;
  min-height: 200px;
}
</style>
