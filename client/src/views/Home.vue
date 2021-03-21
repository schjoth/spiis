<template>
  <article>
    <DinnerOverview :adverts="adverts" v-if="dinners" :dinners="dinners" />
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
      const unfilteredDinners = await getAllDinners();
      dinners.value = unfilteredDinners.filter(
        (dinner) => Date.parse(dinner.date + " " + dinner.endTime) >= Date.now()
      );
      adverts.value = await getAllAdverts();
    }
    onMounted(fetchData);

    return { dinners, adverts };
  }
};
</script>
