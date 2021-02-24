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
      <label class="label">Maks antall gjester</label>
      <div class="control">
        <input
          class="input"
          type="number"
          placeholder=""
          v-model="input.maxGuests"
        />
      </div>
    </div>
    <div class="field">
      <label class="label">Dato</label>
      <div class="control">
        <input class="input" type="date" v-model="input.date" />
      </div>
    </div>
    <div class="field">
      <label class="label">Start-tidspunkt</label>
      <div class="control">
        <input
          class="input"
          type="text"
          placeholder="16:00"
          v-model="input.startTime"
        />
      </div>
    </div>
    <div class="field">
      <label class="label">Slutt-tidspunkt</label>
      <div class="control">
        <input
          class="input"
          type="text"
          placeholder="20:00"
          v-model="input.endTime"
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
import { isValidInteger, todayAsIsoDate } from "@/api/parserUtil";
import { getLogInState } from "@/store/loginState";

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
      city: props.dinner?.city ?? getLogInState().user?.city ?? "",
      maxGuests: props.dinner?.maxGuests ?? 4,
      date: props.dinner?.date ?? todayAsIsoDate(),
      startTime: props.dinner?.startTime ?? "",
      endTime: props.dinner?.endTime ?? ""
    };
    const router = useRouter();
    const id = +useRoute().params.dinnerId;

    const input = reactive(startingValues);

    const errorMessage = ref("");

    const createClicked = async () => {
      errorMessage.value = "";

      if (!isValidInteger(input.maxGuests)) {
        errorMessage.value = "Maks gjester må være et heltall";
        return;
      }

      try {
        if (props.edit == true) {
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
