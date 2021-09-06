<template>
  <div>
    <form v-on:submit.prevent autocomplete="off"> 
      <div class="mb-3">
        <ValidationProvider rules="required|max:20" v-slot="v">
        <div class="label-alignment"><label for="videoTitle" class="form-label">제목</label></div>
        <input class="custom-form-control main-font-light" id="videoTitle" v-model="form.videoTitle">
          <span>{{ v.errors[0] }}</span>
        </ValidationProvider>
      </div>
      <div class="mb-3 d-flex">
        <div class="flex-fill me-3">
        <ValidationProvider v-slot="v"  rules="required">
          <div class="label-alignment"><label class="form-label" for="categoryId">분류</label></div>
          <select class="custon-select-control main-font-light" aria-label="Default select example" v-model="form.categoryId" id="categoryId">
            <option :key="i" :value="d.categoryId" v-for="(d, i) in categoryIds">{{ d.categoryName }}</option>
          </select>
          <span>{{ v.errors[0] }}</span>
        </ValidationProvider>
        </div>
        <div>
          <div class="label-alignment"><label class="form-label">영상용도</label>
            <div class="icon-info" data-bs-toggle="tooltip" data-bs-placement="top" title="용도를 꼭 확인해주세요!💥"></div>
          </div>
          <ValidationProvider rules="" vid="mode">
            <div class="d-flex mt-1">
              <div class="form-check">
                <input class="form-check-input" type="radio" name="flexRadioDefault" id="forShow" value="공연" v-model="form.mode">
                <label class="form-check-label main-font-light" for="forShow" ref="forShow" data-bs-toggle="tooltip" data-placement="bottom" title="등록된 공연을 보여주기 위한 목적">
                  공연용
                </label>
              </div>
              <div class="form-check ms-2">
                <input class="form-check-input" type="radio" name="flexRadioDefault" id="forAd" value="홍보" v-model="form.mode">
                <label class="form-check-label main-font-light" for="forAd" ref="forAd" data-bs-toggle="tooltip" data-placement="bottom" title="예매시스템이 갖춰진 공연 홍보 목적">
                  홍보용
                </label>
              </div>
              <div class="form-check ms-2">
                <input class="form-check-input" type="radio" name="flexRadioDefault" id="forCommunicate" value="소통" v-model="form.mode">
                <label class="form-check-label main-font-light" for="forCommunicate" ref="forCommunicate" data-bs-toggle="tooltip" data-placement="bottom" title="예매시스템 없이 관객과의 소통 목적">
                  소통용
                </label>
              </div>
            </div>
          </ValidationProvider>
        </div>
      </div>
      <div class="mb-3" v-if="form.mode=='공연' || form.mode=='홍보'">
        <div class="label-alignment"><label for="showInfoId" class="form-label">등록한 공연 선택</label></div>
        <div class="d-flex">
          <ValidationProvider :rules="`${reuiqredShowInfo ?'required':''}|min:1`" class="flex-fill" v-slot="v">
            <select @change="getRecentlyTimeTable()" class="custon-select-control main-font-light" v-model="form.showInfoId" id="showInfoId">
              <option :key="i" :value="d.t.showInfoId" v-for="(d, i) in showInfoIds">{{ d.t.showInfoTitle }}</option>
            </select>
            <span>{{ v.errors[0] }}</span>
          </ValidationProvider>
          <button class="plus-button" data-bs-toggle="offcanvas" data-bs-target="#offcanvasTop" aria-controls="offcanvasTop"> </button>
        </div>
        <ValidationProvider :rules="`${requiredShowTime ?'required':''}|min:1`" v-slot="v">
          <input v-if="form.mode=='공연' && $props.showInfoList.length!=0" class="custom-form-control mt-1 main-font-light" id="showTime" v-model="form.showTime" readonly="readonly" >
          <span>{{ v.errors[0] }}</span>
        </ValidationProvider>
      </div>
      <div class="mb-3">
        <div class="label-alignment"><label for="thumbnail" class="form-label">썸네일</label></div>
          <div class="d-flex">
            <input class="custom-form-control thumbnail-provide main-font-light" v-model="this.fileName" readonly="readonly" disabled="disabled"/>
            <ValidationProvider tag="div" rules="required|image|size:1000" ref="fileBrowser">
              <input type="file" class="custom-file-input" id="thumbnail" @change="handleFileChange">
              <label data-browse="Browse" class="search-button" for="thumbnail" @change="handleFileChange">
              </label>
            </ValidationProvider>
          </div>
          {{ this.fileErrorMessage }}
      </div>
      <div class="mb-3">
        <ValidationProvider v-slot="v"  rules="max:200|required" >
          <div class="label-alignment"><label for="videoDescription" class="form-label">설명</label></div>
          <textarea class="custom-form-control main-font-light" id="videoDescription" rows="3" v-model="form.videoDescription"></textarea>
          <span>{{ v.errors[0] }}</span>
        </ValidationProvider>
      </div>
    </form>
  </div>
