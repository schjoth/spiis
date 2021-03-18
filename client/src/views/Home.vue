<template>
  <article>
    <DinnerOverview
        v-bind:dinners="
    dinners.filter(dinner => Date.parse(dinner.date) >= Date.now())" v-if="dinners" />
  </article>
</template>

<script lang="ts">
import DinnerOverview from "@/components/DinnerOverview.vue";
import { getAllDinners } from "@/api/dinner";
import { onMounted, ref } from "vue";
import { DinnerResponse } from "@/api/types";

export default {
  name: "Home",
  components: {
    DinnerOverview
  },
  setup() {
    const dinners = ref<DinnerResponse[] | null>(null);
    async function fetchData() {
      dinners.value = await getAllDinners();
    }
    onMounted(fetchData);
    return { dinners };
  }
};
</script>
