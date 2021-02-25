<template>
  <article class="max-500 trans">
    <h1>Logg inn med e-post og passord:</h1>
    <div class="field">
      <p class="control has-icons-left has-icons-right">
        <input
          class="input"
          type="email"
          placeholder="E-post"
          v-model="input.email"
        />
        <span class="icon is-small is-left">
          <i class="fas fa-envelope"></i>
        </span>
        <span class="icon is-small is-right">
          <i class="fas fa-check"></i>
        </span>
      </p>
    </div>
    <div class="field">
      <p class="control has-icons-left">
        <input
          class="input"
          type="password"
          placeholder="Passord"
          v-model="input.password"
        />
        <span class="icon is-small is-left">
          <i class="fas fa-lock"></i>
        </span>
      </p>
    </div>
    <div class="content has-text-centered" v-if="errorMessage">
      <p class="has-text-danger">
        {{ errorMessage }}
      </p>
    </div>
    <div class="field is-grouped is-grouped-centered">
      <p class="control">
        <button
          class="button is-superdark"
          :disabled="waiting"
          v-on:click="loginClicked"
        >
          LOGG INN
        </button>
      </p>
    </div>
  </article>
</template>

<script lang="ts">
import { logIn } from "@/api/login";
import { reactive, ref, computed } from "vue";
import { getLogInState } from "@/store/loginState";

export default {
  name: "Login",

  setup() {
    const input = reactive({ email: "", password: "" });
    const errorMessage = ref("");

    const loginClicked = async () => {
      errorMessage.value = "";
      try {
        await logIn(input.email, input.password);
      } catch (error) {
        if (error.status === 401)
          errorMessage.value = "Feil brukernavn / passord";
        else errorMessage.value = error.message;
      }
    };

    const waiting = computed(() => getLogInState().status === "loggingIn");

    return {
      input,
      errorMessage,
      loginClicked,
      waiting
    };
  }
};
</script>

<style lang="scss" scoped>
.trans {
  h1 {
    font-size: 12pt;
    font-style: italic;
  }
}
</style>
