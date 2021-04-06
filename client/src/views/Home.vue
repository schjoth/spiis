<template>
  <article>
    <div class="options">
      <label for="city"> Velg by:</label>
      <select name="city" id="city" v-model="cityFilter" v-if="allCities">
        <option v-for="(city, index) in allCities" :key="index" :value="city">
          {{ city }}
        </option>
      </select>
    </div>
    <DinnerOverview
      :adverts="adverts"
      v-if="filteredDinners"
      :dinners="filteredDinners"
    />
  </article>
</template>
s
<script lang="ts">
import DinnerOverview from "@/components/DinnerOverview.vue";
import { getAllDinners } from "@/api/dinner";
import { computed, onMounted, ref } from "vue";
import { AdvertResponse, DinnerResponse } from "@/api/types";
import { getAllAdverts } from "@/api/adverts";

export default {
  name: "Home",
  components: {
    DinnerOverview
  },
  setup() {
    const allDinners = ref<DinnerResponse[] | null>(null);
    const adverts = ref<AdvertResponse[] | null>(null);

    const cityFilter = ref("Alle");
    const filteredDinners = computed(() => {
      if (allDinners.value === null) return null;
      if (cityFilter.value === "Alle") return allDinners.value;
      return allDinners.value.filter((it) => it.city === cityFilter.value);
    });

    const allCities = computed(() => {
      if (allDinners.value === null) return null;
      return ["Alle", ...new Set(allDinners.value.map((it) => it.city))];
    });

    async function fetchData() {
      const unfilteredDinners = await getAllDinners();

      allDinners.value = unfilteredDinners.filter(
        (dinner) =>
          Date.parse(dinner.date + " " + dinner.startTime) >= Date.now()
      );
      adverts.value = await getAllAdverts();
    }
    onMounted(fetchData);

    return { adverts, cityFilter, allCities, filteredDinners };
  }
};
</script>

<style lang="scss" scoped>
.options {
  margin-bottom: 10px;
}
</style>
