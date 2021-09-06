<template>
  <div>
  <div class="modal fade" id="showDetailModal" ref="showDetailModal" tabindex="-1" aria-labelledby="showDetailModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-scrollable bdcolor-bold-npurple show-modal-design">
      <div class="modal-content-m">
        <div class="modal-header no-border">
          <div class="information-header mt-3 ms-3">공연 상세 정보</div>
          <button type="button" class="btn-close me-2 mt-1" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body mx-4">
            <form>
              <div class="d-flex flex-row justify-content-start profile-info">
                <div><img :src="loginUser.profileImageUrl" class="profile-small-img"></div>
                <div class="profile-small-detail">
                  <div class="txtcolor-white-nyellow">{{ loginUser.profileNickname }}</div>
                  <div class="main-font-light">{{ loginUser.accountEmail }}</div>
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
                    <div class="flex-fill">
                      <div class="label-alignment"><label class="form-label">티켓가격</label></div>
                      <div class="d-flex txtcolor-white-npurple main-font-light">{{ getShowData.price }}원</div>
                    </div>
                    <div class="flex-fill">
                      <div class="label-alignment"><label class="form-label">러닝타임</label></div>
                      <div class="d-flex txtcolor-white-ngreen main-font-light">{{ getShowData.runningTime }} min</div>                      
                    </div>
                  </div>
                  <div class="mb-3 d-flex">
                    <div class="flex-fill">
                      <div class="label-alignment"><label class="form-label">공연 시간</label></div>
                      <select class="custom-select-control-m main-font-light" aria-label="Default select showDetail" v-model="timetableId">
                        <option :key="i" :value="d.v" v-for="(d, i) in timetables">{{ d.t }}</option>
                      </select>
                    </div>
                  </div>
                  
                </div>
              </div>

              <div class="show-description">
                <div class="label-alignment"><label for="showDetailFormControlTextarea1" class="form-label"> 공연 설명</label></div>
                <div class="main-font-light">{{ getShowData.description }}</div>
              </div>
            </form>
        </div>
        <div class="modal-footer-m my-3">
          <div><button class="bdcolor-ngreen small-button mx-3" data-bs-toggle="offcanvas" data-bs-target="#deleteShowInfo" aria-controls="deleteShowInfo">삭제</button></div>
          <div><button type="button" class="bdcolor-npink small-button mx-3" data-bs-toggle="modal" data-bs-target="#showUpdateModal">수정</button></div>
        </div>

      </div>
    </div>
    <div class="offcanvas offcanvas-top m-offcanvas m-offcanvas-top bdcolor-nyellow" tabindex="-1" id="deleteShowInfo" ref="showPopup" aria-labelledby="offcanvasTopLabel">
      <div class="offcanvas-header">
        <button type="button" class="btn-close text-reset" data-bs-dismiss="offcanvas" aria-label="Close"></button>
      </div>
      <div class="offcanvas-body">
        <div class="mt-3">
          <div>
            <p class="popUpTitle">삭제 시 해당 데이터는 복구할 수 없습니다.</p> 
            <p class="popUpTitle">정말로 삭제하시겠습니까?</p> 
          </div>
        </div>
        <div class="d-flex justify-content-center show-popup">
          <div><button type="button" class="bdcolor-ngreen small-button mx-3" data-bs-dismiss="offcanvas">취소</button></div>
          <div><button type="button" @click="deleteShow()" class="bdcolor-npink small-button mx-3" data-bs-dismiss="offcanvas">확인</button></div>
        </div>
      </div>
    </div>
  </div>
  </div>
</template>

<script>
import { mapGetters } from "vuex"

export default {
  name: 'ShowDetailDialog',
  data: function() {
    return {
      timetableId: '',
      timetables: [],
    }
  },
  mounted() {
  },
  methods: {
    getMyProfile() {
      this.$store.dispatch('requestGetMyProfile')
      .then((response) => {
        console.log("getMyProfile() SUCCESS!!")
        console.log(response.data)
        var ProfileData = {
          myProfile : response.data,
          following : response.data.followMyArtistResList,
          follower : response.data.followMyFanResList,
          myShows : response.data.showInfoResList,
          myVideos : response.data.videoResList,
          myReservations : response.data.reservationResList,
        }
        this.$store.dispatch('requestSetCreatedProfileData', ProfileData)
      })
      .catch((error) => {
        console.log(error)
      })
    },
    // updateShow(){
    //   var showDetailModal = bootstrap.Modal.getInstance(this.$refs.showDetailModal)
    //   showDetailModal.hide()
    // },
    deleteShow(){
      var showDetailModal = bootstrap.Modal.getInstance(this.$refs.showDetailModal)
      showDetailModal.hide()
      console.log(this.$store.getters.getShowData.showId)
      this.$store.dispatch('requestDeleteShowInfo', this.$store.getters.getShowData.showId)
      .then((res) => {
        this.getMyProfile()
        this.$router.go(this.$router.currentroute)
        
      })
      .catch((err) => {
        console.log('fail')
      })
    },
    formatter(date) {
      var dateTime = new Date(date)
      var month = parseInt(dateTime.getMonth()) + 1
      return `${dateTime.getFullYear()}년 ${month >= 10 ? month : '0' + month}월 ${dateTime.getDate() >= 10 ? dateTime.getDate() : '0' + dateTime.getDate()}일 
        ${dateTime.getHours() >= 10 ? dateTime.getHours() : '0' + dateTime.getHours()}:${dateTime.getMinutes() >= 10 ? dateTime.getMinutes() : '0' + dateTime.getMinutes()}`
    },
    getShowInfoTimeTable(timetables) {
      if (!timetables) return
      this.timetableId = timetables[0].timetableId
      this.timetables = []
      for(var i = 0; i < timetables.length; i++){
        var date = this.formatter(timetables[i].dateTime)
        this.timetables.push({ v: timetables[i].timetableId, t: date})
      }
    }
  },
  computed: {
    ...mapGetters(['loginUser', 'getShowData',]),
  },
  watch: {
    getShowData: function(val, oldval) {
      this.getShowInfoTimeTable(val.timetables)
      var modal= this.$refs.showDetailModal
      var _this = this
      modal.addEventListener('hidden.bs.modal', function (event) {
        console.log('제발')
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
.show-popup {
  margin-top: 50px;
}
.popUpTitle {
  text-align: center  ;
  font-size: 20px;
  font-weight: bold;
  padding: 0 10%;
  margin: 5px 0;
}
div#deleteShowInfo{
  margin: 15px auto;
  width: 547px;
  height: 270px;
}
</style>