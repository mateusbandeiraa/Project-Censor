package control;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter("/adm/*")
public class Filter implements javax.servlet.Filter {

	public Filter() {

	}

	public void destroy() {

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		HttpSession session = req.getSession();
		
		try {
			String authorized = (String) session.getAttribute("userAuthorized");
			if (authorized != null && authorized.equals("true")) {
				chain.doFilter(request, response);
			} else {
				throw new Exception("Não Autorizado");
			}
			
		} catch (Exception ex) {
			resp.sendRedirect("../index.jsp?msg=" + URLEncoder.encode("Não autorizado", "UTF-8"));
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {

	}

}
