package com.axonivy.connector.snowflake.demo;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

@ManagedBean
public class AccessToSnowflakeBean {
	private final static String SELECT_STATEMENT_FORMAT = "Select * from %s";
	private List<String> schemas;
	private List<String> tables;
	private String table;
	private List<String> databases;
	private List<String> roles;
	private List<String> warehouses;
	private String sqlStatement;

	@PostConstruct
	public void init() {
		schemas = new ArrayList<>();
		schemas.add("TPCH_SF10");
		schemas.add("TPCH_SF100");
		schemas.add("TPCH_SF1000");
		tables = new ArrayList<>();
		tables.add("CUSTOMER");
		tables.add("ORDERS");
		tables.add("SUPPLIER");
		databases = new ArrayList<>();
		databases.add("SNOWFLAKE_SAMPLE_DATA");
		roles = new ArrayList<>();
		roles.add("ACCOUNTADMIN");
		warehouses = new ArrayList<>();
		warehouses.add("COMPUTE_WH");
	}

	public void generateSqlStatement() {
		sqlStatement = String.format(SELECT_STATEMENT_FORMAT, table);
	}

	public List<String> getSchemas() {
		return schemas;
	}

	public void setSchemas(List<String> schemas) {
		this.schemas = schemas;
	}

	public List<String> getTables() {
		return tables;
	}

	public void setTables(List<String> tables) {
		this.tables = tables;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public List<String> getDatabases() {
		return databases;
	}

	public void setDatabases(List<String> databases) {
		this.databases = databases;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public List<String> getWarehouses() {
		return warehouses;
	}

	public void setWarehouses(List<String> warehouses) {
		this.warehouses = warehouses;
	}

	public String getSqlStatement() {
		return sqlStatement;
	}

	public void setSqlStatement(String sqlStatement) {
		this.sqlStatement = sqlStatement;
	}
}
