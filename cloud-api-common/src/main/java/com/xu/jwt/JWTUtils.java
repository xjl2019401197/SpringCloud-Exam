package com.xu.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

/**
 * JWT工具类
 * 注意点：
 * 1.生成的token是可以通过base64进行解密出明文信息
 * 2.base64进行解密出的明文信息，修改再进行编码，则会解密失败
 * 3.无法作废已经颁布的令牌token，除非改密钥
 */
public class JWTUtils {
    /**
     * 过期时间为一周
     */
    private static final long Expire = 60000 * 60 * 24 * 7;
    /**
     * 密钥
     */
    private static final String secret = "xdclass.net168";
    /**
     * 令牌前缀
     */
    private static final String Token_PreFix = "xdclass";
    /**
     * subject主题
     */
    private static final String SUBJECT = "xdclass";

    /**
     * 根据用户信息生成令牌
     *
     * @param
     * @return
     */
    public static String geneJsonWebToken(String username,String password,String role) {
        String token = Jwts.builder().setSubject(SUBJECT)
                .claim("username", username)
                .claim("password", password)
                .claim("role",role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + Expire))
                .signWith(SignatureAlgorithm.HS256, secret).compact();
        token = Token_PreFix + token;
        return token;
    }
    public static Claims checkJWT(String token){
        try{

            final Claims claims = Jwts.parser().setSigningKey(secret)
                    .parseClaimsJws(token.replace(Token_PreFix, ""))
                    .getBody();
            claims.get("username");
            return claims;
        }catch (Exception e){
            return null;
        }
    }
}
