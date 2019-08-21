import Vue from 'vue';
import Vuex from 'vuex';
import * as store from './store';
import * as actions from './actions';
import * as getters from './getters';
import common from './modules/common';

Vue.use(Vuex);

export default new Vuex.Store({
   store,
   getters,
   actions,
   modules: {
    common
   }
})
