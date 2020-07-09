
package mz.co.scn.cervejaria;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;


/**
 *
 * @author Sidónio Goenha on Jun 29, 2020
 *
 */
public class Cliente {
	public static void main(String args[]) throws IOException {
		String caminho = "file:///C:/Users/SCNguenha/Documents/clientes.xml";
		URL url = new URL(caminho);
		InputStream inputStream = url.openConnection().getInputStream();
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
		 String line = null;
		 
		 while ((line = bufferedReader.readLine()) != null) {
			 System.out.println(line);
		 }
	}
}
