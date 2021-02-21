<template>
  <article>
    <Profile :user="user" :is-my-user="isMyProfile"></Profile>
  </article>
</template>

<script lang="ts">
import Profile from "../components/Profile.vue";
import { useRoute } from "vue-router";
import { computed, reactive, watch } from "vue";
import { getLogInState } from "@/store/loginState";
import { UserResponse } from "@/api/types";
export default {
  name: "profile",
  components: {
    Profile
  },
  setup() {
    const user = reactive({
      id: 2,
      firstName: "Ola",
      lastName: "Nordmann",
      email: "ola_nordmann@gmail.com",
      location: "Trondheim",
      allergies: ["Gluten"]
    } as UserResponse);
    const isMyProfile = computed(() => user.id === getLogInState().user?.id);

    watch(
      () => useRoute().params,
      (newParams) => {
        const userId = +newParams.userId;
        console.log(`Route changed, new used id: ${userId}`);
      }
    );

    return { user, isMyProfile };
  }
};
</script>
