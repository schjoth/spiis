<template>
  <h1>Opprette nytt arrangement</h1>
  <form>
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
    <input type="submit" value="Opprett arrangement" />
  </form>
</template>

<script>
export default {
  name: "NewDinner"
};
</script>

<style lang="scss" scoped>
@import "src/assets/form.scss";
</style>
