<template>
  <article>
    <div class="options field is-grouped is-align-items-baseline">
      <div class="control">
        <label for="city">Filter: </label>
        <select class="select" name="city" id="city" v-model="cityFilter">
          <option v-for="city in allCities" :key="city" :value="city">
            {{ city }}
          </option>
        </select>
      </div>
      <div class="is-flex-grow-1"></div>
      <div class="control">
        <label for="date">Sortering: </label>
        <select class="select" name="date" id="date" v-model="sorting">
          <option value="soonest">Tidligst kommende</option>
          <option value="soonest!">Senest kommende</option>
          <option value="newest-posted">Nylig publisert</option>
          <option value="newest-posted!">Tidligst publisert</option>
          <option value="host-rating">Best anmeldt vert</option>
        </select>
      </div>
    </div>
    <DinnerOverview
      :adverts="shownAdverts"
      v-if="computedDinners"
      :dinners="computedDinners"
    />
  </article>
</template>

<script lang="ts">
import DinnerOverview from "@/components/DinnerOverview.vue";
import { getAllFutureDinners } from "@/api/dinner";
import { computed, onMounted, ref } from "vue";
import { AdvertResponse, DinnerResponse } from "@/api/types";
import { getRandomAdverts } from "@/api/adverts";

export default {
  name: "Home",
  components: {
    DinnerOverview
  },
  setup() {
    const allDinners = ref<DinnerResponse[] | null>(null);
    const allAdverts = ref<AdvertResponse[] | null>(null);

    const cityFilter = ref("Alle");
    const sorting = ref("soonest");

    const computeAdvertCount = (dinners: number) => Math.floor(dinners / 3);

    const computedDinners = computed(() => {
      if (allDinners.value === null) return null;
      let filteredDinners =
        cityFilter.value === "Alle"
          ? allDinners.value
          : allDinners.value.filter((it) => it.city === cityFilter.value);

      if (sorting.value.startsWith("soonest"))
        filteredDinners.sort(
          (a, b) =>
            new Date(`${a.date} ${a.startTime}`).valueOf() -
            new Date(`${b.date} ${b.startTime}`).valueOf()
        );
      else if (sorting.value.startsWith("newest-posted"))
        filteredDinners.sort(
          (a, b) =>
            new Date(b.createdTime).valueOf() -
            new Date(a.createdTime).valueOf()
        );
      else if (sorting.value.startsWith("host-rating"))
        filteredDinners = filteredDinners.filter(
          (it) => it.host.averageHostRating !== null
        );
      filteredDinners.sort(
        (a, b) => b.host.averageHostRating! - a.host.averageHostRating!
      );

      if (sorting.value.endsWith("!")) filteredDinners.reverse();

      return filteredDinners;
    });

    const shownAdverts = computed(() => {
      if (computedDinners.value === null || allAdverts.value === null)
        return null;
      return allAdverts.value.slice(
        0,
        computeAdvertCount(computedDinners.value.length)
      );
    });

    const allCities = computed(() => {
      if (allDinners.value === null) return ["Alle"];
      return ["Alle", ...new Set(allDinners.value.map((it) => it.city))];
    });

    async function fetchData() {
      allDinners.value = await getAllFutureDinners();
      const advertCount = computeAdvertCount(allDinners.value.length);
      allAdverts.value = await getRandomAdverts(advertCount);
    }
    onMounted(fetchData);

    return {
      allCities,
      cityFilter,
      sorting,
      computedDinners,
      shownAdverts
    };
  }
};
</script>

<style lang="scss" scoped>
.options {
  margin-bottom: 10px;

  label {
    font-size: 1.3rem;
  }
}
</style>
