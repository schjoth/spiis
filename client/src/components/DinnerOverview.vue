<template>
  <div class="container">
    <Dinner
      v-for="dinner in dinners"
      v-bind:dinner="dinner"
      v-bind:key="dinner.id"
    />
    <div v-if="invite">
      <router-link to="/event/new" class="invite"> + </router-link>
    </div>
  </div>
</template>

<script lang="ts">
import Dinner from "./Dinner.vue";
export default {
  name: "DinnerOverview",
  components: {
    Dinner
  },
  props: ["dinners", "invite"]
};
</script>

<style lang="scss" scoped>
.container {
  display: flex;
  flex-direction: column;
  align-items: stretch;
  margin-top: -20px;
  margin-bottom: -20px;
}

.invite {
  line-height: 150px;
  height: 150px;
  font-size: 50px;
}
</style>
