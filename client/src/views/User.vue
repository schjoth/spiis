<template>
  <Profile></Profile>
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
      user: {
        firstname: "Ola",
        lastname: "Nordmann",
        email: "ola_nordmann@gmail.com",
        postcode: "7069, Trondheim",
        allergies: "Gluten"
      },
      isMyUser: false
    };
  }
};
</script>
