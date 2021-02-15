<template>
  <h1>Opprette nytt arrangement</h1>
  <form>
    <div id="form">
      <div>
        <label>Tittel:</label><input type="text" id="title" name="tittel" />
      </div>
      <div>
        <label>Tidspunkt:</label
        ><input type="date" id="time" name="tidspunkt" />
      </div>
      <div>
        <label>Sted:</label><input type="text" id="location" name="sted" />
      </div>
      <div>
        <label>Plasser:</label
        ><input type="number" id="guests" name="plasser" min="1" />
      </div>
      <div>
        <label>Beskrivelse:</label
        ><textarea id="description" name="beskrivelse" rows="4" cols="50" />
      </div>
      <input type="submit" value="Opprett arrangement" />
    </div>
  </form>
</template>

<script>
export default {
  name: "NewDinner"
};
</script>

<style lang="scss" scoped>
h1 {
  text-align: center;
}

#form {
  display: flex;
  flex-direction: column;
  margin: -12px 0;
}

#form > div {
  display: flex;
  flex-direction: row;
  align-items: baseline;
  margin: 12px 0;
}

label {
  display: block;
  margin-right: 10px;
  width: 100px;
}

input,
textarea {
  display: block;
  flex-grow: 1;
  font-size: 1rem;
}

input[type="submit"] {
  margin: 20px 110px;
}
</style>
