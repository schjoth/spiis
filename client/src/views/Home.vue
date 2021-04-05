<template>
  <article>
    <div class="options">
      <label for="city"> Velg by:</label>
      <select name="city" id="city" v-model="cityFilter" v-if="allCities">
        <option v-for="(city, index) in allCities" :key="index" :value="city">
          {{ city }}
        </option>
      </select>
      <label for="date">Sorter på dato:</label>
      <select name="date" id="date" v-model="dateFilter" v-if="allDates">
        <option v-for="(title, index) in allDates" :key="index" :value="title">
          {{ title }}
        </option>
      </select>
    </div>
    <DinnerOverview
      :adverts="adverts"
      v-if="filteredByDate"
      :dinners="filteredByDate"
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
    const dateFilter = ref("Ingen");
    const filteredDinners = computed(() => {
      if (allDinners.value === null) return null;
      if (cityFilter.value === "Alle") return allDinners.value;
      return allDinners.value.filter((it) => it.city === cityFilter.value);
    });

    const filteredByDate = computed(() => {
      if (dateFilter.value === "Tidlig-sent")
        return filteredDinners.value
          ?.slice(0)
          .sort((a, b) => +new Date(a.date) - +new Date(b.date));
      if (dateFilter.value === "Sent-tidlig")
        return filteredDinners.value
          ?.slice(0)
          .sort((a, b) => +new Date(b.date) - +new Date(a.date));
      if (dateFilter.value === "Nyeste")
        return filteredDinners.value
          ?.slice(0)
          .sort((a, b) => +new Date(b.createdTime) - +new Date(a.createdTime));
      if (dateFilter.value === "Eldste")
        return filteredDinners.value
          ?.slice(0)
          .sort((a, b) => +new Date(a.createdTime) - +new Date(b.createdTime));
      return filteredDinners.value;
    });

    const allCities = computed(() => {
      if (allDinners.value === null) return null;
      return ["Alle", ...new Set(allDinners.value.map((it) => it.city))];
    });

    const allDates = new Set();
    allDates.add("Ingen");
    allDates.add("Tidlig-sent");
    allDates.add("Sent-tidlig");
    allDates.add("Nyeste");
    allDates.add("Eldste");

    async function fetchData() {
      const unfilteredDinners = await getAllDinners();

      allDinners.value = unfilteredDinners.filter(
        (dinner) =>
          Date.parse(dinner.date + " " + dinner.startTime) >= Date.now()
      );
      adverts.value = await getAllAdverts();
    }
    onMounted(fetchData);

    return {
      adverts,
      cityFilter,
      allCities,
      filteredDinners,
      dateFilter,
      allDates,
      filteredByDate
    };
  }
};
</script>

<style lang="scss" scoped>
.options {
  margin-bottom: 10px;
}
</style>

<style lang="scss" scoped>
.options {
  margin-bottom: 10px;
}
</style>
