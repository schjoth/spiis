<template>
  <Profile v-bind:user="user" v-bind:is-my-user="isMyProfile"></Profile>
</template>

<script>
import Profile from "../components/Profile";
export default {
  name: "profile",
  components: {
    Profile
  },
  data: () => {
    return {
      isMyProfile: false,
      userId: this.$route.params.userId,
      user: {
        //TODO: replace meta date with http request on userId
        id: 2,
        firstname: "Ola",
        lastname: "Nordmann",
        email: "ola_nordmann@gmail.com",
        postcode: "7069, Trondheim",
        allergies: "Gluten"
      }
    };
  }
};
</script>
