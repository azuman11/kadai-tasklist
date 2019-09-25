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
//show（詳細画面）の作成
/**
 * Servlet implementation class ShowServlet
 */
@WebServlet("/show")
public class ShowServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //creatServletと類似。EntityManagerの作成 Entityは箱。
        EntityManager em = DBUtil.createEntityManager();

        //modelのtaskクラスから、指定したidのメッセージ1件のみをDBから取得
        //Interger.parseInt()でrequest.getParameter()を整数型化
        //em.findでtに情報が入れられている。
        Task t = em.find(Task.class, Integer.parseInt(request.getParameter("id")));

        em.close();

        //tのデータを"task"というリクエストスコープにセット
        request.setAttribute("task", t);

        //ビューのshowJSPへ呼び出し。
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/tasks/show.jsp");
        rd.forward(request, response);
    }

}