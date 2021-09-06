<template>
  <div 
    class="modal fade" 
    id="ticketModal" 
    ref="ticketDialog" 
    tabindex="-1" 
    aria-labelledby="ticketModalLabel" 
    aria-hidden="true"
  >
    <div class="modal-dialog modal-dialog-scrollable bdcolor-bold-npurple ticket-modal-design">
      <div class="modal-content-m">
        <div class="modal-header no-border">
          <div class="information-header mt-3 ms-3">
            {{this.loginUser.profileNickname}}의 예약 정보
          </div>
          <button 
            type="button" 
            class="btn-close me-2 mt-1"
            data-bs-dismiss="modal" 
            aria-label="Close"
          />
        </div>
        <div class="modal-body d-flex flex-column align-items-center ticket-box">
          <div
            v-for="(reservation, idx) in reservations"
            :key="idx"
          >
            <TicketCard
              :timetable="reservation.timetableFindByReservationRes"
              :showInfo="reservation.timetableFindByReservationRes.showInfoRes"
              :showHost="reservation.timetableFindByReservationRes.showInfoRes.userRes"
              :idx="idx"
              @clickCancleTicketbutton="clickCancleTicketbutton"
            />
          </div> 
        </div>
        <div class="modal-footer-m"></div>
      </div>
    </div>
    <!--오프캔버스-->
    <div 
      class="offcanvas offcanvas-top m-offcanvas m-offcanvas-top bdcolor-nyellow" 
      tabindex="-1" 
      id="deleteTicketInfo" 
      ref="showPopup" 
      aria-labelledby="offcanvasTopLabel"
    >
      <div class="offcanvas-body">
        <div class="mt-3">
          <div class="offcanvas-text-box">
            <div class="ticket-popup-title">
              {{ title }} 
            </div>
            <div class="ticket-popup-title">
              {{ date }} {{ time }}
            </div>
          </div> 
          <p class="ticket-popup-title">
            예약을 정말로 삭제하시겠습니까?
          </p>
        </div>
        <div class="d-flex ticket-popup-button">
          <div>
            <button 
              type="button" 
              class="bdcolor-ngreen small-button mx-3"
              data-bs-dismiss="offcanvas"
            >
              취소
            </button>
          </div>
          <div>
            <button 
              type="button" 
              @click="clickConfirmbutton"
              class="bdcolor-npink small-button mx-3" 
              data-bs-dismiss="offcanvas"
            >
              확인
            </button>
          </div>
        </div>
      </div>
    </div>
    <!--오프캔버스-->
  </div>
</template>

<script>
import { mapGetters } from "vuex"
import TicketCard from '@/views/profile/components/TicketCard.vue'

export default {
  name: 'TicketDialog',
  data: function() {
    return {
      timetableIdToBeDeleted : '',
      title : '',
      date : '',
      time : '',
    }
  },
  components: {
    TicketCard,
  },
  methods: {
    clickCancleTicketbutton(timetableId, title, date, time) {
      this.title = title
      this.date = date
      this.time = time
      console.log("티켓다이알로그에서 타임테이블 찍음")
      console.log(timetableId)
      this.timetableIdToBeDeleted = timetableId
      console.log("티켓다이알로그에서 디스삭제될타임테이블 찍음")
      console.log(this.timetableIdToBeDeleted)
    },
    clickConfirmbutton() {
      this.clickReservationDeleteConfirmButton(this.timetableIdToBeDeleted)
      this.timetableIdToBeDeleted = ''
    },
    clickReservationDeleteConfirmButton(timetableIdToBeDeleted) {
      console.log("티켓다이알로그에서 진짜 삭제할 타임테이블 아이디 찍음")
      console.log(timetableIdToBeDeleted)
        this.$store.dispatch('requestDeleteTicket', {timetableId : timetableIdToBeDeleted})
        .then(() => {
          this.$store.dispatch('requestGetLoginUser')
        })
        .catch((error) => {
          console.log('에러났는데여 ????????')
          console.log(error)
        })
    },
  },
  computed: {
    ...mapGetters(['loginUser', 'reservations']),
  },
}
</script>

<style>
.information-header {
  font-size: 20px;
  font-weight: bold;
}
.ticket-modal-design {
  max-height: 650px;
  min-width: 500px;
  width: 70%;
  background-color: #242424;
  color: white;
}
.ticket-box {
  margin: 0;
}
.ticket-popup-button {
  margin-top: 30px;
  justify-content: center;
}
.ticket-popup-title {
  text-align: center  ;
  font-size: 20px;
  font-weight: bold;
  padding: 0 10%;
  margin: 5px 0;
}
div#deleteTicketInfo {
    margin: 15px auto;
    width: 500px;
    height: 230px;
}
</style>