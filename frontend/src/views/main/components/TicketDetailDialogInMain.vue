<template>
  <div 
    class="modal fade" 
    id="ticketDetailModalInMain" 
    ref="ticketDetailModalInMain" 
    tabindex="-1" 
    aria-labelledby="ticketDetailModalInMainLabel" 
    aria-hidden="true"
  >
    <div class="modal-dialog modal-dialog-scrollable bdcolor-bold-npurple ticket-detail-modal-design">
      <div class="modal-content-m">
        <div class="modal-header no-border">
          <div class="information-header mt-3 ms-3">
            {{this.loginUser.profileNickname}}의 티켓 정보
          </div>
          <button 
            type="button" 
            class="btn-close me-2 mt-1"
            data-bs-dismiss="modal" 
            aria-label="Close"
          />
        </div>
        <div class="modal-body">
          <div class="my-ticket-box d-flex flex-row justify-content-evenly">   
            <div class="my-ticket-poster">
              <div class="d-flex ticket-poster-box">
                <img :src="getShowData.posterUrl" class="ticket-poster-img bdcolor-bold-npurple"> 
              </div>
            </div>
            <div class="my-ticket-info">           
              <div class="ticket-show-info">  
                <div class="mb-2"> 
                  <div><label class="ticket-label-in-dialog">TEAM</label></div>
                  <div class="txtcolor-white-ngreen main-font-light">{{ getShowData.profileNickname }}</div>
                </div>       
                <div class="mb-2">
                  <div><label class="ticket-label-in-dialog">공연명</label></div>
                  <div class="txtcolor-white-npink main-font-light">{{ getShowData.title }}</div>
                </div>
                <div class="mb-2">
                  <div><label class="ticket-label-in-dialog">티켓가격</label></div>
                  <div class="txtcolor-white-npurple main-font-light">{{ getShowData.price }}원</div>
                </div>
                <div class="d-flex flex-row mb-2">
                  <div class="me-3">
                    <label class="ticket-label-in-dialog">공연 시간</label>
                    <div class="txtcolor-white-nyellow main-font-light"> {{dateTime}} </div>
                  </div>
                  <div>
                    <div><label class="ticket-label-in-dialog">러닝타임</label></div>
                    <div class="txtcolor-white-ngreen main-font-light">{{ getShowData.runningTime }}min</div> 
                  </div>
                </div>
                <div>
                  <div><label class="ticket-label-in-dialog"> 공연 설명</label></div>
                  <div class="main-font-light">{{ getShowData.description }}</div>
                </div>
              </div>
            </div>
          </div>
        </div>
        
        <div class="modal-footer-m my-3">
          <button 
            type="button" 
            class="bdcolor-ngreen small-button mx-3" 
            data-bs-dismiss="modal"
          >
            닫기
          </button>
           <button 
            type="button" 
            class="bdcolor-npink small-button mx-3" 
            data-bs-dismiss="modal"
            @click="goRoomDetail"
          >
            입장하기
          </button>
        </div>

      </div>
    </div>
  </div>
</template>

<script>
import { mapGetters } from "vuex"

export default {
  name: 'TicketDetailDialogInMain',
  data: function() {
    return {
      showInfoDescription : '',
      title: '',
      description: '',
      posterUrl: '',
      price: '',
      runningTime: '',
      timetableId: '',
      timetables: '',
      dateTime: '',
    }
  },
  created: function () {
  },
  methods: {
    formatter(date) {
      var dateTime = new Date(date)
      
      return `${dateTime.getMonth() >= 10 ? dateTime.getMonth() : '0' + dateTime.getMonth()}/${dateTime.getDate() >= 10 ? dateTime.getDate() : '0' + dateTime.getDate()} ${dateTime.getHours() >= 10 ? dateTime.getHours() : '0' + dateTime.getHours()}:${dateTime.getMinutes() >= 10 ? dateTime.getMinutes() : '0' + dateTime.getMinutes()}`
    },
    getShowInfoTimeTable(dateTime) {
      this.dateTime = this.formatter(dateTime)
    },
    goRoomDetail() {
      this.$router.push({ name: 'RoomDetailForGuest', params: { videoId : this.getShowData.videoId } })
    },
  },
  computed: {
    ...mapGetters(['loginUser', 'getShowData',]),
  },
  watch: {
    getShowData(val, oldVal) {
      this.getShowInfoTimeTable(this.getShowData.dateTime)
    }
  },
}
</script>

<style scoped>
.ticket-detail-modal-design {
  max-height: 660px;
  min-width: 550px;
  width: 70%;
  background-color: #242424;
  color: white;
}
.my-ticket-box {
  min-width: 100%;
  max-width: 100%;
  min-height: 100%;
  max-height: 100%;
}
.my-ticket-poster {
  min-width: 205px;
  max-width: 205px;
  min-height: 450px;
  max-height: 450px;
  border: none;
  border-radius: .25rem;
  background-size: cover;
  background-image: url(~@/assets/ticket_without_content1_detail.png);
  background-color: #242424;
  background-repeat: no-repeat;
  padding-top: 240px;
  padding-left: 55px;
}
.my-ticket-info {
  min-width: 200px;
  max-width: 200px;
  min-height: 440px;
  max-height: 440px;
  border: none;
  border-radius: .25rem;
  background-size: cover;
  background-image: url(~@/assets/ticket_without_content2_detail.png);
  background-color: #242424;
  background-repeat: no-repeat;
  padding-top: 113px;
  padding-bottom: 18px;
  padding-left: 18px;
  padding-right: 18px;
}
.information-header {
  font-size: 20px;
  font-weight: bold;
}
.ticket-profile-img {
  width: 45px;
  height: 45px;
  border-radius: 100%;
  border: 2px #FFFFFF;
  box-shadow: 
    0 0 9px #FFFFFF,
    0 0 12px #FFFFFF,
    0 0 20px #FFFFFF,
}
.ticket-profile-detail{
  width: 500px;
  height: 50px;
  margin-left: 30px;
  text-align: left;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
.ticket-poster-img {
  min-width: 100px;
  max-width: 100px;
  min-height: 130px;
  max-height: 130px;
}
.ticket-show-info {
  text-align: start;
}
.ticket-label-in-dialog {
  font-size: 15px;
  font-weight: bold;
}
</style>