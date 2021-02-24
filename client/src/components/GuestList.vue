<template>
  <p><b>Gjester:</b></p>
  <ul>
    <li v-for="guest in guests" v-bind:key="guest.id">
      <router-link :to="'/user/' + guest.id"
        >{{ guest.firstName }} {{ guest.lastName }}</router-link
      >
      <a class="remove" v-if="isHost"
        v-on:click="$emit('remove', guest.id)"
      >
        X
      </a>
    </li>
  </ul>
</template>

<script lang="ts">
export default {
  name: "GuestList",
  props: { guests: Array, isHost: Boolean },
  emits: {
    remove: (payload: number) => {
      console.log(payload.toString());
      return payload.toString();
    }
  }
};
</script>

<style lang="scss" scoped>
.remove {
  color: red;
  font-weight: bolder;
}
</style>