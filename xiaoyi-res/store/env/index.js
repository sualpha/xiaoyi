import { defineStore } from 'pinia';

export const useEnvStore = defineStore('env', {
    state: () => ({
        host: '',
    }),
    getters: {},
    actions: {
        setHost({ host }) {
		    this.$patch({
                host
		    });
		}
    }
});