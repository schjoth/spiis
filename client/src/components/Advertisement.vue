<template>
  <div class="ad-box" :style="{ order }">
    <a :href="'//' + ad.link">
      <div class="inner-ad-box">
        <img :src="ad.image" :alt="ad.owner" />
      </div>
    </a>
  </div>
</template>

<script lang="ts">
export default {
  name: "Advertisement",
  props: {
    ad: Object,
    /*
    ad.link - link til annonsøren sin nettside
    ad.owner - annonsøren
    ad.image - bildet til annonsen
     */
    order: Number
  }
};
</script>

<style lang="scss" scoped>
.ad-box {
  min-height: 100px;
  background-color: white;
  width: 84%;
  height: auto;
  border-radius: 20px;
  margin-bottom: 20px;
  overflow: hidden;
}

.inner-ad-box {
  width: 100%;
  height: 100%;
  align-content: center;

  img {
    width: 100%;
    height: auto;
  }
}

@media only screen and (max-width: 980px) {
  .ad-box {
    box-sizing: border-box;
    width: 100%;
  }
}
</style>
