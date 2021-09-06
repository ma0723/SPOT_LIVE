<template>
  <div class="modal fade" id="showReservationInProfileModal" ref="showReservationInProfileModal" tabindex="-1" aria-labelledby="showReservationInProfileModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-scrollable bdcolor-bold-npurple show-modal-design">  
      <div class="modal-content-m">
        <div class="modal-header no-border">
          <div class="information-header mt-3 ms-3">공연 상세 정보</div>
          <button
            type="button"
            class="btn-close me-2 mt-1"
            data-bs-dismiss="modal"
            aria-label="Close"
            @click="clearTimeTableArray()"
          ></button>
        </div>
        <div class="modal-body mx-4">
          <form>
            <div class="d-flex flex-row justify-content-start profile-info">
              <div><img :src="getShowData.profileImageUrl" class="profile-small-img"></div>
              <div class="profile-small-detail">
                <div class="txtcolor-white-nyellow">{{ getShowData.profileNickname }}</div>
                <p class="main-font-light">{{ getShowData.userId }}</p>
              </div>
            </div>

            <div class="d-flex flex-row">    
              <div><img :src="getShowData.posterUrl" class="show-detail-img"></div>
              <div class="show-info">
                <div class="mb-3">
                  <div class="label-alignment"><label class="form-label">공연명</label></div>
                  <div class="txtcolor-white-npink main-font-light">{{ getShowData.title }}</div>
                </div>
                <div class="mb-3 d-flex">
                  <div class="flex-fill me-3">
                    <div class="label-alignment"><label class="form-label">티켓가격</label></div>
                    <div class="d-flex txtcolor-white-npurple main-font-light">{{ getShowData.price }}원</div>
                  </div>
                  <div class="flex-fill me-3">
                    <div class="label-alignment"><label class="form-label">러닝타임</label></div>
                    <div class="d-flex txtcolor-white-ngreen main-font-light">{{ getShowData.runningTime }} min</div>                      
                  </div>
                </div>
                <div class="mb-3 d-flex">
                  <div class="flex-fill me-3">
                    <div class="label-alignment"><label class="form-label label-in-dialog">공연 시간</label></div>
                    <select class="custom-select-control-m main-font-light" aria-label="Default select showDetail" v-model="timetableId" v-if="timetables.length > 0">
                      <option :key="i" :value="d.v" v-for="(d, i) in timetables">{{ d.t }}</option>
                    </select>
                    <div class="d-flex main-font-light" v-else>예약 가능한 시간이 없습니다.</div>
                  </div>
                </div>
              </div>
            </div>

            <div class="show-description">
              <div class="label-alignment"><label class="form-label"> 공연 설명</label></div>
              <div class="main-font-light">{{ getShowData.description }}</div>
            </div>

          </form>
        </div>

        <div class="modal-footer-m my-3" >
          <button type="button" class="bdcolor-npink small-button" @click="clearTimeTableArray()" data-bs-dismiss="modal">닫기</button>
          <button type="button" class="bdcolor-ngreen small-button ms-5" @click="reservateShow()" v-if="timetables.length > 0">예약하기</button>
        </div>
      </div>
    </div>

    <div class="offcanvas offcanvas-top m-offcanvas m-offcanvas-top bdcolor-nyellow" tabindex="-1" id="offcanvasTop" ref="showPopup" aria-labelledby="offcanvasTopLabel">
    <div class="offcanvas-header">
      <button type="button" class="btn-close text-reset" data-bs-dismiss="offcanvas" aria-label="Close"></button>
    </div>
  </div>
  <div class="position-fixed top-0 end-0 p-3" style="z-index: 1100">
    <div id="liveToast" ref="alreadyBooked" class="toast" role="alert" aria-live="assertive" aria-atomic="true" data-bs-animation="true" data-bs-delay="3000">
      <div class="toast-header">
        <strong class="me-auto">이미 예약된 상태입니다!</strong>
        <button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button>
      </div>
      <div class="toast-body">
        또 예약하고 싶으신가요~?😁
      </div>
    </div>
  </div> 
  <div class="position-fixed top-0 end-0 p-3" style="z-index: 1100">
    <div id="liveToast" ref="bookCompleted" class="toast" role="alert" aria-live="assertive" aria-atomic="true" data-bs-animation="true" data-bs-delay="3000">
      <div class="toast-header">
        <strong class="me-auto">예약 완료 되었습니다!</strong>
        <button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button>
      </div>
      <div class="toast-body">
        멋진 공연 즐겨봐요❤️
      </div>
    </div>
  </div> 
