package com.zjc.security.controller;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.OSSObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/oss")
public class AliOssController {

    @ResponseBody
    @RequestMapping("/hello")
    public String hello(){
        return "hello";
    }


    @RequestMapping("/down")
    public void ossDownFiile(HttpServletResponse response) throws IOException {
        {

            System.out.println("-------");
           OutputStream outputStream =null ;
           String fileName = "测试test.txt";
            response.setHeader("content-type", "multipart/form-data;charset=UTF-8");
            response.setContentType("multipart/form-data");
            //response.setHeader("Content-Disposition", "attachment;filename=" + new String("测试.png".getBytes("gb2312"),"ISO8859-1"));
            response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("gb2312"),"ISO8859-1"));

            // yourEndpoint填写Bucket所在地域对应的Endpoint。以华东1（杭州）为例，Endpoint填写为https://oss-cn-hangzhou.aliyuncs.com。
            //String endpoint = "oss-cn-beijing.aliyuncs.com";
            String endpoint = "http://oss-cn-beijing-bmjpoc-d01-a.ops.bmjpoccloud.com";
// 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
//            String accessKeyId = "LTAI4Fu5t99q1uYUg5JtZwDg";
//            String accessKeySecret = "5l5DinvaGVzF39OAb2twYPfllV383g";

            String accessKeyId = "eCdaYNV6pKLlJzsd";
            String accessKeySecret = "ROrfpDf7br9OxU6DealqBdXP00iDmL";
// 填写Bucket名称。
//            String bucketName = "zhangjunchao";
//// 填写Object的完整路径。Object完整路径中不能包含Bucket名称。
//            String objectName = "0324638d86424203b658c6bd1d255229file.png";

           // String bucketName = "dbus";
            String bucketName = "dbus";
            String objectName = "123202108191234567890";
// 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

// ossObject包含文件所在的存储空间名称、文件名称、文件元信息以及一个输入流。
            OSSObject ossObject = ossClient.getObject(bucketName, objectName);

// 读取文件内容。
            System.out.println("Object content:");
            InputStream content = ossObject.getObjectContent();
            byte[] bytes = readInputStream(content);

            outputStream = response.getOutputStream();
            outputStream.write(bytes);
            outputStream.flush();
            /*BufferedReader reader = new BufferedReader(new InputStreamReader(ossObject.getObjectContent()));
            outputStream = response.getOutputStream();
            while (true) {
                String line = null;
                try {
                    line = reader.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (line == null) break;

                System.out.println("\n" + line);
                outputStream.write(line.getBytes(StandardCharsets.UTF_8));
                outputStream.flush();
            }*/
// 数据读取完成后，获取的流必须关闭，否则会造成连接泄漏，导致请求无连接可用，程序无法正常工作。
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

// 关闭OSSClient。
            ossClient.shutdown();

        }
    }

    public static byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while((len = inputStream.read(buffer)) != -1){
            bos.write(buffer, 0 ,len);
        }
        bos.close();
        return bos.toByteArray();
    }


}
