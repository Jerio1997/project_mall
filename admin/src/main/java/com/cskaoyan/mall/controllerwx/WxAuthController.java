package com.cskaoyan.mall.controllerwx;

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
			/*result.put("token", null);
			return BaseRespVo.ok(result);*/
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
		/*Map<Object, Object> result = new HashMap<Object, Object>();
		List<User> loginUser = userService.authUser(user);

		if (loginUser.size() == 0) {
			// 登录失败
			result.put("token", null);
			return result;
		}

		// 登录成功，将登录成功的 User 加入到 session 域中
		request.getSession().setAttribute("user", loginUser.get(0));

		//*******************************
		//根据username和password查询user信息
		//*******************************

		// userInfo
		UserInfo userInfo = new UserInfo();
		userInfo.setNickName(user.getUsername());
		//userInfo.setAvatarUrl(user.getAvatar());



		//********************************
		//根据获得userid
		int userId = 1;
		//********************************
		// token
		UserToken userToken = UserTokenManager.generateToken(userId);

		result.put("token", userToken.getToken());
		result.put("tokenExpire", userToken.getExpireTime().toString());
		result.put("userInfo", userInfo);
		return BaseRespVo.ok(result);*/
	}

	@RequestMapping("auth/login_by_weixin")
	public Object loginByWechat(){
		return BaseRespVo.fail(-1,"网络繁忙，请稍后再试");
	}

	@RequestMapping("auth/logout")
	public Object logout(){
		return null;
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
		/*//前端写了一个token放在请求头中
		//*************************
		//获得请求头
		String tokenKey = request.getHeader("X-Litemall-Token");
		Integer userId = UserTokenManager.getUserId(tokenKey);
		//通过请求头获得userId，进而可以获得一切关于user的信息
		//**************************
		if (userId == null) {
			return BaseRespVo.fail();
		}

		Map<Object, Object> data = new HashMap<Object, Object>();
		//***********************************
		//根据userId查询订单信息
		data.put("order", null);
		//***********************************

		return BaseRespVo.ok(data);*/
	}
}
