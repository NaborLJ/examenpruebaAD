
package exa141215;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import javax.xml.stream.XMLStreamException;


public class Exa141215 {

    public static void main(String[] args) throws SQLException, XMLStreamException, FileNotFoundException {
        Conexion con = new Conexion();
        Pedido aux = new Pedido();
        Metodos met = new Metodos();
        
        con.getConexion();
        
        con.closeConexion();
       
    }
    
}
