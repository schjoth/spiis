<template>
  <article class="max-600">
    <h1>Ny Bruker</h1>
    <div class="field">
      <label class="label">Fornavn</label>
      <div class="control">
        <input
          class="input"
          type="text"
          placeholder=""
          v-model="input.firstName"
        />
      </div>
    </div>
    <div class="field">
      <label class="label">Etternavn</label>
      <div class="control">
        <input
          class="input"
          type="text"
          placeholder=""
          v-model="input.lastName"
        />
      </div>
    </div>
    <div class="field">
      <label class="label">E-post</label>
      <div class="control">
        <input class="input" type="text" placeholder="" v-model="input.email" />
      </div>
    </div>
    <div class="field">
      <label class="label">Passord</label>
      <div class="control">
        <input
          class="input"
          type="password"
          placeholder=""
          v-model="input.password"
        />
      </div>
    </div>
    <div class="field">
      <label class="label">Gjenta passord</label>
      <div class="control">
        <input
          class="input"
          type="password"
          placeholder=""
          v-model="input.password2"
        />
      </div>
    </div>
    <div class="field">
      <label class="label">By</label>
      <div class="control">
        <input class="input" type="text" placeholder="" v-model="input.city" />
      </div>
    </div>
    <div class="field">
      <label class="label">Alder</label>
      <div class="control">
        <input class="input" type="number" placeholder="" v-model="input.age" />
      </div>
    </div>
    <div class="field">
      <label class="label">Allergier</label>
      <div class="control">
        <input
          class="input"
          type="text"
          placeholder="Gluten, skalldyr, ++"
          v-model="input.allergies"
        />
      </div>
    </div>
    <div class="content has-text-centered" v-if="errorMessage">
      <p class="has-text-danger">
        {{ errorMessage }}
      </p>
    </div>
    <div class="field is-grouped is-grouped-centered">
      <div class="control">
        <button
          class="button is-primary"
          v-on:click="signupClick"
          :disabled="waiting"
        >
          Ny bruker
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
      postCode: "",
      city: "",
      age: 0,
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
        allergies: input.allergies.split(", ").map((it) => it.trim())
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

<style lang="scss" scoped></style>
