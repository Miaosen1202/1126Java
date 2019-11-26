package com.wdcloud.lms.business.sis;

import com.wdcloud.lms.Constants;
import com.wdcloud.server.frame.interfaces.IDirectComponent;
import com.wdcloud.utils.file.FileUtils;
import com.wdcloud.utils.file.ZipUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.tools.ant.Main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.JarURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

@Slf4j
@IDirectComponent.DirectHandler(
        resource = Constants.RESOURCE_TYPE_SIS,
        function = Constants.FUNCTION_TYPE_TEMPLATE_DOWNLOAD
)
public class SisTemplateDownload implements IDirectComponent<Void> {

    /**
     * 文件类型为jar
     */
    private static final String RESOURCE_FILE_TYPE = "jar:";

    /**
     * @api {get} /sis/templateDownload/direct SIS导入模板下载
     * @apiDescription 返回模板文件流
     * @apiName SisTemplateDownload
     * @apiGroup SIS
     *
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response) {
        List<File> files = new ArrayList<>();
        String[] csvFieles = {"accounts.csv", "courses.csv", "enrollments.csv", "group_categories.csv", "groups.csv",
                "group_membership.csv", "sections.csv", "terms.csv", "user.csv"};

        Path tempDir = null;
        URL res = this.getClass().getClassLoader().getResource("sis/" + csvFieles[0]);
        if (res.toString().startsWith(RESOURCE_FILE_TYPE)) {
            try {
                tempDir = Files.createTempDirectory("tempDir");

                for (String csvFiele : csvFieles) {
                    InputStream input = this.getClass().getClassLoader().getResourceAsStream("sis/" + csvFiele);

                    Path path = Paths.get(tempDir.toString(), csvFiele);

                    File file = path.toFile();
                    OutputStream out = new FileOutputStream(file);
                    int read;
                    byte[] bytes = new byte[1024];
                    while ((read = input.read(bytes)) != -1) {
                        out.write(bytes, 0, read);
                    }
                    files.add(file);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else {
            for (String csvFiele : csvFieles) {
                URL resource = this.getClass().getClassLoader().getResource("sis/" + csvFiele);
                File file = new File(resource.getFile());
                files.add(file);
            }
        }

        try {
            response.setHeader("Content-Disposition", "attachment;filename=template.zip");
            ZipUtils.toZip(files, response.getOutputStream());
        } catch (IOException e) {
            log.error("[SisTemplateDownload] download error, msg={}", e.getMessage(), e);
        } finally {
            if (tempDir != null) {
                deleteFile(tempDir.toFile());
            }
        }
    }

    private void deleteFile(File file) {
        if (file != null && file.exists()) {
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                if (files != null) {
                    for (File f : files) {
                        deleteFile(f);
                    }
                }
                file.delete();
            } else {
                file.delete();
            }
        }
    }
}
