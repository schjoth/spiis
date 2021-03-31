<template>
  <article>
    <Profile :user="user" :is-my-user="isMyProfile" v-if="user" />
    <p class="standalone_p" v-else>
      Brukeren du leter etter finnes ikke eller har blitt slettet
    </p>
  </article>
</template>

<script lang="ts">
import Profile from "../components/Profile.vue";
import { useRoute } from "vue-router";
import { computed, onMounted, ref } from "vue";
import { getLogInState } from "@/store/loginState";
import { UserResponse } from "@/api/types";
import { getUser } from "@/api/user";
export default {
  name: "profile",
  components: {
    Profile
  },
  setup() {
    const id = useRoute().params.userId;
    const user = ref<UserResponse | null>(null);
    const isMyProfile = computed(
      () => user.value?.id === getLogInState().user?.id
    );

    async function fetchData() {
      user.value = await getUser(id);
    }
    onMounted(fetchData);

    return { user, isMyProfile };
  }
};
</script>
