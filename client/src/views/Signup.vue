<template>
  <article class="max-500 trans">

    <h1>Ny bruker</h1>

    <div class="field">
      <div class="control">
        <input
          class="input"
          type="text"
          placeholder="Fornavn"
          v-model="input.firstName"
        />
      </div>
    </div>

    <div class="field">
      <div class="control">
        <input
          class="input"
          type="text"
          placeholder="Etternavn"
          v-model="input.lastName"
        />
      </div>
    </div>

    <div class="field">
      <div class="control">
        <input
          class="input"
          type="text"
          placeholder="Alder"
          v-model="input.age"
        />
      </div>
    </div>

    <div class="field">
      <div class="control">
        <input
          class="input"
          type="text"
          placeholder="By"
          v-model="input.city"
        />
      </div>
    </div>

    <div class="field">
      <div class="control">
        <input
          class="input"
          type="text"
          placeholder="E-post"
          v-model="input.email"
        />
      </div>
    </div>

    <div class="field">
      <div class="control">
        <input
          class="input"
          type="password"
          placeholder="Passord"
          v-model="input.password"
        />
      </div>
    </div>

    <div class="field">
      <div class="control">
        <input
          class="input"
          type="password"
          placeholder="Gjenta passord"
          v-model="input.password2"
        />
      </div>
    </div>

    <!--
    <div class="field">
      <label class="label">Matprederanser/allergener</label>
      <div class="control">
        <input
          class="input"
          type="text"
          placeholder="Gluten, skalldyr, ++"
          v-model="input.allergies"
        />
      </div>
    </div>
-->

    <div class="content has-text-centered" v-if="errorMessage">
      <p class="has-text-danger">
        {{ errorMessage }}
      </p>
    </div>

    <div class="field is-grouped is-grouped-centered">
      <div class="control" id="NyBrukerKnapp">
        <button
          class="button is-primary"
          v-on:click="signupClick"
          :disabled="waiting"
        >
          Registrer ny bruker
        </button>
      </div>
    </div>

  </article>
</template>

<script lang="ts">
import { reactive, ref, computed } from "vue";
import { getLogInState } from "@/store/loginState";
import { signUp } from "@/api/login";
import { SignUpRequest } from "@/api/types";

export default {
  name: "Signup",
  setup() {
    const input = reactive({
      firstName: "",
      lastName: "",
      email: "",
      password: "",
      password2: "",
      city: "",
      age: "",
      allergies: ""
    });
    const errorMessage = ref("");

    const signupClick = async () => {
      errorMessage.value = "";
      if (input.password !== input.password2) {
        errorMessage.value = "Passord stemmer ikke overens";
        return;
      }

      const signUpRequest: SignUpRequest = {
        firstName: input.firstName,
        lastName: input.lastName,
        email: input.email,
        password: input.password,
        city: input.city,
        age: +input.age,
        allergies: input.allergies.split(",").map((it) => it.trim())
      };

      try {
        await signUp(signUpRequest);
      } catch (error) {
        errorMessage.value = error.message;
      }
    };

    const waiting = computed(() => getLogInState().status === "loggingIn");

    return { input, errorMessage, signupClick, waiting };
  }
};
</script>

<style lang="scss" scoped>

  .trans{
    padding-bottom: 5px;

    .button{
      font-size: 14pt;
    }

    .button:hover{
      background-color: white;
      border-radius: 40px;
      color: #323232;
    }

    .button :active{
      font-weight: bolder;
    }

  }

</style>
