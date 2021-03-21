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

    <p class="category">BESKRIVELSE</p>
    <p class="description">{{ dinner.description }}</p>


    <div class="info">
      <p class="category">INFO</p>
      <table>
      <tr>
        <td>
          <b>Arrangør: </b>
        </td>
        <td>
          <router-link :to="'/user/' + dinner.host.id">
          {{ dinner.host.firstName }} {{ dinner.host.lastName }}
        </router-link>
        </td>
      </tr>
      <tr>
        <td>
          <b>Sted:</b>
        </td>
        <td>
          {{ dinner.addressLine }}, {{ dinner.postCode }}
        {{ dinner.city }}
        </td>
      </tr>
      </table>
    </div>

    <div class="expenses">
      <p class="category">UTGIFTER</p>
      <table>
        <tr>
          <td><b>Totalt: </b></td>
          <td>{{ dinner.expenses }}</td>
        </tr>
        <tr>
          <td><b>Pr. pers: </b></td>
          <td>{{ dinner.expenses / (dinner.guests.length + 1) }}</td>
        </tr>
      </table>
    </div>

    <p class="category">GJESTER ({{ dinner.guests.length }}/{{ dinner.maxGuests }}):</p>
    <GuestList
      :guests="dinner.guests"
      :isHost="isHost"
      v-if="isGuest || isHost"
      @remove="removeGuestFromDinner"
    />
    <router-link :to="'/event/' + dinner.id + '/edit'" v-if="isHost">
      Rediger
    </router-link>
    <a v-on:click="cancelDinner" v-if="isHost && !dinner.cancelled"  class="avlys">Avlys</a>

    <button type="button" v-on:click="addToDinner" v-if="!isGuest && !isHost">
      Meld deg på
    </button>
    <button type="button" v-on:click="removeFromDinner" v-else-if="!isHost">
      Meld meg av
    </button>
  </article>
  <article v-else>Laster inn middag...</article>
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
import GuestList from "@/components/GuestList.vue";

export default {
  name: "DinnerEvent",
  components: {
    GuestList
  },
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

    async function addToDinner() {
      await addGuest(dinnerId, userId.value!);
      await fetchData();
    }

    async function removeFromDinner() {
      await removeGuest(dinnerId, userId.value!);
      await fetchData();
    }

    async function removeGuestFromDinner(guestId: number) {
      await removeGuest(dinnerId, guestId);
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
      addToDinner,
      removeFromDinner,
      removeGuestFromDinner,
      cancelDinner,
      unCancelDinner
    };
  }
};
</script>

<style lang="scss" scoped>
.cancelled {
  padding: 20px;
  margin-bottom: 10px;
  background-color: rgb(255, 220, 85);
  color: black;
  font-size: 20px;
  display: flex;
  flex-direction: row;
  align-items: baseline;
}

.button:focus:not(:active),
.button.is-primary:focus:not(:active) {
  background-color: #fda45705;
  font-style: normal;
}

.category {
  font-size: 15pt;
}

.rediger {
  margin-top: 20px;
  font-weight: lighter;
}

.rediger, .avlys {
  display: block;
}

.description {
  margin-bottom: 20px;
}

.info {
  min-width: 50%;
}

td {
  padding-right: 10px;
  padding-top: 5px;
}

.expenses {
  margin-top: 20px;
  margin-bottom: 20px;
}
</style>
