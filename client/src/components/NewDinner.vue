<template>
  <article class="max-600">
    <h1>Inviter til middag!</h1>
    <div class="field">
      <label class="label">Tittel</label>
      <div class="control">
        <input class="input" type="text" placeholder="" v-model="input.title" />
      </div>
    </div>
    <div class="field">
      <label class="label">Beskrivelse</label>
      <div class="control">
        <textarea class="textarea" placeholder="" v-model="input.description" />
      </div>
    </div>
    <div class="field">
      <label class="label">Sted</label>
      <div class="control">
        <input
          class="input"
          type="text"
          placeholder=""
          v-model="input.location"
        />
      </div>
    </div>
    <div class="field">
      <label class="label">Maks deltagere</label>
      <div class="control">
        <input
          class="input"
          type="number"
          placeholder=""
          v-model="input.maxGuests"
        />
      </div>
    </div>
    <div class="content has-text-centered" v-if="errorMessage">
      <p class="has-text-danger">
        {{ errorMessage }}
      </p>
    </div>
    <div class="field is-grouped is-grouped-centered">
      <div class="control">
        <button class="button is-primary" v-on:click="createClicked">
          Opprett arrangement!
        </button>
      </div>
    </div>
  </article>
</template>

<script lang="ts">
import { ref, reactive } from "vue";

export default {
  name: "NewDinner",

  setup() {
    const input = reactive({
      title: "",
      description: "",
      location: "",
      maxGuests: ""
    });
    const errorMessage = ref("");

    const createClicked = () => {
      //TODO
    };

    return {
      input,
      errorMessage,
      createClicked
    };
  }
};
</script>

<style lang="scss" scoped></style>
