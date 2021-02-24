<template>
  <article v-if="dinner">
    <h1>{{ dinner.title }}</h1>
    <p>
      <b>Arrangør: </b>
      <router-link :to="'/user/' + dinner.host.id">
        {{ dinner.host.firstName }} {{ dinner.host.lastName }}
      </router-link>
    </p>
    <p>
      <b>Antall gjester:</b> {{ dinner.guests.length }} /
      {{ dinner.maxGuests }}
    </p>
    <p>
      <b>Sted:</b> {{ dinner.addressLine }} {{ dinner.postCode }}
      {{ dinner.city }}
    </p>
    <p><b>Beskrivelse: </b> {{ dinner.description }}</p>
    <GuestList
      :guests="dinner.guests"
      :isHost="isHost"
      v-if="isGuest || isHost"
      @remove="removeGuestFromDinner"
    ></GuestList>
    <router-link :to="'/event/' + dinner.id + '/edit'" v-if="isHost">
      rediger
    </router-link>
    <button
      type="button"
      v-on:click="addGuestToDinner"
      v-if="!isGuest && !isHost"
    >
      Meld deg på
    </button>
    <button type="button" v-on:click="removeFromDinner" v-else-if="!isHost">
      Meld meg av
    </button>
  </article>
</template>

<script lang="ts">
import { DinnerResponse } from "@/api/types";
import { getDinner, addGuest, removeGuest } from "@/api/dinner";
import { useRoute } from "vue-router";
import { computed, onMounted, ref } from "vue";
import { getLogInState } from "@/store/loginState";
import GuestList from "@/components/GuestList.vue";

export default {
  name: "DinnerEvent",
  components: {
    GuestList
  },
  setup() {
    const dinnerId = useRoute().params.dinnerId;
    const dinner = ref<DinnerResponse | null>(null);
    const userId = getLogInState().user?.id;

    async function fetchData() {
      dinner.value = await getDinner(dinnerId);
    }

    const isHost = computed(() => dinner.value?.host.id === userId);

    const isGuest = computed(() =>
      dinner.value?.guests?.some((a) => a.id == userId)
    );

    async function addGuestToDinner() {
      await addGuest(dinnerId, userId?.toString()!);
      fetchData();
    }

    async function removeFromDinner() {
      await removeGuest(dinnerId, userId?.toString()!);
      fetchData();
    }

    async function removeGuestFromDinner(guestId: string) {
      await removeGuest(dinnerId, guestId);
      fetchData();
    }

    onMounted(fetchData);

    return {
      dinner,
      isHost,
      isGuest,
      addGuestToDinner,
      removeFromDinner,
      removeGuestFromDinner
    };
  }
};
</script>
