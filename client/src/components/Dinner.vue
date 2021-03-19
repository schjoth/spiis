<template>
  <div class="box">
    <p class="dateLine">
      {{ dinner.startTime }} - {{ dinner.endTime }} [<span class="date">{{ dinner.date }}</span>]
      <b><span v-if="dinner.cancelled" class="cancelled">AVLYST</span></b>
    </p>

    <router-link :to="'/event/' + dinner.id">
      <h1>
        {{ dinner.title }}
      </h1>
    </router-link>
    <p class="host">
      hos
      <router-link :to="'/user/' + dinner.host.id">
        {{ dinner.host.firstName }} {{ dinner.host.lastName }}
      </router-link>
    </p>
    <p class="guests">
      {{ dinner.guests.length }}/{{ dinner.maxGuests }} gjester påmeldt
    </p>
    <p class="location">
      <img src="assets/location-icon.png" alt="Sted" width="40" height="40"> {{ dinner.postCode }}, {{ dinner.city }}
    </p>
  </div>
  
</template>

<script lang="ts">
export default {
  name: "Dinner",
  props: {
    dinner: Object
  }
};
</script>

<style lang="scss" scoped>
.box {
  width: 40%;
  height: 300px;
  border-radius: 20px;

  h1 {
    color: inherit;
  }

  .date {
    font-size: small;
    justify-content: right;
  }

  .dateLine {
    margin-bottom: 10px;
  }

  .guests{}

  .host{
    margin-bottom: 15px;
  }

  .location{
    position: absolute;
  }


}

@media only screen and (max-width: 980px) {
  .box {
    box-sizing: border-box;
    width: 100%;
  }
}

.cancelled {
  color: red;
  font-style: normal;
  font-size: 1.4rem;
  font-family: sans-serif;
}

</style>
