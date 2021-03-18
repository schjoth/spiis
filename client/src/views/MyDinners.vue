<template>
  <article>
    <h1>Mine Middager</h1>

    <h2>Arrangerer</h2>
    <DinnerOverview v-bind:dinners="hosting" />

    <h2>Deltar på</h2>
    <DinnerOverview v-bind:dinners="guesting" />
  </article>
</template>

<script lang="ts">
import DinnerOverview from "@/components/DinnerOverview.vue";
import { computed, onMounted, ref } from "vue";
import { getLogInState } from "@/store/loginState";
import { getGuestDinners, getHostDinners } from "@/api/dinner";
import { authorized } from "@/api/client";
import { DinnerResponse } from "@/api/types";
export default {
  name: "MyDinners",
  components: { DinnerOverview },
  setup() {
    const hosting = ref<DinnerResponse[] | null>(null);
    const guesting = ref<DinnerResponse[] | null>(null);
    const userId = computed(() => getLogInState().user?.id);

    async function fetchData() {
      hosting.value = await getHostDinners(userId.value!);
      guesting.value = await getGuestDinners(userId.value!);
    }

    onMounted(() => authorized(fetchData));

    return { hosting, guesting };
  }
};
</script>
