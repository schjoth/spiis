<template>
  <div class="box max-500" v-if="user">
    <h1 class="has-text-centered">{{ user.firstName }} {{ user.lastName }}</h1>
    <div class="info_options_divider">
      <div class="user_info">
        <p v-if="userIsAdmin" class="admin_text">Administrator</p>
        <p><b>Alder: </b>{{ user.age }}</p>
        <p v-if="isMyUser || loggedInAsAdmin"><b>Epost: </b>{{ user.email }}</p>
        <p><b>By: </b>{{ user.city }}</p>
        <p>
          <b>Allergier: </b>
          {{ user.allergies.join(", ") }}
        </p>
      </div>

      <div class="admin_options">
        <p>
          <a
            v-if="loggedInAsAdmin && !isMyUser"
            class="admin_toggle"
            v-on:click="toggleAdmin"
          >
            {{
              userIsAdmin ? "Frata administratorrolle" : "Gi administratorrolle"
            }}
          </a>
        </p>
        <p v-if="adminErrorText" class="admin_error">{{ adminErrorText }}</p>
        <p>
          <a
            class="remove"
            v-if="loggedInAsAdmin"
            v-on:click="$emit('deleteProperty', user.id)"
          >
            Slett bruker
          </a>
        </p>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { getLogInState } from "@/store/loginState";
import { computed, ref, watchEffect } from "vue";
import { UserResponse } from "@/api/types";
import { setAdministrator } from "@/api/admin";

export default {
  name: "Profile",
  props: {
    user: Object,
    isMyUser: Boolean
  },
  setup(props: { user: UserResponse | null; isMyUser: boolean }) {
    const loggedInAsAdmin = computed(
      () => getLogInState().user?.admin === true
    );
    const userIsAdmin = ref<boolean>(false);
    const adminErrorText = ref<string | null>(null);

    watchEffect(() => {
      userIsAdmin.value = props.user?.admin === true;
    });

    const toggleAdmin = async () => {
      const newState = !userIsAdmin.value;

      try {
        adminErrorText.value = null;
        await setAdministrator(props.user?.id!, newState);
        userIsAdmin.value = newState;
      } catch (e) {
        adminErrorText.value = `Failed setting admin: ${e.message}`;
      }
    };

    return {
      userIsAdmin,
      adminErrorText,
      loggedInAsAdmin,
      toggleAdmin
    };
  }
};
</script>

<style lang="scss" scoped>
.box {
  margin: auto;
  color: #323232;
  border-radius: 20px;
  padding: 30px;
  background-color: white;
}

.info_options_divider {
  display: flex;
  flex-direction: row;
}

.admin_options {
  margin-left: 20px;
  text-align: end;

  p {
    margin: 0 20px;
  }
}

.user_info {
  margin-right: auto;
}

.admin_text {
  font-size: 1.3rem;
  color: brown;
}

.admin_error {
  color: darkred;
}
</style>
