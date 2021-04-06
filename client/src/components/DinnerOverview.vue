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
    flex-grow: 1;
    margin-left: 20px;
    margin-right: 20px;
  }
}

.invite {
  line-height: 300px;
  font-size: 50pt;
  width: 100%;
  background-color: white;
  padding: 260% 50% 267%;
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
    padding: 0 41.75vw;
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
