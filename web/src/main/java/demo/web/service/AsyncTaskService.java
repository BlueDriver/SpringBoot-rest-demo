package demo.web.service;

import demo.web.config.TaskExecutorConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 * parent
 * demo.web.service
 * 异步任务处理service
 *
 * @author BlueDriver
 * @email cpwu@foxmail.com
 * @date 2019/03/28 10:21 Thursday
 */
@Service
public class AsyncTaskService {
    /**
     * 定义异步任务
     */
    @Async
    public void exec() throws InterruptedException {
        System.out.println("exec async task...");
        Thread.sleep(5000);
        System.out.println("finish task");
    }

    /**
     * 异常将被捕捉 {@link TaskExecutorConfig#getAsyncUncaughtExceptionHandler}
     */
    @Async
    public void exec1() throws Exception {
        System.out.println("exec async task1...");
        throw new Exception("in");
    }

    /**
     * 文件上传路径
     */
    @Value("${web.upload-path}")
    private String path;

    /**
     * 异步保存文件
     */
    @Async
    public void saveFile(MultipartFile file) throws IOException {
        copyFileToResource(file);
    }

    /**
     * 保存多个文件
     */
    @Async
    public void saveFile(MultipartFile[] files) throws IOException {
        for (MultipartFile file : files) {
            copyFileToResource(file);
        }
    }

    /**
     * 保存文件
     */
    private void copyFileToResource(MultipartFile file) throws IOException {
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
    }

    /**
     * 获得文件后缀名
     */
    private String getFileExtension(String name) {
        return name.substring(name.lastIndexOf("."));
    }
}