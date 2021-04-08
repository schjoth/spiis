<template>
  <div class="ad-box" :style="{ order }">
    <a :href="'//' + ad.link">
      <div class="inner-ad-box">
        <img :src="ad.picture" :alt="ad.title" />
      </div>
    </a>
  </div>
</template>

<script lang="ts">
export default {
  name: "Advertisement",
  props: {
    ad: Object,
    order: Number
  }
};
</script>

<style lang="scss" scoped>
.ad-box {
  min-height: 100px;
  background-color: #ff8c00;
  width: 84%;
  min-width: 335px;
  height: auto;
  border-radius: 20px;
  margin-bottom: 20px;
  overflow: hidden;
  padding: 7px;
}

.inner-ad-box {
  width: 100%;
  height: 100%;
  align-content: center;

  img {
    width: 100%;
    height: auto;
    border-radius: 20px;
  }
}

@media only screen and (max-width: 980px) {
  .ad-box {
    box-sizing: border-box;
    width: 100vw;
  }

  .inner-ad-box {
    width: auto;
    height: auto;
  }
}

@media only screen and (max-width: 375px) {
}
</style>
