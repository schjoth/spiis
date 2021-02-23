<template>
  <nav class="navbar" role="navigation" aria-label="main navigation">
    <div class="navbar-brand">
      <router-link class="navbar-item" to="/">
        <img
          src="@/assets/Spiis_logo.png"
          title="Tilbake til forsiden"
          alt="Spiis-logo"
        />
      </router-link>

      <a
        role="button"
        class="navbar-burger"
        aria-label="menu"
        aria-expanded="false"
        data-target="navbarBasicExample"
      >
        <span aria-hidden="true"></span>
        <span aria-hidden="true"></span>
        <span aria-hidden="true"></span>
      </a>
    </div>

    <div id="navbarBasicExample" class="navbar-menu">
      <div class="navbar-start">
        <router-link to="/" class="navbar-item"> Middager </router-link>

        <router-link to="/MyDinners" class="navbar-item">
          Mine Middager
        </router-link>

        <router-link to="/event/new" class="navbar-item">
          Inviter til Middag
        </router-link>
      </div>

      <div class="navbar-end">
        <router-link class="navbar-item" to="/MyProfile" v-if="loggedIn">
          {{ name }}
        </router-link>
        <div class="navbar-item" v-if="loggedIn">
          <div class="buttons">
            <a class="button is-dark" v-on:click="logOut">
              <strong>Logg ut</strong>
            </a>
          </div>
        </div>
        <div class="navbar-item" v-if="loggedOut">
          <div class="buttons">
            <router-link to="/signup" class="button is-primary">
              <strong>Opprett bruker</strong>
            </router-link>
            <router-link to="/Login" class="button is-light">
              Logg inn
            </router-link>
          </div>
        </div>
      </div>
    </div>
  </nav>
</template>

<script lang="ts">
import { computed } from "vue";
import { getLogInState } from "@/store/loginState";
import { logOut } from "@/api/login";
import router from "@/router";

export default {
  name: "Navbar",

  setup() {
    const loggedIn = computed(() => getLogInState().status === "loggedIn");
    const loggedOut = computed(() => getLogInState().status === "loggedOut");
    const name = computed(
      () =>
        `${getLogInState().user?.firstName} ${getLogInState().user?.lastName}`
    );

    return {
      loggedIn,
      loggedOut,
      name,
      logOut: async () => {
        await router.replace("/");
        await logOut();
      }
    };
  }
};
</script>

<style lang="scss" scoped>
.navbar-item img {
  max-height: 90px;
}
</style>
