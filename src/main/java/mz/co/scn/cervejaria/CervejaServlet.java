
package mz.co.scn.cervejaria;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import mz.co.scn.cervejaria.model.Cerveja;
import mz.co.scn.cervejaria.model.Estoque;
import mz.co.scn.cervejaria.model.rest.Cervejas;

/**
 *
 * @author Sidónio Goenha on Jul 7, 2020
 *
 */
@WebServlet(value = "/cervejas/*")
public class CervejaServlet extends HttpServlet {

	private static final long serialVersionUID = -245495699089721524L;
	private Estoque estoque = new Estoque();
	private static JAXBContext context;

	static {
		try {
			context = JAXBContext.newInstance(Cervejas.class);
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String acceptHeader = req.getHeader("Accept");

		if (acceptHeader == null || acceptHeader.contains("application/xml")) {
			escreveXML(req, resp);
		} else if (acceptHeader.contains("application/json")) {
			escreveJSON(req, resp);
		}else {
			// o header accept foi recebido com um valor não suportado
			resp.sendError(415); // Formato não suportado
		}
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			String identificador = null;
			try {
				identificador = obtemIdentificador(req);
			} catch (RecursoSemIdentificadorException e) {
				resp.sendError(400, e.getMessage()); //Manda um erro 400 - Bad Request
			}
			
			if (identificador != null && estoque.recuperarCervejaPeloNome(identificador) != null) {
				resp.sendError(409, "Já existe uma cerveja com esse nome");
				return;
			}
			
			Unmarshaller unmarshaller = context.createUnmarshaller();
			Cerveja cerveja = (Cerveja)unmarshaller.unmarshal(req.getInputStream());
			cerveja.setNome(identificador);
			estoque.adicionarCerveja(cerveja);
			String requestURI = req.getRequestURI();
			resp.setHeader("Location", requestURI);
			resp.setStatus(201);
			escreveXML(req, resp);
		} catch (Exception e) {
			resp.sendError(500, e.getMessage());
		}
	}

	private void escreveXML(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Cervejas cervejas = new Cervejas();
		// cervejas.setCervejas(new ArrayList<Cerveja>(estoque.listarCervejas()));

		Object cerveja = localizaObjectoASerEnviado(req);

		if (cerveja == null) {
			resp.sendError(404); // objecto não encontrado
			return;
		}

		try {
			resp.setContentType("application/xml;charset=UTF-8");
			Marshaller marshaller = context.createMarshaller();
			marshaller.marshal(cerveja, resp.getWriter());
		} catch (JAXBException e) {
			resp.sendError(500); // Erro interno inesperado
		}
	}

	private void escreveJSON(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Cervejas cervejas = new Cervejas();
		// cervejas.setCervejas(new ArrayList<Cerveja>(estoque.listarCervejas()));
		Object cerveja = localizaObjectoASerEnviado(req);

		if (cerveja == null) {
			resp.sendError(404); // objecto não encontrado
			return;
		}

		try {
			resp.setContentType("application/json;charset=UTF-8");
			//MappedNamespaceConvention con = new MappedNamespaceConvention();

			//XMLStreamWriter xmlStreamWriter = new MappedXMLStreamWriter(con, resp.getWriter());

			Marshaller marshaller = context.createMarshaller();
			marshaller.marshal(cerveja, resp.getWriter());

		} catch (JAXBException e) {
			resp.sendError(500);
		}
	}

	private String obtemIdentificador(HttpServletRequest req) throws RecursoSemIdentificadorException {
		String requestUri = req.getRequestURI();

		String[] pedacosDaUri = requestUri.split("/");

		boolean contextoCervejasEncontrado = false;
		for (String contexto : pedacosDaUri) {
			if (contexto.equals("cervejas")) {
				contextoCervejasEncontrado = true;
				continue; // Faz o loop avançar até o próximo
			}

			if (contextoCervejasEncontrado) {
				try {
					return URLDecoder.decode(contexto, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					return URLDecoder.decode(contexto);
				}
			}
		}

		throw new RecursoSemIdentificadorException("Recurso sem identificador");
	}

	private Object localizaObjectoASerEnviado(HttpServletRequest req) {
		Object objecto = null;

		try {
			String identificador = obtemIdentificador(req);
			objecto = estoque.recuperarCervejaPeloNome(identificador);
		} catch (RecursoSemIdentificadorException e) {
			Cervejas cervejas = new Cervejas();
			cervejas.setCervejas(new ArrayList<Cerveja>(estoque.listarCervejas()));
			objecto = cervejas;
		}

		return objecto;
	}
	/*
	 * public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, java.io.IOException
	 * { PrintWriter out = resp.getWriter(); Collection<Cerveja> cervejas = estoque.listarCervejas(); for (Cerveja
	 * cerveja : cervejas) { out.print(cerveja); } }
	 */
}
