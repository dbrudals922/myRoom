const { defineConfig } = require('@vue/cli-service')
module.exports = {
  transpileDependencies: [ "@splidejs" ],
  outputDir: 'target/dist',
  assetsDir: 'static',
  lintOnSave: false,
  devServer: {
    proxy: {
      '/api': {
        target: 'http://localhost:8081',
        ws: true,
        changeOrigin: true
      }
    }
  },
}
