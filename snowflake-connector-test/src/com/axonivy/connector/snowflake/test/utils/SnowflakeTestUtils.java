package com.axonivy.connector.snowflake.test.utils;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

import com.axonivy.connector.snowflake.test.constant.CommonConstant;

import ch.ivyteam.ivy.application.IApplication;
import ch.ivyteam.ivy.bpm.engine.client.BpmClient;
import ch.ivyteam.ivy.bpm.engine.client.element.BpmProcess;
import ch.ivyteam.ivy.bpm.engine.client.sub.SubRequestBuilder;
import ch.ivyteam.ivy.environment.AppFixture;
import ch.ivyteam.ivy.environment.Ivy;
import ch.ivyteam.ivy.rest.client.RestClient;
import ch.ivyteam.ivy.rest.client.RestClients;
import ch.ivyteam.ivy.rest.client.security.CsrfHeaderFeature;

public class SnowflakeTestUtils {
  private static final String FEATURE_SUFFIX = ".Features";
  private static final String REST_CLIENT_PREFIX = "RestClients.";
  private static final String LOCATOR_URL_KEY = "locatorUrl";
  private static final String LOCATOR_URL_MOCK = "{ivy.app.baseurl}/api/snowflakeDataMock";
  private static final String LOCATOR_KEY = "locator";
  private static final String LOCATOR_MOCK = "BD77504";
  private static final String JWT_TOKEN = "jwtToken";
  private static final String USERNAME_KEY = "username";
  private static final String USERNAME_MOCK = "HUNGPH";
  private static final String PRIVATE_KEY_KEY = "privateKey";
  private static final String PRIVATE_KEY_MOCK =
      "${decrypt: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}";
  private static final String SNOWFLAKE_CONNECTOR_PREFIX = "snowflakeConnector.";
  private static final String DATA_JSON_FEATURE = "com.axonivy.connector.snowflake.auth.DataJsonFeature";
  private static final String SNOWFLAKE_AUTH_FEATURE = "com.axonivy.connector.snowflake.auth.SnowflakeAuthFeature";
  private static final String LOCAL_CREDENTIALS_FILE_PATH = "credentials.properties";

  public static final String JWT_TOKEN_MOCK =
      "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJCRDc3NTA0LkhVTkdQSC5TSEEyNTY6UWlhdjRSYmo2bHhjdmdJdFpFREtZb0ZRN2hMODZVYS95bURNTVdMRnpLdz0iLCJzdWIiOiJCRDc3NTA0LkhVTkdQSCIsImlhdCI6MTczNzA5NTcyNywiZXhwIjoxNzM3MDk5MzI3fQ.xs4A6xmciluee2rz-SpqSp5QHqMQabXdH5WWxj93QjhFebVgZivodCGZP65mrwuwLO57yKhCO-C4jUuqFt20EMHJuTWrYAVCa_WWyhOtvpu6607m7D9gPXWZPE3XafDpAEgxl0yV_3C3Tm1itadTdH5GZM4xGSNQYP82NhS1wBEEpWh2egbpdltR29Qn4DlW4JXO31Abxlf1Wu2AReNlKHykj4BrtxGG3M-0jDYZZswbWqsPK979-vqX2ttRnKCJCNuLYRVrryhlsEQldx29VdBABYJfUzM7JdbsbaXt3oKXhXOe04vjgLjfej4GOzRY1MhT0kZz4dUaZd9lMUP6tA";
  public static final String JWT_TOKEN_AUTH_PROPERTY = "AUTH.JWTToken";

