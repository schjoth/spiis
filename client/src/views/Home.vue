<template>
  <article>
    <div class="options field is-grouped is-align-items-baseline">
      <div class="control">
        <label for="city">Filter: </label>
        <select class="select" name="city" id="city" v-model="cityFilter">
          <option v-for="(city, index) in allCities" :key="index" :value="city">
            {{ city }}
          </option>
        </select>
      </div>
      <div class="is-flex-grow-1"></div>
      <div class="control">
        <label for="date">Sortering: </label>
        <select class="select" name="date" id="date" v-model="sorting">

        </select>
      </div>
    </div>
    <DinnerOverview
      :adverts="adverts"
      v-if="computedDinners"
      :dinners="computedDinners"
    />
  </article>
</template>
s
<script lang="ts">
import DinnerOverview from "@/components/DinnerOverview.vue";
import {getAllDinners, getAllFutureDinners} from "@/api/dinner";
import { computed, onMounted, ref } from "vue";
import { AdvertResponse, DinnerResponse } from "@/api/types";
import {getAllAdverts, getRandomAdverts} from "@/api/adverts";

export default {
  name: "Home",
  components: {
    DinnerOverview
  },
  setup() {
    const allDinners = ref<DinnerResponse[] | null>(null);
    const adverts = ref<AdvertResponse[] | null>(null);

    const cityFilter = ref("Alle");
    const sorting = ref("");

    const computedDinners = computed(() => {
      if (allDinners.value === null) return null;
      if (cityFilter.value === "Alle") return allDinners.value;
      return allDinners.value.filter((it) => it.city === cityFilter.value);
    });

    const allCities = computed(() => {
      if (allDinners.value === null) return ["Alle"];
      return ["Alle", ...new Set(allDinners.value.map((it) => it.city))];
    });

    async function fetchData() {
      allDinners.value = await getAllFutureDinners();
      const advertCount = Math.floor(allDinners.value.length / 3)-1;
      adverts.value = await getRandomAdverts(advertCount);
    }
    onMounted(fetchData);

    return {
      adverts,
      cityFilter,
      allCities,
      computedDinners,
      sorting,
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

