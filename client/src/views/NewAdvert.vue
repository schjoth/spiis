<template>
  <article class="box max-600">
    <h1>Legg til ny annonse</h1>
    <div class="field">
      <label for="companyName" class="label">Bedriftsnavn: </label>
      <div class="control">
        <input type="text" id="companyName" v-model="input.companyName" />
      </div>
    </div>

    <div class="field">
      <label for="adTitle" class="label">Annonsetittel: </label>
      <div class="control">
        <input type="text" id="adTitle" v-model="input.adTitle" />
      </div>
    </div>

    <div class="field">
      <label for="adLink" class="label">Lenke til nettsted</label>
      <div class="control">
        <input type="text" id="adLInk" v-model="input.adLink" />
      </div>
    </div>

    <div class="field">
      <label for="adImage" class="label">Bilde: </label>
      <div class="control">
        <input id="adImage" accept="image/*" type="file" />
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
          Lag Annonse
        </button>
      </div>
    </div>
  </article>
</template>

<script lang="ts">
//import { AdvertRequest, AdvertResponse } from "../api/types";
import { reactive, ref } from "vue";
import { createDinner, updateDinner } from "@/api/dinner";

export default {
  name: "NewAdvert",
  setup() {
    const values: AdvertRequest = {
      companyName: "",
      adTitle: "",
      adLink: "",
      adImage: ""
    };
    const input = reactive(values);

    const errorMessage = ref("");

    const createClicked = async (e) => {
      errorMessage.value = "";

      //Har ingen peiling om linjen under funker
      this.input.adImage = new FileReader().readAsDataURL(e.target.files[0]);

      try {
        const response: AdvertResponse = await createDinner(input);
      } catch (error) {
        errorMessage.value = error.message;
      }
    };

    return {
      input,
      errorMessage,
      createClicked
    };
  }
};
</script>

<style lang="scss" scoped>
$orange: rgb(253, 164, 87);

h1 {
  color: #222222;
}

.button {
  font-size: 14pt;
  font-weight: bolder;
  color: #323232;
  outline-width: 3px;
  outline-color: #323232;
  border-radius: 20px;
}

.button:hover {
  background-color: $orange;
  border-radius: 40px;
  color: #ffffff;
}

article {
  background-color: white;

  .input,
  .textarea {
    color: black;
    border-color: #323232;
  }
}
</style>
