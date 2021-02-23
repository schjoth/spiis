<template>
  <article>
    <DinnerOverview v-bind:dinners="dinners" v-if="dinners" />
  </article>
</template>

<script lang="ts">
import DinnerOverview from "@/components/DinnerOverview.vue";
import { getAllDinners, getDinner } from "@/api/dinner";
import { useRoute } from "vue-router";
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
