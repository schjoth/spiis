<template>
  <article>
    <h1>Mine Middager</h1>

    <h2>Arrangerer</h2>
    <DinnerOverview v-bind:dinners="hosting" v-bind:invite="true" />

    <h2>Deltar på</h2>
    <DinnerOverview v-bind:dinners="guesting" />
  </article>
</template>

<script lang="ts">
import DinnerOverview from "@/components/DinnerOverview.vue";
import { onMounted, ref } from "vue";
import { getLogInState } from "@/store/loginState";
import { getGuestDinners, getHostDinners } from "@/api/dinner";
import { DinnerResponse } from "@/api/types";
export default {
  name: "MyDinners",
  components: { DinnerOverview },
  setup() {
    const hosting = ref<DinnerResponse[] | null>(null);
    const guesting = ref<DinnerResponse[] | null>(null);
    const userId = getLogInState().user?.id.toString()!;

    async function fetchData() {
      hosting.value = await getHostDinners(userId);
      guesting.value = await getGuestDinners(userId);
    }

    onMounted(fetchData);

    return { hosting, guesting };
  }
};
</script>
