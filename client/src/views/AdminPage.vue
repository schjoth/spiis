<template>
  <article class="box">
    <div v-if="amIAdmin === true" class="admin-box">
      <div class="view">
        <router-view></router-view>
      </div>
      <div class="menu">
        <p><router-link to="/admin/info">Informasjon</router-link></p>
        <p><router-link to="/admin/ads">Annonser</router-link></p>
      </div>
    </div>
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

<style lang="scss" scoped>
.admin-box {
  display: flex;
  flex-direction: row;
  flex-flow: row;
}

.menu {
  border-left: 1px solid black;
  margin-left: 20px;

  p {
    margin: 0 20px;
  }
}

.view {
  margin-right: auto;
}
</style>
