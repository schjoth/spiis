<template>
  <div id="login">
    <h1>Login</h1>
    <label>
      <input
        type="text"
        name="email"
        v-model="input.email"
        placeholder="Email"
      />
    </label>
    <label>
      <input
        type="password"
        name="password"
        v-model="input.password"
        placeholder="Password"
      />
    </label>
    <div class="error" v-if="errorMessage">error: {{ errorMessage }}</div>
    <button type="button" v-on:click="login()">Login</button>
  </div>
</template>

<script>
import { logIn } from "@/api/login";
export default {
  name: "Login",

  data() {
    return {
      input: {
        email: "",
        password: ""
      },
      errorMessage: ""
    };
  },
  methods: {
    async login() {
      if (this.input.email !== "" && this.input.password !== "") {
        this.errorMessage = "";
        try {
          await logIn(this.input.email, this.input.password);
        } catch (error) {
          this.errorMessage = error.message;
        }
      }
    }
  }
};
</script>

<style scoped>
#login {
  width: 500px;
  border: 1px solid #cccccc;
  background-color: #ffffff;
  margin: auto;
  margin-top: 200px;
  padding: 20px;
}
</style>
