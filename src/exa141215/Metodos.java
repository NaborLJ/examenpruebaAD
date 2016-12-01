package exa141215;

import static exa141215.Conexion.closeConexion;
import static exa141215.Conexion.conn;
import exa141215.Pedido;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
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
            int gasto = 0;
            
              if(reader.getEventType()==XMLStreamConstants.START_ELEMENT){
                Statement state = conn.createStatement();
            PreparedStatement Stockactu = conn.prepareStatement("UPDATE produtos SET STOCK=? WHERE CODIGOP=?");
            PreparedStatement Gastoact = conn.prepareStatement("UPDATE clientes SET GASTO=gasto+? WHERE CODIGOC=?");
            PreparedStatement insert = conn.prepareStatement("insert into vendas values(?,?,?,?)");
                  
                  
                    if ("Pedido".equals(reader.getLocalName())) {
                        codCli = reader.getAttributeValue(0);
                        System.out.println(codCli);

                        codPro = reader.getAttributeValue(1);
                        System.out.println(codPro);
                    }
                    if ("Cantidade".equals(reader.getLocalName())) {
                        cantidad = (reader.getElementText());
                        System.out.println(cantidad);
                    }
                    if ("Data".equals(reader.getLocalName())) {
                        data = reader.getElementText();
                        System.out.println(data);

                        
                        rs = state.executeQuery("select * from produtos where CODIGOP='" + codPro + "'");
                        rs.next();
                        stock = rs.getInt("STOCK");
                        precio = rs.getInt("PREZO");
                        System.out.println(precio+""+stock);
                        rs.close();
                        int cantFinal = stock - Integer.parseInt(cantidad);

                        total = (Integer.parseInt(cantidad) * precio);
                        gasto = gasto + total;

                        
                            Stockactu.setInt(1, cantFinal);
                            Stockactu.setString(2, codPro);
                            Stockactu.executeUpdate();
                        
                        
                            Gastoact.setInt(1,gasto);
                            Gastoact.setString(2,codCli);
                            Gastoact.executeUpdate();
                        
                       
                            insert.setString(1,codCli);
                            insert.setString(2,codPro);
                            insert.setString(3,data);
                            insert.setInt(4,total);
                            insert.executeUpdate();
                        }
}
                    
    
            reader.next();}

        reader.close();
        closeConexion();
        

    }

}
