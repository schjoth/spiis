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

    //TODO: replace with server call
    const adverts = {
      add1: {
        link: "www.vg.no",
        owner: "VG"
      },
      ad2: {
        link: "www.google.no",
        owner: "Google"
      }
    };

    return { dinners, adverts };
  }
};
</script>
