<template>
  <article>
    <h1>Annonser</h1>
    <p class="new_advert">
      <router-link to="/admin/ads/new"> Legg til annonse </router-link>
    </p>
    <div v-for="(advert, index) in adverts" :key="index" class="advert_box">
      <p>{{ advert.owner }}; "{{ advert.title }}"</p>
      <Advertisement
        :ad="advert"
        :key="advert.id"
        class="advert"
      ></Advertisement>
      <a v-on:click="deleteAdvert(advert.id)">Slett denne annonsen</a>
    </div>
  </article>
</template>

<script lang="ts">
import Advertisement from "@/components/Advertisement.vue";
import { onMounted, ref } from "vue";
export default {
  name: "AdvertOverview",
  components: { Advertisement },
  setup() {
    //Mildertidige verider
    const basevalues = [
      {
        id: 1,
        link: "www.vg.no",
        owner: "VG",
        title: "Dager er ikke det samme uten VG"
      },
      {
        id: 2,
        link: "www.google.no",
        owner: "Google",
        title: "Med google kan du grave frem gamle bilder av vennene dine"
      }
    ];
    const adverts = ref(basevalues);

    async function fetchData() {
      //TODO: koble til backend
      //adverts.value = await getAdverts()
    }
    onMounted(fetchData);

    async function deleteAdvert(advertId: number) {
      //TODO: legg til backendfunksjonalitet
    }

    return { adverts, deleteAdvert };
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
