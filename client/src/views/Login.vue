<template>
  <article class="max-600 box">
    <h1>Login</h1>
    <div class="field">
      <p class="control has-icons-left has-icons-right">
        <input class="input" type="email" placeholder="Email" />
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
        <input class="input" type="password" placeholder="Password" />
        <span class="icon is-small is-left">
          <i class="fas fa-lock"></i>
        </span>
      </p>
    </div>
    <div class="field is-grouped is-grouped-centered">
      <p class="control">
        <button class="button is-success">Login</button>
      </p>
    </div>
  </article>
</template>

<script>
import { logIn } from "@/api/login";
import { reactive, ref, computed } from "vue";
import { getLogInState } from "@/store/loginState";

export default {
  name: "Login",

  setup() {
    const input = reactive({ email: "", password: "" });
    const errorMessage = ref("");

    const onLogin = async () => {
      errorMessage.value = "";
      try {
        await logIn(input.email, input.password);
      } catch (error) {
        errorMessage.value = error.message;
      }
    };

    const waiting = computed(() => getLogInState().status === "loggingIn");

    return {
      input,
      errorMessage,
      onLogin,
      waiting
    };
  }
};
</script>

<style lang="scss" scoped></style>
