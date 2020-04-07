
export class MetricsDTO {

  processList: Array<String[]>;
  maxUsedConnections: Array<String[]>;
  waitTimeout: Array<String[]>;
  threadsConnected: Array<String[]>;
  threadsRunning: Array<String[]>;
  slowQueries: Array<String[]>;
  top10StatementsByLongestAverageRunTime: Array<String[]>;
  performanceSchemas: Array<String[]>;
  statementsWithRuntimesIn95thPercentile: Array<String[]>;
  statementsWithErrorsOrWarnings: Array<String[]>;
  maxConnections: Array<String[]>;

}
