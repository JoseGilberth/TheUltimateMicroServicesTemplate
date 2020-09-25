package dao.database;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import model.auth.log.LogRequest;

public interface PostgresDataBaseDao extends JpaRepository<LogRequest, Long> {

	@Query(value = "SELECT Cast(datname as varchar), pid, usename, application_name, Cast(client_addr as varchar),client_port,state, query from pg_stat_activity", nativeQuery = true)
	List<Object[]> processList();

	@Query(value = "select 'Max_used_connections', count(*) used from pg_stat_activity", nativeQuery = true)
	List<Object[]> maxUsedConnections();

	@Query(value = "SHOW SESSION VARIABLES LIKE 'wait_timeout'", nativeQuery = true)
	List<Object[]> waitTimeout();

	@Query(value = "show status where `variable_name` = 'Threads_connected'", nativeQuery = true)
	List<Object[]> threadsConnected();

	@Query(value = "SHOW GLOBAL STATUS LIKE 'Threads_running'", nativeQuery = true)
	List<Object[]> threadsRunning();

	@Query(value = "SHOW GLOBAL STATUS LIKE 'Slow_queries'", nativeQuery = true)
	List<Object[]> slowQueries();

	@Query(value = "SELECT substr(digest_text, 1, 1000) AS digest_text_start," + " count_star," + " avg_timer_wait"
			+ " FROM performance_schema.events_statements_summary_by_digest" + " ORDER BY avg_timer_wait DESC"
			+ " LIMIT 10", nativeQuery = true)
	List<Object[]> top10StatementsByLongestAverageRunTime();

	@Query(value = "SELECT substr(digest_text, 1, 1000) AS digest_text_start," + " count_star,"
			+ " TRUNCATE(avg_timer_wait/1000000000000,6)"
			+ " FROM performance_schema.events_statements_summary_by_digest " + " ORDER BY avg_timer_wait DESC"
			+ " LIMIT 10", nativeQuery = true)
	List<Object[]> performanceSchemas();

	@Query(value = "SELECT * FROM sys.statements_with_runtimes_in_95th_percentile", nativeQuery = true)
	List<Object[]> statementsWithRuntimesIn95thPercentile();

	@Query(value = "SELECT * FROM sys.statements_with_errors_or_warnings", nativeQuery = true)
	List<Object[]> statementsWithErrorsOrWarnings();

	@Query(value = "SHOW max_connections", nativeQuery = true)
	List<Object[]> maxConnections();

}
