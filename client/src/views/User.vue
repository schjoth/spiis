<template>
  <article>
    <Profile
      :user="user"
      :is-my-user="isMyProfile"
      v-if="canSeeUser"
      @blockThisUser="blockThisUser"
    />
    <p v-else class="standalone_p">
      Brukeren du leter etter finnes ikke, eller har blitt slettet.
    </p>
  </article>
</template>

<script lang="ts">
import Profile from "../components/Profile.vue";
import { useRoute } from "vue-router";
import { computed, onMounted, ref } from "vue";
import { getLogInState } from "@/store/loginState";
import { UserResponse } from "@/api/types";
import { blockUser, getUser } from "@/api/user";
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
      console.log(user.value.blocked);
    }
    onMounted(fetchData);

    async function blockThisUser() {
      if (user.value) await blockUser(user.value.id, true);
      await fetchData();
    }

    const amIAdmin = computed(() => getLogInState().user?.admin === true);

    const canSeeUser = computed(
      () => user.value != null && (user.value.blocked || amIAdmin)
    );

    return { user, isMyProfile, blockThisUser, canSeeUser };
  }
};
</script>
