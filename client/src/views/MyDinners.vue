<template>
  <article>
    <h1>Mine Middager</h1>

    <h2>Arrangerer</h2>
    <DinnerOverview :dinners="activeHosting" />

    <h2>Deltar på</h2>
    <DinnerOverview :dinners="activeGuesting" />
  </article>
  <article>
    <h1>Arkiverte middager</h1>

    <h2>Arrangerte</h2>
    <DinnerOverview :dinners="expiredHosting" />

    <h2>Deltok på</h2>
    <DinnerOverview :dinners="expiredGuesting" />
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

    const activeHosting = computed(() => {
      if (hosting.value === null) return null;
      return hosting.value.filter(
        (dinner) => Date.parse(dinner.date + " " + dinner.endTime) >= Date.now()
      );
    });
    const activeGuesting = computed(() => {
      if (guesting.value === null) return null;
      return guesting.value.filter(
        (dinner) => Date.parse(dinner.date + " " + dinner.endTime) >= Date.now()
      );
    });
    const expiredHosting = computed(() => {
      if (hosting.value === null) return null;
      return hosting.value.filter(
        (dinner) => Date.parse(dinner.date + " " + dinner.endTime) < Date.now()
      );
    });
    const expiredGuesting = computed(() => {
      if (guesting.value === null) return null;
      return guesting.value.filter(
        (dinner) => Date.parse(dinner.date + " " + dinner.endTime) < Date.now()
      );
    });

    async function fetchData() {
      hosting.value = await getHostDinners(userId.value!);
      guesting.value = await getGuestDinners(userId.value!);
    }

    onMounted(() => authorized(fetchData));

    return {
      activeHosting,
      activeGuesting,
      expiredHosting,
      expiredGuesting
    };
  }
};
</script>
