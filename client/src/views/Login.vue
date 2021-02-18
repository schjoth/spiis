<template>
  <main>
    <div id="login">
      <h1>Login</h1>
      <input
        type="text"
        name="email"
        v-model="input.email"
        placeholder="Email"
      />
      <input
        type="password"
        name="password"
        v-model="input.password"
        placeholder="Password"
      />
      <div class="error" v-if="errorMessage">Error: {{ errorMessage }}</div>
      <button type="button" v-on:click="onLogin" v-bind:disabled="waiting">
        Login
      </button>
    </div>
  </main>
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

<style lang="scss" scoped>
#login {
  max-width: 500px;
  padding: 20px;
  margin: auto;

  display: flex;
  flex-direction: column;
  align-items: center;
  border: 1px solid #cccccc;
  background-color: #ffffff;
}

#login > * {
  margin: 10px 0;
}

input[type="text"],
input[type="password"] {
  max-width: 400px;
  width: 100%;
}

button {
  max-width: 200px;
  width: 100%;
}

.error {
  color: red;
}
</style>
