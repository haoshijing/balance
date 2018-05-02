package com.yoyzi.balance.auth;

import com.google.common.collect.Lists;
import com.youzi.balance.base.ApiResponse;
import com.yoyzi.balance.auth.request.UpdatePwdRequest;
import com.yoyzi.balance.auth.response.UserDataResponse;
import com.yoyzi.balance.constants.RetCode;
import com.yoyzi.balance.controller.AbstractController;
import com.yoyzi.balance.service.auth.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController extends AbstractController {

    @Autowired
    private AdminService adminService;

    @RequestMapping("/info")
    public ApiResponse<UserDataResponse> getUserInfo(){
        UserDataResponse response = new UserDataResponse();
        response.setAvatar("https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        response.setRoles(Lists.newArrayList("admin"));
        response.setIntroduction("");
        response.setName("admin");
        return new ApiResponse<>(response);
    }

    @RequestMapping("/updatePwd")
    @ResponseBody
    public ApiResponse<Boolean> updatePwd(@RequestBody UpdatePwdRequest updatePwdRequest, HttpServletRequest request){
        try{
            String oldPwd = updatePwdRequest.getOldPwd();
            String newPwd =updatePwdRequest.getNewPwd();
            if(StringUtils.isEmpty(oldPwd) || StringUtils.isEmpty(newPwd)){
                return new ApiResponse<>(RetCode.PARAM_ERROR,"参数不能为空",false);
            }
            if(StringUtils.equals(oldPwd,newPwd)){
                return new ApiResponse<>(RetCode.PARAM_ERROR,"新旧密码不能一样",false);
            }

            Boolean updateRet = adminService.updatePwd(oldPwd, newPwd);
            if(!updateRet) {
                return new ApiResponse<>(RetCode.ERROR,"旧密码错误",false);
            }
            return new ApiResponse<>(updateRet);
        }catch (Exception e){
            return new ApiResponse<>(RetCode.ERROR,e.getMessage(),false);
        }
    }
}
