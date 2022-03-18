import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    isCollapse:false
  },
  getters:{
    getCollapse: state => {
      return state.isCollapse
    }
  },
  mutations: {
    changeCollapse (state) {
      state.isCollapse = ! state.isCollapse
    }
  },
  actions: {
  },
  modules: {
  }
})
