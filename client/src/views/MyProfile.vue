<template>
  <main id="profile">
    <Profile v-bind:user="user" v-bind:is-my-user="isMyProfile"></Profile>
  </main>
</template>

<script>
import Profile from "../components/Profile";
export default {
  name: "MyProfile",
  components: {
    Profile
  },
  data: () => {
    return {
      isMyProfile: true,
      userId: "", //TODO: replace with logged in user
      user: {
        //TODO: replace meta date with http request on userId
        firstname: "Ola",
        lastname: "Nordmann",
        email: "ola_nordmann@gmail.com",
        location: "gate 1, 7069, Trondheim",
        postcode: "7069, Trondheim",
        allergies: "Skalldyr"
      }
    };
  }
};
</script>

<style lang="scss" scoped></style>
