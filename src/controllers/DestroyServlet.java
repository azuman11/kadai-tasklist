package controllers;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Task;
import utils.DBUtil;

/**
 * Servlet implementation class DestroyServlet
 */
@WebServlet("/destroy")
public class DestroyServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public DestroyServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String _token = (String)request.getParameter("_token");
        if(_token != null && _token.equals(request.getSession().getId())) {
            EntityManager em = DBUtil.createEntityManager();

            //showServletと同じ
            Task t = em.find(Task.class, (Integer)(request.getSession().getAttribute("task_id")));

            //DBの方を呼び出し
            em.getTransaction().begin();
            //データ削除
            em.remove(t);
            //DBの方も更新
            em.getTransaction().commit();
            em.close();

            // セッションスコープ上の不要データを削除
            request.getSession().removeAttribute("task_id");

            // indexへリダイレクト
            response.sendRedirect(request.getContextPath() + "/index");
        }
    }

}