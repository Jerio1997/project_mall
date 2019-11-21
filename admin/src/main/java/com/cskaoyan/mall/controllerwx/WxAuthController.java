package com.cskaoyan.mall.controllerwx;

import com.cskaoyan.mall.bean.*;
import com.cskaoyan.mall.mapper.UserMapper;
import com.cskaoyan.mall.service.OrderService;
import com.cskaoyan.mall.service.SmsService;
import com.cskaoyan.mall.service.UserService;
import com.cskaoyan.mall.shiro.CustomToken;
import com.cskaoyan.mall.utils.Md5Util;
import com.cskaoyan.mall.utils.UserInfo;
import com.cskaoyan.mall.utils.UserToken;
import com.cskaoyan.mall.utils.UserTokenManager;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.tomcat.util.http.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Created by little Stone
 * Date 2019/7/8 Time 20:55
 */
@RestController
@RequestMapping("/wx")
public class WxAuthController {

    private Map<String, String> regCaptchaMap = new HashMap<>();

    @Autowired
    UserService userService;
    @Autowired
    SmsService smsService;
    @Autowired
    OrderService orderService;

    @RequestMapping("/auth/login")
    public Object login(@RequestBody User user) {
//		String username = JacksonUtil.parseString(body, "username");
//		String password = JacksonUtil.parseString(body, "password");
        Map<Object, Object> result = new HashMap<Object, Object>();
        String username = user.getUsername();
        String password = user.getPassword();
        Subject subject = SecurityUtils.getSubject();


        try {
            String md5Password = Md5Util.getMd5(password);
            CustomToken customToken = new CustomToken(username, md5Password, "wx");
            subject.login(customToken);
        } catch (AuthenticationException | NoSuchAlgorithmException e) {
            return BaseRespVo.fail(515, null);
        }
        Serializable sessionId = subject.getSession().getId();
        result.put("token", sessionId);
        LocalDateTime tokenExpire = LocalDateTime.now().plusDays(1);
        result.put("tokenExpire", tokenExpire);
        User userFromDb = (User) subject.getPrincipal();
        HashMap<Object, Object> userInfo = new HashMap<>();
        userInfo.put("nickName", userFromDb.getNickname());
        userInfo.put("avatarUrl", userFromDb.getAvatar());
        result.put("userInfo", userInfo);
        return BaseRespVo.ok(result);

    }

    @RequestMapping("auth/login_by_weixin")
    public Object loginByWechat() {
        return BaseRespVo.fail(-1, "网络繁忙，请稍后再试");
    }

    @RequestMapping("auth/logout")
    public Object logout() {
        SecurityUtils.getSubject().logout();
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }

    @RequestMapping("auth/register")
    public Object register(@RequestBody WxRegister wxRegister) {
        String codeOne = wxRegister.getCode();
        String codeTwo = regCaptchaMap.get(wxRegister.getMobile());
        if (!codeTwo.equals(codeOne)) {
            return BaseRespVo.fail(520, null);
        }
        User user = userService.getUserByUsername(wxRegister.getUsername());
        if (user != null) {
            return BaseRespVo.fail(519, null);
        }
        userService.register(wxRegister);

        User userLogin = new User();

        userLogin.setUsername(user.getUsername());
        userLogin.setPassword(user.getPassword());

        return login(userLogin);
    }

    @RequestMapping("auth/regCaptcha")
    public Object getRegCaptcha(@RequestBody Map mobileMap) {
        String mobile = (String) mobileMap.get("mobile");
        String str = "0123456789";
        String code = new String();
        for (int i = 0; i < 4; i++) {
            char ch = str.charAt(new Random().nextInt(str.length()));
            code += ch;
        }
        smsService.sendRegCaptcha(mobile, code);
        regCaptchaMap.put(mobile, code);
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        baseReqVo.setErrno(0);
        return baseReqVo;
    }

    @RequestMapping("auth/reset")
    public Object reset() {
        return null;
    }

    @RequestMapping("auth/bindPhone")
    public Object bindPhone() {
        return null;
    }

    @GetMapping("user/index")
    public Object list(HttpServletRequest request) {
        Subject subject = SecurityUtils.getSubject();
        User userLogined = (User) subject.getPrincipal();
        Integer id = userLogined.getId();
        HashMap<Object, Object> order = new HashMap<>();
        String unrecv = "301";
        String uncommentSystem = "402";
        String uncommentUser = "401";
        String unpaid = "101";
        String unship = "201";
        List<Order> orderunrecvList = orderService.selectOrderByUserIdAndStatus(id, unrecv);
        List<Order> orderuncommentSystemList = orderService.selectOrderByUserIdAndStatus(id, uncommentSystem);
        List<Order> orderuncommentUserList = orderService.selectOrderByUserIdAndStatus(id, uncommentUser);
        List<Order> orderunpaidList = orderService.selectOrderByUserIdAndStatus(id, unpaid);
        List<Order> orderunshipList = orderService.selectOrderByUserIdAndStatus(id, unship);
        order.put("unrecv", orderunrecvList.size());
        order.put("uncomment", orderuncommentSystemList.size() + orderuncommentUserList.size());
        order.put("unpaid", orderunpaidList.size());
        order.put("unship", orderunshipList.size());
        HashMap<Object, Object> data = new HashMap<>();
        data.put("order", order);
        return BaseRespVo.ok(data);

    }

}
