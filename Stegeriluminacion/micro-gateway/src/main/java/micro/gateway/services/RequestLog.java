package micro.gateway.services;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netflix.zuul.context.RequestContext;

import dao.auth.LogDao;
import micro.gateway.Utils;
import modelo.auth.log.Log;

@Service
public class RequestLog {

 
	@Autowired
	LogDao logDao;

	public void deleteLog(RequestContext ctx) {
		HttpServletRequest request = ctx.getRequest();
		Log log = Utils.fillLog(ctx, request.getHeader("Authorization").replace("Bearer ", "") , true); 
		log.setAccion("BORRADO");
		log.setApartado("PostFilter");
		logDao.saveAndFlush(log);
	}
	 
	public void updateLog(RequestContext ctx) {
		HttpServletRequest request = ctx.getRequest();
		Log log = Utils.fillLog(ctx, request.getHeader("Authorization").replace("Bearer ", ""), true); 
		log.setAccion("ACTUALIZADO");
		log.setApartado("PostFilter");
		logDao.saveAndFlush(log);
	}
	 
	public void postLog(RequestContext ctx) {
		HttpServletRequest request = ctx.getRequest();
		Log log = Utils.fillLog(ctx, request.getHeader("Authorization").replace("Bearer ", ""), true); 
		log.setAccion("POST");
		log.setApartado("PostFilter");
		logDao.saveAndFlush(log);
	}
	

	

}
