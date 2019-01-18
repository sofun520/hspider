package cn.heckman.moduleservice.serviceImpl;

import cn.heckman.modulecommon.utils.DateUtils;
import cn.heckman.moduleservice.entity.FileUploadVo;
import cn.heckman.moduleservice.service.FileUploadService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * Created by heckman on 2018/7/29.
 */
@Service
public class FileUploadServiceImpl implements FileUploadService {

    @Override
    public FileUploadVo fileUpload4Base64(MultipartFile file, String filePath, String fileAccessUrl) {

        //String fileName = file.getOriginalFilename();
        String fileName = DateUtils.getTime("yyyyMMddhhmmssSSS");
        Long size = file.getSize();
        System.out.println(fileName + "-->" + size);

        System.out.println(filePath);

        File dest = new File(filePath + File.separator + fileName + ".png");
        if (!dest.getParentFile().exists()) { //判断文件父目录是否存在
            dest.getParentFile().mkdir();
        }
        try {
            file.transferTo(dest); //保存文件
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileUploadVo fileUploadVo = new FileUploadVo(fileName, fileAccessUrl + "/" + fileName + ".png", size);
        return fileUploadVo;
    }
}