</template>

<script>
import { ValidationProvider } from 'vee-validate';
import { mapGetters } from 'vuex'

export default {
  name: 'RoomSettingDialogForm',
  components: {
    ValidationProvider,
  },
  props: {
    categoryIds: {
      type: Array,
      default: [],
    },
    showInfoList: {
      type: Array,
      default: [],
    },
    closing: {
      type: Boolean
    }
  },
  data: function () {
    return {
      form: {
        categoryId: '0',
        thumbnailImage: [],
        videoDescription: '',
        videoTitle: '',
        showInfoId: '',
        timetableId: '',
        showTime:'',
        mode: '공연',
      },
      thumbnail: '',
      fileName:'',
      showInfoIds: [],
      toast: null,
      fileErrorMessage: ''
    }
  },
  computed: {
    ...mapGetters(['fileNamevuex', 'createdVideoData']),
    reuiqredShowInfo() { return (this.form.mode == '공연' || this.form.mode=='홍보') },
    requiredShowTime() { return this.form.mode == '공연'}
  },
  watch: {
    fileNamevuex(value, oldvalue) {
      this.fileName = value
    },
    closing(value, oldvalue) {
      console.log("룸쎄다폼켜져따")
      if (value == true) {
        this.initDataWhenClosing()
      } else {
        this.initDataWhenOpenSettingDialog()
      }
    },
    form: {
      deep: true,
      handler(value) {
        if(this.$props.closing != true) { 
          this.$store.dispatch('requestSetCreatedVideoData', value)
        }
      }
    },
  },
  methods: {
    async handleFileChange(e) {
      const { valid } = await this.$refs.fileBrowser.validate(e);
      if (valid) {
        this.form.thumbnailImage = e.target.files[0] // 파일을 넣고
        this.fileName = e.target.files[0].name // 파일이름을 넣음
        this.$store.dispatch('requestSetFileNameOfVideo', this.fileName)
        this.fileErrorMessage = ''
        console.log('Uploaded the file...');
      } else {
        this.fileName = ''
        this.fileErrorMessage = this.$refs.fileBrowser.errors[0]        
      }
    },
    makeShowInfoIds() {
      if (this.$props.showInfoList.length == 0) {
        this.showInfoIds.push({v: 0, t: {showInfoId: '', showInfoTitle: '등록된 공연이 없습니다.'}})
        this.form.showInfoId = ''
      } else {
        this.$props.showInfoList.forEach((showInfo, index) => {
          this.showInfoIds.push({ v: index, t: showInfo})
        })
        if (this.createdVideoData.showInfoId != '') {
          this.form.showInfoId = this.createdVideoData.showInfoId

        } else {
          this.form.showInfoId = this.showInfoIds[0].t.showInfoId
        }
      }
      if (this.form.showInfoId != '') {
        this.getRecentlyTimeTable()
      }
    },
    formatter(date) {
      var dateTime = new Date(date)
      var month = parseInt(dateTime.getMonth()) + 1
      return `${dateTime.getFullYear()}년 ${month >= 10 ? month : '0' + month}월 ${dateTime.getDate() >= 10 ? dateTime.getDate() : '0' + dateTime.getDate()}일 ${dateTime.getHours() >= 10 ? dateTime.getHours() : '0' + dateTime.getHours()}:${dateTime.getMinutes() >= 10 ? dateTime.getMinutes() : '0' + dateTime.getMinutes()}`
    },
    getRecentlyTimeTable() {
      this.$store.dispatch("requestGetRecentlyTimeTable", { showInfoId: this.form.showInfoId })
      .then((response) => {
        if (response.data.length == 0) {
          '현재 30분 내 공연이 존재하지 않습니다. 공연을 등록해주세요.'
          this.form.showTime = ''
        } else {
          this.form.showTime = this.formatter(response.data.dateTime)
          this.form.timetableId = response.data.timetableId
        }
      }).catch((error) => {
      })
    },
    makeToolTipsObject () {
      var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'))
      var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
        return bootstrap.Tooltip.getOrCreateInstance(tooltipTriggerEl)
      })
    },
    initDataWhenClosing() {
      this.form = {
        categoryId: '0',
        thumbnailImage: [], // 파일이 들어감
        videoDescription: '',
        videoTitle: '',
        showInfoId: '1',
        timetableId: '',
        showTime:'',
        mode: '공연',
      }
      this.fileName = ''
      this.showInfoIds = []
    },
    initDataWhenOpenSettingDialog() {
      this.form.categoryId = this.createdVideoData.categoryId
      this.fileName = this.fileNamevuex
      this.form.thumbnailImage = this.createdVideoData.thumbnailImage
      this.form.mode = this.createdVideoData.mode
      this.form.videoDescription = this.createdVideoData.videoDescription
      this.form.videoTitle = this.createdVideoData.videoTitle
      this.makeShowInfoIds()
    },
    onSubmit() {
      this.$refs.settingDialogObserver.validate()
      .then(valid => {
        console.log(valid)
      })
    }
  },
  mounted() {
    this.makeToolTipsObject()
  },
}
</script>

