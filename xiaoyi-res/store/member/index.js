import { defineStore } from 'pinia';

export const useMemberStore = defineStore('member', {
    state: () => ({
        memberId: '',
        nickName: '',
        avatar: '',
		registerTime: '',
    }),
    getters: {},
    actions: {
        setMemberInfo({ memberId, nickName, avatar, registerTime}) {
            this.$patch({
                memberId,
                nickName,
                avatar,
				registerTime
            });
        },
        changeMemberInfo({ nickName, avatar }) {
            this.$patch({
                nickName,
                avatar
            });
        },
		setNickName({ nickName }) {
		    this.$patch({
		        nickName
		    });
		}
    }
});