<template>
  <div id="container">
    <div v-bind:key="dinner.id" v-for="dinner in dinners">
      <Dinner v-bind:dinner="dinner" />
    </div>
  </div>
</template>

<script>
import Dinner from "./Dinner";
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
          title: "Taco",
          guests: "1",
          maxGuests: "4",
          date: "23/02/2021",
          location: "Gløshaugen"
        },
        {
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

<style scoped>
div {
  display: flex;
}
</style>
