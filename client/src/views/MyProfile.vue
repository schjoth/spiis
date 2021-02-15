<template>
  <Profile v-bind:user="user"></Profile>
</template>

<script>
import Profile from "../components/Profile";
export default {
  name: "MyProfile",
  components: {
    Profile
  },
  data: function () {
    return {
      user: {
        firstname: "Ola",
        lastname: "Nordmann",
        email: "ola_nordmann@gmail.com",
        location: "Trondheim"
      }
    };
  }
};
</script>
