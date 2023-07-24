package com.github.karkarych.pixelbattledsr.security;

import com.github.karkarych.pixelbattledsr.service.model.auth.Token;
import com.nimbusds.jose.*;
import com.nimbusds.jwt.EncryptedJWT;
import com.nimbusds.jwt.JWTClaimsSet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.function.Function;

@Slf4j
@RequiredArgsConstructor
public class TokenCookieJweStringSerializer implements Function<Token, String> {

  private final JWEEncrypter jweEncrypter;
  private final JWEAlgorithm jweAlgorithm = JWEAlgorithm.DIR;
  private final EncryptionMethod encryptionMethod = EncryptionMethod.A256GCM;

  @Override
  public String apply(Token token) {
    JWEHeader jwsHeader = new JWEHeader.Builder(this.jweAlgorithm, this.encryptionMethod)
      .keyID(token.id().toString())
      .build();

    JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
      .jwtID(token.id().toString())
      .subject(token.subject())
      .issueTime(Date.from(token.createdAt()))
      .expirationTime(Date.from(token.expiresAt()))
      .claim("authorities", token.authorities())
      .build();

    EncryptedJWT encryptedJWT = new EncryptedJWT(jwsHeader, claimsSet);
    try {
      encryptedJWT.encrypt(this.jweEncrypter);
      return encryptedJWT.serialize();
    } catch (JOSEException exception) {
      log.error(exception.getMessage(), exception);
    }

    return null;
  }
}
