package ruiji_takeout.Controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ruiji_takeout.common.R;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/common")
@Slf4j
public class commonController {

    // 把属性关联到配置文件中的设置--这是存放瑞吉外卖上传的照片的路径
    @Value("${jpg.path}")
    private String basepath;

    @PostMapping("/upload")
    public R<String> upload(MultipartFile file) throws IOException {
        // 1. 上传照片第一步：继承MultipartFile ，其中file要和前端的名字一致
        // file是一个临时文件，需要转存到指定位置，否则本次请求完成后临时文件就会删除
        // 获取原始的文件名
        String originalFilename = file.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        // 使用uuid重新生成文件名，防止文件名重复，造成文件的覆盖
        String filename = UUID.randomUUID().toString() + suffix;
        File dir=new File(basepath);
        if (!dir.exists()){
            dir.mkdirs();
        }
        file.transferTo(new File(basepath+filename));
        return R.success(filename);


    }
    /**
     * 文件下载---这里的下载应该是读取上传的文件并且渲染到浏览器的意思，不是下载到本地
     */
    @GetMapping("/download")
    public void download(String name, HttpServletResponse response) throws IOException {
        // 输入流，通过输入流读取文件 内容
        FileInputStream fileInputStream=new FileInputStream(new File(basepath+name));
        // 输出流，通过输出流将文件写回浏览器，在浏览器展示图片了
        ServletOutputStream outputStream = response.getOutputStream();

        // 响应的类型是图片文件
        response.setContentType("image/jpeg");


        // io流读写操作
        int len=0;
        byte[] bytes=new byte[1024];
        while ((len=fileInputStream.read(bytes))!=-1){
            outputStream.write(bytes,0,len);
            outputStream.flush();
        }
        // 关闭资源
        outputStream.close();
        fileInputStream.close();
    }
}
