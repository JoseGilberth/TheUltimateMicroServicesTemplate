package micro.gateway.services.interfaces;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.auth.log.LogRequest;
 

public interface ICustomLogService {

	LogRequest fillLog(HttpServletRequest request, HttpServletResponse response, String token) ;
	
}
