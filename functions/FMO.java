package functions;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JRViewer;
import net.sf.jasperreports.view.JasperViewer;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Gigara
 */
public class FMO {

    public Pane fmoPane;

    public static void main(String[] args) {

    }

    public static void initialize(){
        try {

            JasperDesign jd = JRXmlLoader.load("src\\report1.jrxml");

            DBCollection furnitureCollection = DatabaseConnect.database("Furnitures");
            JRBeanCollectionDataSource items = new JRBeanCollectionDataSource(getAllDocuments(furnitureCollection));

            DBCollection FloorCollection = DatabaseConnect.database("floors");
            JRBeanCollectionDataSource floorDetails = new JRBeanCollectionDataSource(getAllDocuments(FloorCollection));

            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("ItemDataSource", items);
            parameters.put("FloorDataSource", floorDetails);

            JREmptyDataSource source = new JREmptyDataSource();
            JasperReport jr = JasperCompileManager.compileReport(jd);
            JasperPrint jp = JasperFillManager.fillReport(jr, parameters, source);
           // JasperViewer jv = new JasperViewer(jp, false);
           // jv.setVisible(true);

            JFrame frame = new JFrame("Report");
            frame.setPreferredSize(new Dimension(900, 700));
            frame.getContentPane().add(new JRViewer(jp));
            frame.pack();
            frame.setVisible(true);

        } catch (Exception e) {
            System.out.println(e);
       /* } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }*/
        }
    }
    public static List<BasicDBObject> getAllDocuments(DBCollection collection) {
        DBCursor cursor = collection.find();
        List<BasicDBObject> documentList = new ArrayList<BasicDBObject>();

        while (cursor.hasNext()) {
            documentList.add((BasicDBObject) cursor.next());
        }
        return documentList;
    }
}
