package cn.heckman.moduleservice.service;

import cn.heckman.moduleservice.entity.FileUploadVo;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by heckman on 2018/7/29.
 */
public interface FileUploadService {

    FileUploadVo fileUpload4Base64(MultipartFile file, String filePath, String fileAccessUrl);

}
