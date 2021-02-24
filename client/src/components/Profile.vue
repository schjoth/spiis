<template>
  <div class="box has-text-centered">
    <h1>{{ user.firstName }} {{ user.lastName }}</h1>
    <p><b>Alder: </b>{{ user.age }}</p>
    <p v-if="isMyUser"><b>Epost: </b>{{ user.email }}</p>
    <p><b>By: </b>{{ user.city }}</p>
    <p>
      <b>Allergier: </b>
      {{ user.allergies.join(", ") }}
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
