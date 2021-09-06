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
        <div class="flex-fill">
        <ValidationProvider v-slot="v"  rules="required">
          <div class="label-alignment"><label class="form-label" for="categoryId">분류</label></div>
          <select class="custon-select-control main-font-light" aria-label="Default select example" v-model="form.categoryId" id="categoryId">
            <option :key="i" :value="d.categoryId" v-for="(d, i) in categoryIds">{{ d.categoryName }}</option>
          </select>
          <span>{{ v.errors[0] }}</span>
        </ValidationProvider>
        </div>
      </div>
      <div class="mb-3">
        <div class="label-alignment"><label for="thumbnail" class="form-label">썸네일</label></div>
        <div class="d-flex">
          <input class="custom-form-control main-font-light" v-model="this.fileName" readonly="readonly" disabled="disabled"/>
          <ValidationProvider rules="required|image|size:1000" ref="fileBrowser">
            <input type="file" class="custom-file-update-input" id="updateThumbnail" @change="updateHandleFileChange">
            <label data-browse="Browse" class="search-button" for="updateThumbnail" @change="updateHandleFileChange">
            </label>
          </ValidationProvider>
        </div>
        {{ this.fileErrorMessage }}
      </div>
      <div class="mb-3">
        <ValidationProvider v-slot="v"  rules="max:200 |required" >
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
      },
      thumbnail: '',
      fileName:'',
      fileErrorMessage: ''
    }
  },
  computed: {
    ...mapGetters(['fileNamevuex', 'createdVideoData']),
  },
  watch: {
    fileNamevuex(value, oldvalue) {
      this.fileName = value
    },
    closing(value, oldvalue) {
      if (value == true) {
        // this.initDataWhenClosing()
      } else {
        this.initDataWhenOpenSettingUpdateDialog()
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
    async updateHandleFileChange(e) {
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
    makeToolTipsObject() {
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
          }
      this.fileName = ''
    },
    initDataWhenOpenSettingUpdateDialog() {
      this.form.categoryId = this.createdVideoData.categoryId
      this.fileName = this.fileNamevuex
      this.form.thumbnailImage = this.createdVideoData.thumbnailImage
      this.form.videoDescription = this.createdVideoData.videoDescription
      this.form.videoTitle = this.createdVideoData.videoTitle
    },
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
.search-button {
  width: 45px;
  height: 40px;
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

.custom-form-control {
  display: block;
  padding: .375rem 2.25rem .375rem .75rem;
  width: 100%;
  background-color: #595959;
  border: 0px;
  font-size: 1rem;
  color: white;
  border: 0px;
  border-radius: .25rem;
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

.custom-file-update-input {
    display: none;
}
.tooltip.tooltip-top,
.tooltip.tooltip-bottom,
.tooltip.tooltip-left,
.tooltip.tooltip-right {
  z-index: 100000;
}
</style>