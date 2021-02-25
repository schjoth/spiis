<template>
  <div>
    <h1>{{ dinner.title }} <span v-if="dinner.cancelled" class="cancelled">(AVLYST!)</span></h1>
    <p>
      <span class="date">{{ dinner.date }}</span> {{dinner.startTime}} - {{dinner.endTime}}
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
    <p>
      <router-link :to="'/event/' + dinner.id"> Gå til påmelding </router-link>
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
  background-color: white;
  color: black;
  border-radius: 20px;

  padding: 30px;
  margin: 2%;

  h1 {
    color: inherit;
  }
}

.cancelled {
  color: red;
}

.date {
  font-weight: bold;
}

</style>
