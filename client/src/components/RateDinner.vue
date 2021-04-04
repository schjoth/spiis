<template>
  <div class="box">
    <p>
      Arrangør:
      <router-link :to="'/user/' + dinner.host.id">
        {{ dinner.host.firstName + " " + dinner.host.lastName }}
      </router-link>
    </p>

    <p>
      Arrangement:
      <router-link :to="'/event/' + dinner.id">
        {{ dinner.title }}
      </router-link>
    </p>
    <br />
    <p>
      <label :for="'rating' + dinner.id">Din vurdering: </label>
      <input
        type="number"
        min="1"
        max="6"
        :id="'rating' + dinner.id"
        placeholder="1 - 6"
        v-model="rating"
      />
    </p>

    <p>
      <input type="submit" v-on:click="$emit('rateHost', rating, dinner.id)" />
    </p>

    <p v-if="errorMessage">{{ errorMessage }}</p>
  </div>
</template>

<script lang="ts">
import { ref } from "vue";

export default {
  name: "RateDinner",
  props: {
    dinner: Object,
    errorMessage: {
      type: String,
      default: null
    }
  },
  emits: ["rateHost"],
  setup() {
    const rating = ref<number | null>(null);

    return {
      rating
    };
  }
};
</script>

<style lang="scss">
.box {
  width: auto;
  height: auto;
  margin: 20px;
}
</style>
