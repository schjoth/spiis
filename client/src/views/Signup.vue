<template>
  <main class="max-600">
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
        <input
          class="input"
          type="text"
          placeholder=""
          v-model="input.location"
        />
      </div>
    </div>
    <div class="field">
      <label class="label">Alder</label>
      <div class="control">
        <input class="input" type="number" placeholder="" v-model="input.age" />
      </div>
    </div>
    <div class="field is-grouped is-grouped-centered">
      <div class="control">
        <button class="button is-primary" v-on:click="signupClick">
          Ny bruker
        </button>
      </div>
    </div>
  </main>
</template>

<script lang="ts">
import { reactive, ref } from "vue";
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
      location: "",
      age: 0
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
        location: input.location,
        age: +input.age,
        allergies: []
      };

      try {
        await signUp(signUpRequest);
      } catch (error) {
        errorMessage.value = error.message;
      }
    };

    return { input, errorMessage, signupClick };
  }
};
</script>

<style lang="scss" scoped></style>
