<template>
  <div class="box" :style="{ order }">
    <table class="timeAndDate">
      <tr>
        <td>
          <span class="date0">
            <img
              src="@/assets/calendar.svg"
              alt="Sted"
              width="16"
              height="16"
            />
            {{ dinner.date }}</span
          >
        </td>
        <td class="time">
          <img src="@/assets/clock.svg" alt="Sted" width="16" height="16" />
          {{ dinner.startTime }} - {{ dinner.endTime }}
        </td>
      </tr>
    </table>

    <p>
      <span class="date">
        <img src="@/assets/calendar.svg" alt="Sted" width="16" height="16" />
        {{ dinner.date }}</span
      >
    </p>
    <p class="dateLine">
      <img src="@/assets/clock.svg" alt="Sted" width="16" height="16" />
      {{ dinner.startTime }} - {{ dinner.endTime }}

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
      <img src="@/assets/users.svg" alt="Sted" width="20" height="20" />
      {{ dinner.guests.length }}/{{ dinner.maxGuests }}
    </p>

    <p class="location">
      <img src="@/assets/map-pin.svg" alt="Sted" width="20" height="20" />
      {{ dinner.postCode }}, {{ dinner.city }}
    </p>
  </div>
</template>

<script lang="ts">
export default {
  name: "Dinner",
  props: {
    dinner: Object,
    order: Number
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

  .host {
    margin-bottom: 15px;
  }

  .location {
    position: absolute;
  }
}

.timeAndDate {
  display: none;
}

.cancelled {
  color: red;
  font-style: normal;
  font-size: 1.4rem;
  font-family: sans-serif;
}

@media only screen and (max-width: 980px) {
  .box {
    box-sizing: border-box;
    width: 100%;
  }

  .dateLine,
  .date {
    display: none;
  }

  .timeAndDate {
    display: inline;
    width: 100%;
    font-size: 16px;

    img {
      width: 14px;
      height: 14px;
    }
  }

  .time {
    padding-left: 25px;
  }
}

@media only screen and (max-width: 375px) {
  .time {
    padding-left: 24vw;
  }
}
</style>
