package com.axonivy.connector.snowflake.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;

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

  @BeforeEach
  void beforeEach(ExtensionContext context, AppFixture fixture, IApplication app) {
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
    assertEquals(sqlStatementExecutionResultSet.getCode(), CommonConstant.EMPTY_SQL_STATEMENT_RETURN_CODE);
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
    assertEquals(sqlStatementExecutionResultSet.getCode(), CommonConstant.SUCCESS_DATA_QUERY_RETURN_CODE);
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
    assertEquals(sqlStatementExecutionResultSet.getCode(), CommonConstant.IN_PROGRESS_RETURN_CODE);

    ExecutionResult sqlStatementExecutionStatusResult = SnowflakeTestUtils
        .getSubProcessWithNameAndPath(client, CommonConstant.GET_SQL_STATEMENT_EXECUTION_STATUS_CHECKING_PROCESS_PATH,
            CommonConstant.GET_SQL_STATEMENT_EXECUTION_STATUS_CHECKING_PROCESS_NAME)
        .execute(sqlStatementExecutionResultSet.getStatementHandle().toString());
    var sqlStatementExecutionStatusObj =
        sqlStatementExecutionStatusResult.data().last().get(CommonConstant.RESULT_SET_KEY);
    assertTrue(sqlStatementExecutionStatusObj instanceof ResultSet);
    ResultSet sqlStatementExecutionStatus = (ResultSet) sqlStatementExecutionStatusObj;
    assertEquals(sqlStatementExecutionStatus.getCode(), CommonConstant.SUCCESS_DATA_QUERY_RETURN_CODE);
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
    assertEquals(sqlStatementExecutionStatus.getCode(), CommonConstant.NOT_FOUND_RETURN_CODE);
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
    assertEquals(cancelStatus.getCode(), CommonConstant.NOT_FOUND_RETURN_CODE);
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
}
