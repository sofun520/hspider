package cn.heckman.console.controller;

import cn.heckman.modulecommon.utils.Base64Util;
import cn.heckman.moduleservice.base.ErrorConstant;
import cn.heckman.moduleservice.base.RestResponse;
import cn.heckman.moduleservice.entity.FileUploadVo;
import cn.heckman.moduleservice.service.FileUploadService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by heckman on 2018/7/29.
 */
@SuppressWarnings("all")
@RestController
@RequestMapping("/api/file")
public class FileController {

    private Logger logger = LoggerFactory.getLogger(ErrorController.class);

    @Autowired
    private FileUploadService fileUploadService;

    @Value("${file.upload.path}")
    private String filePath;

    @Value("${file.access.url}")
    private String fileAccessUrl;

    @RequestMapping(value = "/upload4Base64", method = RequestMethod.POST)
    public RestResponse upload4Base64(@RequestBody JSONObject obj) {
        RestResponse restResponse = new RestResponse();
        try {
            String content = obj.getString("base64");
            Base64Util base64Util = new Base64Util();
            MultipartFile file = base64Util.base64ToMultipart(content);
            FileUploadVo fileUploadVo = fileUploadService.fileUpload4Base64(file, filePath, fileAccessUrl);
            restResponse.setSuccess(true);
            restResponse.setData(fileUploadVo);
        } catch (Exception ex) {
            logger.error("inner error", ex);
            restResponse.setErrorCode(ErrorConstant.INNER_ERROR_CODE);
            restResponse.setErrorMsg(ErrorConstant.getErrMsg(ErrorConstant.INNER_ERROR_CODE));
        }
        return restResponse;
    }

}