</div>
</template>

<script>
import { mapGetters } from "vuex"

export default {
  name: 'ShowReservationDialogInProfile',
  data: function() {
    return {
      timetableId: '',
      timetables: [],
    }
  },
  created: function () {
  },
  methods: {
    formatter(date) {
      let dateTime = new Date(date)
      let month = parseInt(dateTime.getMonth()) + 1
      return `${dateTime.getFullYear()}년 ${month >= 10 ? month : '0' + month}월 ${dateTime.getDate() >= 10 ? dateTime.getDate() : '0' + dateTime.getDate()}일 
        ${dateTime.getHours() >= 10 ? dateTime.getHours() : '0' + dateTime.getHours()}:${dateTime.getMinutes() >= 10 ? dateTime.getMinutes() : '0' + dateTime.getMinutes()}`
    },
    getShowInfoTimeTable(timetables) {
      if (!timetables) return
      let length = timetables.length
      this.timetables = []
      var now = this.formatter(new Date())
      for(var i = 0; i < length; i++){
        var date = this.formatter(timetables[i].dateTime)
        if (date > now) {
          if (this.timetableId == '') this.timetableId = timetables[i].timetableId
          this.timetables.push({ v: timetables[i].timetableId, t: date})
        }
      }
    },
    clearTimeTableArray() {
      this.timetables = []
    },
    reservateShow() {
      this.$store.dispatch('requestShowLoadingSpinner', true)
      this.$store.dispatch('requestShowIsReservated', this.timetableId)
      .then(({ status} ) => {
        if(status == 200) {
          this.clickToast(1)
        } else if(status == 204) { // 예약안한 공연이므로 예약 axios 한번 더 호출
          this.clickToast(2)
          this.$store.dispatch('requestReservateShow', {timetableId : this.timetableId})
        } else {
          console.log("requestShowIsReservated Fail...")
        }
        this.$store.dispatch('requestShowLoadingSpinner', false)
      })
      .catch((error) => {
        console.log(error)
        this.$store.dispatch('requestShowLoadingSpinner', false)
      })
    },
    clickToast(viewId) {
      if (viewId == 1) {
        var myToast = bootstrap.Toast.getOrCreateInstance(this.$refs.alreadyBooked)
        myToast.show()
      } else {
        var myToast = bootstrap.Toast.getOrCreateInstance(this.$refs.bookCompleted)
        myToast.show()
      }
    },
  },
  computed: {
    ...mapGetters(['loginUser', 'getShowData',]),
  },
  watch: {
    getShowData(val, oldVal) {
      console.log(this.getShowData.timetables)
      this.getShowInfoTimeTable(this.getShowData.timetables)
    },
  },
}
</script>

<style scoped>
.show-modal-design {
  max-height: 700px;
  min-width: 550px;
  width: 70%;
  background-color: #242424;
  color: white;
}
.information-header {
  font-size: 20px;
  font-weight: bold;
}
.profile-info {
  margin-left: 25px;
  margin-bottom: 30px;
}
.profile-small-img {
  width: 50px;
  height: 50px;
  border-radius: 100%;
  border: none;
  box-shadow: 
    0 0 9px #FFFFFF,
    0 0 12px #FFFFFF,
    0 0 20px #FFFFFF;
}
.profile-small-detail{
  width: 300px;
  height: 50px;
  margin-left: 30px;
  text-align: left;
}
.show-detail-img {
  margin-left: 20px;
  min-width: 180px;
  max-width: 180px;
  min-height: 230px;
  max-height: 230px;
}
.show-info {
  margin-left: 30px;
  text-align: start;
}
.show-description {
  margin-top: 30px;
  margin-left: 20px;
  margin-right: 20px;
  text-align: start;
}
.custom-select-control-m {
  background-color: #595959;
  padding: .375rem 0.8rem .375rem .75rem;
  font-size: 1rem;
  font-weight: 400;
  color: white;
  background-repeat: no-repeat;
  background-position: right .75rem center;
  background-size: 16px 12px;
  border: 0px;
  border-radius: .25rem;
  transition: border-color .15s ease-in-out,box-shadow .15s ease-in-out;
  cursor: pointer;
}
</style>