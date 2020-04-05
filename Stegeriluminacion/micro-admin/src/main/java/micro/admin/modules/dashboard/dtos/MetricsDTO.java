package micro.admin.modules.dashboard.dtos;

import java.util.List;
import java.util.Properties;

import lombok.Data;

@Data
public class MetricsDTO {

	private String catalogSeparator;
	private String catalogTerm;
	private int databaseMajorVersion;
	private int databaseMinorVersion;
	private String databaseProductName;
	private String databaseProductVersion;
	private int defaultTransactionIsolation;
	private int driverMajorVersion;
	private int driverMinorVersion;
	private String driverName;
	private String driverVersion;
	private String extraNameCharacters;
	private String identifierQuoteString;
	private int jDBCMajorVersion;
	private int jDBCMinorVersion;
	private int maxBinaryLiteralLength;
	private int maxCatalogNameLength;
	private int maxCharLiteralLength;
	private int maxColumnNameLength;
	private int maxColumnsInGroupBy;
	private int maxColumnsInIndex;
	private int maxColumnsInOrderBy;
	private int maxColumnsInSelect;
	private int maxColumnsInTable;
	private int maxConnections;
	private int maxCursorNameLength;
	private int maxIndexLength;
	private long maxLogicalLobSize;
	private int maxProcedureNameLength;
	private int maxRowSize;
	private int maxSchemaNameLength;
	private int maxStatementLength;
	private int maxStatements;
	private int maxTableNameLength;
	private int maxTablesInSelect;
	private int maxUserNameLength;
	private String numericFunctions;
	private String procedureTerm;
	private int resultSetHoldability;
	private String schemaTerm;
	private String searchStringEscape;
	private String sQLKeywords;
	private int sQLStateType;
	private String stringFunctions;
	private String systemFunctions;
	private String timeDateFunctions;
	private String uRL;
	private String userName;
	private List<String> catalogs;
	private List<String> clientInfoProperties;
	private String rowIdLifetime;
	private List<String> schemas;
	private List<String> tableTypes;
	private List<String> typeInfo;
	private boolean supportsGetGeneratedKeys;
	private int loginTimeout;
	private Properties  clientInfo;
	private int networkTimeout;
	private boolean autoCommit;
	private String warnings;
	private int queryTimeout;
	private String schema;  
	

}
