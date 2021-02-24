<template>
  <article>
    <h1 class="text-on-image">Mine Middager</h1>

    <h2>Arrangerer</h2>
    <DinnerOverview v-bind:dinners="hosts" v-bind:invite="true" />

    <h2>Deltar på</h2>
    <DinnerOverview v-bind:dinners="participant" />
  </article>
</template>

<script lang="ts">
import DinnerOverview from "@/components/DinnerOverview.vue";
export default {
  name: "MyDinners",
  components: { DinnerOverview },
  data() {
    return {
      hosts: [
        //TODO: hent middager, som brukeren arrangerer, fra serveren
        {
          id: "1",
          title: "Taco",
          guests: "1",
          maxGuests: "4",
          date: "23/02/2021",
          location: "Gløshaugen"
        },
        {
          id: "2",
          title: "Pizza",
          guests: "0",
          maxGuests: "7",
          date: "15/03/2021",
          location: "Italia"
        }
      ],
      participant: [
        //TODO: hent middager, som brukeren deltar på, fra serveren
        {
          id: "3",
          title: "Fårikål",
          guests: "3",
          maxGuests: "3",
          date: "7/03/2021",
          location: "Moholt"
        }
      ]
    };
  }
};
</script>
