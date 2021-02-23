<template>
  <NewDinner :dinner="dinner" :edit="true" v-if="dinner" />
</template>

<script lang="ts">
import NewDinner from "../components/NewDinner.vue";
import { useRoute } from "vue-router";
import { onMounted, ref } from "vue";
import { getDinner } from "@/api/dinner";
import { DinnerResponse } from "@/api/types";
export default {
  name: "EditEvent",
  components: { NewDinner },

  setup() {
    const id = useRoute().params.dinnerId;
    const dinner = ref<DinnerResponse | null>(null);
    async function fetchData() {
      dinner.value = await getDinner(id);
    }
    onMounted(fetchData);
    return { dinner, id };
  }
};
</script>
