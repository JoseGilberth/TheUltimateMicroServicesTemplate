package micro.admin.modules.dashboard.services;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.database.DataBaseDao;
import dto.main.Respuesta;
import micro.admin.modules.dashboard.dtos.MetricsDTO;
import micro.admin.modules.dashboard.interfaces.IMetricsDataBaseServices;

@Service
public class MetricsDataBaseService implements IMetricsDataBaseServices {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	DataBaseDao dataBaseDao;

	@Override
	public Respuesta<MetricsDTO> metrics() throws SQLException {
		
		MetricsDTO metricsDTO = new MetricsDTO();
		metricsDTO.setMaxConnections(dataBaseDao.maxConnections());
		metricsDTO.setMaxUsedConnections(dataBaseDao.maxUsedConnections());
		metricsDTO.setPerformanceSchemas(dataBaseDao.performanceSchemas());
		metricsDTO.setProcessList(dataBaseDao.processList());
		metricsDTO.setSlowQueries(dataBaseDao.slowQueries());
		metricsDTO.setStatementsWithErrorsOrWarnings(dataBaseDao.statementsWithErrorsOrWarnings());
		metricsDTO.setStatementsWithRuntimesIn95thPercentile(dataBaseDao.statementsWithRuntimesIn95thPercentile());
		metricsDTO.setThreadsConnected(dataBaseDao.threadsConnected());
		metricsDTO.setThreadsRunning(dataBaseDao.threadsRunning());
		metricsDTO.setTop10StatementsByLongestAverageRunTime(dataBaseDao.top10StatementsByLongestAverageRunTime());
		metricsDTO.setWaitTimeout(dataBaseDao.waitTimeout());
		return new Respuesta<MetricsDTO>(200, 200, metricsDTO, "", true);
	}

