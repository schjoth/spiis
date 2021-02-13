<template>
  <div>
    <h1>Opprette nytt arrangement</h1>
    <form>
      <div id="formFlex">
        <div>
          <label for="title">Tittel: </label>
          <br />
          <label for="time">Tidspunkt: </label>
          <br />
          <label for="location">Sted: </label>
          <br />
          <label for="guests">Antall plasser: </label>
          <br />
          <label for="description">Beskrivelse: </label>
        </div>
        <div>
          <input type="text" id="title" name="tittel" />
          <br />
          <input type="date" id="time" name="tidspunkt" />
          <br />
          <input type="text" id="location" name="sted" />
          <br />
          <input type="number" id="guests" name="plasser" min="1" />
          <br />
          <textarea id="description" name="beskrivelse" rows="4" cols="50" />
          <br />
          <input type="submit" value="Opprett arrangement" />
        </div>
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
