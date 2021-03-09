<template>
  <article>
    <h1>404 Not Found</h1>
    <p>
      Kunne ikke finne siden: spiis.haved.no/{{
        this.$route.params.pathMatch.join("/")
      }}
    </p>
  </article>
</template>
