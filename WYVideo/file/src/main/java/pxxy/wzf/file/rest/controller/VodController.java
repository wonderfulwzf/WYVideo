package pxxy.wzf.file.rest.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSSClient;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.CreateUploadVideoResponse;
import com.aliyuncs.vod.model.v20170321.GetMezzanineInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pxxy.wzf.file.rest.vo.common.Rest;
import pxxy.wzf.file.rest.vo.util.VodUtil;
import pxxy.wzf.server.dto.FileDto;
import pxxy.wzf.server.service.FileService;
import pxxy.wzf.server.utils.UuidUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequestMapping("/file")
public class VodController {
    private static final Logger LOG = LoggerFactory.getLogger(VodController.class);
    public static final String BUSINESS_NAME = "文件vod";
    @Value("${vod.accessKeyId}")
    private String accessKeyId;

    @Value("${vod.accessKeySecret}")
    private String accessKeySecret;
    @Autowired
    private FileService fileService;

    @RequestMapping("/vod")
    public Rest<FileDto> appendUpload(@RequestParam MultipartFile video, HttpServletRequest request) throws IOException {
        Rest<FileDto> rest = new Rest<>();
        String key ="wzf/"+UuidUtil.getShortUuid()+".mp4";
        //文件上传
        String vod = "";
        String fileUrl = "";
        String resource = request.getParameter("name");
        try {
            // 初始化VOD客户端并获取上传地址和凭证
            System.out.println(accessKeyId);
            System.out.println(accessKeySecret);
            DefaultAcsClient vodClient = VodUtil.initVodClient(accessKeyId, accessKeySecret);
            CreateUploadVideoResponse createUploadVideoResponse = VodUtil.createUploadVideo(vodClient, key);
            // 执行成功会返回VideoId、UploadAddress和UploadAuth
            vod = createUploadVideoResponse.getVideoId();
            JSONObject uploadAuth = JSONObject.parseObject(
                    Base64.decodeBase64(createUploadVideoResponse.getUploadAuth()), JSONObject.class);
            JSONObject uploadAddress = JSONObject.parseObject(
                    Base64.decodeBase64(createUploadVideoResponse.getUploadAddress()), JSONObject.class);
            // 使用UploadAuth和UploadAddress初始化OSS客户端
            OSSClient ossClient = VodUtil.initOssClient(uploadAuth, uploadAddress);
            // 上传文件，注意是同步上传会阻塞等待，耗时与文件大小和网络上行带宽有关
            VodUtil.uploadLocalFile(ossClient, uploadAddress, video.getInputStream());
            LOG.info("上传视频成功, vod : " + vod);
            GetMezzanineInfoResponse response = VodUtil.getMezzanineInfo(vodClient, vod);
            System.out.println("获取视频信息, response : " + JSON.toJSONString(response));
            fileUrl = response.getMezzanine().getFileURL();
            // 关闭OSSClient。
            ossClient.shutdown();
        } catch (Exception e) {
            LOG.info("上传视频失败, ErrorMessage : " + e.getLocalizedMessage(), e);
        }

        //保存文件记录
        LOG.info("保存文件记录开始");
        FileDto fileDto = new FileDto();
        fileDto.setId(UuidUtil.getShortUuid());
        fileDto.setPath(key);
        fileDto.setVod(vod);
        fileDto.setResource(resource);
        fileDto.setSize((int) video.getSize());
        fileDto.setName(video.getOriginalFilename());
        fileService.add(fileDto);
        fileDto.setPath(fileUrl);
        return rest.resultSuccessInfo(fileDto);
    }


    @RequestMapping(value = "/get-auth/{vod}", method = RequestMethod.GET)
    public Rest<String> getAuth(@PathVariable String vod) throws ClientException {
        LOG.info("获取播放授权开始: ");
        Rest<String> rest = new Rest<>();
        DefaultAcsClient client = VodUtil.initVodClient(accessKeyId, accessKeySecret);
        GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();
        try {
            response = VodUtil.getVideoPlayAuth(client, vod);
            LOG.info("授权码 = {}", response.getPlayAuth());
            rest.setData(response.getPlayAuth());
            //VideoMeta信息
            LOG.info("VideoMeta = {}", JSON.toJSONString(response.getVideoMeta()));
        } catch (Exception e) {
            System.out.print("ErrorMessage = " + e.getLocalizedMessage());
        }
        LOG.info("获取播放授权结束");
        return rest;
    }
}
