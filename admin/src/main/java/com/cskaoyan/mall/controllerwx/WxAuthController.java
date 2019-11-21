package com.cskaoyan.mall.controllerwx;

import com.cskaoyan.mall.bean.BaseReqVo;
import com.cskaoyan.mall.bean.BaseRespVo;
import com.cskaoyan.mall.bean.User;
import com.cskaoyan.mall.mapper.UserMapper;
import com.cskaoyan.mall.service.UserService;
import com.cskaoyan.mall.shiro.CustomToken;
import com.cskaoyan.mall.utils.UserInfo;
import com.cskaoyan.mall.utils.UserToken;
import com.cskaoyan.mall.utils.UserTokenManager;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.apache.tomcat.util.http.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by little Stone
 * Date 2019/7/8 Time 20:55
 */
@RestController
@RequestMapping("/wx")
public class WxAuthController {

	@Autowired
	private UserService userService;

	@RequestMapping("/auth/login")
	public Object login(@RequestBody User user) {
//		String username = JacksonUtil.parseString(body, "username");
//		String password = JacksonUtil.parseString(body, "password");
		Map<Object, Object> result = new HashMap<Object, Object>();
		String username = user.getUsername();
		String password = user.getPassword();
		Subject subject = SecurityUtils.getSubject();

		CustomToken customToken = new CustomToken(username, password, "wx");
		try {
			subject.login(customToken);
		} catch (AuthenticationException e) {
			return BaseRespVo.fail(515,"登陆失败");
		}
		Serializable sessionId = subject.getSession().getId();
		result.put("token",sessionId);
		LocalDateTime tokenExpire = LocalDateTime.now().plusDays(1);
		result.put("tokenExpire",tokenExpire);
		User userFromDb = (User) subject.getPrincipal();
		HashMap<Object, Object> userInfo = new HashMap<>();
		userInfo.put("nickName",userFromDb.getNickname());
		userInfo.put("avatarUrl",userFromDb.getAvatar());
		result.put("userInfo",userInfo);
		return BaseRespVo.ok(result);

	}

	@RequestMapping("auth/login_by_weixin")
	public Object loginByWechat(){
		return BaseRespVo.fail(-1,"网络繁忙，请稍后再试");
	}

	@RequestMapping("auth/logout")
	public Object logout(){
		SecurityUtils.getSubject().logout();
		BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
		baseReqVo.setErrmsg("成功");
		baseReqVo.setErrno(0);
		return baseReqVo;
	}


	@GetMapping("user/index")
	public Object list(HttpServletRequest request) {
		Subject subject = SecurityUtils.getSubject();
		User userLogined = (User) subject.getPrincipal();
		Integer id = userLogined.getId();
		HashMap<Object, Object> order = new HashMap<>();
		order.put("unrecv",1);
		order.put("uncomment",2);
		order.put("unpaid",3);
		order.put("unship",4);
		HashMap<Object, Object> data = new HashMap<>();
		data.put("order",order);
		return BaseRespVo.ok(data);
		
	}
}
