package dto.admin;

import java.util.List;

import lombok.Data;

@Data
public class MetricsDTO {
	
	List<Object[]> processList;
	List<Object[]> maxUsedConnections;
	List<Object[]> waitTimeout;
	List<Object[]> threadsConnected;
	List<Object[]> threadsRunning;
	List<Object[]> slowQueries;
	List<Object[]> top10StatementsByLongestAverageRunTime;
	List<Object[]> performanceSchemas;
	List<Object[]> statementsWithRuntimesIn95thPercentile;
	List<Object[]> statementsWithErrorsOrWarnings;
	List<Object[]> maxConnections; 
}
