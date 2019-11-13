package com.example.demo.controller;


//import com.yxtech.common.Constant;
//        import com.yxtech.sys.domain.FileRes;
//        import com.yxtech.sys.service.FileResService;
//        import com.yxtech.sys.service.ResService;
//        import com.yxtech.utils.file.FileMd5Util;
//        import com.yxtech.utils.file.NameUtil;
//        import com.yxtech.utils.file.PathUtil;
//        import jodd.datetime.JDateTime;
        import com.example.demo.bean.FileRes;
        import com.example.demo.common.Constant;
        import com.example.demo.service.FileResService;
        import com.example.demo.util.FileMd5Util;
        import com.example.demo.util.NameUtil;
        import com.example.demo.util.PathUtil;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.context.annotation.Scope;
        import org.springframework.web.bind.annotation.RequestMapping;
        import org.springframework.web.bind.annotation.RequestParam;
        import org.springframework.web.bind.annotation.RestController;
        import org.springframework.web.multipart.MultipartFile;
//        import tk.mybatis.mapper.entity.Example;

        import javax.servlet.http.HttpServletRequest;
        import java.io.File;
        import java.io.FileInputStream;
        import java.io.FileOutputStream;
        import java.io.IOException;
        import java.text.SimpleDateFormat;
        import java.util.*;

/**
 * Created by Administrator on 2015/10/9.
 */
@RestController
@Scope("prototype")
@RequestMapping(value = "/test")
public class TestController {
    @Autowired
    private FileResService fileResService;
//    @Autowired
//    private ResService resService;



