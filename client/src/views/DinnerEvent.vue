<template>
  <article>
    <p>id: {{ $route.params.dinnerId }}</p>
    <p>Denne siden vil ikke vise noe den før koblet til databasen</p>
    <h1>{{ dinner.title }}</h1>
    <p><b>Antall gjester:</b> {{ dinner.guests }}/{{ dinner.maxGuests }}</p>
    <p><b>Sted:</b> {{ dinner.location }}</p>
    <p><b>Beskrivelse: </b> {{ dinner.description }}</p>
    <Dinner />
    <router-link :to="'/event/' + $route.params.dinnerId + '/edit'">
      rediger
    </router-link>
  </article>
</template>

<script lang="ts">
import Dinner from "@/components/Dinner.vue";
import { DinnerResponse } from "@/api/types";
import { getDinner } from "@/api/dinner";
import { useRoute } from "vue-router";
import { onMounted, ref } from "vue";

export default {
  name: "DinnerEvent",
  components: {
    Dinner
  },
  setup() {
    const dinner = ref<DinnerResponse | null>(null);
    async function fetchData() {
      dinner.value = await getDinner(useRoute().params.dinnerId);
    }
    onMounted(fetchData);
    return { dinner };
  }
};
</script>
