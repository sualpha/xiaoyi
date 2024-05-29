import { useState } from '@/hooks/useState';

export const changeEnv = (options) => {
    return new Promise((resolve, reject) => {
        const { setHost } = useState();
        let host = "https://xiaoyi.cloudyi.tech";

        if (options.ENV == 'DEV') {
            host="http://localhost:8888";
        }
        if (options.ENV == 'PROD') {
            host = "https://xiaoyi.cloudyi.tech";
        }
        console.log(`当前启动环境为${options.ENV || 'PROD'}`)
		setHost({
			host: host
		});
    });
};