<template>
  <article id="profile">
    <Profile :user="user" :is-my-user="true"></Profile>
  </article>
</template>

<script lang="ts">
import Profile from "../components/Profile.vue";
import { getLogInState } from "@/store/loginState";
import { computed } from "vue";
export default {
  name: "MyProfile",
  components: {
    Profile
  },
  setup() {
    const user = computed(() => getLogInState().user);
    return { user };
  }
};
</script>

<style lang="scss" scoped></style>
