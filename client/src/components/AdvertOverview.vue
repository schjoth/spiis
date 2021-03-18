<template>
  <article>
    <h1>Annonser</h1>
    <router-link to="/admin/ads/new"> Legg til annonse </router-link>
    <Advertisement
      v-for="(advert, index) in adverts"
      :ad="advert"
      :key="index"
      style="border: 1px solid black"
    ></Advertisement>
  </article>
</template>

<script lang="ts">
import Advertisement from "@/components/Advertisement.vue";
import { onMounted, ref } from "vue";
export default {
  name: "AdvertOverview",
  components: { Advertisement },
  setup() {
    const adverts = ref([]);

    async function fetchData() {
      //TODO: koble til backend
      //adverts.value = await getAdverts()
      adverts.value = [
        {
          link: "www.vg.no",
          owner: "VG"
        },
        {
          link: "www.google.no",
          owner: "Google"
        }
      ];
    }
    onMounted(fetchData);

    return { adverts };
  }
};
</script>