    /**
     * 上传文件
     *
     * @param request
     * @return
     * @throws IllegalStateException
     * @throws IOException
     */
    @RequestMapping(value = "/upload")
    public Map<String, Object> upload(
            HttpServletRequest request, @RequestParam(value = "data",required = false) MultipartFile multipartFile) throws IllegalStateException, IOException, Exception {

        String action = request.getParameter("action");
        String uuid = request.getParameter("uuid");
        String fileName = request.getParameter("name");
        String size = request.getParameter("size");//总大小
        int total = Integer.valueOf(request.getParameter("total"));//总片数
        int index = Integer.valueOf(request.getParameter("index"));//当前是第几片
        String fileMd5 = request.getParameter("filemd5"); //整个文件的md5
        System.out.println(fileMd5);
        String date = request.getParameter("date"); //文件第一个分片上传的日期(如:20170122)
        String md5 = request.getParameter("md5"); //分片的md5

        //生成上传文件的路径信息，按天生成
        String savePath = Constant.FILE_PATH + File.separator + date;
//        String savePath ="";
        String saveDirectory = PathUtil.getAppRootPath(request) + savePath + File.separator + uuid;
        //验证路径是否存在，不存在则创建目录
        File path = new File(saveDirectory);
        if (!path.exists()) {
            path.mkdirs();
        }
        //文件分片位置
        File file = new File(saveDirectory, uuid + "_" + index);

        //根据action不同执行不同操作. check:校验分片是否上传过; upload:直接上传分片
        Map<String, Object> map = new HashMap<>();
        if("check".equals(action)){
            String md5Str = FileMd5Util.getFileMD5(file);
            if (md5Str != null && md5Str.length() == 31) {
                System.out.println("check length =" + md5.length() + " md5Str length" + md5Str.length() + "   " + md5 + " " + md5Str);
                md5Str = "0" + md5Str;
            }
            if (md5Str != null && md5Str.equals(md5)) {
                //分片已上传过
                map = new HashMap<>();
                map.put("flag", "2");
                map.put("fileId", uuid);
                map.put("status", true);
                return map;
            } else {
                //分片未上传
                map = new HashMap<>();
                map.put("flag", "1");
                map.put("fileId", uuid);
                map.put("status", true);
                return map;
            }
        }else if("upload".equals(action)){
            //分片上传过程中出错,有残余时需删除分块后,重新上传
            if (file.exists()) {
                file.delete();
            }
            multipartFile.transferTo(new File(saveDirectory, uuid + "_" + index));
        }

        if (path.isDirectory()) {
            File[] fileArray = path.listFiles();
            if (fileArray != null) {
                if (fileArray.length == total) {
                    //分块全部上传完毕,合并
                    String suffix = NameUtil.getExtensionName(fileName);

                    File newFile = new File(PathUtil.getAppRootPath(request) + savePath, uuid + "." + suffix);
                    FileOutputStream outputStream = new FileOutputStream(newFile, true);//文件追加写入
                    byte[] byt = new byte[10 * 1024 * 1024];
                    int len;

                    FileInputStream temp = null;//分片文件
                    for (int i = 0; i < total; i++) {
                        int j = i + 1;
                        temp = new FileInputStream(new File(saveDirectory, uuid + "_" + j));
                        while ((len = temp.read(byt)) != -1) {
                            System.out.println("-----" + len);
                            outputStream.write(byt, 0, len);
                        }
                    }
                    //关闭流
                    temp.close();
                    outputStream.close();
                    //修改FileRes记录为上传成功
//                    Example example = new Example(FileRes.class);
//                    Example.Criteria criteria = example.createCriteria();
                    map.put("md5",fileMd5);
                    map.put("status",Constant.ONE);
//                    criteria.andEqualTo("md5",fileMd5);
//                    FileRes fileRes = new FileRes();
//                    fileRes.setStatus(Constant.ONE);
                    fileResService.update(map);
                }else if(index == 1){
                    //文件第一个分片上传时记录到数据库
                    FileRes fileRes = new FileRes();
                    String name = NameUtil.getFileNameNoEx(fileName);
                    if (name.length() > 50) {
                        name = name.substring(0, 50);
                    }
                    fileRes.setName(name);
                    fileRes.setSuffix(NameUtil.getExtensionName(fileName));
                    fileRes.setUuid(uuid);
                    fileRes.setPath(savePath + File.separator + uuid + "." + fileRes.getSuffix());
                    fileRes.setSize(Integer.parseInt(size));
                    fileRes.setMd5(fileMd5);
                    fileRes.setStatus(Constant.ZERO);
                    fileRes.setCreateTime(new Date());
                    fileResService.insert(fileRes);
                }
            }
        }

        map = new HashMap<>();
        map.put("flag", "3");
        map.put("fileId", uuid);
        map.put("status", true);
        return map;
    }

    /**
     * 上传文件前校验
     *
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/isUpload")
    public Map<String, Object> isUpload(HttpServletRequest request) throws Exception {

        String md5 = request.getParameter("md5");

//        Example example = new Example(FileRes.class);
//        Example.Criteria criteria = example.createCriteria();
//        criteria.andEqualTo("md5", md5);
//        Map<String, Object> map = new HashMap<>();
//        map.put("md5", md5);
        List<FileRes> list = fileResService.select(md5);
        
        
        int flag = 0;
        //求文件上传日期
        SimpleDateFormat sdf=new SimpleDateFormat("YYYYMMdd");
        Date date=new Date();
        String strDate=sdf.format(date);
        Map<String, Object> map = null;
        String uuid = UUID.randomUUID().toString();
        if (flag == 0) {
            //没有上传过文件
            map = new HashMap<>();
            map.put("flag", "1");
            map.put("fileId", uuid);
            map.put("date", strDate);
            map.put("status", true);
            flag = 1;
        } else {
            FileRes fileRes = list.get(0);

            if(flag==1){
                //文件上传部分
                map = new HashMap<>();
                map.put("flag", "2");
                map.put("fileId", uuid);
                map.put("date",strDate);
                map.put("status", true);
            }else if(fileRes.getStatus()==1){
                //文件上传成功
                map = new HashMap<>();
                map.put("flag", "3");
                map.put("fileId", fileRes.getUuid());
                map.put("date",strDate);
                map.put("status", true);
            }

        }

        return map;
    }


}