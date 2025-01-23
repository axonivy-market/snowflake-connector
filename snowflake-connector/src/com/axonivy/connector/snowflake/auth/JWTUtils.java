package com.axonivy.connector.snowflake.auth;

import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import ch.ivyteam.ivy.environment.Ivy;

public class JWTUtils {
  private final static String RSA = "RSA";
  private final static String RSA_256 = "SHA-256";
  private final static String RSA_256_COLON = "SHA256:";
  private final static String DOT = ".";

  public static String generateJWT() throws Exception {
    KeyFactory keyFactory = KeyFactory.getInstance(RSA);
    var privateKey = readPrivateKey(keyFactory);
    RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(privateKey.getModulus(), privateKey.getPublicExponent());
    RSAPublicKey publicKey = (RSAPublicKey) keyFactory.generatePublic(publicKeySpec);
    Algorithm algorithm = Algorithm.RSA256(publicKey, privateKey);

    var qualifiedUserName =
        Ivy.var().get("snowflakeConnector.locator") + DOT + Ivy.var().get("snowflakeConnector.username");

    MessageDigest digest = MessageDigest.getInstance(RSA_256);
    var publicKeyFp = RSA_256_COLON + Base64.getEncoder().encodeToString(digest.digest(publicKey.getEncoded()));

    var issuedTs = new Date();
    var expiresTs = new Date(issuedTs.getTime() + TimeUnit.HOURS.toMillis(1));
    return JWT.create().withIssuer(qualifiedUserName + DOT + publicKeyFp).withSubject(qualifiedUserName)
        .withIssuedAt(issuedTs).withExpiresAt(expiresTs).sign(algorithm);
  }

  private static RSAPrivateCrtKey readPrivateKey(KeyFactory keyFactory) throws Exception {
    byte[] encoded = Base64.getDecoder().decode(Ivy.var().get("snowflakeConnector.privateKey"));
    PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
    return (RSAPrivateCrtKey) keyFactory.generatePrivate(keySpec);
  }
}
