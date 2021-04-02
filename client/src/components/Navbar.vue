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

      <div :class="'navbar-burger burgerIcon' + abcd" role="button" v-on:click="toggleHamburger"  aria-label="menu" aria-expanded="false" data-target="navbarBasicExample">
        <span aria-hidden="true" class="L1"></span>
        <span aria-hidden="true" class="L2"></span>
        <span aria-hidden="true" class="L1"></span>
      </div>

      <!--
      <div class="burgerIcon" v-on:click="toggleHamburger">
        <div class="L1"></div>
        <div class="L2"></div>
        <div class="L3"></div>
      </div>
      -->

      <div :class="'hamburgerDiv' + abc">
        <li><router-link to="/" class="navbar-item"> Forside </router-link></li>
        <li>
          <router-link to="/MyDinners" class="navbar-item"
            >Mine Middager</router-link
          >
        </li>
        <li>
          <router-link to="/event/new" class="navbar-item"
            >Inviter til Middag</router-link
          >
        </li>
        <li>
          <router-link class="navbar-item" to="/admin" v-if="isAdmin"
            >Dashboard</router-link
          >
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
            <router-link to="/Login" class="button is-light">
              Logg inn
            </router-link>
          </div>
        </li>
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

    const abc = ref<string>("");
    const abcd = ref<string>("");
    function toggleHamburger() {
      abc.value = "hamburgerDiv-active";
      abcd.value = "exit";
    }

    return {
      loggedIn,
      loggedOut,
      name,
      isAdmin,
      abc,
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
nav {
  position: fixed;
  width: 100%;
}

.navbar-item img {
  max-height: 90px;
}

a.navbar-item:focus,
a.navbar-item:focus-within,
a.navbar-item:hover,
a.navbar-item.is-active,
.navbar-link:focus,
.navbar-link:focus-within,
.navbar-link:hover,
.navbar-link.is-active {
  color: white;
  font-weight: bolder;
  font-size: larger;
  background-color: transparent;
}

.hamburgerDiv{
  display: none; /*hamburger menyen skal ikke syntes før hamburger ikonet er synlig og trykket på*/

  /* Styling felles for hamburgermenyen i begge media queriene: */
  z-index: 3;
  position: absolute;
  top: 60px;
  padding: 0;
  background: rgba(9, 7, 10, 0.918);
  height: 100vh; /*gir hamburgermenyen høyden til hele viewporten*/
  width: 100vw; /*gir hamburgermenyen bredden til hele viewporten*/
  color: white;
}


.burgerIcon{
  //display: none; /*hamburger ikonet skal syntes når det er behov for å bruke hamburgermenyen (skjermen er såpass liten at det ikke
    //er særlig brukervennlig å ha menyen i navbaren)*/

  /*plasserer ikonet*/
  position: absolute;
  cursor: pointer;
  right: 5%;
  top: 50%;
  transform: translate(-5%, -50%);
}

.burgerIcon div{
  z-index: 3;
  width: 30px;
  height: 3px;
  margin: 6px;
  background-color: white;
  border-radius: 5px;
}


.hamburgerDiv{
  font-family: Georgia, 'Times New Roman', Times, serif;
  font-size: 14pt;
  letter-spacing: 5px;
  flex-direction: column;
  align-items: center;
  justify-content: space-around;

  transform: translate(-100%);
  transition: transform 0.15s ease-in;

}

ul, li{
  list-style-type: none;
}

.hamLinks:hover, .hamburgerDiv .dropbutton:hover{
  font-size: 18pt;
}

/* Viser hamburgermenyen når brukeren trykker på hamburger ikonet */
.hamburgerDiv-active{
  transform: translateX(0%);
}



/* endrer hamburger-ikonet til et kryss, slik at det blir tydelig for brukeren at vedkommende må trykke
på samme punkt på skjermen for å gå ut av hamburgermenyen (om vedkommende ikke trykker på en av linkene)*/
.exit .L1{
  transform: rotate(-45deg) translate(-8px, 5px);
  background-color: rgb(153, 153, 153);
}

.exit .L2{
  background-color: transparent;
}

.exit .L3{
  transform: rotate(45deg) translate(-8px, -5px);
  background-color: rgb(153, 153, 153);
}


@media only screen and (max-width: 980px){
  .burgerIcon{
    display: block;
  }

  .hamburgerDiv {
    display: flex;
  }

}





</style>
