<template>
  <article class="box" v-if="dinner">
    <div class="hero cancelled" v-if="dinner?.cancelled">
      <div class="is-flex-grow-1"></div>
      <div>Middagen er avlyst!</div>
      <div class="is-flex-grow-1"></div>
      <button
        class="button is-primary"
        v-if="isHost"
        v-on:click="unCancelDinner"
      >
        Angre
      </button>
    </div>
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
    <router-link :to="'/event/' + dinner?.id + '/edit'" v-if="isHost">
      Rediger
    </router-link>
    <a v-on:click="cancelDinner" v-if="isHost && !dinner.cancelled">Avlys</a>
    <button
      type="button"
      v-on:click="addGuestToDinner"
      v-if="!isGuest && !isHost"
    >
      Meld deg på
    </button>
    <button
      type="button"
      v-on:click="removeGuestFromDinner"
      v-else-if="!isHost"
    >
      Meld meg av
    </button>
  </article>
  <article v-else>Laster inn middag</article>
</template>

<script lang="ts">
import { DinnerResponse } from "@/api/types";
import {
  getDinner,
  addGuest,
  removeGuest,
  setDinnerCancelled
} from "@/api/dinner";
import { useRoute, useRouter } from "vue-router";
import { computed, onMounted, ref } from "vue";
import { getLogInState } from "@/store/loginState";
import { authorized } from "@/api/client";

export default {
  name: "DinnerEvent",
  setup() {
    const dinnerId = +useRoute().params.dinnerId;
    if (!Number.isInteger(dinnerId)) useRouter().replace("/404");
    const dinner = ref<DinnerResponse | null>(null);
    const userId = computed(() => getLogInState().user?.id);

    async function fetchData() {
      dinner.value = await getDinner(dinnerId);
    }

    const isHost = computed(() => dinner.value?.host.id === userId.value);

    const isGuest = computed(() =>
      dinner.value?.guests?.some((a) => a.id == userId.value)
    );

    async function addGuestToDinner() {
      await addGuest(dinnerId, userId.value!);
      await fetchData();
    }

    async function removeGuestFromDinner() {
      await removeGuest(dinnerId, userId.value!);
      await fetchData();
    }

    async function cancelDinner() {
      await setDinnerCancelled(dinnerId, true);
      await fetchData();
    }

    async function unCancelDinner() {
      await setDinnerCancelled(dinnerId, false);
      await fetchData();
    }

    onMounted(() => authorized(fetchData));

    return {
      dinner,
      isHost,
      isGuest,
      addGuestToDinner,
      removeGuestFromDinner,
      cancelDinner,
      unCancelDinner
    };
  }
};
</script>

<style lang="scss">
.cancelled {
  padding: 20px;
  margin-bottom: 10px;
  background-color: rgb(255, 220, 85);
  color: black;
  font-size: 20px;
  display: flex;
  flex-direction: row;
  align-items: baseline;

  .button {
  }
}
</style>
