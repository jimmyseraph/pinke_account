package com.pinke.liudao.pinke_account.actions;

import com.pinke.liudao.pinke_account.entities.*;
import com.pinke.liudao.pinke_account.services.AccountService;
import com.pinke.liudao.pinke_account.utils.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/account")
public class AccountAction {
    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public AccountResponseEntity register(@RequestBody RegisterUserEntity registerUserEntity){
        AccountResponseEntity accountResponseEntity = new AccountResponseEntity();
        // 1、传入的json数据格式检查
        // 1.1、 检查用户名是否为空
        if(registerUserEntity.getUsername() == null || registerUserEntity.getUsername().trim().equals("")){
            accountResponseEntity.setRetCode(20001);
            accountResponseEntity.setRetMsg("username is null");
            System.err.println("error --> username is null");
            return accountResponseEntity;
        }
        // 1.2、 检查密码是否为空
        if(registerUserEntity.getPassword() == null || registerUserEntity.getPassword().equals("")){
            accountResponseEntity.setRetCode(20002);
            accountResponseEntity.setRetMsg("password is null");
            System.err.println("error --> password is null");
            return accountResponseEntity;
        }
        // 1.3、 检查用户名长度是否在3-12位之间
        if(registerUserEntity.getUsername().length() < 3 || registerUserEntity.getUsername().length() > 12){
            accountResponseEntity.setRetCode(20003);
            accountResponseEntity.setRetMsg("the length of the username is invalid");
            System.err.println("error --> the length of the username is invalid");
            return accountResponseEntity;
        }
        // 1.4、 检查用户名是否包含大小写字母、数字、下划线以外的非法字符
        if(!registerUserEntity.getUsername().matches("^[a-zA-Z0-9_]+$")){
            accountResponseEntity.setRetCode(20004);
            accountResponseEntity.setRetMsg("username contains illegal characters");
            System.err.println("error --> username contains illegal characters");
            return accountResponseEntity;
        }
        // 1.5、 检查密码长度是否在6-18位之间
        if(registerUserEntity.getPassword().length() < 6 || registerUserEntity.getPassword().length() > 18){
            accountResponseEntity.setRetCode(20005);
            accountResponseEntity.setRetMsg("the length of the password is invalid");
            System.err.println("error --> the length of the password is invalid");
            return accountResponseEntity;
        }
        // 1.6、检查确认密码是否和密码一致
        if(!registerUserEntity.getConfirmPassword().equals(registerUserEntity.getPassword())){
            accountResponseEntity.setRetCode(20006);
            accountResponseEntity.setRetMsg("Confirm-password is not same as password");
            System.err.println("error --> Confirm-password is not same as password");
            return accountResponseEntity;
        }
        // 1.7、 检查用户名是否已存在
        if(accountService.doCheckUserExist(registerUserEntity.getUsername())){
            accountResponseEntity.setRetCode(20007);
            accountResponseEntity.setRetMsg("username is already exist");
            System.err.println("error --> username is already exist");
            return accountResponseEntity;
        }
        System.out.println("get username: "+registerUserEntity.getUsername());
        System.out.println("get password: "+registerUserEntity.getPassword());
        System.out.println("get confirmPassword: "+registerUserEntity.getConfirmPassword());

        // 2、 将用户名和密码写入持久层（todo）
        UserDTO user = new UserDTO();
        user.setUserId(1);
        user.setUsername(registerUserEntity.getUsername());
        try {
            user.setPassword(DigestUtils.hashDigest(registerUserEntity.getPassword(),"MD5"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        accountService.doAddUser(user);
        accountResponseEntity.setRetCode(10000);
        accountResponseEntity.setRetMsg("register successful");
        return accountResponseEntity;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public AccountResponseEntity login(
            @RequestParam(value = "username") String username,
            @RequestParam("password") String password
    ){
        AccountResponseEntity accountResponseEntity = new AccountResponseEntity();
        if(accountService.doCheckUser(username, password)){
            accountResponseEntity.setRetCode(10000);
            accountResponseEntity.setRetMsg("login successful");
            AccessTokenResponseEntity accessTokenResponseEntity = new AccessTokenResponseEntity();
            AccessTokenDTO accessTokenDTO = accountService.doRegisterToken(username);
            accessTokenResponseEntity.setToken(accessTokenDTO.getToken());
            accessTokenResponseEntity.setExpiredTime(accessTokenDTO.getExpireTime());
            accountResponseEntity.setData(accessTokenResponseEntity);
        }else {
            accountResponseEntity.setRetCode(20008);
            accountResponseEntity.setRetMsg("username or password is invalid");
            System.err.println("error --> username or password is invalid");
        }
        return accountResponseEntity;
    }
}
