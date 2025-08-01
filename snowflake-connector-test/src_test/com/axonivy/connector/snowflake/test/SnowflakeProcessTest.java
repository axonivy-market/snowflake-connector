package com.axonivy.connector.snowflake.test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.ObjectUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;

import com.axonivy.connector.snowflake.connector.sqlstatementexecution.SQLStatementExecutionParamData;
import com.axonivy.connector.snowflake.test.constant.CommonConstant;
import com.axonivy.connector.snowflake.test.utils.SnowflakeTestUtils;
import com.snowflakecomputing.org.account.client.CancelStatus;
import com.snowflakecomputing.org.account.client.ResultSet;

import ch.ivyteam.ivy.application.IApplication;
import ch.ivyteam.ivy.bpm.engine.client.BpmClient;
import ch.ivyteam.ivy.bpm.engine.client.ExecutionResult;
import ch.ivyteam.ivy.bpm.exec.client.IvyProcessTest;
import ch.ivyteam.ivy.environment.AppFixture;

@IvyProcessTest(enableWebServer = true)
@ExtendWith(com.axonivy.connector.snowflake.test.context.MultiEnvironmentContextProvider.class)
public class SnowflakeProcessTest {
  private boolean isMockTest;

  @BeforeEach
  void beforeEach(ExtensionContext context, AppFixture fixture, IApplication app) {
    isMockTest = context.getDisplayName() == CommonConstant.MOCK_SERVER_CONTEXT_DISPLAY_NAME;
    SnowflakeTestUtils.setUpConfigForContext(context.getDisplayName(), fixture, app);
  }

  @TestTemplate
  public void testExecuteSQLStatement_ReturnsEmptyStatementData(BpmClient client)
      throws NoSuchFieldException, InterruptedException {
    ExecutionResult sqlStatementExecutionResult =
        SnowflakeTestUtils.getSubProcessWithNameAndPath(client, CommonConstant.GET_SQL_STATEMENT_EXECUTION_PROCESS_PATH,
            CommonConstant.GET_SQL_STATEMENT_EXECUTION_PROCESS_NAME).execute(new SQLStatementExecutionParamData());
    var sqlStatementExecutionResultObj = sqlStatementExecutionResult.data().last().get(CommonConstant.RESULT_SET_KEY);
    assertTrue(sqlStatementExecutionResultObj instanceof ResultSet);
    ResultSet sqlStatementExecutionResultSet = (ResultSet) sqlStatementExecutionResultObj;
    assertTrue(getPosibleReturnCodes(CommonConstant.EMPTY_SQL_STATEMENT_RETURN_CODE)
        .contains(sqlStatementExecutionResultSet.getCode()));
  }

  @TestTemplate
  public void testExecuteSQLStatement_ReturnsQueriedData(BpmClient client)
      throws NoSuchFieldException, InterruptedException {
    ExecutionResult sqlStatementExecutionResult = SnowflakeTestUtils
        .getSubProcessWithNameAndPath(client, CommonConstant.GET_SQL_STATEMENT_EXECUTION_PROCESS_PATH,
            CommonConstant.GET_SQL_STATEMENT_EXECUTION_PROCESS_NAME)
        .execute(prepareSQLStatementExecutionParamData(false));
    var sqlStatementExecutionResultObj = sqlStatementExecutionResult.data().last().get(CommonConstant.RESULT_SET_KEY);
    assertTrue(sqlStatementExecutionResultObj instanceof ResultSet);
    ResultSet sqlStatementExecutionResultSet = (ResultSet) sqlStatementExecutionResultObj;
    assertTrue(getPosibleReturnCodes(CommonConstant.SUCCESS_DATA_QUERY_RETURN_CODE)
        .contains(sqlStatementExecutionResultSet.getCode()));
  }

