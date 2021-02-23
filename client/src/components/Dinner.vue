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
import { DinnerResponse } from "@/api/types";

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
