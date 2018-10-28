package com.pinke.liudao.pinke_account.daos;

import com.pinke.liudao.pinke_account.entities.AccessTokenDTO;

public interface AccessTokenDAO {
    int insertToken(AccessTokenDTO accessTokenDTO);
    int modifyToekn(AccessTokenDTO accessTokenDTO);
    AccessTokenDTO getAccessTokenByToken(String token);
    AccessTokenDTO getAccessTokenByUserId(int userId);
}
