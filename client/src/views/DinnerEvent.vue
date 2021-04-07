<template>
  <article class="box" v-if="dinner">
    <div class="hero cancelled" v-if="dinner?.cancelled">
      <div class="is-flex-grow-1"></div>
      <div>
        {{
          dinner.lockedByAdmin
            ? "Middagen har blitt avlyst av admin"
            : "Middagen er avlyst!"
        }}
      </div>
      <div class="is-flex-grow-1"></div>
      <button
        class="button is-primary"
        v-if="isHost && !dinner.lockedByAdmin"
        v-on:click="unCancelDinner"
      >
        Angre
      </button>
    </div>

    <div class="is-flex is-flex-wrap-wrap-reverse">
      <div class="is-flex-grow-1"></div>
      <p class="dateLine">
        kl. {{ dinner.startTime }} - {{ dinner.endTime }} [<span class="date">{{
          dinner.date
        }}</span
        >]
      </p>
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
          <td v-if="dinner.host.averageHostRating !== null">
            <img src="@/assets/star.svg" width="16" />
            {{ dinner.host.averageHostRating.toFixed(1) }} / 6
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
          <td>
            {{ Math.floor(dinner.expenses / (dinner.guests.length + 1)) }}
          </td>
        </tr>
      </table>
    </div>

    <p class="category">
      GJESTER ({{ dinner.guests.length }}/{{ dinner.maxGuests }}):
    </p>

    <GuestList
      :guests="dinner.guests"
      :isHost="isHost"
      v-if="isGuest || isHost"
      @remove="removeGuestFromDinner"
      @block="blockGuestFromDinner"
    />

    <button type="button" v-on:click="addToDinner" v-if="!isGuest && !isHost">
      Meld deg på
    </button>
    <button type="button" v-on:click="removeFromDinner" v-else-if="!isHost">
      Meld meg av
    </button>

    <div class="settings">
      <router-link
        :to="'/event/' + dinner.id + '/edit'"
        class="rediger"
        v-if="isHost && !dinner.lockedByAdmin"
      >
        <img src="@/assets/edit.svg" width="16" />
        Rediger
      </router-link>

      <a
        v-on:click="cancelDinner"
        class="avlys"
        v-if="isHost && !dinner.cancelled"
      >
        <img src="@/assets/x-circle.svg" width="16" />
        Avlys
      </a>

      <a v-on:click="toggleAdminCancelDinner" class="avlys" v-if="isAdmin">
        <img src="@/assets/x-circle.svg" width="16" />
        {{
          dinner.lockedByAdmin ? "Åpne arrangement" : "Avlys og lås arrangement"
        }}
      </a>

      <a href="mailto:report@spiis.no" v-if="!isHost">
        <img src="@/assets/report.svg" width="16" />
        Rapporter Arrangement
      </a>
    </div>

    <p v-if="errorText" class="error">{{ errorText }}</p>

    <p class="category">KOMMENTARER</p>

    <CommentSection
      :dinner-id="dinner.id"
      :is-guest="isGuest"
      :is-host="isHost"
      :is-admin="isAdmin"
    />
  </article>
  <article v-else>Laster inn middag...</article>
</template>

<script lang="ts">
import { DinnerResponse } from "@/api/types";
import {
  getDinner,
  addGuest,
  removeGuest,
  setDinnerCancelled,
  blockGuest,
  lockDinnerByAdmin
} from "@/api/dinner";
import { useRoute, useRouter } from "vue-router";
import { computed, onMounted, ref } from "vue";
import { getLogInState } from "@/store/loginState";
import { authorized } from "@/api/client";
import GuestList from "@/components/GuestList.vue";
import CommentSection from "@/components/comments/CommentSection.vue";

export default {
  name: "DinnerEvent",
  components: {
    CommentSection,
    GuestList
  },
  setup() {
    const dinnerId = +useRoute().params.dinnerId;
    if (!Number.isInteger(dinnerId)) useRouter().replace("/404");
    const dinner = ref<DinnerResponse | null>(null);
    const userId = computed(() => getLogInState().user?.id);
    const errorText = ref("");

    async function fetchData() {
      dinner.value = await getDinner(dinnerId);
    }

    const isHost = computed(() => dinner.value?.host.id === userId.value);

    const isGuest = computed(() =>
      dinner.value?.guests?.some((a) => a.id == userId.value)
    );

    const isAdmin = computed(() => getLogInState().user?.admin === true);

    function errorWrapped<T>(func: (args: T) => void): (args: T) => void {
      return async (args: T) => {
        try {
          errorText.value = "";
          await func(args);
          await fetchData();
        } catch (e) {
          errorText.value = e.message;
        }
      };
    }

    const addToDinner = errorWrapped(
      async () => await addGuest(dinnerId, userId.value!)
    );
    const removeFromDinner = errorWrapped(
      async () => await removeGuest(dinnerId, userId.value!)
    );
    const removeGuestFromDinner = errorWrapped(
      async (guestId: number) => await removeGuest(dinnerId, guestId)
    );
    const blockGuestFromDinner = errorWrapped(
      async (guestId: number) => await blockGuest(dinnerId, guestId)
    );
    const cancelDinner = errorWrapped(
      async () => await setDinnerCancelled(dinnerId, true)
    );
    const unCancelDinner = errorWrapped(
      async () => await setDinnerCancelled(dinnerId, false)
    );
    const toggleAdminCancelDinner = errorWrapped(
      async () =>
        await lockDinnerByAdmin(dinnerId, !dinner.value!.lockedByAdmin)
    );

    onMounted(() => authorized(fetchData));

    return {
      dinner,
      isHost,
      isGuest,
      isAdmin,
      addToDinner,
      removeFromDinner,
      removeGuestFromDinner,
      blockGuestFromDinner,
      cancelDinner,
      unCancelDinner,
      toggleAdminCancelDinner,
      errorText
    };
  }
};
</script>

<style lang="scss" scoped>
img {
  top: 1px;
  position: relative;
}

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

button {
  margin-bottom: 20px;
  margin-top: 10px;
}

.category {
  font-size: 15pt;
  margin-top: 20px;
}

.rediger,
.avlys {
  display: block;
}

.description {
  margin-bottom: 20px;
}

.settings {
  margin-top: 20px;
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

.error {
  color: red;
}
</style>
