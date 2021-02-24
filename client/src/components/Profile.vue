<template>
  <section class="box">
    <p><b>Fornavn: </b>{{ user.firstName }}</p>
    <p><b>Etternavn: </b>{{ user.lastName }}</p>
    <p><b>Alder: </b>{{ user.age }}</p>
    <p v-if="isMyUser"><b>Epost: </b>{{ user.email }}</p>
    <p><b>By: </b>{{ user.city }}</p>
    <p>
      <b>Allergier: </b>
      <span v-for="(allergy, index) in user.allergies" :key="index">
        <span v-if="index !== 0">, </span>
        {{ allergy }}
      </span>
    </p>
  </section>
</template>

<script lang="ts">
export default {
  name: "Profile",
  props: {
    user: Object,
    isMyUser: Boolean
  }
};
</script>
