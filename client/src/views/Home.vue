<template>
  <article>
    <DinnerOverview v-bind:dinners="dinners" />
  </article>
</template>

<script lang="ts">
import DinnerOverview from "@/components/DinnerOverview.vue";

export default {
  name: "Home",
  components: {
    DinnerOverview
  },
  data() {
    return {
      dinners: [
        //Replace with call from server
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
      ]
    };
  }
};
</script>
