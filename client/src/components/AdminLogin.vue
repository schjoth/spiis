<template>
  <div>
    <div class="field">
      <label class="label">Admin bootstrap-token</label>
      <div class="control">
        <input
          class="input"
          type="text"
          placeholder="token"
          v-model="adminToken"
        />
      </div>
    </div>
    <div class="error" v-if="errorText">{{ errorText }}</div>
    <div class="control">
      <button class="button is-link" v-on:click="$emit('tryToken', adminToken)">
        Submit
      </button>
    </div>
  </div>
</template>

<script lang="ts">
import { ref } from "vue";

export default {
  name: "AdminLogin",
  emits: {
    tryToken: (payload: string) => {
      return payload;
    }
  },
  setup() {
    const adminToken = ref("");
    return { adminToken };
  }
};
</script>
