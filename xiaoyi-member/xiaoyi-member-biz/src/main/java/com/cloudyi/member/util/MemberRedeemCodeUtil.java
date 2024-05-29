package com.cloudyi.member.util;

import java.security.SecureRandom;

public class MemberRedeemCodeUtil {

    public static String generateMemberRedeemCode() {
        // 定义允许的字符集
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        // 创建一个安全的随机数生成器
        SecureRandom random = new SecureRandom();

        // 创建一个StringBuilder来存储生成的兑换码
        StringBuilder codeBuilder = new StringBuilder(12);

        // 生成指定长度的兑换码
        for (int i = 0; i < 12; i++) {
            int randomIndex = random.nextInt(characters.length());
            char randomChar = characters.charAt(randomIndex);
            codeBuilder.append(randomChar);
        }
        return codeBuilder.toString();
    }
}
