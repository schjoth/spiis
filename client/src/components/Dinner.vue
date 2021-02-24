<template>
  <div>
    <h1>{{ dinner.title }}</h1>
    <p>
      <b>Antall gjester:</b> {{ dinner.guests.length }}/{{ dinner.maxGuests }}
    </p>
    <p><b>Sted:</b> {{ dinner.postCode }}, {{ dinner.city }}</p>
    <p>
      <b>Arrangør: </b>
      <router-link :to="'/user/' + dinner.host.id">
        {{ dinner.host.firstName }} {{ dinner.host.lastName }}
      </router-link>
    </p>
    <p>
      <router-link :to="'/event/' + dinner.id"> Gå til påmelding </router-link>
    </p>
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
  width: 300px;
  height: 300px;
}
</style>
