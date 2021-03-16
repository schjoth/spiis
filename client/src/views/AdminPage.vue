<template>
  <article class="max-500 box">
    <div v-if="amIAdmin === true">Du er administrator!</div>
    <div v-if="amIAdmin === false">
      <div class="field">
        <label class="label">Admin bootstrap-token</label>
        <div class="control">
          <input
            class="input"
            type="text"
            placeholder="token"
            v-model="adminToken"
          />
        </div>
      </div>
      <div class="error" v-if="errorText">{{ errorText }}</div>
      <div class="control">
        <button class="button is-link" v-on:click="adminBootstrap">
          Submit
        </button>
      </div>
    </div>
  </article>
</template>

<script lang="ts">
import { computed, ref } from "vue";
import { makeMeAdmin } from "@/api/admin";
import { useRouter } from "vue-router";
import { tryReusingToken } from "@/api/login";
import { getLogInState } from "@/store/loginState";

export default {
  name: "AdminPage",
  setup() {
    const adminToken = ref("");
    const errorText = ref<null | string>(null);
    const amIAdmin = computed(() => getLogInState().user?.admin);

    const router = useRouter();

    const adminBootstrap = async () => {
      try {
        errorText.value = null;
        await makeMeAdmin(adminToken.value);
        await tryReusingToken(); //To get our User info again
        await router.replace("/MyProfile");
      } catch (e) {
        errorText.value = `Error: ${e.message}`;
      }
    };

    return {
      amIAdmin,
      adminToken,
      errorText,
      adminBootstrap
    };
  }
};
</script>

<style scoped></style>
