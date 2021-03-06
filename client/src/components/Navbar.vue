<template>
  <nav
    class="navbar is-spaced is-paddingless"
    role="navigation"
    aria-label="main navigation"
  >
    <div class="navbar-brand">
      <router-link class="navbar-item" to="/">
        <img
          src="@/assets/Spiis_logo.png"
          title="Tilbake til forsiden"
          alt="Spiis-logo"
        />
      </router-link>

      <div
        :class="'navbar-burger burgerIcon' + closeHam"
        role="button"
        v-on:click="toggleHamburger"
        aria-label="menu"
        aria-expanded="true"
        data-target=".hamburgerDiv"
      >
        <span aria-hidden="true" class="L1"></span>
        <span aria-hidden="true" class="L2"></span>
        <span aria-hidden="true" class="L1"></span>
      </div>
    </div>

    <div id="navbarBasicExample" class="navbar-menu">
      <div class="navbar-start">
        <router-link to="/" class="navbar-item"> Forside </router-link>

        <router-link to="/MyDinners" class="navbar-item"
          >Mine Middager</router-link
        >

        <router-link to="/event/new" class="navbar-item"
          >Inviter til Middag</router-link
        >
      </div>

      <div class="navbar-end">
        <router-link class="navbar-item" to="/admin" v-if="isAdmin"
          >Dashboard</router-link
        >
        <router-link class="navbar-item" to="/MyProfile" v-if="loggedIn">{{
          name
        }}</router-link>
        <div class="navbar-item" v-if="loggedIn">
          <div class="buttons">
            <a class="button is-primary" v-on:click="logOut">
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

  <div :class="'hamburgerDiv' + openHam">
    <li>
      <router-link to="/" class="navbar-item"> Forside </router-link>
    </li>
    <li>
      <router-link to="/MyDinners" class="navbar-item">
        Mine Middager
      </router-link>
    </li>
    <li>
      <router-link to="/event/new" class="navbar-item">
        Inviter til Middag
      </router-link>
    </li>
    <li>
      <router-link class="navbar-item" to="/admin" v-if="isAdmin">
        Dashboard
      </router-link>
    </li>
    <li>
      <router-link class="navbar-item" to="/MyProfile" v-if="loggedIn">{{
        name
      }}</router-link>
    </li>
    <li>
      <div class="navbar-item buttons" v-if="loggedIn">
        <a class="button is-primary" v-on:click="logOut">
          <strong>Logg ut</strong>
        </a>
      </div>
    </li>
    <li>
      <div class="navbar-item buttons" v-if="loggedOut">
        <router-link to="/signup" class="button is-primary">
          <strong>Opprett bruker</strong>
        </router-link>
      </div>
    </li>
    <li>
      <div class="navbar-item buttons" v-if="loggedOut">
        <router-link to="/Login" class="button is-primary">
          Logg inn
        </router-link>
      </div>
    </li>
    <!--
    <li>
      <div class="navbar-item buttons">
        <a v-on:click="toggleHamburger">Lukk</a>
      </div>
    </li>
    -->
  </div>
</template>

<script lang="ts">
import { computed, ref } from "vue";
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
    const isAdmin = computed(() => getLogInState().user?.admin);

    const openHam = ref<string>("");
    const closeHam = ref<string>("");

    const isHamburgerActive = ref<boolean>(false);

    const toggleHamburger = () => {
      isHamburgerActive.value = !isHamburgerActive.value;
      if (isHamburgerActive.value) {
        openHam.value = "hamburgerDiv-active";
        closeHam.value = "exit";
      } else {
        openHam.value = "";
        closeHam.value = "";
      }
    };

    return {
      loggedIn,
      loggedOut,
      name,
      isAdmin,
      openHam,
      closeHam,
      toggleHamburger,
      logOut: async () => {
        await logOut();
        await router.replace("/loggedOut");
      }
    };
  }
};
</script>

<style lang="scss" scoped>
a.navbar-item:focus,
a.navbar-item:focus-within {
  color: white;
  text-decoration: overline;
  text-decoration-color: rgba(255, 255, 255, 0.5);
}

