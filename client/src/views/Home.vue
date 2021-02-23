<template>
  <article>
    <DinnerOverview v-bind:dinners="dinners" />
  </article>
</template>

<script lang="ts">
import DinnerOverview from "@/components/DinnerOverview.vue";
import { getAllDinners } from "@/api/dinner";

export default {
  name: "Home",
  components: {
    DinnerOverview
  },
  setup() {
    return {
      dinners: await getAllDinners()
    };
  }
};
</script>
