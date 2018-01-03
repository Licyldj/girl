package com.springboot.learning.controller;

import com.springboot.learning.config.GeetestConfig;
import com.springboot.learning.utils.GeetestLib;
import com.springboot.learning.utils.SystemUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

/**
 * 极验 demo
 */
@RestController
public class StartCaptchaController {

    /**
     * 大图点击Demo
     * @return
     */
    @GetMapping("/login1")
    public ModelAndView login1(){
        ModelAndView mav = new ModelAndView("login1");
        return mav;
    }
    /**
     * 滑动demo
     * @return
     */
    @GetMapping("/login")
    public ModelAndView login(){
        ModelAndView mav = new ModelAndView("login");
        return mav;
    }


    @GetMapping("/gt/register1")
    public void startCaptchaServlet1(HttpServletRequest request,
                                    HttpServletResponse response)throws IOException {

        GeetestLib gtSdk = new GeetestLib("36864497f68bb5494a400df8a201cba0", "77c867a96bc3d14cc47054da4969b957",
                GeetestConfig.isnewfailback());

        String resStr = "{}";

        String userid = "test";

        //自定义参数,可选择添加
        HashMap<String, String> param = new HashMap<String, String>();
        param.put("user_id", userid); //网站用户id
        param.put("client_type", "web"); //web:电脑上的浏览器；h5:手机上的浏览器，包括移动应用内完全内置的web_view；native：通过原生SDK植入APP应用的方式
        String ipAddr = SystemUtils.getIpAddr(request);
        param.put("ip_address", ipAddr); //传输用户请求验证时所携带的IP
//        param.put("ip_address", "192.168.10.113"); //传输用户请求验证时所携带的IP

        //进行验证预处理
        int gtServerStatus = gtSdk.preProcess(param);

        //将服务器状态设置到session中
        request.getSession().setAttribute(gtSdk.gtServerStatusSessionKey, gtServerStatus);
        //将userid设置到session中
        request.getSession().setAttribute("userid", userid);

        resStr = gtSdk.getResponseStr();

        PrintWriter out = response.getWriter();
        out.println(resStr);
    }

    @GetMapping("/gt/register2")
    public void startCaptchaServlet2(HttpServletRequest request,
                                     HttpServletResponse response)throws IOException {

        GeetestLib gtSdk = new GeetestLib("36864497f68bb5494a400df8a201cba0", "77c867a96bc3d14cc47054da4969b957",
                GeetestConfig.isnewfailback());

        String resStr = "{}";

        //自定义userid
        String userid = "test";

        //自定义参数,可选择添加
        HashMap<String, String> param = new HashMap<String, String>();
        param.put("user_id", userid); //网站用户id
        param.put("client_type", "web"); //web:电脑上的浏览器；h5:手机上的浏览器，包括移动应用内完全内置的web_view；native：通过原生SDK植入APP应用的方式
        String ipAddr = SystemUtils.getIpAddr(request);
        param.put("ip_address", ipAddr); //传输用户请求验证时所携带的IP
//        param.put("ip_address", "192.168.10.113"); //传输用户请求验证时所携带的IP

        //进行验证预处理
        int gtServerStatus = gtSdk.preProcess(param);

        //将服务器状态设置到session中
        request.getSession().setAttribute(gtSdk.gtServerStatusSessionKey, gtServerStatus);
        //将userid设置到session中
        request.getSession().setAttribute("userid", userid);

        resStr = gtSdk.getResponseStr();

        PrintWriter out = response.getWriter();
        out.println(resStr);

    }


