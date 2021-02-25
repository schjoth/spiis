<template>
  <div class="max-500">
    <p><b>Fornavn: </b>{{ user.firstName }}</p>
    <p><b>Etternavn: </b>{{ user.lastName }}</p>
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

<style lang="scss" scoped>
article {
  display: flex;
  justify-content: center;
}
</style>