a.navbar-item:focus-within,
a.navbar-item:hover,
a.navbar-item:focus {
  background-color: transparent;
}

nav {
  position: fixed;
  width: 100%;
}

.navbar-item img {
  max-height: 90px;
}

.hamburgerDivhamburgerDiv-active {
  z-index: 3;
  position: absolute;
  right: 0;
  text-align: right;
  top: 105px;
  padding: 0;
  min-width: 20vw; /*gir hamburgermenyen bredden til hele viewporten*/
  color: white;

  font-size: 14pt;
  //letter-spacing: 5px;
  flex-direction: column;
  align-items: center;
  justify-content: space-around;

  transform: translateX(0%);
  background: rgb(227, 153, 87, 0.8);
}

.navbar-burger:hover {
  background-color: rgba(0, 0, 0, 0);

  span {
    color: rgba(255, 255, 255, 0.6);
  }
}

.hamburgerDiv {
  display: none;
  transform: translate(-120%);
  transition: transform 0.15s ease-in;
}

.navbar-burger {
  /*display: none; /*hamburger ikonet skal syntes når det er behov for å bruke hamburgermenyen (skjermen er såpass liten at det ikke
  //er særlig brukervennlig å ha menyen i navbaren)*/

  /*plasserer ikonet*/
  position: absolute;
  cursor: pointer;
  right: 5%;
  top: 50%;
  transform: translate(-5%, -50%);
  line-height: 1.5;
}

.navbar-burger span {
  z-index: 3;
  width: 25px;
  background-color: white;
  border-radius: 5px;
}

ul,
li {
  list-style-type: none;
}

.hamLinks:hover,
.hamburgerDiv .buttons:hover {
  font-size: 18pt;
}

/* endrer hamburger-ikonet til et kryss, slik at det blir tydelig for brukeren at vedkommende må trykke
  på samme punkt på skjermen for å gå ut av hamburgermenyen (om vedkommende ikke trykker på en av linkene)*/
.exit .L1 {
  transform: rotate(-45deg) translate(-8px, 5px);
  background-color: rgb(153, 153, 153);
}

.exit .L2 {
  background-color: transparent;
}

.exit .L3 {
  transform: rotate(45deg) translate(-8px, -5px);
  background-color: rgb(153, 153, 153);
}

@media only screen and (max-width: 980px) {
  .navbar-burger {
    display: block;
  }

  .hamburgerDiv {
    display: flex;
  }

  .hamburgerDiv-active {
    transform: translateX(0%);
  }

  .hamburgerDivhamburgerDiv-active {
    width: 100vw;
  }

  a.navbar-item:focus,
  a.navbar-item:focus-within,
  a.navbar-item:hover,
  a.navbar-item.is-active,
  .buttons:focus,
  .buttons:focus-within,
  .buttons:hover,
  .buttons.is-active {
    color: white;
    font-size: larger;
    background-color: rgb(227, 153, 87, 0.55);
  }

  #navbarBasixExample {
    display: none;
  }

  .buttons:last-child {
    margin-bottom: 0;
  }

  a.navbar-item:focus,
  a.navbar-item:focus-within,
  a.navbar-item:hover,
  a.navbar-item.is-active,
  .buttons:focus,
  .buttons:focus-within,
  .buttons:hover,
  .buttons.is-active {
    text-decoration: none;
  }
}

@media only screen and (max-width: 375px) {
  .hamburgerDiv {
    display: none;
    font-weight: bolder;
  }

  .hamburgerDivhamburgerDiv-active {
    top: 106px;
    width: 80vw;
    height: 100vh;
    background: rgb(227, 153, 87);
  }

  a.navbar-item:focus,
  a.navbar-item:focus-within,
  a.navbar-item:hover,
  a.navbar-item.is-active,
  .buttons:focus,
  .buttons:focus-within,
  .buttons:hover,
  .buttons.is-active {
    color: rgb(227, 153, 87);
    font-size: initial;
    background-color: rgb(255, 255, 255, 0.55);
  }
}
</style>
