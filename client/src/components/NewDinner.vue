<template>
  <main>
    <h1>Inviter til middag!</h1>
    <label>Tittel:<input type="text" id="title" name="tittel" /></label>
    <label>Tidspunkt:<input type="date" id="time" name="tidspunkt" /></label>
    <label>Sted:<input type="text" id="location" name="sted" /></label>
    <label
      >Plasser:<input type="number" id="guests" name="plasser" min="1"
    /></label>
    <label
      >Beskrivelse:<textarea
        id="description"
        name="beskrivelse"
        rows="4"
        cols="50"
      />
    </label>
    <button class="is-primary">Opprett arrangement</button>
  </main>
</template>

<script>
export default {
  name: "NewDinner"
};
</script>

<style lang="scss" scoped></style>
