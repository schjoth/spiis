<template>
  <div class="dinners">
    <Dinner
      v-for="(dinner, index) in dinners"
      :dinner="dinner"
      :key="index"
      :order="(index + 1) * spacing"
    />
    <Advertisement
      v-for="(advert, index) in adverts?.slice(
        0,
        dinners?.length / advertFrequency
      )"
      :ad="advert"
      :key="index"
      :order="(index + 1) * advertFrequency * spacing"
    />
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
  props: {
    dinners: Array,
    adverts: {
      type: Array,
      default() {
        return [];
      }
    }
  },
  setup() {
    const advertFrequency = 4;
    const spacing = 2;
    return { advertFrequency, spacing };
  }
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
  font-size: 50pt;
  width: 100%;
  background-color: white;
  padding-top: 260%;
  padding-bottom: 267%;
  padding-left: 50%;
  padding-right: 50%;
  border-radius: 20px;
}

.invite:hover,
a.invite:hover {
  color: #413d39;
}

@media only screen and (max-width: 980px) {
  .invite {
    box-sizing: unset;
  }

  .dinners {
    justify-content: center;
  }

  div {
  }

  .invite {
    justify-content: center;
    padding-left: 41.75vw;
    padding-right: 41.75vw;
    padding-top: 0;
    padding-bottom: 0;
    line-height: 150px;
  }
}

@media only screen and (max-width: 375px) {
  .invite {
    justify-content: center;
    padding-left: 39vw;
    padding-right: 39vw;
    line-height: 150px;
    margin-left: 20px;
    margin-right: 20px;
  }
}
</style>
