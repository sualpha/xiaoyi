package com.cloudyi.mini.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author subo
 * @date 2023/8/20 18:34
 **/
public interface MiniLedgerDetailsService {

   String voiceAccounting(MultipartFile file,Long memberId);
}
