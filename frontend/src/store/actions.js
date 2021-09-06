import $axios from '../util/axios'

export default {
    // login.vue
    requestGetKakaoLoginUrl() {
        const URL = '/auth/kakao/showlogin'
        return $axios.get(URL)
    },

    requestDoKakaoLogin(context, payload) {
        const URL = '/auth/kakao/login'

        $axios.get(URL + "?code=" + payload)
        .then((response) => {
            context.commit("DO_KAKAO_LOGIN", {
                loginUser: response.data,
            })
        }).catch((error) => {
            console.log(error)
        })
    },

    requestGetUserByAccountEmail(context, payload) {
        const URL = `/auth/user/${payload.accountEmail}`
        return $axios.get(URL)
    },

    requestGetLoginUser(context, payload) {
        const URL = '/auth/user/'
        return $axios.get(URL)
            .then((response) => {
            context.commit("GET_LOGIN_USER", response.data)
        }).catch((error) => {
            console.log(error)
        })
    },

    requestLogout(context, payload) {
        context.commit("LOGOUT")
    },

    // RoomCreate.vue (민권)
    requestInitSession(context, payload) {
        context.commit("INIT_SESSION", payload)
    },

    requestGetSessionForOpenvidu(context) {
        const URL = '/video/openvidu/session'
        return $axios.get(URL)
    },

    requestGetTokenForOpenvidu(context, payload) {
        const URL = `/video/openvidu/token/${payload.sessionId}`
        return $axios.get(URL)
    },

    requestSetSessionIdAndTokenForOpenvidu(context, payload) {
        context.commit("SET_SESSION_ID_AND_TOKEN_FOR_OPENVIDU", payload)
    },

    requestSetAllDevices(context) {
        context.commit("SET_ALL_DEVICES")
    },

    requestAddEventInSession(context) {
        context.commit("ADD_EVENT_IN_SESSION")
    },

    requestConnectSession(context) {
        context.commit("CONNECT_SESSION")
    },
    
    requestConnectSessionForGuest(context) {
        context.commit("CONNECT_SESSION_FOR_GUEST")
    },

    requestSetmainStreamManager(context, payload) {
        context.commit("SET_MAIN_STREAM_MANAGER", payload)
    },

    requestSetSubscribe(context, payload) {
        context.commit("SET_SUBSCRIBE", payload)
    },

    requestChangeDevice(context, payload) {
        context.commit("CHANGE_DEVICE", payload)
    },

    requestSendChat(context, payload) {
        context.commit("SEND_CHAT", payload)
    },

    requestSendJoin(context) {
        context.commit("SEND_JOIN")
    },

    requestSendExit(context) {
        context.commit("SEND_EXIT")
    },

    requestSendUpdateVideo(context, payload) {
        context.commit('SEND_UPDATE_VIDEO', payload)
    },

    // RoomCreate.vue (희진)
    requestGetCategoryIds(context) {
        const URL = '/category/'
        
        return $axios.get(URL)
    },

    requestSetCreatedVideoData({ commit }, payload) {
        commit('SET_CREATEVIDEO_DATA', payload)
    },

    requestStartStreaming(context, payload) {
        const URL = '/video/insert'

        return $axios.post(URL, payload)
    },

    requestSetUserOnCreateVideo({ commit }, payload) {
        commit('SET_USER_ON_CREATE_VIDEO', payload)
    },

    requestSetFileNameOfVideo({ commit }, payload) {
        commit('SET_FILENAME_OF_VIDEO', payload)
    },

    requestGetShowInfoIds( context, payload ) {
        const URL = '/showinfo'

        return $axios.get(URL)
    },

    requestSetInvalidStartStreaming({ commit }, payload) {
        commit('SET_INVALID_START_STREAMING', payload)
    },

    // RoomDetail.vue
    requestGetRoomDetail(context, payload) {
        const URL = `/video/${payload}`

        return $axios.get(URL)
    },

    requestCloseVideo(context, payload) {
        const URL = `/video/close/${payload}`
        return $axios.patch(URL)
    },

    requestDeleteVideo(context, payload) {
        const URL = `/video/${payload}`
        return $axios.delete(URL)
    },

    requestLeaveSession(context, payload) {
        context.commit("LEAVE_SESSION")
    },
    
    requestSetDefaultForOpenvidu(context, payload) {
        context.commit("SET_DEFAULT_FOR_OPENVIDU")
    },

    requestStartRecording(context, payload) {
        const OPENVIDU_URL = "https://i5a405.p.ssafy.io:8443"
        const AUTH_REQ = {
            username: 'OPENVIDUAPP',
            password: 'MY_SECRET',
        }
        const URL = OPENVIDU_URL + '/openvidu/api/recordings/start'

        return $axios.post(URL, JSON.stringify(payload), { auth: AUTH_REQ })
    },

    requestEndRecording(context, payload) {
        const OPENVIDU_URL = "https://i5a405.p.ssafy.io:8443"
        const AUTH_REQ = {
            username: 'OPENVIDUAPP',
            password: 'MY_SECRET',
        }
        const URL = OPENVIDU_URL + '/openvidu/api/recordings/stop/' + payload.ovSessionId

        return $axios.post(URL, JSON.stringify({}), { auth: AUTH_REQ })
    },

    requestInsertVideoUrl(context, payload) {
        const URL = '/video/record'
        return $axios.post(URL, payload)
    },

    requestPlusHit(context, payload) {
        const URL = `/video/join/${payload.videoId}`
        return $axios.patch(URL)
    },

    requestMinusHit(context, payload) {
        const URL = `/video/exit/${payload.videoId}`
        return $axios.patch(URL)
    },
        
    requestSetShowReservationInfo({ commit }, payload) {
        commit('SET_SHOW_RESERVATION_INFO', payload)
    },

    requestGetShowTimetable(context, payload) {
        const URL = `/showinfo/${payload}`
        return $axios.get(URL)
    },

    // RoomSettingDialogForm.vue
    requestGetRecentlyTimeTable(context, payload) {
        const URL = `/showinfo/timetable/${payload.showInfoId}`
        console.log(URL)
        return $axios.get(URL)
    },

    requestUpdateSettingDialog(context, payload) {
        const URL = `/video/${payload.videoId}`

        return $axios.patch(URL, payload.videoData)
    },

    requestSetVideoId({ commit }, payload) {
        commit('SET_VIDEO_ID', payload)
    },

    requestShowIsReservated(context, payload) {
        const URL = `/reservation/${payload}`

        return $axios.get(URL)
    },

    requestReservateShow(context, payload) {
        const URL = `/reservation/${payload.timetableId}`

        return $axios.post(URL)
    },
    
    // Main.vue
    requestGetCarouselVideos() {
        const URL = '/main/top'

        return $axios.get(URL)
    },

    requestGetFilterButtons() {
        const URL = '/category'

        return $axios.get(URL)
    },

    requestGetTotalMainVideos(context, payload) {
        const URL = '/main/all'
        const PAGE_VALUE = payload.pageValue
        const SIZE_VALUE = payload.sizeValue
        const CATEGORY_ID = payload.categoryId

        return $axios.get(URL, { params: { page: PAGE_VALUE, size: SIZE_VALUE, categoryId: CATEGORY_ID}})
    },

    requestGetAdVideos(context, payload) {
        const URL = '/main/ad'
        const PAGE_VALUE = payload.pageValue
        const SIZE_VALUE = payload.sizeValue

        return $axios.get(URL, { params: { page: PAGE_VALUE, size: SIZE_VALUE }})
    },

    requestGetShowVideos(context, payload) {
        const URL = '/main/show'
        const PAGE_VALUE = payload.pageValue
        const SIZE_VALUE = payload.sizeValue

        return $axios.get(URL, { params: { page: PAGE_VALUE, size: SIZE_VALUE }})
    },

    requestGetTalkVideos(context, payload) {
        const URL = '/main/talk'
        const PAGE_VALUE = payload.pageValue
        const SIZE_VALUE = payload.sizeValue

        return $axios.get(URL, { params: { page: PAGE_VALUE, size: SIZE_VALUE }})
    },

    requestGetLiveVideos(context, payload) {
        const URL = '/main/live'
        const PAGE_VALUE = payload.pageValue
        const SIZE_VALUE = payload.sizeValue

        return $axios.get(URL, { params: { page: PAGE_VALUE, size: SIZE_VALUE }})
    },

    requestGetReplayVideos(context, payload) {
        const URL = '/main/replay'
        const PAGE_VALUE = payload.pageValue
        const SIZE_VALUE = payload.sizeValue

        return $axios.get(URL, { params: { page: PAGE_VALUE, size: SIZE_VALUE }})
    },

    requestGetFollowVideos(context, payload) {
        const URL = '/main/follow'
        const PAGE_VALUE = payload.pageValue
        const SIZE_VALUE = payload.sizeValue

        return $axios.get(URL, { params: { page: PAGE_VALUE, size: SIZE_VALUE }})
    },

    requestGetReservationVideos(context, payload) {
        const URL = '/main/reservation'

        return $axios.get(URL)
    },

    requestSetIsReservation(context, payload){
        context.commit('SET_UPDATE_ISRESERVATION', payload)
    },
    
    // MainSidebar.vue
    requestGetFollowingList() {
        const URL = '/main/user'

        return $axios.get(URL)
    },

    // Profile.vue
    requestGetMyProfile() {
        const URL = '/auth/user'

        return $axios.get(URL)
    },
    requestGetProfile(context, payload) {
        const URL = `/auth/user/${payload.profileId}`

        return $axios.get(URL)
    },
    requestClickFollowButton(context, payload) {
        const URL = `/follow/${payload.profileId}`

        return $axios.post(URL)
    },
    requestClickUnfollowButton(context, payload) {
        const URL = `/unfollow/${payload.profileId}`

        return $axios.delete(URL)
    },
    requestSetCreatedProfileData({ commit }, payload) {
        commit('SET_CREATEPROFILE_DATA', payload)
    },

    // Search.vue
    requestGetSearchVideos(context, payload) {
        const URL = '/main/search'
        const KEYWORD_VALUE = payload.keywordValue
        const PAGE_VALUE = payload.pageValue
        const SIZE_VALUE = payload.sizeValue

        return $axios.get(URL, { params: { keyword: KEYWORD_VALUE, page: PAGE_VALUE, size: SIZE_VALUE }})
    },

    // ProfileUpdateDialog.vue
    requestUpdateProfile(context, payload) {
        const URL = `/auth/user`
        return $axios.patch(URL, payload)
    },
    
    // TicketCard.vue
    requestDeleteTicket(context, payload) {
        const URL = `/reservation/${payload.timetableId}`
        return $axios.delete(URL)
    },
    requestGetTimetables(context, payload) {
        const URL = `/showinfo/${payload.showId}`
        console.log(URL)
        return $axios.get(URL)
    },

    // MyShowCard.vue
    requestGetShowData({ commit }, payload) {
        commit('SET_GETSHOW_DATA', payload)
    },
    // TicketDetailDialog.vud
    requestDeleteGetShowData({ commit }) {
        commit('DELETE_GETSHOW_DATA')
    },
    //ShowCreateDialog.vue
    requestPostShow(context, payload){
      const URL = `/showinfo/`
      return $axios.post(URL, payload)
    },
    // ShowDetailDialog.vue
    requestDeleteShowInfo(context, payload) {
        const URL = `/showinfo/${payload}`

        return $axios.delete(URL)
    },

    // ShowUpdateDialog.vue
    requestPutShow(context, payload) {
        const URL = `/showinfo/${payload.showInfoId}`

        return $axios.put(URL, payload.formData)
    },

    // loadingSpinner
    requestShowLoadingSpinner({ commit }, payload) {
      commit("SHOW_LOADING_SPINNER", payload)
    },

    // MyVideoCard.vue
    requestDeleteVideo(context, payload){
        const URL = `video/${payload}`

        return $axios.delete(URL)
    }
}