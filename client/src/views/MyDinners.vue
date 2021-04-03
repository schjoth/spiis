<template>
  <article v-if="unratedGuesting">
    <p class="standalone_p">
      Heisann, du har deltatt på følgende arrangementer og vi setter pris på om
      du kunne tatt deg tiden til å gi arrangørene en liten feedback av dem
    </p>
    <div class="noe">
      <RateDinner
        v-for="dinner in unratedGuesting"
        :dinner="dinner"
        :key="dinner.id"
      />
    </div>
  </article>

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
import RateDinner from "@/components/RateDinner.vue";
import { computed, onMounted, ref } from "vue";
import { getLogInState } from "@/store/loginState";
import { getGuestDinners, getHostDinners } from "@/api/dinner";
import { authorized } from "@/api/client";
import { DinnerResponse } from "@/api/types";
import { hasUserRatedDinner } from "@/api/rating";
export default {
  name: "MyDinners",
  components: { DinnerOverview, RateDinner },
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
    const unratedGuesting = computed(() => {
      if (guesting.value === null || userId.value == undefined) return null;
      const currentUserID: number = userId.value;
      console.log(guesting.value);
      guesting.value.forEach((a) =>
        console.log(a.title + ", " + !!hasUserRatedDinner(a.id, currentUserID))
      );

      return guesting.value.filter(
        (dinner) =>
          Date.parse(dinner.date + " " + dinner.endTime) < Date.now() &&
          !hasUserRatedDinner(dinner.id, currentUserID)
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
      expiredGuesting,
      unratedGuesting
    };
  }
};
</script>

<style lang="scss" scoped>
.noe {
  display: flex;
  flex-wrap: wrap;
}
</style>
