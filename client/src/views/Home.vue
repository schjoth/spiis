<template>
  <article>
    <DinnerOverview
      :adverts="adverts"
      v-bind:dinners="dinners"
      v-if="dinners"
    />
  </article>
</template>

<script lang="ts">
import DinnerOverview from "@/components/DinnerOverview.vue";
import { getAllDinners } from "@/api/dinner";
import { onMounted, ref } from "vue";
import { AdvertResponse, DinnerResponse } from "@/api/types";
import { getAllAdverts } from "@/api/adverts";

export default {
  name: "Home",
  components: {
    DinnerOverview
  },
  setup() {
    const dinners = ref<DinnerResponse[] | null>(null);
    const adverts = ref<AdvertResponse[] | null>(null);
    async function fetchData() {
      dinners.value = await getAllDinners();
      adverts.value = await getAllAdverts();
    }
    onMounted(fetchData);

    return { dinners, adverts };
  }
};
</script>
