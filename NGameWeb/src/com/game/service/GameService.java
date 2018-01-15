package com.game.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.json.JsonObject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.game.db.dao.BaseDb;
import com.game.db.dao.DbOperation;
import com.game.spring.BeanUtil;
import com.game.spring.DataSourceContextHolder;
import com.game.spring.DataSourceType;
import com.game.transmission.Message;
import com.game.transmission.TableStruct;
import com.game.utils.Common;
import com.game.utils.MD5;
import com.game.utils.NGameUtil;

/**
 * Servlet implementation class GameService
 */
@WebServlet("/GameService")
public class GameService extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GameService() {
		super();
	}
	
	public static interface IProx{
		public String doProx(String json);
	}
	
	private void doService(HttpServletRequest request, HttpServletResponse response,IProx prox)throws ServletException, IOException{
		request.setCharacterEncoding("utf-8");  
        response.setContentType("application/json;charset=utf-8");  
		DataSourceContextHolder.setDbType(DataSourceType.DB_ACCOUNT);
		Message message = new Message();
		String json = request.getParameter("operationsql");
		String md5 = request.getParameter("md5");
		String appkey =Common.decryptJson(request.getParameter("appkey"));
		
		if (json!=null && !"".equals(json)) {
			String data=null;
			try {
				json = Common.decryptAESJson(json);
				if (md5.equals(MD5.getMessageDigest(json.getBytes()))) {
					data= prox.doProx(json);//getOperationSql(json);
					if (data==null) {
						message.setErrorCode(-1);
						message.setErrorInfo("请求出错");
					}
				}else {
					message.setErrorCode(-3);
					message.setErrorInfo("被人修改了数据");
				}
				
			} catch (Exception e) {
				message.setErrorCode(-1);
				message.setErrorInfo(e.getMessage());
				e.printStackTrace();
			}
			
			message.setData(data);
			response.getWriter().append(JSONObject.toJSONString(message));
		}else {
			message.setErrorCode(-1);
			message.setErrorInfo(" operationsql= err");
			response.getWriter().append(JSONObject.toJSONString(message));
		}
	}

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		doService(request, response, new IProx(){
			@Override
			public String doProx(String json) {
				//return getOperationSql(json);
				TableStruct struct = JSONObject.parseObject(json, TableStruct.class);
				return NGameUtil.dbSubmitSql(struct);
			}
		});
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doService(request, response, new IProx(){
			@Override
			public String doProx(String json) {
				//return posoPerationSql(json);
				TableStruct struct = JSONObject.parseObject(json, TableStruct.class);
				return NGameUtil.dbSubmitSql(struct);
				
			}
			
		});
		
	}
	
	
//	private String posoPerationSql(String json) {
//		TableStruct struct = JSONObject.parseObject(json, TableStruct.class);
//		if (DbOperation.insert.getOperation().equals(struct.getOperation())){
//			int index = BeanUtil.getBean(BaseDb.class).insert(struct.getTable(), struct.getColumn(), struct.getColumnData());
//			return String.valueOf(index);
//		}
//		
//		if (DbOperation.deleteWhere.getOperation().equals(struct.getOperation())){
//			int index = BeanUtil.getBean(BaseDb.class).deleteWhere(struct.getTable(), struct.getWhere());
//			return String.valueOf(index);
//		}
//		
//		if (DbOperation.update.getOperation().equals(struct.getOperation())){
//			int index = BeanUtil.getBean(BaseDb.class).update(struct.getTable(), struct.getSetColumn(), struct.getWhere());
//			return String.valueOf(index);
//		}
//		
//		if (DbOperation.selectAll.getOperation().equals(struct.getOperation())){
//			BeanUtil.getBean(BaseDb.class).selectAll(struct.getTable());
//		}
//		
//		if (DbOperation.selectWhere.getOperation().equals(struct.getOperation())){
//			BeanUtil.getBean(BaseDb.class).selectWhere(struct.getTable(), struct.getWhere());
//		}
//		return null;
//		
//	}
//	
//	
//	private String getOperationSql(String json) {
//		TableStruct struct = JSONObject.parseObject(json, TableStruct.class);
//		
//		if (DbOperation.selectAll.getOperation().equals(struct.getOperation())){
//			List<Map<String, Object>> list = BeanUtil.getBean(BaseDb.class).selectAll(struct.getTable());
//			return JSONObject.toJSONString(list);
//			
//		}
//		
//		if (DbOperation.selectWhere.getOperation().equals(struct.getOperation())){
//			List<Map<String, Object>> list =BeanUtil.getBean(BaseDb.class).selectWhere(struct.getTable(), struct.getWhere());
//			return JSONObject.toJSONString(list);
//		}
//		return null;
//	}

}
