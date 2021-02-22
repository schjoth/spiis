<template>
  <article>
    <p>id: {{ $route.params.dinnerId }}</p>
    <p>Denne siden vil ikke vise noe den før koblet til databasen</p>
    <!-- <h1>{{ dinner.title }}</h1>
    <p><b>Antall gjester:</b> {{ dinner.guests }}/{{ dinner.maxGuests }}</p>
    <p><b>Sted:</b> {{ dinner.location }}</p>
    <p><b>Beskrivelse: </b> {{ dinner.description }}</p>
    -->
    <router-link :to="'/event/' + $route.params.dinnerId + '/edit'">
      rediger
    </router-link>
  </article>
</template>

<script lang="ts">
export default {
  name: "DinnerEvent"
  //TODO hent middag som har id $route.params.dinnerId )
};
</script>
