
package mz.co.scn.cervejaria;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.stream.XMLStreamWriter;

import org.codehaus.jettison.mapped.MappedNamespaceConvention;
import org.codehaus.jettison.mapped.MappedXMLStreamWriter;

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

	private Estoque estoque = new Estoque();
	private static JAXBContext context;

	static {
		try {
			context = JAXBContext.newInstance(Cervejas.class);
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, java.io.IOException {
		String acceptHeader = req.getHeader("Accept");

		if (acceptHeader == null || acceptHeader.contains("application/xml")) {
			escreveXML(req, resp);
		} else if (acceptHeader.contains("application/json")) {
			escreveJSON(req, resp);
		} else {
			// o header accept foi recebido com um valor não suportado
			resp.sendError(415); // Formato não suportado
		}
	}

	private void escreveXML(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Cervejas cervejas = new Cervejas();
		cervejas.setCervejas(new ArrayList<Cerveja>(estoque.listarCervejas()));

		try {
			resp.setContentType("application/xml;charset=UTF-8");
			Marshaller marshaller = context.createMarshaller();
			marshaller.marshal(cervejas, resp.getWriter());
		} catch (JAXBException e) {
			resp.sendError(500); // Erro interno inesperado
		}
	}

	private void escreveJSON(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Cervejas cervejas = new Cervejas();
		cervejas.setCervejas(new ArrayList<Cerveja>(estoque.listarCervejas()));

		try {
			resp.setContentType("application/json;charset=UTF-8");
			MappedNamespaceConvention con = new MappedNamespaceConvention();

			XMLStreamWriter xmlStreamWriter = new MappedXMLStreamWriter(con, resp.getWriter());

			Marshaller marshaller = context.createMarshaller();
			marshaller.marshal(cervejas, xmlStreamWriter);

		} catch (JAXBException e) {
			resp.sendError(500);
		}
	}
	/*
	 * public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, java.io.IOException
	 * { PrintWriter out = resp.getWriter(); Collection<Cerveja> cervejas = estoque.listarCervejas(); for (Cerveja
	 * cerveja : cervejas) { out.print(cerveja); } }
	 */
}