    @PostMapping("/gt/ajax-validate1")
    public void verifyLogin1(HttpServletRequest request,
                                     HttpServletResponse response)throws IOException {

//        GeetestLib gtSdk = new GeetestLib(GeetestConfig.getGeetest_id(), GeetestConfig.getGeetest_key(),
//                GeetestConfig.isnewfailback());

        GeetestLib gtSdk = new GeetestLib("36864497f68bb5494a400df8a201cba0", "77c867a96bc3d14cc47054da4969b957",
                GeetestConfig.isnewfailback());

        String challenge = request.getParameter(GeetestLib.fn_geetest_challenge);
        String validate = request.getParameter(GeetestLib.fn_geetest_validate);
        String seccode = request.getParameter(GeetestLib.fn_geetest_seccode);

        //从session中获取gt-server状态
        int gt_server_status_code = (Integer) request.getSession().getAttribute(gtSdk.gtServerStatusSessionKey);

        //从session中获取userid
        String userid = (String)request.getSession().getAttribute("userid");

        //自定义参数,可选择添加
        HashMap<String, String> param = new HashMap<String, String>();
        param.put("user_id", userid); //网站用户id
        param.put("client_type", "web"); //web:电脑上的浏览器；h5:手机上的浏览器，包括移动应用内完全内置的web_view；native：通过原生SDK植入APP应用的方式
        String ipAddr = SystemUtils.getIpAddr(request);
        param.put("ip_address", ipAddr); //传输用户请求验证时所携带的IP
//        param.put("ip_address", "192.168.10.113"); //传输用户请求验证时所携带的IP
        int gtResult = 0;

        if (gt_server_status_code == 1) {
            //gt-server正常，向gt-server进行二次验证

            gtResult = gtSdk.enhencedValidateRequest(challenge, validate, seccode, param);
            System.out.println(gtResult);
        } else {
            // gt-server非正常情况下，进行failback模式验证

            System.out.println("failback:use your own server captcha validate");
            gtResult = gtSdk.failbackValidateRequest(challenge, validate, seccode);
            System.out.println(gtResult);
        }


        if (gtResult == 1) {
            // 验证成功
            PrintWriter out = response.getWriter();
            JSONObject data = new JSONObject();
            try {
                data.put("status", "success");
                data.put("version", gtSdk.getVersionInfo());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            out.println(data.toString());
        }
        else {
            // 验证失败
            JSONObject data = new JSONObject();
            try {
                data.put("status", "fail");
                data.put("version", gtSdk.getVersionInfo());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            PrintWriter out = response.getWriter();
            out.println(data.toString());
        }

    }

    @PostMapping("/gt/ajax-validate2")
    public void verifyLogin2(HttpServletRequest request,
                             HttpServletResponse response)throws IOException {

//        GeetestLib gtSdk = new GeetestLib(GeetestConfig.getGeetest_id(), GeetestConfig.getGeetest_key(),
//                GeetestConfig.isnewfailback());

        GeetestLib gtSdk = new GeetestLib("36864497f68bb5494a400df8a201cba0", "77c867a96bc3d14cc47054da4969b957",
                GeetestConfig.isnewfailback());

        String challenge = request.getParameter(GeetestLib.fn_geetest_challenge);
        String validate = request.getParameter(GeetestLib.fn_geetest_validate);
        String seccode = request.getParameter(GeetestLib.fn_geetest_seccode);

        //从session中获取gt-server状态
        int gt_server_status_code = (Integer) request.getSession().getAttribute(gtSdk.gtServerStatusSessionKey);

        //从session中获取userid
        String userid = (String)request.getSession().getAttribute("userid");

        //自定义参数,可选择添加
        HashMap<String, String> param = new HashMap<String, String>();
        param.put("user_id", userid); //网站用户id
        param.put("client_type", "web"); //web:电脑上的浏览器；h5:手机上的浏览器，包括移动应用内完全内置的web_view；native：通过原生SDK植入APP应用的方式
        param.put("ip_address", "192.168.10.113"); //传输用户请求验证时所携带的IP

        int gtResult = 0;

        if (gt_server_status_code == 1) {
            //gt-server正常，向gt-server进行二次验证

            gtResult = gtSdk.enhencedValidateRequest(challenge, validate, seccode, param);
            System.out.println(gtResult);
        } else {
            // gt-server非正常情况下，进行failback模式验证

            System.out.println("failback:use your own server captcha validate");
            gtResult = gtSdk.failbackValidateRequest(challenge, validate, seccode);
            System.out.println(gtResult);
        }


        if (gtResult == 1) {
            // 验证成功
            PrintWriter out = response.getWriter();
            JSONObject data = new JSONObject();
            try {
                data.put("status", "success");
                data.put("version", gtSdk.getVersionInfo());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            out.println(data.toString());
        }
        else {
            // 验证失败
            JSONObject data = new JSONObject();
            try {
                data.put("status", "fail");
                data.put("version", gtSdk.getVersionInfo());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            PrintWriter out = response.getWriter();
            out.println(data.toString());
        }

    }


//    public static void main(String[] args) {
//        GeetestLib gtSdk = new GeetestLib("36864497f68bb5494a400df8a201cba0", "77c867a96bc3d14cc47054da4969b957",
//                GeetestConfig.isnewfailback());
//
//        String resStr = "{}";
//
//        String userid = "test";
//
//        //自定义参数,可选择添加
//        HashMap<String, String> param = new HashMap<String, String>();
//        param.put("user_id", userid); //网站用户id
//        param.put("client_type", "web"); //web:电脑上的浏览器；h5:手机上的浏览器，包括移动应用内完全内置的web_view；native：通过原生SDK植入APP应用的方式
////        String ipAddr = SystemUtils.getIpAddr(request);
////        param.put("ip_address", ipAddr); //传输用户请求验证时所携带的IP
//        param.put("ip_address", "192.168.10.113"); //传输用户请求验证时所携带的IP
//        for (int i = 0; i < 500; i++) {
//            int gtServerStatus = gtSdk.preProcess(param);
//            System.out.println("gtServerStatus----"+i+"____"+gtServerStatus);
//        }
//    }
}
