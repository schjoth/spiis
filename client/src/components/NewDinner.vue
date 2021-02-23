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
    <!--
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
    </div>-->
    <div class="field">
      <label class="label">Gateadresse</label>
      <div class="control">
        <input
          class="input"
          type="text"
          placeholder=""
          v-model="input.addressLine"
        />
      </div>
    </div>
    <div class="field">
      <label class="label">Postnummer</label>
      <div class="control">
        <input
          class="input"
          type="text"
          placeholder=""
          v-model="input.postCode"
        />
      </div>
    </div>
    <div class="field">
      <label class="label">By</label>
      <div class="control">
        <input class="input" type="text" placeholder="" v-model="input.city" />
      </div>
    </div>
    <div class="field">
      <label class="label">Maks deltagere</label>
      <div class="control">
        <input
          class="input"
          type="number"
          placeholder=""
          v-model="input.maxPeople"
        />
      </div>
    </div>
    <div class="field">
      <label class="label">Tid</label>
      <div class="control">
        <input class="input" type="text" placeholder="" v-model="input.time" />
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
          {{ !edit ? "Opprett Arrangemet!" : "Oppdater" }}
        </button>
      </div>
    </div>
  </article>
</template>

<script lang="ts">
import { ref, reactive, defineComponent } from "vue";
import { DinnerRequest, DinnerResponse } from "@/api/types";
import { createDinner, updateDinner } from "@/api/dinner";
import { useRoute, useRouter } from "vue-router";

export default defineComponent({
  name: "NewDinner",
  props: {
    dinner: Object,
    edit: Boolean
  },

  setup(props) {
    const startingValues: DinnerRequest = {
      title: props.dinner?.title ?? "",
      description: props.dinner?.description ?? "",
      expenses: "",
      addressLine: props.dinner?.addressLine ?? "",
      postCode: props.dinner?.postCode ?? "",
      city: props.dinner?.city ?? "",
      maxGuests: props.dinner?.maxPeople ?? 4,
      endTime: "",
      startTime: ""
    };
    const router = useRouter();
    const id = useRoute().params.dinnerId;

    const input = reactive(startingValues);

    const errorMessage = ref("");

    const createClicked = async () => {
      errorMessage.value = "";
      try {
        if (props.edit == true) {
          //TODO updateDinner
          await updateDinner(id, input);
          await router.push(`/event/${id}`);
        } else {
          const response: DinnerResponse = await createDinner(input);
          await router.push(`/event/${response.id}`);
        }
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
});
</script>

<style lang="scss" scoped></style>
