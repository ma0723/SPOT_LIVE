<template>
  <div class="bgimg">
    <div class="container">
      <div class="flux">Spot </div>
      <div class="flicking">L<span id="offset">I</span>VE</div>
      <button class="bdcolor-nyellow kakaobtn" @click="moveToKakaoLoginPage">
      Kakaotalk Login
    </button>
    </div>
    
  </div>
</template>

<script>

export default {
  name:'Login',
  data() {
    return {
      moveToKakaoLoginPageURL: "",
    }
  },
  methods: {
    moveToKakaoLoginPage() {
      // alert(this.moveToKakaoLoginPageURL)
      window.open(this.moveToKakaoLoginPageURL, "_top")
    },
  },
  created() {
    const code = this.$route.query.code
    if(code != "" && code != null && code != undefined) {
      this.$store.dispatch('requestDoKakaoLogin', code)
    } else {
      this.$store.dispatch('requestGetKakaoLoginUrl', {})
      .then((response) => {
        this.moveToKakaoLoginPageURL = response.data
      }).catch((error) => {
        console.log(error)
      })
    }
  },
}
</script>

<style scoped>
.bgimg {
  min-height: 100%;
  width: 100%;
  position: fixed;
  background-image: url('~@/assets/bg-login-final.png');
  background-position: center;
  background-size: cover;
  text-align: center;
  vertical-align: middle;
}

.kakaobtn {
  width: 350px;
  height: 60px;
  min-width: 250px;
  background: none;
  color: white;
  font-size: 2rem;
  font-weight: bold;
  margin-top: 80px;
}

@font-face {
  font-family: neon;
  src: url(https://s3-us-west-2.amazonaws.com/s.cdpn.io/707108/neon.ttf);
}

.container {
  position: absolute;
  text-align: center;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  margin-top: 350px;
  margin-left: 550px;
  width: fit-content;
  bottom: 20vh;
  right: 15vw;
}

.flux {
  font-family: neon;
  color: #04F7CA;
  font-size: 10rem;
  line-height: 5vw;
}

.flux {
  animation: flux 2s linear infinite;
  -moz-animation: flux 2s linear infinite;
  -webkit-animation: flux 2s linear infinite;
  -o-animation: flux 2s linear infinite;
}

@keyframes flux {
  0%,
  100% {
    text-shadow: 0 0 1vw #01aa7f, 0 0 3vw #01aa7f, 0 0 10vw #01aa7f, 0 0 10vw #01aa7f, 0 0 .4vw #6efae0;
    color: #04F7CA;
  }
}

.flicking {
  color:#F84ABF;
  font-family: 'neon', sans-serif;
  font-size: 10rem;
  animation: text-flicker 3s linear infinite;
}

#offset {
  animation: letter-flicker 2s linear infinite;
}

@keyframes text-flicker {
  0% {
    opacity:0.1;
    text-shadow: 0px 0px 29px #F84ABF;
  }
  
  2% {
    opacity:1;
    text-shadow: 0px 0px 29px #F84ABF;
  }
  8% {
    opacity:0.1;
    text-shadow: 0px 0px 29px #F84ABF;
  }
  9% {
    opacity:1;
    text-shadow: 0px 0px 29px #F84ABF;
  }
  12% {
    opacity:0.1;
    text-shadow: 0px 0px #F84ABF;
  }
  20% {
    opacity:1;
    text-shadow: 0px 0px 29px #F84ABF;
  }
  25% {
    opacity:0.3;
    text-shadow: 0px 0px 29px #F84ABF;
  }
  30% {
    opacity:1;
    text-shadow: 0px 0px 29px #F84ABF;
  }
  
  70% {
    opacity:0.7;
    text-shadow: 0px 0px 29px #F84ABF;
  }
  
  72% {
    opacity:0.2;
    text-shadow:0px 0px 29px #F84ABF;
  }
  
  77% {
    opacity:.9;
    text-shadow: 0px 0px 29px #F84ABF;
  }
  100% {
    opacity:.9;
    text-shadow: 0px 0px 29px #F84ABF;
  }
}



@keyframes letter-flicker {
  0% {
    opacity:0.1;
    text-shadow: 0px 0px 29px #F84ABF;
  }
  2% {
    opacity:0.1;
    text-shadow: 0px 0px 29px #F84ABF;
  }
  4% {
    opacity:1;
    text-shadow: 0px 0px 29px #F84ABF;
  }
  
  
  19% {
    opacity:1;
    text-shadow: 0px 0px 29px #F84ABF;
  }
  21% {
    opacity:0.1;
    text-shadow: 0px 0px 29px #F84ABF;
  }
  23% {
    opacity:1;
    text-shadow: 0px 0px 29px #F84ABF;
  }
  
  80% {
    opacity:1;
    text-shadow: 0px 0px 29px #F84ABF;
  }
  83% {
    opacity:0.4;
    text-shadow: 0px 0px 29px #F84ABF;
  }
  87% {
    opacity:1;
    text-shadow: 0px 0px 29px #F84ABF;
  }
}
</style>