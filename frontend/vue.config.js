module.exports = {
  devServer: {
    https: false,
    port: 8083,
    open: true,
    // proxy: {
    //   // '/api': {
    //   //   target: 'http://localhost:8080/'
    //   // },
    // },
    historyApiFallback: true,
    hot: true
  },
  css: {
    requireModuleExtension: false // import 시에 경로에 .module 포함 안해도 됨.
  },
  lintOnSave: false,
  outputDir: '../backend/src/main/resources/dist'
}
