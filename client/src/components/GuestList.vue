<template>
  <div class="guest-list">
    <div class="guest" v-for="guest in guests" v-bind:key="guest.id">
      <div class="remove-area" v-if="isHost">
        <a
            class="remove"
            v-if="removing !== guest.id"
            v-on:click="removing = guest.id"
        >
          X <span></span>
        </a>
        <div v-else>
          <a class="remove" v-on:click="$emit('remove', guest.id)"> Kick </a>
          <a class="remove" v-on:click="$emit('block', guest.id)"> Block </a>
        </div>
      </div>
      <router-link :to="'/user/' + guest.id">
        {{ guest.firstName }} {{ guest.lastName }}
      </router-link>
      <!--<div class="is-flex-grow-1"></div>-->
    </div>
  </div>
</template>

<script lang="ts">
import { ref } from "vue";

export default {
  name: "GuestList",
  props: { guests: Array, isHost: Boolean },
  emits: ["remove", "block"],
  setup() {
    const removing = ref<number | null>(null);
    return {
      removing
    };
  }
};
</script>

<style lang="scss" scoped>
.guest-list {
  display: flex;
  flex-direction: column;
  flex-direction: column;
  border-left: 2px solid gray;
  margin-right: 5px;
  margin-left: 5px;
}

.guest {
  display: flex;
  padding: 3px;
  //border-bottom: 2px solid #777;
}

.remove {
  color: red;
  font-weight: bolder;
  padding: 0 5px;
}

.remove:hover {
  text-decoration: underline;
}

span{
  display: inline-block;
  width: 20px;
}

</style>
