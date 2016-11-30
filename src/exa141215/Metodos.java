package exa141215;

import static exa141215.Conexion.conn;
import exa141215.Pedido;
import static exa141215.Pedido.cantidade;
import static exa141215.Pedido.codcli;
import static exa141215.Pedido.codpro;
import static exa141215.Pedido.data;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

public class Metodos {
    ResultSet rs;
    String codPro = "";
    String codCli = "";
    String data;
    String cantidad = "";
    int total;
    int precio;
    int stock;

    public void Leer() throws XMLStreamException, FileNotFoundException, SQLException {
        String fichero = "/home/oracle/NetBeansProjects/exa141215/pedidos.xml";
        XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        XMLStreamReader reader = inputFactory.createXMLStreamReader(new FileReader(fichero));
        while (reader.hasNext()) {
            int eventType = reader.next();

            switch (eventType) {
                case 1:
                    if ("Pedido".equals(reader.getLocalName())) {
                        codcli = reader.getAttributeValue(0);
                        System.out.print(codcli);

                        codpro = reader.getAttributeValue(1);
                        System.out.print(codpro);
                    }
                    if ("Cantidade".equals(reader.getLocalName())) {
                        cantidad = (reader.getElementText());
                        System.out.println(cantidad);
                    }
                    if ("Data".equals(reader.getLocalName())) {
                        data = reader.getElementText();
                        System.out.println(data);

                        Statement state = conn.createStatement();
                        rs = state.executeQuery("select * from produtos where CODIGOP='" + codpro + "'");
                        rs.next();
                        precio = rs.getInt("PREZO");
                        stock = rs.getInt("STOCK");
                        reader.close();

                    }

            }

        }

    }

}
