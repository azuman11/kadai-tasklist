package controllers;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Task;
import utils.DBUtil;

/**
 * Servlet implementation class EditServlet
 */
@WebServlet("/edit")
public class EditServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Entityは箱
        EntityManager em = DBUtil.createEntityManager();

        //ShowServlet
        Task t = em.find(Task.class, Integer.parseInt(request.getParameter("id")));

        em.close();

        //リクエストスコープに、メッセージ情報とセッションIDを登録
        request.setAttribute("task", t);
        request.setAttribute("_token", request.getSession().getId());

        //tにデータが存在している時のみ
        //セッションスコープに、IDを登録し、/updateへ
        if(t != null) {
            request.getSession().setAttribute("task_id", t.getId());
        }


        //ビューのedit.jspへ呼び出し。
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/tasks/edit.jsp");
        rd.forward(request, response);
    }

}