  public static void setUpConfigForMockServer(AppFixture fixture) {
    fixture.config(REST_CLIENT_PREFIX + CommonConstant.SNOWFLAKE_REST_CLIENT_NAME + FEATURE_SUFFIX,
        List.of(DATA_JSON_FEATURE, SNOWFLAKE_AUTH_FEATURE));

    fixture.var(SNOWFLAKE_CONNECTOR_PREFIX + LOCATOR_URL_KEY, LOCATOR_URL_MOCK);
    fixture.var(SNOWFLAKE_CONNECTOR_PREFIX + JWT_TOKEN, JWT_TOKEN_MOCK);
    fixture.var(SNOWFLAKE_CONNECTOR_PREFIX + LOCATOR_KEY, LOCATOR_MOCK);
    fixture.var(SNOWFLAKE_CONNECTOR_PREFIX + USERNAME_KEY, USERNAME_MOCK);
    fixture.var(SNOWFLAKE_CONNECTOR_PREFIX + PRIVATE_KEY_KEY, PRIVATE_KEY_MOCK);
  }

  public static void setUpConfigForContext(String contextName, AppFixture fixture, IApplication app) {
    switch (contextName) {
      case CommonConstant.REAL_CALL_CONTEXT_DISPLAY_NAME:
        setUpConfigForApiTest(fixture);
        removeClientForMockPostMethod(app);
        break;
      case CommonConstant.MOCK_SERVER_CONTEXT_DISPLAY_NAME:
        setUpConfigForMockServer(fixture);
        addClientForMockPostMethod(app);
        break;
      default:
        break;
    }
  }

  private static void addClientForMockPostMethod(IApplication app) {
    RestClients clients = RestClients.of(app);
    RestClient snowflakeClient = RestClients.of(app).find(CommonConstant.SNOWFLAKE_REST_CLIENT_NAME);
    var testClient = snowflakeClient.toBuilder().feature(CsrfHeaderFeature.class.getName())
        .property(SnowflakeTestUtils.JWT_TOKEN_AUTH_PROPERTY, SnowflakeTestUtils.JWT_TOKEN_MOCK).toRestClient();
    clients.set(testClient);
  }

  private static void removeClientForMockPostMethod(IApplication app) {
    RestClients clients = RestClients.of(app);
    clients.remove(CommonConstant.SNOWFLAKE_REST_CLIENT_NAME);
  }

  public static void setUpConfigForApiTest(AppFixture fixture) {
    String locatorUrl = System.getProperty(LOCATOR_URL_KEY);
    String locator = System.getProperty(LOCATOR_KEY);
    String username = System.getProperty(USERNAME_KEY);
    String privateKey = System.getProperty(PRIVATE_KEY_KEY);

    // Local setup for testing
    if (StringUtils.isAnyBlank(new String[] {locatorUrl, locator, username, privateKey})) {
      try (var in = SnowflakeTestUtils.class.getResourceAsStream(LOCAL_CREDENTIALS_FILE_PATH)) {
        if (in != null) {
          Properties props = new Properties();
          props.load(in);
          locatorUrl = props.getProperty(LOCATOR_URL_KEY);
          locator = props.getProperty(LOCATOR_KEY);
          username = props.getProperty(USERNAME_KEY);
          privateKey = props.getProperty(PRIVATE_KEY_KEY);
        }
      } catch (IOException e) {
        Ivy.log().warn("Can't get credential from local file with path: {0}", LOCAL_CREDENTIALS_FILE_PATH);
      }
    }

    fixture.var(SNOWFLAKE_CONNECTOR_PREFIX + LOCATOR_URL_KEY, locatorUrl);
    fixture.var(SNOWFLAKE_CONNECTOR_PREFIX + JWT_TOKEN, JWT_TOKEN_MOCK);
    fixture.var(SNOWFLAKE_CONNECTOR_PREFIX + LOCATOR_KEY, locator);
    fixture.var(SNOWFLAKE_CONNECTOR_PREFIX + USERNAME_KEY, username);
    fixture.var(SNOWFLAKE_CONNECTOR_PREFIX + PRIVATE_KEY_KEY, privateKey);
  }

  public static SubRequestBuilder getSubProcessWithNameAndPath(BpmClient client, String subProcessPath,
      String subProcessName) {
    return client.start().subProcess(BpmProcess.path(subProcessPath).elementName(subProcessName));
  }
}
