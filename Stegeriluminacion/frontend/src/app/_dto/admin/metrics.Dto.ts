
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

  constructor() {
    this.processList = [[]];
    this.maxUsedConnections = [[]];
    this.waitTimeout = [[]];
    this.threadsConnected = [[]];
    this.threadsRunning = [[]];
    this.slowQueries = [[]];
    this.top10StatementsByLongestAverageRunTime = [[]];
    this.performanceSchemas = [[]];
    this.statementsWithRuntimesIn95thPercentile = [[]];
    this.statementsWithErrorsOrWarnings = [[]];
    this.maxConnections = [[]];
  }

}
