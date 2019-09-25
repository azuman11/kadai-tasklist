package filters;
//文字化け防止

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

/**
 * Servlet Filter implementation class EncodingFilter
 */
@WebFilter("/*")
public class EncodingFilter implements Filter {

    /**
     * Default constructor.
     */
    //コンストラクタ時に実行
    public EncodingFilter() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @see Filter#destroy()
     */
    //フィルタの処理が不要になったため）フィルタを破棄する」というときの処理を定義
    public void destroy() {
        // TODO Auto-generated method stub
    }

    /**
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    //フィルタとしての実行内容を定義
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        //chainより前 サーブレットが処理を実行する前
        chain.doFilter(request, response);
        //chainより後 サーブレットが処理を実行する後
    }

    /**
     * @see Filter#init(FilterConfig)
     */
    //フィルタの処理が初めて実行されるときの処理を定義
    public void init(FilterConfig fConfig) throws ServletException {
        // TODO Auto-generated method stub
    }

}
