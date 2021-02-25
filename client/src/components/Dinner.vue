<template>
  <div class="box">
    <router-link :to="'/event/' + dinner.id">
      <h1>
        {{ dinner.title }}
        <span v-if="dinner.cancelled" class="cancelled">(AVLYST!)</span>
      </h1>
    </router-link>
    <p>
      <span class="date">{{ dinner.date }}</span> {{ dinner.startTime }} -
      {{ dinner.endTime }}
    </p>
    <p>
      <b>Antall gjester:</b> {{ dinner.guests.length }}/{{ dinner.maxGuests }}
    </p>
    <p><b>Sted:</b> {{ dinner.postCode }}, {{ dinner.city }}</p>
    <p>
      <b>Arrangør: </b>
      <router-link :to="'/user/' + dinner.host.id">
        {{ dinner.host.firstName }} {{ dinner.host.lastName }}
      </router-link>
    </p>
  </div>
</template>

<script lang="ts">
export default {
  name: "Dinner",
  props: {
    dinner: Object
  }
};
</script>

<style lang="scss" scoped>
div {
  width: 40%;
  height: 300px;
  border-radius: 20px;

  h1 {
    color: inherit;
  }
}

.cancelled {
  color: red;
  font-style: normal;
  font-size: 1.4rem;
  font-family: sans-serif;
}

.date {
  font-weight: bold;
}
</style>
