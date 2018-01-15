package com.game.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.game.db.bean.FileUpTable;
import com.game.utils.NGameUtil;
import com.game.utils.TextUtils;

/**
 * Servlet implementation class UIManager
 * 界面管理
 */
@WebServlet("/UIManager")
public class UIManager extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UIManager() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String operation = request.getParameter("operation");
		if (operation.equals("1")) {
			String pageStr = request.getParameter("page");
			if (TextUtils.isEmpty(pageStr)) {
				pageStr = "1";
			}
		 int page = Integer.parseInt(pageStr)-1;
			//素材
		 int count = 	NGameUtil.count(FileUpTable.class);
		
		 System.out.println(count);
			
		 int rows = 10;
		
		 int pagecount = (int) Math.ceil(count*1.0/rows);
		 if (page>=pagecount) {
			page=pagecount-1;
		  }
		 if (page<0) {
			page=0;
		  }
		 int offset = page*rows;	
		 ArrayList<FileUpTable> fileUpTables = (ArrayList<FileUpTable>) NGameUtil.limit(FileUpTable.class, String.valueOf(offset), String.valueOf(rows)); //selectAll(FileUpTable.class);
		 
		 
		 request.setAttribute("fileups", fileUpTables);
		 request.setAttribute("pagecount", pagecount);
		 request.setAttribute("page", page);
		 request.setAttribute("pageRows", (int) Math.ceil((page+1)*1.0/5) - 1);
		 
		 request.getRequestDispatcher("FileUpTable.jsp").forward(request,response); 
		}
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
