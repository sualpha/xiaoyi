package com.cloudyi.admin.util;

import cn.hutool.crypto.digest.DigestUtil;

public class PwdUtils {

	public static String encryptPassword(String userName,String password){
		String data = userName+password;
		return DigestUtil.md5Hex(data);
	}

	public static void main(String[] args) {
		/*String s = encryptPassword();
		System.out.println(s);*/
	}
	
}
