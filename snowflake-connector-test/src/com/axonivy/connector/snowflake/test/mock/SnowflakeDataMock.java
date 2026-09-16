package com.axonivy.connector.snowflake.test.mock;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import com.axonivy.connector.snowflake.test.constant.CommonConstant;
import com.snowflakecomputing.org.account.client.V2StatementsBody;

import io.swagger.v3.oas.annotations.Hidden;

@Path("snowflakeDataMock")
@PermitAll
@Hidden
public class SnowflakeDataMock {

  @POST
  @Path("api/v2/statements")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response executeStatement(@QueryParam(CommonConstant.REQUEST_ID_PARAM) UUID requestId,
      @QueryParam(CommonConstant.ASYNC_PARAM) boolean async,
      @QueryParam(CommonConstant.NULLABLE_PARAM) boolean nullable, V2StatementsBody body) {
    String resultPath = StringUtils.EMPTY;
    if (StringUtils.isBlank(body.getStatement())) {
      resultPath = CommonConstant.EMPTY_SQL_STATEMENT_JSON_FILE_PATH;
    } else {
      resultPath =
          async ? CommonConstant.IN_PROGRESS_JSON_FILE_PATH : CommonConstant.COUNT_ALL_CUSTOMERS_JSON_FILE_PATH;
    }
    return Response.status(200).entity(load(resultPath)).build();
  }

  @GET
  @Path("api/v2/statements/{statementHandle}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response checkSQLStatementExecution(@PathParam(CommonConstant.STATEMENT_HANDLE_PARAM) UUID statementHandle) {
    String resultPath = StringUtils.equals(CommonConstant.NOT_FOUND_STATEMENT_HANDLE_UUID, statementHandle.toString())
        ? CommonConstant.NOT_FOUND_JSON_FILE_PATH
        : CommonConstant.COUNT_ALL_CUSTOMERS_JSON_FILE_PATH;
    return Response.status(200).entity(load(resultPath)).build();
  }

  @POST
  @Path("/api/v2/statements/{statementHandle}/cancel")
  @Produces(MediaType.APPLICATION_JSON)
  public Response cancelSQLStatementExecution(@PathParam(CommonConstant.STATEMENT_HANDLE_PARAM) UUID statementHandle,
      @QueryParam(CommonConstant.PARTITION_PARAM) int partition,
      @QueryParam(CommonConstant.REQUEST_ID_PARAM) UUID requestId) {
    return Response.status(200).entity(load(CommonConstant.NOT_FOUND_JSON_FILE_PATH)).build();
  }

  private static String load(String path) {
    try (InputStream is = SnowflakeDataMock.class.getResourceAsStream(path)) {
      return IOUtils.toString(is, StandardCharsets.UTF_8);
    } catch (IOException ex) {
      throw new RuntimeException("Failed to read resource: " + path);
    }
  }
}
