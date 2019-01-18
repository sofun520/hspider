package cn.heckman.moduleservice.entity;

/**
 * Created by heckman on 2018/7/29.
 */
public class FileUploadVo {

    private String fileName;
    private String url;
    private Long fileSize;

    public FileUploadVo() {
    }

    public FileUploadVo(String fileName, String url, Long fileSize) {
        this.fileName = fileName;
        this.url = url;
        this.fileSize = fileSize;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }
}
