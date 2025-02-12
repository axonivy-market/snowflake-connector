package com.axonivy.connector.snowflake.test.constant;

public class CommonConstant {
  public static final String RESULT_SET_KEY = "resultSet";
  public static final String RESULTS_KEY = "results";
  public static final String CANCEL_STATUS_KEY = "cancelStatus";
  public static final String REAL_CALL_CONTEXT_DISPLAY_NAME = "Real Server Test";
  public static final String MOCK_SERVER_CONTEXT_DISPLAY_NAME = "Mock Server Test";
  public static final String GET_SQL_STATEMENT_EXECUTION_PROCESS_PATH = "SQLStatementExecution";
  public static final String GET_SQL_STATEMENT_EXECUTION_PROCESS_NAME = "executeSQLStatement(SQLStatementParams)";
  public static final String GET_SQL_STATEMENT_EXECUTION_STATUS_CHECKING_PROCESS_PATH =
      "SQLStatementExecutionStatusChecking";
  public static final String GET_SQL_STATEMENT_EXECUTION_STATUS_CHECKING_PROCESS_NAME =
      "checkSQLStatementStatus(String)";
  public static final String GET_SQL_STATEMENT_EXECUTION_CANCELLING_PROCESS_PATH = "SQLStatementExecutionCancelling";
  public static final String GET_SQL_STATEMENT_EXECUTION_CANCELLING_PROCESS_NAME =
      "cancelSQLStatementExecution(String,Number,String)";
  public static final String SNOWFLAKE_REST_CLIENT_NAME = "Snowflake SQL API";
  public static final String NOT_FOUND_STATEMENT_HANDLE_UUID = "4d3c2b1d-0000-0000-0000-000000000000";
  public static final String NOT_FOUND_RETURN_CODE = "000709";
  public static final String IN_PROGRESS_RETURN_CODE = "333334";
  public static final String SUCCESS_DATA_QUERY_RETURN_CODE = "090001";
  public static final String EMPTY_SQL_STATEMENT_RETURN_CODE = "000900";
  public static final String REQUEST_ID_PARAM = "requestId";
  public static final String STATEMENT_HANDLE_PARAM = "statementHandle";
  public static final String PARTITION_PARAM = "partition";
  public static final String ASYNC_PARAM = "async";
  public static final String NULLABLE_PARAM = "nullable";
  public static final String NOT_FOUND_JSON_FILE_PATH =  "json/not-found.json";
  public static final String COUNT_ALL_CUSTOMERS_JSON_FILE_PATH = "json/count-all-customers-result.json";
  public static final String IN_PROGRESS_JSON_FILE_PATH = "json/in-progress-result.json";
  public static final String EMPTY_SQL_STATEMENT_JSON_FILE_PATH = "json/empty-sql-statement.json";
}
