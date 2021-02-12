<template>
  <div id="container">
    <Dinner
      v-for="dinner in dinners"
      v-bind:dinner="dinner"
      v-bind:key="dinner.id"
    />
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
#container {
  display: flex;
  width: 80vw;
}
</style>
