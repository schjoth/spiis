<template>
  <div id="container">
    <Dinner
      v-for="dinner in dinners"
      v-bind:dinner="dinner"
      v-bind:key="dinner.id"
    />
    <div v-if="invite">
      <router-link to="/event/new" id="invite"> + </router-link>
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
  props: { dinners: Array, invite: Boolean }
};
</script>

<style lang="scss" scoped>
#container {
  display: flex;
  flex-wrap: wrap;
}
#invite {
  line-height: 300px;
  padding-left: 10%;
  padding-right: 1%;
  height: 150px;
  font-size: 50pt;
  text-shadow: 0px 0px 20px #000000;
}

#invite:hover{
  color: #ffffff;
}

</style>
