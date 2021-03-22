<template>
  <h1>Liste over registrerte brukere</h1>
  <ul v-if="users">
    <li v-for="(user, index) in users" :key="index">
      <router-link :to="'/user/' + user.id">
        {{ user.firstName + " " + user.lastName }}</router-link
      >
    </li>
  </ul>
</template>

<script lang="ts">
import { onMounted, ref } from "vue";
import { UserResponse } from "@/api/types";
import { getAllUsers } from "@/api/user";
import { authorized } from "@/api/client";

export default {
  name: "AdminUserOverview",
  setup: function () {
    const users = ref<UserResponse[] | null>(null);

    async function fetchData() {
      users.value = await getAllUsers();
    }

    onMounted(() => authorized(fetchData));

    return { users };
  }
};
</script>
