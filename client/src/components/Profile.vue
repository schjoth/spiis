<template>
  <div id="innlogging">
    <p>Fornavn: {{ user.firstName }}</p>
    <p>Etternavn: {{ user.lastName }}</p>
    <p v-if="isMyUser">Epost: {{ user.email }}</p>
    <p>By: {{ user.location }}</p>
    <p>
      Allergier:
      <span v-for="(allergy, index) in user.allergies" :key="index">
        {{ allergy }}
      </span>
    </p>
  </div>
</template>

<script lang="ts">
export default {
  name: "Profile",
  props: {
    user: Object
  }
};
</script>
