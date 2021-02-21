<template>
  <article id="profile">
    <Profile :user="user" :is-my-user="true"></Profile>
  </article>
</template>

<script lang="ts">
import Profile from "../components/Profile.vue";
import { getLogInState } from "@/store/loginState";
import { ref } from "vue";
export default {
  name: "MyProfile",
  components: {
    Profile
  },
  setup() {
    const user = ref(getLogInState().user);
    return { user };
  }
};
</script>

<style lang="scss" scoped></style>
