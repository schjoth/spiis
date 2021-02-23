<template>
  <div>
    <h1>{{ dinner.title }}</h1>
    <p><b>Antall gjester:</b> {{ dinner.guests }}/{{ dinner.maxGuests }}</p>
    <p><b>Sted:</b> {{ dinner.location }}</p>
    <router-link :to="'/event/' + $route.params.dinnerId">
      Gå til påmelding
    </router-link>
  </div>
</template>

<script lang="ts">
import { getDinner } from "@/api/dinner";
import { useRoute } from "vue-router";

export default {
  name: "Dinner",
  props: {
    title: String,
    description: String,
    addressLine: String,
    postCode: String,
    city: String,
    time: String,
    maxPeople: Number
  },

  async setup() {
    await getDinner(useRoute().params.dinnerId);
  }
};
</script>

<style lang="scss" scoped>
div {
  width: 300px;
  height: 300px;
}
</style>
