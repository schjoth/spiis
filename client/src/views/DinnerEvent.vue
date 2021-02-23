<template>
  <article>
    <h1>dinner.title</h1>
    <p>id: {{ $route.params.dinnerId }}</p>
    <p>Denne siden vil ikke vise noe den før koblet til databasen</p>
    <p><b>Antall gjester:</b> dinner.guests / dinner.maxGuests</p>
    <ul>
      <li>dinner.users</li>
    </ul>
    <p><b>Sted:</b> dinner.location</p>
    <p><b>Beskrivelse: </b> dinner.description</p>
    <router-link :to="'/event/' + $route.params.dinnerId + '/edit'">
      rediger
    </router-link>
    <button type="button" v-on:click="registrerTilMiddag">
      Meld deg på middagen.
    </button>
  </article>
</template>

<script lang="ts">
import { DinnerResponse } from "@/api/types";
import { getDinner } from "@/api/dinner";
import { useRoute } from "vue-router";
import { onMounted, ref } from "vue";

export default {
  name: "DinnerEvent",
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
