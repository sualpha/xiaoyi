<script setup name="app">

import { onLaunch } from "@dcloudio/uni-app";
import {changeEnv} from "./hooks/changeEnv";

onLaunch((options) => {

    changeEnv(options.query);
    // 检查更新
    const updateManager = uni.getUpdateManager();

    updateManager.onUpdateReady(() => updateManager.applyUpdate());

});

</script>

<template></template>

<style></style>
