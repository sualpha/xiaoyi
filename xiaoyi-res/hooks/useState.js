import { storeToRefs } from 'pinia';
import { useMemberStore } from '@/store/member';
import { useAppStore } from '@/store/app';
import { useEnvStore } from '@/store/env';

export const useState = () => {

    const memberStore = useMemberStore();
    const appStore = useAppStore();
    const envStore = useEnvStore();

    return {
        ...memberStore,
        ...storeToRefs(memberStore),
        ...appStore,
        ...storeToRefs(appStore),
        ...envStore,
        ...storeToRefs(envStore)
    };

};