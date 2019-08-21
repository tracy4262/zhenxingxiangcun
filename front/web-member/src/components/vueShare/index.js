import Share from './vueShare'

Share.install = Vue => {
  Vue.component('share', Share)
}

if (typeof window !== 'undefined' && window.vue) {
  window.Share = Share
}

export default Share