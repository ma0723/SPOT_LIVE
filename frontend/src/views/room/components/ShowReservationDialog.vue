<template>
  <div class="modal fade" id="showReservationDialog" ref="showReservationDialog" tabindex="-1" aria-hidden="true">
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
              <div><img :src="showReservationData.userRes.profileImageUrl" class="profile-small-img"></div>
              <div class="profile-small-detail">
                <div class="txtcolor-white-nyellow">{{ showReservationData.userRes.userName }}</div>
                <p class="main-font-light">{{ showReservationData.userRes.accountEmail }}</p>
              </div>
            </div>

            <div class="d-flex flex-row"> 
              <div><img :src="showReservationData.posterUrl" class="show-detail-img"></div>
              <div class="show-info">
                <div class="mb-3">
                  <div class="label-alignment"><label class="form-label">공연명</label></div>
                  <div class="txtcolor-white-npink main-font-light">{{ showReservationData.showInfoTitle }}</div>
                </div>
                <div class="mb-3 d-flex">
                  <div class="flex-fill me-3">
                    <div class="label-alignment"><label class="form-label">티켓가격</label></div>
                    <div class="d-flex txtcolor-white-npurple main-font-light">{{ showReservationData.price }}원</div>
                  </div>
                  <div class="flex-fill me-3">
                    <div class="label-alignment"><label class="form-label">러닝타임</label></div>
                    <div class="d-flex txtcolor-white-ngreen main-font-light">{{ showReservationData.runningTime }} min</div>                      
                  </div>
                </div>
                <div class="mb-3 d-flex">
                  <div class="flex-fill me-3">
                    <div class="label-alignment"><label class="form-label">공연 시간</label></div>
                    <select class="custom-select-control-m main-font-light" v-model="timetableId" v-if="timetables.length > 0">
                      <option :key="i" :value="d.v" v-for="(d, i) in timetables">{{ d.t }}</option>
                    </select>
                    <div class="d-flex" v-else>예약 가능한 시간이 없습니다.</div>
                  </div>
                </div>
              </div>
            </div>

            <div class="show-description">
              <div class="label-alignment"><label class="form-label"> 공연 설명</label></div>
              <div class="main-font-light">{{ showReservationData.showInfoDescription }}</div>
            </div>

          </form>
        </div>

        <div class="modal-footer-m my-3">
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

<script scoped>
import { mapGetters } from "vuex"
export default {
  name: 'ShowReservationDialog',
  data: function () {
    return {
      showInfoId: '',
      user: {},
      timetableId: '',
      timetables: [],
    }
  }, 
  methods: {
    formatter(date) {
      let dateTime = new Date(date)
      let month = parseInt(dateTime.getMonth()) + 1
      return `${dateTime.getFullYear()}년 ${month >= 10 ? month : '0' + month}월 ${dateTime.getDate() >= 10 ? dateTime.getDate() : '0' + dateTime.getDate()}일 
        ${dateTime.getHours() >= 10 ? dateTime.getHours() : '0' + dateTime.getHours()}:${dateTime.getMinutes() >= 10 ? dateTime.getMinutes() : '0' + dateTime.getMinutes()}`
    },
    getShowInfoTimeTable() {
      var now = this.formatter(new Date())
      this.timetables = []
      this.$store.dispatch('requestGetShowTimetable', this.showInfoId)
        .then(res => {
          this.timetables=[]
          this.timetableId = res.data.timetables[0].timetableId
          res.data.timetables.forEach((timetable) => {
            var date = this.formatter(timetable.dateTime)
            if (date > now) {
              if (this.timetableId == '') this.timetableId = timetable.timetableId
              this.timetables.push({ v: timetable.timetableId, t: date})
            }
          })
        })
    },
    clearTimeTableArray() {
      this.timetables = []
    },
    reservateShow() {
      this.$store.dispatch('requestShowIsReservated', this.timetableId)
      .then(res => {
          console.log(res.status == 204)
          if (res.status == 200) {
          this.clickToast(1)
        } else if (res.status == 204){
          this.$store.dispatch('requestReservateShow', {timetableId: this.timetableId})
          .then(res => {
            this.clickToast(2)
          })
          .catch(err => {
            alert(err)
          })
        }
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
    ...mapGetters(['showReservationData']),
  },
  watch: {
    showReservationData: function(val, oldval) {
      var modal= this.$refs.showReservationDialog
      var _this = this
      modal.addEventListener('show.bs.modal', function (event) {
        _this.showInfoId = val.showInfoId
        _this.user = val.userRes
        _this.getShowInfoTimeTable()
      })
      modal.addEventListener('hidden.bs.modal', function (event) {
        _this.timetables = []
      })
    }
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