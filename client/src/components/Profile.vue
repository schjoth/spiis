<template>
  <div>
    <p><b>Fornavn:</b> {{ user.firstName }}</p>
    <p><b>Etternavn:</b> {{ user.lastName }}</p>
    <p><b>Alder:</b> {{ user.age }}</p>
    <p v-if="isMyUser"><b>Epost:</b> {{ user.email }}</p>
    <p v-if="isMyUser">
      <b>Adresse:</b> {{ user.addressLine }} {{ user.postCode }},
      {{ user.city }}
    </p>
    <p v-else><b>By:</b> {{ user.city }}</p>
    <p>
      <b>Allergier:</b>
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
    user: Object,
    isMyUser: Boolean
  }
};
</script>
