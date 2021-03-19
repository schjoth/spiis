<template>
  <div class="box" :style="{ order }">
    <router-link :to="'/event/' + dinner.id">
      <h1>
        {{ dinner.title }}
        <span v-if="dinner.cancelled" class="cancelled">(AVLYST!)</span>
      </h1>
    </router-link>
    <p class="dateLine">
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

  .date {
    font-weight: bold;
  }

  .dateLine {
    margin-bottom: 10px;
  }
}

@media only screen and (max-width: 980px) {
  .box {
    box-sizing: border-box;
    width: 100%;
  }
}

.cancelled {
  color: red;
  font-style: normal;
  font-size: 1.4rem;
  font-family: sans-serif;
}
</style>
