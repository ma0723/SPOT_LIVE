<template>
  <div class="modal fade" id="roomSettingDialog" ref="roomSettingDialog" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog modal-dialog-scrollable bdcolor-bold-npurple modal-design">
      <div class="modal-content-m">
          <div class="modal-header no-border">
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close" @click="closeRoomSettingDialog()"></button>
          </div>
          <div class="modal-body">
            <div class='tabs'>
              <input type='radio' id='r1' name='t' checked>
              <label for='r1' class="tab-label">설정</label>
              <div class='content'>
                <ValidationObserver ref="settingDialogObserver" @change="changeInput()">
                  <RoomSettingDialogForm
                    :categoryIds="categoryIds"
                    :showInfoList="showInfoList"
                    :closing="closing"
                  />
                </ValidationObserver>
              </div>
              <input type='radio' id='r2' name='t'>
              <label for='r2' class="tab-label">카메라</label>
              <div class='content'>
                <RoomSettingDialogCameraForm/>
              </div>
              <div id='slider'></div>
            </div>
          </div>
          <div class="modal-footer-m">
            <button type="button" class="bdcolor-ngreen small-button setting-button" :disabled="invalid" data-bs-dismiss="modal">확인</button>
          </div>
      </div>
    </div>
    <div class="offcanvas offcanvas-top m-offcanvas m-offcanvas-top bdcolor-nyellow" tabindex="-1" id="offcanvasTop" ref="showPopup" aria-labelledby="offcanvasTopLabel">
    <div class="offcanvas-header">
      <button type="button" class="btn-close text-reset" data-bs-dismiss="offcanvas" aria-label="Close"></button>
    </div>
    <div class="offcanvas-body">
      <h5 class="popUpTitle">공연을 추가하기 위해 프로필로 이동해주세요</h5>
      등록된 공연이 없다면<br>
      <strong>프로필 > 공연 생성</strong> 버튼 클릭하여<br>
      상세 공연 정보를 등록 후 스트리밍을 진행할 수 있습니다.
      <div class="d-flex justify-content-center mt-4">
        <div><button type="button" class="bdcolor-npink small-button mx-3" data-bs-dismiss="offcanvas" @click="routeToProfile()">프로필로 가기</button></div>
      </div>
    </div>
  </div>
  </div>
</template>

<script scoped>
import { mapGetters } from "vuex"
import RoomSettingDialogForm from './RoomSettingDialogForm.vue'
import RoomSettingDialogCameraForm from './RoomSettingDialogCameraForm.vue'
import { ValidationObserver } from 'vee-validate';
export default {
  name: 'RoomSettingDialog',
  components: {
    RoomSettingDialogForm,
    RoomSettingDialogCameraForm,
    ValidationObserver,
  },
  data: function () {
    return {
      categoryIds: [],
      showInfoList: [],
      closing: true,
      invalid: true,
    }
  }, 
  methods: {
    routeToProfile: function () {
      var roomSettingModal = bootstrap.Modal.getInstance(this.$refs.roomSettingDialog)
      roomSettingModal.hide()
      this.$router.push({name: 'Profile', query: { profileId : this.loginUser.accountEmail }})
      this.$router.go()
    },
    changeInput: function () {
      if (this.$refs.settingDialogObserver.flags.invalid) {
        this.invalid = true
        this.$store.dispatch('requestSetInvalidStartStreaming', this.invalid)
      } else {
        this.invalid = false
        this.$store.dispatch('requestSetInvalidStartStreaming', this.invalid)
      }
    },
  },
  computed: {
    ...mapGetters([
    'loginUser',
    ]),
  },
  mounted() {
    var modal= this.$refs.roomSettingDialog
    var _this = this
    modal.addEventListener('show.bs.modal', function (event) {
      _this.closing = false
    })
    modal.addEventListener('hidden.bs.modal', function (event) {
      _this.closing = true
    })
    this.$store.dispatch('requestGetCategoryIds')
    .then(response => {
      this.categoryIds = response.data
    })
    this.$store.dispatch('requestGetShowInfoIds')
    .then(response => {
      this.showInfoList = response.data
    })
  },
  
}
</script>

<style scoped>
.tabs {
  top: 51%;
  left: 50%;
  height: 50%;
  min-width: 400px;
}
.tab-label {
  display: inline-block;
  font-weight: bold;
  text-align: center;
  color: #AAA;
  width: 200px;
  height: auto;
  padding: 20px 0px;
}

#slider {
  display: inline-block;
  font-weight: bold;
  text-align: center;
  color: #AAA;
  width: 200px;
  height: auto;
  padding: 20px 0px;
}

label:hover {
  color: white;
  cursor: pointer;
}

.tabs [name=t] {
  display: none;   
}

.content {
  position: absolute;
  top: 50px; right: 0; bottom: 0; left: 0;
  padding-top: 50px;
  padding-right: 30px;
  padding-left: 30px;
  display: none;
}

#slider {
  background-color: transparent;
  position: absolute;
  border-bottom: 3px solid #C752FE;
  margin: 10px 0px;
  transition: transform 0.3s;
  width: 200px;
}

[name=t],#r1:checked ~ #slider {
  transform: translate(-400px, 0px);
}

[name=t],#r2:checked ~ #slider {
  transform: translate(-200px, 0px);
}

[name=t]:checked + label {
  color: white;
}

[name=t]:checked + label + .content {
  display: inline-block;
}
.btn-close {
  background-image: url('~@/assets/icon-x.png');
  opacity: 1;
}
.btn-close:hover {
  background-image: url('~@/assets/icon-x.png');
}
.setting-button:disabled {
  border-color: black;
  color: gray;
}
</style>