
package mz.co.scn.cervejaria;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Sidónio Goenha on Jun 29, 2020
 *
 */
@WebServlet(urlPatterns = "/clientes", loadOnStartup = 1)
public class ClientesServlet extends HttpServlet {
	private String clientes;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.getWriter().print(clientes);
	}

	@Override
	public void init() {

		String caminho = "file:///C:/Users/SCNguenha/Documents/clientes.xml";
		try {
			URL url = new URL(caminho);
			InputStream inputStream = url.openConnection().getInputStream();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			String line = null;
			StringBuilder builder = new StringBuilder();
			while ((line = bufferedReader.readLine()) != null) {
				builder.append(line);
				builder.append("\n");
			}
			System.out.println(builder.toString());
			clientes = builder.toString();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
