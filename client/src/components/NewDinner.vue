<template>
  <div>
    <h1>Opprette nytt arrangement</h1>
    <form>
      <div>
        <label>Tittel:<input type="text" id="title" name="tittel" /></label>
        <input type="date" id="time" name="tidspunkt" />
        <input type="text" id="location" name="sted" />
        <input type="number" id="guests" name="plasser" min="1" />
        <textarea id="description" name="beskrivelse" rows="4" cols="50" />
        <input type="submit" value="Opprett arrangement" />
      </div>
    </form>
  </div>
</template>

<script>
export default {
  name: "NewDinner"
};
</script>

<style scoped>
#formFlex {
  display: flex;
  flex-direction: row;
  width: 50vh;
  margin: auto;
}

label {
  text-align: left;
  margin-right: auto;
  width: 120px;
  padding: 2px 0;
}

label,
input,
textarea {
  display: block;
  margin-top: 20px;
  margin-right: auto;
}
</style>
