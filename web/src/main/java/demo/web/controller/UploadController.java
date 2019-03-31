package demo.web.controller;

import demo.web.service.AsyncTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * demo.web.controller
 * parent
 * 文件上传
 *
 * @author BlueDriver
 * @email cpwu@foxmail.com
 * @date 2019/03/31 09:55 Sunday
 */
@RestController
public class UploadController {
    /**
     * 返回的数据，记得每次用之前都得new一下
     */
    private Map<String, Object> map;

    /**
     * 文件上传（可多个，公用name=file）
     */
    @PostMapping("/upload")
    public Map<String, Object> upload(@RequestParam String desc, @RequestParam("file") MultipartFile[] files) {
        map = new HashMap<>();
        map.put("desc", desc);
        List<String> list = new ArrayList<>();
        for (MultipartFile file : files) {
            list.add(file.getOriginalFilename());
        }
        map.put("files", list);
        return map;
    }

    /**
     * 文件上传（可多个，文件对应不同的name）
     */
    @PostMapping("/upload1")
    public Map<String, Object> upload(@RequestParam String desc, @RequestParam("file1") MultipartFile file1,
                                      @RequestParam("file2") MultipartFile file2) {
        map = new HashMap<>();
        map.put("desc", desc);
        map.put("file1", file1.getOriginalFilename());
        map.put("file2", file2.getOriginalFilename());
        return map;
    }

    /**
     * 异步任务
     */
    @Autowired
    private AsyncTaskService asyncTaskService;

    /**
     * 文件上传并保存
     */
    @PostMapping("/upload2")
    public Map<String, Object> upload(@RequestParam String desc, @RequestParam("file") MultipartFile file) throws IOException {
        long start = System.currentTimeMillis();
        map = new HashMap<>();
        if (file.isEmpty()) {
            map.put("msg", "file is empty");
            return map;
        }
        String contentType = file.getContentType();
        long size = file.getSize();
        String originalName = file.getOriginalFilename();
        boolean isImage = isImage(file);
        //保存文件（同步）
        Resource resource = copyFileToResource(file);
        String fileName = resource.getFilename();
        map.put("fileName", fileName);
        /**
         * 使用异步任务来保存，可提升响应的速速，但是可能会失败抛错
         */
        //asyncTaskService.saveFile(file);
        map.put("desc", desc);
        map.put("contentType", contentType);
        map.put("size", size);
        map.put("originalName", originalName);
        map.put("isImage", isImage);
        map.put("cost", System.currentTimeMillis() - start);
        return map;
    }

    /**
     * 文件上传的路径
     */
    @Value("${web.upload-path}")
    private String path;

    /**
     * 保存文件
     */
    private Resource copyFileToResource(MultipartFile file) throws IOException {
        String fileExtension = getFileExtension(file.getOriginalFilename());
        File f = new File(path);
        if (!f.exists()) {
            f.mkdirs();
        }
        File tempFile = File.createTempFile("upload_", fileExtension, f);

        try (InputStream in = file.getInputStream();
             OutputStream out = new FileOutputStream(tempFile)) {
            FileCopyUtils.copy(in, out);
        }
        return new FileSystemResource(tempFile);
    }

    /**
     * 获得文件后缀名
     */
    private String getFileExtension(String name) {
        return name.substring(name.lastIndexOf("."));
    }

    /**
     * 文件是否为图片
     */
    private boolean isImage(MultipartFile file) {
        return file.getContentType().startsWith("image");
    }
}
