<template>
  <p>Fornavn: {{ user.firstname }}</p>
  <p>Etternavn: {{ user.lastname }}</p>
  <p v-if="isMyUser">Epost: {{ user.email }}</p>
  <p v-if="isMyUser">Adresse: {{ user.location }}</p>
  <p v-else>Adresse: {{ user.postcode }}</p>
  <p>Allergener: {{ user.allergies }}</p>
</template>

<script lang="ts">
export default {
  name: "Profile",
  props: ["user", "isMyUser"]
};
</script>
