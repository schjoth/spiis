<template>
  <div class="dinners">
    <Dinner
      v-for="(dinner, index) in dinners"
      :dinner="dinner"
      :key="index"
      :order="index * (adverts.length+1)"
    />
    <Advertisement
      v-for="(advert, index) in adverts"
      :ad="advert"
      :key="index"
      :order="(index + 1) * dinners.length"
    />
    <div v-if="invite">
      <router-link to="/event/new" class="invite"> + </router-link>
    </div>
  </div>
</template>

<script lang="ts">
import Dinner from "./Dinner.vue";
import Advertisement from "@/components/Advertisement.vue";
export default {
  name: "DinnerOverview",
  components: {
    Dinner,
    Advertisement
  },
  props: { dinners: Array, invite: Boolean, adverts: Array },
};
</script>

<style lang="scss" scoped>
.dinners {
  display: flex;
  flex-wrap: wrap;
  margin-left: -20px;
  margin-right: -20px;

  > * {
    margin-left: 2%;
    margin-right: 2%;
  }
}

.invite {
  line-height: 300px;
  padding-left: 10%;
  padding-right: 1%;
  height: 150px;
  font-size: 50pt;
  text-shadow: 0 0 20px #000000;
}

.invite:hover {
  color: #ffffff;
}

@media only screen and (max-width: 980px) {
  .invite {
    padding-left: 0;
    padding-right: 0;

    box-sizing: unset;
  }

  .dinners {
    justify-content: center;
  }
}
</style>
