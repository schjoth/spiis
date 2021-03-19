<template>
  <article>
    <h1>Annonser</h1>
    <p class="new_advert">
      <router-link to="/admin/ads/new"> Legg til annonse </router-link>
    </p>
    <div v-if="adverts">
      <div v-for="(advert, index) in adverts" :key="index" class="advert_box">
        <p>
          {{ advert.companyName }}; <i>"{{ advert.title }}"</i>
        </p>
        <Advertisement
          :ad="advert"
          :key="advert.id"
          class="advert"
        ></Advertisement>
        <a v-on:click="removeAdvert(advert.id)">Slett denne annonsen</a>
      </div>
    </div>
  </article>
</template>

<script lang="ts">
import Advertisement from "@/components/Advertisement.vue";
import { onMounted, ref } from "vue";
import { AdvertResponse } from "@/api/types";
import { deleteAdvert, getAllAdverts } from "@/api/adverts";
export default {
  name: "AdvertOverview",
  components: { Advertisement },
  setup() {
    const adverts = ref<AdvertResponse[] | null>(null);

    async function fetchData() {
      adverts.value = await getAllAdverts();
    }
    onMounted(fetchData);

    async function removeAdvert(advertId: number) {
      await deleteAdvert(advertId);
      await fetchData();
    }
    return { adverts, removeAdvert };
  }
};
</script>

<style lang="scss" scoped>
.new_advert {
  margin-bottom: 20px;
}

.advert_box {
  margin: 20px 0;

  .advert {
    margin-bottom: 0;
    border: 1px solid black;
  }
}
</style>
