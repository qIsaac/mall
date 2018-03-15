package com.mmall.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @Author : Isaac
 * @Description:
 * @Date :Created in 12:50 2018/3/15
 */
public interface IFileService {
    String upload(MultipartFile file, String path);
}
