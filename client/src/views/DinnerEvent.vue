<template>
  <article v-if="dinner">
    <h1>{{ dinner.title }}</h1>
    <p>
      <b>Antall gjester:</b> {{ dinner.guests.length }} /
      {{ dinner.maxGuests }}
    </p>
    <p>
      <b>Sted:</b> {{ dinner.addressLine }} {{ dinner.postCode }}
      {{ dinner.city }}
    </p>
    <p><b>Beskrivelse: </b> {{ dinner.description }}</p>
    <router-link :to="'/event/' + id + '/edit'"> rediger </router-link>
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
    const id = useRoute().params.dinnerId;
    const dinner = ref<DinnerResponse | null>(null);
    async function fetchData() {
      dinner.value = await getDinner(id);
    }
    onMounted(fetchData);
    return { dinner, id };
  }
};
</script>
