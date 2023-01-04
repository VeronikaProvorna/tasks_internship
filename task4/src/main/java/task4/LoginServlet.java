package task4;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/login", name = "loginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String login = request.getParameter("userName");
        String pwd = request.getParameter("userPwd");

        UserDao userDao = new UserDao();

        //check if user exist and if exist check his password
        if (userDao.isUserExist(login) && userDao.checkPassword(login, pwd)) {
            HttpSession session = request.getSession();
            session.setAttribute("login", login);

            //show welcome page
            response.sendRedirect(request.getContextPath() + "/welcome");
        } else {
            //show the same page but with error message
            out.print("<div align=center style=" + "color:red;margin:8px;font-size:20px" + ">Login or Password is wrong!</div>");
            request.getRequestDispatcher("/").include(request, response);
        }
        out.close();
    }
}
