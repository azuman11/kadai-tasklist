package controllers;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Task;
import models.validators.TaskValidator;
import utils.DBUtil;


/**
 * Servlet implementation class UpdateServlet
 */
@WebServlet("/update")
public class UpdateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //EntityManagerの作成 Entityは箱。
        EntityManager em = DBUtil.createEntityManager();

        //ShowServletと同じ
        Task t = em.find(Task.class, (Integer)(request.getSession().getAttribute("task_id")));

        //基本creatServlet同じだが、行為は上書き
        String content = request.getParameter("content");
        t.setContent(content);

        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        t.setUpdated_at(currentTime);

        // バリデーション
        List<String> errors = TaskValidator.validate(t);
        if(errors.size() > 0) {
            em.close();

            // フォームに初期値を設定、さらにエラーメッセージを送る
            request.setAttribute("_token", request.getSession().getId());
            request.setAttribute("task", t);
            request.setAttribute("errors", errors);

            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/tasks/edit.jsp");
            rd.forward(request, response);
        } else {
        //DBの方を更新
        em.getTransaction().begin();
        //commit 登録
        em.getTransaction().commit();
        request.getSession().setAttribute("flush", "更新が完了しました。");
        em.close();

        // セッションスコープ上の不要になったデータを削除
        request.getSession().removeAttribute("task_id");

        // indexページへリダイレクト
        response.sendRedirect(request.getContextPath() + "/index");

        }

    }

}
