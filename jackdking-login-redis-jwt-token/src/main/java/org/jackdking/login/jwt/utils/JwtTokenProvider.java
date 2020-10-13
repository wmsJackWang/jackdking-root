package org.jackdking.login.jwt.utils;

import java.util.Base64;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenProvider {

  /**
   * THIS IS NOT A SECURE PRACTICE! For simplicity, we are storing a static key here. Ideally, in a
   * microservices environment, this key would be kept on a config-server.
   */
  @Value("${security.jwt.token.secret-key:secret-key}")
  private String secretKey;

  @Value("${security.jwt.token.expire-length:3600000}")
  private long validityInMilliseconds = 3600000; // 1h


  @PostConstruct
  protected void init() {
    secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
  }

  public String createToken(String username) {

    Claims claims = Jwts.claims().setSubject(username);

    Date now = new Date();
    Date validity = new Date(now.getTime() + validityInMilliseconds);

    return Jwts.builder()//
        .setClaims(claims)//相当于类中   存放所有数据的map结构属性，下面的set方法也会放入到claims中
        .setIssuedAt(now)//jwt token创建时间
        .setExpiration(validity)//jwt token过期时间
        .signWith(SignatureAlgorithm.HS256, secretKey)//签名key
        .compact();//最终生成 jwt token
  }

//  //jwt filter用来解析jwt token，得到认证数据
//  public Authentication getAuthentication(String token) {
//    UserDetails userDetails = myUserDetails.loadUserByUsername(getUsername(token));
//    return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
//  }

  //jwt token解析，得到username数据
  public String getUsername(String token) {
    return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
  }

  //jwt filter 从request对象中获取得到jwt token的值
  public String resolveToken(HttpServletRequest req) {
    String bearerToken = req.getHeader("Authorization");
    if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7);
    }
    return null;
  }
  
  public String resolveTokenFromCookie(){
    String bearerToken = CookieUtil.getCookie("Authorization");
    if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7);
    }
    return null;
  }
	  
  

  //校验jwt token是否过期  是否为异常数据
  public boolean validateToken(String token) throws Exception {
    try {
    	//throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, 
    	//SignatureException, IllegalArgumentException
    	// token签名解析错误  ， token过期  都会抛出异常
      Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
      return true;
    } catch (JwtException | IllegalArgumentException e) {
      throw new JwtTokenException("Expired or invalid JWT token");
    }
  }
	
	public void saveJwtToken(String jwtToken) {
		// TODO Auto-generated method stub
		
		CookieUtil.addCookie("Authorization", "Bearer "+jwtToken);
		
	}

	public void removeJwtToken() {

		CookieUtil.removeCookie("Authorization");
		
	}

}
