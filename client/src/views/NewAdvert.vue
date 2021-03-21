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
        <input type="text" id="adTitle" v-model="input.title" />
      </div>
    </div>

    <div class="field">
      <label for="adLink" class="label">Lenke til nettsted</label>
      <div class="control">
        <input type="text" id="adLInk" v-model="input.link" />
      </div>
    </div>

    <div class="field">
      <label for="adImage" class="label">Bilde: </label>
      <div class="control">
        <input
          id="adImage"
          accept="image/*"
          type="file"
          v-on:change="updateFile"
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
          Lag Annonse
        </button>
      </div>
    </div>
  </article>
</template>

<script lang="ts">
import { reactive, ref } from "vue";
import { createAdvert } from "@/api/adverts";
import { AdvertRequest } from "@/api/types";
import router from "@/router";

export default {
  name: "NewAdvert",
  setup() {
    const values: AdvertRequest = {
      companyName: "",
      title: "",
      link: "",
      picture: ""
    };
    const input = reactive(values);

    const errorMessage = ref("");

    function updateFile(event: { target: { files: Blob[] } }) {
      const fileReader = new FileReader();
      fileReader.readAsDataURL(event.target.files[0]);
      fileReader.onloadend = () => {
        const noe: string | null =
          fileReader.result instanceof ArrayBuffer ? "" : fileReader.result;
        input.picture = noe || "";
      };
    }

    const createClicked = async () => {
      errorMessage.value = "";

      try {
        await createAdvert(input);
      } catch (error) {
        errorMessage.value = error.message;
      }

      await router.push("/admin/ads");
    };

    return {
      input,
      errorMessage,
      createClicked,
      updateFile
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
