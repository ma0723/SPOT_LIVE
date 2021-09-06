export default {

    loginUser(state) {
        return state.loginUser
    },
    reservations(state) {
        return state.loginUser.reservationResList
    },
    isLogin(state) {
        return state.isLogin
    },
    ovSessionId(state) {
        return state.ovSessionId
    },
    ovToken(state) {
        return state.ovToken
    },
    OV(state) {
        return state.OV
    },
    ovSession(state) {
        return state.ovSession
    },
    videoDevices(state) {
        return state.videoDevices
    },
    audioDevices(state) {
        return state.audioDevices
    },
    videoDeviceId(state) {
        return state.videoDeviceId
    },
    audioDeviceId(state) {
        return state.audioDeviceId
    },
    mainStreamManager(state) {
        return state.mainStreamManager
    },
    publisher(state) {
        return state.publisher
    },
    subscribers(state) {
        return state.subscribers
    },
    createdVideoData(state) {
        return state.createdVideoData
    },
    RESOLUTION(state) {
        return state.RESOLUTION
    },
    onCreateVideoLive(state) {
        return state.onCreateVideoLive
    },
    videoId(state) {
        return state.videoId
    },
    MAX_CHAT_LIST_SIZE(state) {
        return state.MAX_CHAT_LIST_SIZE
    },
    showReservationData(state) {
        return state.showReservationData
    },
    getShowData(state) {
        return state.getShowData
    },
    fileNamevuex(state) {
        return state.fileNamevuex
    },
    createdProfileData(state) {
        return state.createdProfileData
    },
    invalidForStart(state) {
        return state.invalidForStart
    },
    loading(state) {
        return state.loading
    },
    isReservation(state) {
        return state.isReservation
    }
}