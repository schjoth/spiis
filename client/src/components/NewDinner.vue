<template>
  <article class="box max-600">
    <h1>{{ !edit ? "Inviter til middag!" : "Rediger arrangment" }}</h1>
    <div class="field">
      <label class="label" for="title">Tittel</label>
      <div class="control">
        <input
          class="input"
          type="text"
          placeholder=""
          v-model="input.title"
          id="title"
        />
      </div>
    </div>
    <div class="field">
      <label class="label" for="description">Beskrivelse</label>
      <div class="control">
        <textarea
          class="textarea"
          placeholder=""
          v-model="input.description"
          id="description"
        />
      </div>
    </div>
    <div class="field">
      <label class="label" for="address">Gateadresse</label>
      <div class="control">
        <input
          class="input"
          type="text"
          v-model="input.addressLine"
          id="address"
        />
      </div>
    </div>
    <div class="field">
      <label class="label" for="postcode">Postnummer</label>
      <div class="control">
        <input
          class="input"
          type="text"
          v-model="input.postCode"
          id="postcode"
        />
      </div>
    </div>
    <div class="field">
      <label class="label" for="city">By</label>
      <div class="control">
        <input class="input" type="text" v-model="input.city" id="city" />
      </div>
    </div>
    <div class="field">
      <label class="label" for="guests">Maks antall gjester</label>
      <div class="control">
        <input
          class="input"
          type="number"
          v-model="input.maxGuests"
          id="guests"
        />
      </div>
    </div>
    <div class="field">
      <label class="label" for="expenses">Utgifter</label>
      <div class="control">
        <input
          class="input"
          type="number"
          v-model="input.expenses"
          id="expenses"
        />
      </div>
    </div>
    <div class="field">
      <label class="label" for="date">Dato</label>
      <div class="control">
        <input class="input" type="date" v-model="input.date" id="date" />
      </div>
    </div>
    <div class="field">
      <label class="label" for="starttime">Start-tidspunkt</label>
      <div class="control">
        <input
          class="input"
          type="text"
          placeholder="16:00"
          v-model="input.startTime"
          id="starttime"
        />
      </div>
    </div>
    <div class="field">
      <label class="label" for="endtime">Slutt-tidspunkt</label>
      <div class="control">
        <input
          class="input"
          type="text"
          placeholder="20:00"
          v-model="input.endTime"
          id="endtime"
        />
      </div>
    </div>
    <div class="field">
      <label class="label" for="deadlinedate">Dato for påmeldingsfrist</label>
      <div class="control">
        <input
          class="input"
          type="date"
          v-model="input.registrationDeadlineDate"
          id="deadlinedate"
        />
      </div>
    </div>
    <div class="field">
      <label class="label" for="deadlinetime">
        Tidspunkt for påmeldingfrist
      </label>
      <div class="control">
        <input
          class="input"
          type="text"
          placeholder="14:00"
          v-model="input.registrationDeadlineTime"
          id="deadlinetime"
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
          {{ !edit ? "OPPRETT ARRANGEMENT" : "Oppdater" }}
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
      expenses: props.dinner?.expenses ?? "",
      addressLine: props.dinner?.addressLine ?? "",
      postCode: props.dinner?.postCode ?? "",
      city: props.dinner?.city ?? getLogInState().user?.city ?? "",
      maxGuests: props.dinner?.maxGuests ?? 4,
      date: props.dinner?.date ?? todayAsIsoDate(),
      startTime: props.dinner?.startTime ?? "",
      endTime: props.dinner?.endTime ?? "",
      registrationDeadlineDate: props.dinner?.registrationDeadlineDate ?? "",
      registrationDeadlineTime: props.dinner?.registrationDeadlineTime ?? ""
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
