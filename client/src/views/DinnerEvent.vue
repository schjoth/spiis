<template>
  <article v-if="dinner">
    <h1>{{ dinner.title }}</h1>
    <p>
      <b>Arrangør: </b>
      <router-link :to="'/user/' + dinner.host.id">
        {{ dinner.host.firstName }} {{ dinner.host.lastName }}
      </router-link>
    </p>
    <p>
      <b>Antall gjester:</b> {{ dinner.guests.length }} /
      {{ dinner.maxGuests }}
    </p>
    <p>
      <b>Sted:</b> {{ dinner.addressLine }} {{ dinner.postCode }}
      {{ dinner.city }}
    </p>
    <p><b>Beskrivelse: </b> {{ dinner.description }}</p>
    <router-link :to="'/event/' + id + '/edit'" v-if="isHost">
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
import { computed, onMounted, ref } from "vue";
import { getLogInState } from "@/store/loginState";

export default {
  name: "DinnerEvent",
  setup() {
    const dinnerId = useRoute().params.dinnerId;
    const dinner = ref<DinnerResponse | null>(null);
    async function fetchData() {
      dinner.value = await getDinner(dinnerId);
    }

    const isHost = computed(
      () => dinner.value?.host.id === getLogInState().user?.id
    );

    onMounted(fetchData);

    return { dinner, dinnerId, isHost };
  }
};
</script>
