import {
	onLoad
} from "@dcloudio/uni-app";
import {
	useState
} from '@/hooks/useState';
import _ from 'lodash';
import {
	memberAuth
} from '@/hooks/memberAuth';

export const onMounted = (fucntion) => {
	const checkForPageLoad = () => {
		const token = uni.getStorageSync('token');
		const memberId = useState().memberId.value;
		if (!token || !memberId) {
			return memberAuth();
		}else{
			return Promise.resolve();
		}
	};

	onLoad((opts) => {
		checkForPageLoad().then(() => {
			fucntion(opts);
		});
	});
};