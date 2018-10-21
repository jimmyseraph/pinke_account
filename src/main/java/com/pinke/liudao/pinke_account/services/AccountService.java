package com.pinke.liudao.pinke_account.services;

import com.pinke.liudao.pinke_account.daos.UserDAO;
import com.pinke.liudao.pinke_account.entities.AccessTokenDTO;
import com.pinke.liudao.pinke_account.entities.UserDTO;
import com.pinke.liudao.pinke_account.utils.DigestUtils;
import com.pinke.liudao.pinke_account.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

@Service
public class AccountService {
    @Autowired
    private UserDAO userDAO;

    public boolean doCheckUserExist(String username){
        UserDTO user = userDAO.getUserByName(username);
        return user != null;
    }

    public void doAddUser(UserDTO user){
        userDAO.addUser(user);
    }

    public boolean doCheckUser(String username, String password){
        UserDTO user = userDAO.getUserByName(username);
        if(user == null){
            System.err.println("username is invalid");
            return false;
        }else{
            try {
                password = DigestUtils.hashDigest(password,"MD5");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return password.equals(user.getPassword());
        }
    }

    public AccessTokenDTO doRegisterToken(String username){
        String keyString = TokenUtils.genKeyString();
        SecretKey key = TokenUtils.genSecretKey(keyString);
        Date expireTime = new Date(System.currentTimeMillis() + 30 * 60 * 1000);
        String token = TokenUtils.createToken(key, username, expireTime);
        AccessTokenDTO accessToken = new AccessTokenDTO();
        accessToken.setExpireTime(expireTime);
        accessToken.setPrivateKey(keyString);
        accessToken.setToken(token);
        accessToken.setUserId(userDAO.getUserByName(username).getUserId());
        return accessToken;
    }
}
