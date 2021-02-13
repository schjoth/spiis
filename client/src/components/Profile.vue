<template>
  <p>Fornavn: {{ user.firstname }}</p>
  <p>Etternavn: {{ user.lastname }}</p>
  <p>Epost: {{ user.email }}</p>
  <p>Adresse: {{ user.location }}</p>
</template>

<script>
export default {
  name: "Profile",
  props: ["user"]
};
</script>
