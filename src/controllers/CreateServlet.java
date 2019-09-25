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

//create（挿入処理）の作成
/**
 * Servlet implementation class CreateServlet
 */
@WebServlet("/create")
public class CreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //CSRF対策 値が無、又はセッションIDと値が異なる場合はじく。
        String _token = (String)request.getParameter("_token");
        if(_token != null && _token.equals(request.getSession().getId())) {

            //EntityManagerの作成 Entityは箱。
            EntityManager em = DBUtil.createEntityManager();

            Task t = new Task();

            //idはMySQLの auto_incrementの採番
            Timestamp currentTime = new Timestamp(System.currentTimeMillis());
            t.setCreated_at(currentTime);
            t.setUpdated_at(currentTime);
            //入力されたcontentをTask(型) t に入れる。
            String content = request.getParameter("content");
            t.setContent(content);


            // バリデーション
            List<String> errors = TaskValidator.validate(t);
            //新規登録のフォームに戻る
            if(errors.size() > 0) {
                em.close();

                // フォームに初期値を設定、さらにエラーメッセージを送る
                request.setAttribute("_token", request.getSession().getId());
                request.setAttribute("task", t);
                request.setAttribute("errors", errors);

                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/tasks/new.jsp");
                rd.forward(request, response);
            //登録
            } else {
                em.getTransaction().begin();
                //persist = INSERT エンティティオブジェクトをDBに追加
                em.persist(t);
                //commit 登録
                em.getTransaction().commit();
                //リダイレクト時に消えてしまうので、フラッシュメッセージをセッションスコープに保存し、index.jspを呼出時にセッションスコープ表示
                request.getSession().setAttribute("flush", "登録が完了しました。");
                em.close();

                //indexページへリダイレクト
                response.sendRedirect(request.getContextPath() + "/index");

            }
        }
    }
}