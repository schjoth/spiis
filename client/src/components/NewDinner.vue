<template>
  <article class="max-600">
    <h1>{{ !edit ? "Inviter til middag!" : "Rediger arrangment" }}</h1>
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
          {{ !edit ? "Opprett Arrangement" : "Oppdater" }}
        </button>
      </div>
    </div>
  </article>
</template>

<script lang="ts">
import { ref, reactive, defineComponent } from "vue";

export default defineComponent({
  name: "NewDinner",
  props: {
    dinner: Object,
    edit: Boolean
  },

  setup(props) {
    const input = reactive({
      title: props.dinner?.title,
      description: props.dinner?.description,
      location: props.dinner?.location,
      maxGuests: props.dinner?.maxGuests
    });

    const errorMessage = ref("");

    const createClicked = () => {
      if (props.edit == true) {
        //TODO updateDinner
      }
      //TODO
    };

    return {
      input,
      errorMessage,
      createClicked
    };
  }
});
</script>

<style lang="scss" scoped></style>
