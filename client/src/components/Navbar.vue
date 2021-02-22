<template>
  <div id="nav">

    <div class="burgerIcon">
        <div class="L1"></div>
        <div class="L2"></div>
        <div class="L3"></div>
    </div>

    <ul class="hamburgerDiv">
        <li><router-link to="/MyProfile">Min bruker</router-link></li>
        <li><router-link to="/">Mine middager</router-link></li>
        <li><router-link to="/CreateNewDinner">Ny Middag</router-link></li>

    </ul>

  </div>
</template>




<script>

    export default {
      name: "Navbar"
    };

</script>





<style lang="scss">
    #nav {
      width: 100vw; /*gir navbaren bredden til hele viewporten*/
        height: 70px;
        display: flex;
        justify-content: space-around;
        align-items: center;
        color: white;
        text-align: center;
        text-decoration: none;
        margin: 0 0 0 0;
        background-color: rgb(125,62,35, 0.3);
        top: 0;

        .hamburgerDiv{
            font-family: Georgia, 'Times New Roman', Times, serif;
            font-size: 14pt;
            letter-spacing: 5px;

            /* Styling felles for hamburgermenyen i begge media queriene: */
            z-index: 3;
            position: absolute;
            top: 60px;
            padding: 0;
            height: 100vh; /*gir hamburgermenyen høyden til hele viewporten*/
            width: 100vw; /*gir hamburgermenyen bredden til hele viewporten*/
            color: white;


            display: flex;
            flex-direction: column;
            align-items: right;
            justify-content: space-around;
            transform: translate(0, -150%);
            transition: transform 0.3s ease-in;
        }


    .burgerIcon{
            /*plasserer ikonet*/
        position: absolute;
        display: inline;
        cursor: pointer;
        right: 2%;
        top: 3%;
        //transform: translate(-5%, -50%);
    }


    .burgerIcon div{
        z-index: 3;
        width: 30px;
        height: 3px;
        margin: 6px;
        background-color: white;
        border-radius: 5px;
    }


    ul, li{
        list-style-type: none;
    }


    .hamLinks:hover, .hamburgerDiv .dropbutton:hover{
        font-size: 18pt;
    }


      /* Viser hamburgermenyen når brukeren trykker på hamburger ikonet */
      .hamburgerDiv-active{
          transform: translateX(0%);
      }



      /* endrer hamburger-ikonet til et kryss, slik at det blir tydelig for brukeren at vedkommende må trykke
      på samme punkt på skjermen for å gå ut av hamburgermenyen (om vedkommende ikke trykker på en av linkene)*/
      .exit .L1{
          transform: rotate(-45deg) translate(-8px, 5px);
          background-color: rgb(153, 153, 153);
      }

      .exit .L2{
          background-color: transparent;
      }

      .exit .L3{
          transform: rotate(45deg) translate(-8px, -5px);
          background-color: rgb(153, 153, 153);
      }




      a {
        font-weight: bold;
        color: #2c3e50;

        &.router-link-exact-active {
          color: #42b983;
        }
      }
    }

</style>
