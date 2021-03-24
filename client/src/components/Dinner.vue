<template>
  <div class="box" :style="{ order }">
    <p>
      <span class="date">
        <img src="@/assets/calendar.svg" alt="Sted" width="16" height="16" />
        {{ dinner.date }}</span
      >
    </p>
    <p class="dateLine">
      <img src="@/assets/clock.svg" alt="Sted" width="16" height="16" />
      {{ dinner.startTime }} - {{ dinner.endTime }}

      <b><span v-if="dinner.cancelled" class="cancelled">AVLYST</span></b>

      <span class="date0"
        >,
        <img src="@/assets/calendar.svg" alt="Sted" width="16" height="16" />
        {{ dinner.date }}</span
      >
    </p>

    <router-link :to="'/event/' + dinner.id">
      <h1>
        {{ dinner.title }}
      </h1>
    </router-link>

    <p class="host">
      hos
      <router-link :to="'/user/' + dinner.host.id">
        {{ dinner.host.firstName }} {{ dinner.host.lastName }}
      </router-link>
    </p>

    <p class="guests">
      <img src="@/assets/users.svg" alt="Sted" width="20" height="20" />
      {{ dinner.guests.length }}/{{ dinner.maxGuests }}
    </p>

    <p class="location">
      <img src="@/assets/map-pin.svg" alt="Sted" width="20" height="20" />
      {{ dinner.postCode }}, {{ dinner.city }}
    </p>
  </div>
</template>

<script lang="ts">
export default {
  name: "Dinner",
  props: {
    dinner: Object,
    order: Number
  }
};
</script>

<style lang="scss" scoped>
.box {
  width: 40%;
  height: 300px;
  border-radius: 20px;

  h1 {
    color: inherit;
  }

  .guests {
  }

  .host {
    margin-bottom: 15px;
  }

  .location {
    position: absolute;
  }

  .date0 {
    display: none;
  }
}

.cancelled {
  color: red;
  font-style: normal;
  font-size: 1.4rem;
  font-family: sans-serif;
}

@media only screen and (max-width: 980px) {
  .box {
    box-sizing: border-box;
    width: 100%;
  }

  .date {
    display: none;
  }

  .date0 {
    display: inline;
  }
}

@media only screen and (max-width: 375px) {
}
</style>
