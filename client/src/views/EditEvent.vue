<template>
  <NewDinner :dinner="dinner" :edit="true" />
</template>

<script lang="ts">
import NewDinner from "../components/NewDinner.vue";
export default {
  components: { NewDinner },
  data: function () {
    return {
      dinner: {
        title: "fisk",
        description: "noe",
        maxGuests: 5,
        location: "et sted"
      }
      //dinner: "" //TODO: hent middagsinformasjon hvor middag.id = $route.params.eventId,
    };
  }
};
</script>
