<template>
  <article>
    <p>id: {{ $route.params.dinnerId }}</p>
    <p>Denne siden vil ikke vise noe den før koblet til databasen</p>
    <!-- <h1>{{ dinner.title }}</h1>
    <p><b>Antall gjester:</b> {{ dinner.guests }}/{{ dinner.maxGuests }}</p>
    <p><b>Sted:</b> {{ dinner.location }}</p>
    <p><b>Beskrivelse: </b> {{ dinner.description }}</p>
    -->
  </article>
</template>

<script lang="ts">
//get dinner instance where id = $route.params.dinnerId
export default {
  name: "DinnerEvent"
  /* props: () -> {
    return GetDinnerFromServer( $route.params.dinnerId )
  }
  */
};
</script>
