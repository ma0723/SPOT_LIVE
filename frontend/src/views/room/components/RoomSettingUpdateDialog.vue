<template>
  <div class="modal fade" id="roomSettingUpdateDialog" ref="roomSettingUpdateDialog" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog modal-dialog-scrollable bdcolor-bold-npurple modal-design">
      <div class="modal-content-m">
        <div class="modal-header no-border">
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close" @click="closeRoomSettingDialog()"></button>
        </div>
        <div class="modal-body">
          <div class='tabs'>
            <input type='radio' id='rr1' name='u' checked>
            <label for='rr1' class="tab-label-u">설정</label>
            <div class='content-u'>
              <ValidationObserver ref="settingUpdateDialogObserver" @change="changeInput()">
                <RoomSettingUpdateDialogForm
                  :categoryIds="categoryIds"
                  :closing="closing"
                />
              </ValidationObserver>
            </div>
            <input type='radio' id='rr2' name='u'>
            <label for='rr2' class="tab-label-u">카메라</label>
            <div class='content-u'>
              <RoomSettingDialogCameraForm/>
            </div>
            <div id='slider-u'></div>
          </div>
        </div>
        <div class="modal-footer-m">
          <button type="button" class="bdcolor-ngreen small-button setting-button" :disabled="invalid" @click="roomSettingUpdateDialogButton()">확인</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script scoped>
import { mapGetters } from "vuex"
import RoomSettingUpdateDialogForm from './RoomSettingUpdateDialogForm.vue'
import RoomSettingDialogCameraForm from './RoomSettingDialogCameraForm.vue'
import { ValidationObserver } from 'vee-validate';
export default {
  name: 'RoomSettingUpdateDialog',
  components: {
    RoomSettingUpdateDialogForm,
    RoomSettingDialogCameraForm,
    ValidationObserver,
  },
  data: function () {
    return {
      categoryIds: [],
      showInfoList : '',
      videoData: {},
      closing: true,
      invalid: true,
    }
  }, 
  methods: {
    makeFormDataForUpdateDialog() {
      let formData = new FormData()
      let videoUpdateByIdPatchReq = {
        "videoTitle": this.createdVideoData.videoTitle,
        "videoDescription": this.createdVideoData.videoDescription,
        "categoryId": this.createdVideoData.categoryId,
      }
      formData.append('thumbnailImage', this.createdVideoData.thumbnailImage)
      formData.append('videoUpdateByIdPatchReq', new Blob([JSON.stringify(videoUpdateByIdPatchReq)] , {type: "application/json"}))

      return formData
    },
    changeInput: function () {
      if (this.$refs.settingUpdateDialogObserver.flags.invalid) {
        console.log(true)
        this.invalid = true
      } else {
        console.log(false)
        this.invalid = false
      }
    },
    async roomSettingUpdateDialogButton() {
      this.$store.dispatch('requestShowLoadingSpinner', true)
      let videoData = this.makeFormDataForUpdateDialog()
      let payload = {
        videoData: videoData,
        videoId: this.videoId
      }
      console.log(payload)
      await this.$store.dispatch('requestUpdateSettingDialog', payload)
      .then(res => {
        this.$store.dispatch('requestShowLoadingSpinner', false)
      })
      .catch(err => {
        alert('라이브 영상 수정을 하다 오류가 났어요! 다시 시도해주세요!')
        this.$store.dispatch('requestShowLoadingSpinner', false)
      })

      await this.$store.dispatch('requestGetRoomDetail', this.videoId)
      .then((response) => {
        console.log(response)
        this.videoDescription = response.data.videoDescription
        this.category = response.data.categoryRes.categoryName
        this.videoTitle = response.data.videoTitle
        this.startTime = response.data.startTime
        this.hit = response.data.hit
        this.profileImageUrl = response.data.userRes.profileImageUrl
        let videoData = {
          categoryId: response.data.categoryRes.categoryId,
          thumbnailImage: response.data.thumbnailUrl,
          videoDescription: this.videoDescription,
          videoTitle: this.videoTitle,
          showInfoId: response.data.showInfoRes != null ? response.data.showInfoRes.showInfoId : '',
          showTime: response.data.showInfoRes != null ? response.data.showInfoRes.showTime : '',
          timetableRes: response.data.timetableRes != null ? response.data.timetableRes : '',
          mode: response.data.mode,
        }
        console.log(videoData)
        this.$store.dispatch('requestSetCreatedVideoData', videoData)
      }).catch(err => {
        alert('수정된 데이터를 가져오지 못했어요! 다시 시도해주세요!')
      })

      let roomSettingUpdateDialog = bootstrap.Modal.getInstance(this.$refs.roomSettingUpdateDialog)
      this.sendUpdateVideo()
      roomSettingUpdateDialog.hide()
    },
    sendUpdateVideo() {
      this.$store.dispatch("requestSendUpdateVideo")
    },
  },
  computed: {
    ...mapGetters([
    'loginUser',
    'videoId',
    'createdVideoData'
    ]),
  },
  mounted() {
    let modal= this.$refs.roomSettingUpdateDialog
    let _this = this
    modal.addEventListener('show.bs.modal', function (event) {
      _this.closing = false
    })
    modal.addEventListener('hidden.bs.modal', function (event) {
      _this.closing = true
    })
    this.$store.dispatch('requestGetCategoryIds')
    .then((response) => {
      this.categoryIds = response.data
    })
  },
}
</script>

<style scoped>
.tabs-u {
  top: 51%;
  left: 50%;
  height: 50%;
  min-width: 400px;
}
.tab-label-u {
  display: inline-block;
  font-weight: bold;
  text-align: center;
  color: #AAA;
  width: 200px;
  height: auto;
  padding: 20px 0px;
}

#slider-u {
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

.tabs [name=u] {
  display: none;   
}

.content-u {
  position: absolute;
  top: 50px; right: 0; bottom: 0; left: 0;
  padding-top: 50px;
  padding-right: 30px;
  padding-left: 30px;
  display: none;
}

#slider-u {
  background-color: transparent;
  position: absolute;
  border-bottom: 3px solid #C752FE;
  margin: 10px 0px;
  transition: transform 0.3s;
  width: 200px;
}

[name=u],#rr1:checked ~ #slider-u {
  transform: translate(-400px, 0px);
}

[name=u],#rr2:checked ~ #slider-u {
  transform: translate(-200px, 0px);
}

[name=u]:checked + label {
  color: white;
}

[name=u]:checked + label + .content-u {
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