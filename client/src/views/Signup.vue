<template>
  <main id="form">
    <h1>Ny Bruker</h1>
    <div class="twocol">
      <label>
        Fornavn
        <input type="text" name="firstName" v-model="input.firstName" />
      </label>
      <label>
        Etternavn
        <input type="text" name="lastName" v-model="input.lastName" />
      </label>
    </div>
    <label>
      E-post
      <input type="text" name="email" v-model="input.email" />
    </label>
    <label>
      Passord
      <input type="password" name="password" v-model="input.password" />
    </label>
    <label>
      Gjennta passord
      <input type="password" name="password2" v-model="input.password2" />
    </label>
    <label>
      By
      <input type="text" name="location" v-model="input.location" />
    </label>
    <label>
      Alder
      <input type="number" name="age" v-model="input.age" />
    </label>
    <button v-on:click="signupClick">Ny bruker</button>
  </main>
</template>

<script lang="ts">
import { reactive } from "vue";

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

    const signupClick = () => {
      console.log("Signing up!");
    };

    return { input, signupClick };
  }
};
</script>

<style lang="scss" scoped>
@import "src/assets/form.scss";
</style>