<style>
form {
  margin-top: 20px;
}

.plus-button {
  width: 45px;
  height: 40px;
  margin-left: 10px;
  background-color: #595959;
  border: none;
  border-radius: .25rem;
  background-image: url('~@/assets/icon-plus.png');
  background-repeat: no-repeat;
  background-position: center;
}

.search-button {
  width: 45px;
  height: 36px;
  margin-left: 10px;
  background-color: #595959;
  border: none;
  border-radius: .25rem;
  background-image: url('~@/assets/icon-search.png');
  background-repeat: no-repeat;
  background-position: center;
}

.icon-info {
  display: inline-block;
  margin-left: 3px;
  width: 15px;
  height: 15px;
  background-image: url('~@/assets/icon-info.png');
  background-repeat: no-repeat;
  background-position: center;
}

.custon-select-control {
  background-color: #595959;
  padding: .375rem 2.25rem .375rem .75rem;
  width: 100%;
  font-size: 1rem;
  font-weight: 400;
  color: white;
  background-repeat: no-repeat;
  background-position: right .75rem center;
  background-size: 16px 12px;
  border: 0px;
  border-radius: .25rem;
  transition: border-color .15s ease-in-out,box-shadow .15s ease-in-out;
}

.label-alignment {
  text-align: left;
}

.form-check-input {
  background-color: #595959;
}

.form-check-input:checked {
  background-color: #04F7CA;
  border-color: #04F7CA;
}

.custom-file-input {
    display: none;
}
.tooltip.tooltip-top,
.tooltip.tooltip-bottom,
.tooltip.tooltip-left,
.tooltip.tooltip-right {
  z-index: 100000;
}
</style>