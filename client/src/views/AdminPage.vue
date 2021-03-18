<template>
  <article class="max-500 box">
    <div v-if="amIAdmin === true">Du er administrator!</div>
    <AdminLogin v-else @tryToken="adminBootstrap" />
  </article>
</template>

<script lang="ts">
import { computed, ref } from "vue";
import { makeMeAdmin } from "@/api/admin";
import { useRouter } from "vue-router";
import { tryReusingToken } from "@/api/login";
import { getLogInState } from "@/store/loginState";
import AdminLogin from "@/components/AdminLogin.vue";

export default {
  name: "AdminPage",
  components: { AdminLogin },
  setup() {
    const errorText = ref<null | string>(null);
    const amIAdmin = computed(() => getLogInState().user?.admin);

    const router = useRouter();

    const adminBootstrap = async (adminToken: string) => {
      try {
        errorText.value = null;
        await makeMeAdmin(adminToken);
        await tryReusingToken(); //To get our User info again
        await router.replace("/MyProfile");
      } catch (e) {
        errorText.value = `Error: ${e.message}`;
      }
    };

    return {
      amIAdmin,
      errorText,
      adminBootstrap
    };
  }
};
</script>

<style lang="scss" scoped></style>
