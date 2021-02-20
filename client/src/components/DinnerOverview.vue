<template>
  <div id="container">
    <Dinner
      v-for="dinner in dinners"
      v-bind:dinner="dinner"
      v-bind:key="dinner.id"
    />
  </div>
</template>

<script lang="ts">
import Dinner from "./Dinner.vue";
export default {
  name: "DinnerOverview",
  components: {
    Dinner
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

<style lang="scss" scoped>
#container {
  display: flex;
  flex-wrap: wrap;
}
</style>