  @TestTemplate
  public void testCombineExecuteSQLStatementAndCheckStatus_ReturnsQueriedData(BpmClient client)
      throws NoSuchFieldException, InterruptedException {
    ExecutionResult sqlStatementExecutionResult = SnowflakeTestUtils
        .getSubProcessWithNameAndPath(client, CommonConstant.GET_SQL_STATEMENT_EXECUTION_PROCESS_PATH,
            CommonConstant.GET_SQL_STATEMENT_EXECUTION_PROCESS_NAME)
        .execute(prepareSQLStatementExecutionParamData(true));
    var sqlStatementExecutionResultObj = sqlStatementExecutionResult.data().last().get(CommonConstant.RESULT_SET_KEY);
    assertTrue(sqlStatementExecutionResultObj instanceof ResultSet);
    ResultSet sqlStatementExecutionResultSet = (ResultSet) sqlStatementExecutionResultObj;
    assertTrue(getPosibleReturnCodes(CommonConstant.IN_PROGRESS_RETURN_CODE)
        .contains(sqlStatementExecutionResultSet.getCode()));
    if (!isMockTest && ObjectUtils.isEmpty(sqlStatementExecutionResultSet.getStatementHandle())) {
      sqlStatementExecutionResultSet.setStatementHandle(UUID.randomUUID());
    }
    ExecutionResult sqlStatementExecutionStatusResult = SnowflakeTestUtils
        .getSubProcessWithNameAndPath(client, CommonConstant.GET_SQL_STATEMENT_EXECUTION_STATUS_CHECKING_PROCESS_PATH,
            CommonConstant.GET_SQL_STATEMENT_EXECUTION_STATUS_CHECKING_PROCESS_NAME)
        .execute(sqlStatementExecutionResultSet.getStatementHandle().toString());
    var sqlStatementExecutionStatusObj =
        sqlStatementExecutionStatusResult.data().last().get(CommonConstant.RESULT_SET_KEY);
    assertTrue(sqlStatementExecutionStatusObj instanceof ResultSet);
    ResultSet sqlStatementExecutionStatus = (ResultSet) sqlStatementExecutionStatusObj;
    assertTrue(getPosibleReturnCodes(CommonConstant.SUCCESS_DATA_QUERY_RETURN_CODE)
        .contains(sqlStatementExecutionStatus.getCode()));
  }

  @TestTemplate
  public void testCheckSQLStatementExecutionStatus_ReturnsNotFoundData(BpmClient client)
      throws NoSuchFieldException, InterruptedException {
    ExecutionResult sqlStatementExecutionStatusResult = SnowflakeTestUtils
        .getSubProcessWithNameAndPath(client, CommonConstant.GET_SQL_STATEMENT_EXECUTION_STATUS_CHECKING_PROCESS_PATH,
            CommonConstant.GET_SQL_STATEMENT_EXECUTION_STATUS_CHECKING_PROCESS_NAME)
        .execute(CommonConstant.NOT_FOUND_STATEMENT_HANDLE_UUID);
    var sqlStatementExecutionStatusObj =
        sqlStatementExecutionStatusResult.data().last().get(CommonConstant.RESULT_SET_KEY);
    assertTrue(sqlStatementExecutionStatusObj instanceof ResultSet);
    ResultSet sqlStatementExecutionStatus = (ResultSet) sqlStatementExecutionStatusObj;
    assertTrue(
        getPosibleReturnCodes(CommonConstant.NOT_FOUND_RETURN_CODE).contains(sqlStatementExecutionStatus.getCode()));
  }

  @TestTemplate
  public void testCancelSQLStatementExecution_ReturnsQueriedData(BpmClient client)
      throws NoSuchFieldException, InterruptedException {
    Thread.sleep(3000);
    ExecutionResult result = SnowflakeTestUtils
        .getSubProcessWithNameAndPath(client, CommonConstant.GET_SQL_STATEMENT_EXECUTION_CANCELLING_PROCESS_PATH,
            CommonConstant.GET_SQL_STATEMENT_EXECUTION_CANCELLING_PROCESS_NAME)
        .execute(CommonConstant.NOT_FOUND_STATEMENT_HANDLE_UUID, 1, UUID.randomUUID().toString());
    var object = result.data().last().get(CommonConstant.CANCEL_STATUS_KEY);
    assertTrue(object instanceof CancelStatus);
    CancelStatus cancelStatus = (CancelStatus) object;
    assertTrue(getPosibleReturnCodes(CommonConstant.NOT_FOUND_RETURN_CODE).contains(cancelStatus.getCode()));
  }

  private SQLStatementExecutionParamData prepareSQLStatementExecutionParamData(boolean isAsync) {
    SQLStatementExecutionParamData param = new SQLStatementExecutionParamData();
    param.setRequestId(UUID.randomUUID().toString());
    param.setStatement("Select count(*) from CUSTOMER");
    param.setTimeout(60000L);
    param.setDatabase("SNOWFLAKE_SAMPLE_DATA");
    param.setSchema("TPCH_SF10");
    param.setWarehouse("COMPUTE_WH");
    param.setRole("ACCOUNTADMIN");
    param.setAsync(isAsync);
    param.setBindings(new Object());
    return param;
  }
  
  private List<String> getPosibleReturnCodes(String expectedCode) {
    return isMockTest ? List.of(expectedCode) : List.of(expectedCode, CommonConstant.USER_ACCOUNT_IS_LOCKED);
  }
}