	/*
	 * 
	 * try {
	 * 
	 * metricsDTO.setLoginTimeout(dataSource.getLoginTimeout());
	 * metricsDTO.setCatalogSeparator(dbMetadata.getCatalogSeparator());
	 * metricsDTO.setCatalogTerm(dbMetadata.getCatalogTerm());
	 * metricsDTO.setDatabaseMajorVersion(dbMetadata.getDatabaseMajorVersion());
	 * metricsDTO.setDatabaseMinorVersion(dbMetadata.getDatabaseMinorVersion());
	 * metricsDTO.setDatabaseProductName(dbMetadata.getDatabaseProductName());
	 * metricsDTO.setDatabaseProductVersion(dbMetadata.getDatabaseProductVersion());
	 * metricsDTO.setDefaultTransactionIsolation(dbMetadata.
	 * getDefaultTransactionIsolation());
	 * metricsDTO.setDriverMajorVersion(dbMetadata.getDriverMajorVersion());
	 * metricsDTO.setDriverMinorVersion(dbMetadata.getDriverMinorVersion());
	 * metricsDTO.setDriverName(dbMetadata.getDriverName());
	 * metricsDTO.setDriverVersion(dbMetadata.getDriverVersion());
	 * metricsDTO.setExtraNameCharacters(dbMetadata.getExtraNameCharacters());
	 * metricsDTO.setIdentifierQuoteString(dbMetadata.getIdentifierQuoteString());
	 * metricsDTO.setJDBCMajorVersion(dbMetadata.getJDBCMajorVersion());
	 * metricsDTO.setJDBCMinorVersion(dbMetadata.getJDBCMinorVersion());
	 * metricsDTO.setMaxBinaryLiteralLength(dbMetadata.getMaxBinaryLiteralLength());
	 * metricsDTO.setMaxCatalogNameLength(dbMetadata.getMaxCatalogNameLength());
	 * metricsDTO.setMaxCharLiteralLength(dbMetadata.getMaxCharLiteralLength());
	 * metricsDTO.setMaxColumnNameLength(dbMetadata.getMaxColumnNameLength());
	 * metricsDTO.setMaxColumnsInGroupBy(dbMetadata.getMaxColumnsInGroupBy());
	 * metricsDTO.setMaxColumnsInIndex(dbMetadata.getMaxColumnsInIndex());
	 * metricsDTO.setMaxColumnsInOrderBy(dbMetadata.getMaxColumnsInOrderBy());
	 * metricsDTO.setMaxColumnsInSelect(dbMetadata.getMaxColumnsInSelect());
	 * metricsDTO.setMaxColumnsInTable(dbMetadata.getMaxColumnsInTable());
	 * metricsDTO.setMaxConnections(dbMetadata.getMaxConnections());
	 * metricsDTO.setMaxCursorNameLength(dbMetadata.getMaxCursorNameLength());
	 * metricsDTO.setMaxIndexLength(dbMetadata.getMaxIndexLength());
	 * metricsDTO.setMaxLogicalLobSize(dbMetadata.getMaxLogicalLobSize());
	 * metricsDTO.setMaxProcedureNameLength(dbMetadata.getMaxProcedureNameLength());
	 * metricsDTO.setMaxRowSize(dbMetadata.getMaxRowSize());
	 * metricsDTO.setMaxSchemaNameLength(dbMetadata.getMaxSchemaNameLength());
	 * metricsDTO.setMaxStatementLength(dbMetadata.getMaxStatementLength());
	 * metricsDTO.setMaxStatements(dbMetadata.getMaxStatements());
	 * metricsDTO.setMaxTableNameLength(dbMetadata.getMaxTableNameLength());
	 * metricsDTO.setMaxTablesInSelect(dbMetadata.getMaxTablesInSelect());
	 * metricsDTO.setMaxUserNameLength(dbMetadata.getMaxUserNameLength());
	 * metricsDTO.setNumericFunctions(dbMetadata.getNumericFunctions());
	 * metricsDTO.setProcedureTerm(dbMetadata.getProcedureTerm());
	 * metricsDTO.setResultSetHoldability(dbMetadata.getResultSetHoldability());
	 * metricsDTO.setSchemaTerm(dbMetadata.getSchemaTerm());
	 * metricsDTO.setSearchStringEscape(dbMetadata.getSearchStringEscape());
	 * metricsDTO.setSQLKeywords(dbMetadata.getSQLKeywords());
	 * metricsDTO.setSQLStateType(dbMetadata.getSQLStateType());
	 * metricsDTO.setStringFunctions(dbMetadata.getStringFunctions());
	 * metricsDTO.setSystemFunctions(dbMetadata.getSystemFunctions());
	 * metricsDTO.setTimeDateFunctions(dbMetadata.getTimeDateFunctions());
	 * metricsDTO.setURL(dbMetadata.getURL());
	 * metricsDTO.setUserName(dbMetadata.getUserName());
	 * 
	 * ResultSet rs = dbMetadata.getCatalogs(); List<String> catalogos = new
	 * ArrayList<String>(); if (rs != null) { while (rs.next()) {
	 * catalogos.add(rs.getString(1).trim()); } } metricsDTO.setCatalogs(catalogos);
	 * 
	 * rs = dbMetadata.getClientInfoProperties(); List<String> clientInfoProperties
	 * = new ArrayList<String>(); if (rs != null) { while (rs.next()) {
	 * clientInfoProperties.add(rs.getString(1).trim()); } }
	 * metricsDTO.setClientInfoProperties(clientInfoProperties);
	 * metricsDTO.setRowIdLifetime(dbMetadata.getRowIdLifetime().name());
	 * 
	 * List<String> schemas = new ArrayList<String>(); rs = dbMetadata.getSchemas();
	 * if (rs != null) { while (rs.next()) { schemas.add(rs.getString(1).trim()); }
	 * } metricsDTO.setSchemas(schemas);
	 * 
	 * List<String> tableTypes = new ArrayList<String>(); rs =
	 * dbMetadata.getTableTypes(); if (rs != null) { while (rs.next()) {
	 * tableTypes.add(rs.getString(1).trim()); } }
	 * metricsDTO.setTableTypes(tableTypes);
	 * 
	 * List<String> typeInfo = new ArrayList<String>(); rs =
	 * dbMetadata.getTypeInfo(); if (rs != null) { while (rs.next()) {
	 * typeInfo.add(rs.getString(1).trim()); } } metricsDTO.setTypeInfo(typeInfo);
	 * 
	 * metricsDTO.setSupportsGetGeneratedKeys(dbMetadata.supportsGetGeneratedKeys())
	 * ; metricsDTO.setLoginTimeout(dataSource.getLoginTimeout());
	 * metricsDTO.setClientInfo(dataSource.getConnection().getClientInfo());
	 * metricsDTO.setNetworkTimeout(dataSource.getConnection().getNetworkTimeout());
	 * metricsDTO.setAutoCommit(dataSource.getConnection().getAutoCommit());
	 * metricsDTO.setQueryTimeout(jdbcTemplate.getQueryTimeout());
	 * 
	 * } catch (Exception ex) { ex.printStackTrace(); }
	 * 
	 */

}
