<template>
  <NewDinner :dinner="dinner" :edit="true" v-if="dinner" />
  <article class="has-text-centered" v-else>
    Henter informasjon fra server...
  </article>
</template>

<script lang="ts">
import NewDinner from "../components/NewDinner.vue";
import { useRoute, useRouter } from "vue-router";
import { onMounted, ref, watchEffect } from "vue";
import { getDinner } from "@/api/dinner";
import { DinnerResponse } from "@/api/types";
import { getLogInState } from "@/store/loginState";
export default {
  name: "EditEvent",
  components: { NewDinner },

  setup() {
    const id = +useRoute().params.dinnerId;
    if (!Number.isInteger(id)) useRouter().replace("/404");
    const dinner = ref<DinnerResponse | null>(null);
    async function fetchData() {
      if (getLogInState().user?.id === undefined) return;
      dinner.value = await getDinner(id);
    }
    onMounted(() => watchEffect(fetchData));
    return { dinner, id };
  }
};
</script>
