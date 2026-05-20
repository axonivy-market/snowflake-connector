package com.snowflakecomputing.org.account;

/**
 * Minimal stub required by openapi-generator generated model classes.
 * The generated models use ApiClient.valueToString() in toUrlQueryString(),
 * which is not needed at runtime (real implementation is in the prebuilt JAR).
 */
public class ApiClient {
  public static String valueToString(Object value) {
    if (value == null) {
      return "";
    }
    return value.toString();
  }
}
