<template>
  <!-- en eller annen form for å gi en verdi -->
  <div class="rating">
    <p>Arrangør: {{ dinner.host.firstName + " " + dinner.host.lastName }}</p>

    <p v-if="errorMessage">{{ errorMessage }}</p>
  </div>
</template>

<script lang="ts">
import { computed, ref } from "vue";
import { getLogInState } from "../store/loginState";
import { setHostRating } from "@/api/rating.ts";
import { DinnerResponse } from "@/api/types";

export default {
  name: "RateDinner",
  props: {
    dinner: Object
  },
  setup(props: { dinner: DinnerResponse }) {
    //const userId = computed(() => getLogInState().user?.id);
    const rating = ref<number | null>(null);
    const errorMessage = ref<string | null>(null);

    async function rateHost() {
      errorMessage.value = null;
      if (rating.value === null) {
        errorMessage.value = "Du må velge et tall";
      } else {
        try {
          await setHostRating(props.dinner.id, rating.value);
        } catch (error) {
          errorMessage.value = error.message;
        }
      }
    }

    return {
      errorMessage,
      rating
    };
  }
};
</script>

<style lang="scss">
.rating {
  width: 400px;
  height: 400px;
  display: block;
}
</style>
