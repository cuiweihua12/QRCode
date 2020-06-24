package com.mr.cwh.rq.controller;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.UUID;

/**
 * @program: login
 * @description:
 * @author: cuiweihua
 * @create: 2020-06-23 16:36
 */
@Controller
@RequestMapping("/uuid")
public class UU {

    @RequestMapping("/index")
    public String index(HttpServletRequest request, HttpServletResponse response){
        System.out.println("进入首页,生成UUID");
        request.setAttribute("uuid", UUID.randomUUID());
        System.out.println(request.getAttribute("uuid"));
        return "index";
    }

    /**
     * 生成微信图片二维码
     *
     * @param request
     * @param response
     * @param content   为前端传过来的二维码的内容，即路径链接
     * @throws Exception
     */
    @GetMapping("/qrcodeaa")
    public void qrcode(HttpServletRequest request, HttpServletResponse response, @RequestParam(name = "content") String content) throws Exception {
        //调用工具类，生成二维码
        RecodeUtil.creatRrCode(content, 180,180,response);   //180为图片高度和宽度
    }

    @RequestMapping("/qrcode/{uuid}")
    @ResponseBody
    public String createQRCode(@PathVariable String uuid,HttpServletResponse response){
        System.out.println("生成二维码");
        String text = "D:\\pic\\img\\"+uuid;
        int width = 300;
        int height = 300;
        String format = "PNG";
        //将uuid放入缓存
        ScanPool scanPool = new ScanPool();
        PoolCache.cacheMao.put(uuid.toString(),scanPool);
        try {
            HashMap<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
            hints.put(EncodeHintType.CHARACTER_SET,"utf-8");
            //容错率
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
            BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height, hints);
            MatrixToImageWriter.writeToStream(bitMatrix,format, response.getOutputStream());
        } catch (WriterException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查询是否被扫描
     */
    @RequestMapping("/pool")
    @ResponseBody
    public String pool(@RequestBody String uuid){
        System.out.println("检测["+uuid+"]是否登录");
        ScanPool pool = PoolCache.cacheMao.get(uuid.toString());
        if (pool == null){
            return "timeout";
        }
        boolean scanFlag = pool.getScanStatus();
        if (scanFlag){
            return "success";
        }else {
            return "fail";
        }
    }

    /**
     * 被扫描成功后
     */
    @RequestMapping("/login/{uuid}")
    @ResponseBody
    String login(@PathVariable String uuid){
        ScanPool pool = PoolCache.cacheMao.get(uuid);
        if (pool == null){
            return "timeout,scan fail";
        }
        pool.scanSuccess();
        System.out.println("登录成功");
        return "scan success";
    }
}
