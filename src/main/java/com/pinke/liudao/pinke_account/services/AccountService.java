package com.pinke.liudao.pinke_account.services;

import com.pinke.liudao.pinke_account.daos.AccessTokenDAO;
import com.pinke.liudao.pinke_account.daos.UserDAO;
import com.pinke.liudao.pinke_account.entities.AccessTokenDTO;
import com.pinke.liudao.pinke_account.entities.AccountResponseEntity;
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

    @Autowired
    private AccessTokenDAO accessTokenDAO;

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
        int userId = userDAO.getUserByName(username).getUserId();
        AccessTokenDTO accessToken = accessTokenDAO.getAccessTokenByUserId(userId);
        if(accessToken == null){
            accessToken = new AccessTokenDTO();
            accessToken.setExpiredTime(expireTime);
            accessToken.setPrivateKey(keyString);
            accessToken.setToken(token);
            accessToken.setUserId(userId);
            accessTokenDAO.insertToken(accessToken);
        } else{
            accessToken.setExpiredTime(expireTime);
            accessToken.setPrivateKey(keyString);
            accessToken.setToken(token);
            accessTokenDAO.modifyToekn(accessToken);
        }
        return accessToken;
    }

    public void doCheckToken(String token, AccountResponseEntity accountResponseEntity){
        AccessTokenDTO accessTokenDTO = accessTokenDAO.getAccessTokenByToken(token);
        if(accessTokenDTO == null){
            accountResponseEntity.setRetCode(20009);
            accountResponseEntity.setRetMsg("Token is invalid");
            return;
        }
        if(accessTokenDTO.getExpiredTime().compareTo(new Date()) < 0){
            accountResponseEntity.setRetCode(20010);
            accountResponseEntity.setRetMsg("Token is out of date");
            return;
        }
        accountResponseEntity.setRetCode(10000);
        accountResponseEntity.setRetMsg("Token is valid");
    }
}
