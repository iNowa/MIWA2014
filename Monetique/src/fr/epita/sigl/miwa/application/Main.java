package fr.epita.sigl.miwa.application;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.Date;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import java.sql.*;

import fr.epita.sigl.miwa.application.clock.ClockClient;
import fr.epita.sigl.miwa.application.messaging.AsyncMessageListener;
import fr.epita.sigl.miwa.application.messaging.SyncMessHandler;
import fr.epita.sigl.miwa.db.DbHandler;
import fr.epita.sigl.miwa.db.InitMysqlConnector;
import fr.epita.sigl.miwa.st.Conf;
import fr.epita.sigl.miwa.st.EApplication;
import fr.epita.sigl.miwa.st.async.file.exception.AsyncFileException;
import fr.epita.sigl.miwa.st.async.message.AsyncMessageFactory;
import fr.epita.sigl.miwa.st.async.message.exception.AsyncMessageException;
import fr.epita.sigl.miwa.st.sync.SyncMessFactory;

public class Main {
	private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

	public static void main(String[] args) throws AsyncFileException,
			AsyncMessageException {
		/* ST DO NOT REMOVE/MODIFY OR PUT ANYTHING ABOVE */
		Conf.getInstance();
		SyncMessFactory.initSyncMessReceiver();	
		AsyncMessageFactory.getInstance().getAsyncMessageManager()
				.initListener(new AsyncMessageListener());
		/* !ST DO NOT REMOVE/MODIFY OR PUT ANYTHING ABOVE */
		
		/* CODE HERE */
		new BufferedReader(new InputStreamReader(System.in));	
		Date clockDate = ClockClient.getClock().getHour();
		System.out.println(clockDate);
	
		// Init MySQL connector
		InitMysqlConnector.init();
		
		/*DbHandler dbHandler = new DbHandler();

        try {
            Connection connection = dbHandler.open();

            PreparedStatement pS = connection.prepareStatement("SELECT id_loyalty_card_type as id, card_type_code as type FROM loyalty_card_type;");
            //pS.setInt(1, idEM);
            ResultSet result = pS.executeQuery();

            while (result.next()) {
                Integer id = result.getInt("id");
                String nom = result.getString("type");
                System.out.println("*****" + id + " -- " + nom);
            }

            dbHandler.close();

        } catch ( SQLException e ) {
            System.err.println("ERROR : " + e.getMessage());
        }*/
		
		
	    DocumentBuilder db;
		try {
			db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		    InputSource is = new InputSource();
		    is.setCharacterStream(new StringReader("<?xml version=\"1.0\" encoding=\"UTF-8\"?><monetique service=\"paiement_cb\"><montant>XX.XX</montant><cb><numero>XXXXXXXXXXXXXXXX</numero><date_validite>MMAA</date_validite><pictogramme>XXX</pictogramme></cb></monetique>"));
		    Document doc = db.parse(is);
			SyncMessHandler.getSyncMessSender().sendXML(EApplication.MONETIQUE, doc);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		/*try {
			Thread.sleep(40000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		/*AsyncMessageFactory.getInstance().getAsyncMessageManager().send(message, destination);
		SyncMessFactory.getSyncMessSender().sendMessage(to, message)
		ClockClient.getClock().wakeMeUp(date, message);*/
//		SyncMessHandler.getSyncMessSender().sendMessage(
//				EApplication.BI, "Coucou BI");
		/* !CODE HERE */
		
		/* ST DO NOT REMOVE/MODIFY OR PUT ANYTHING BELOW */
		AsyncMessageFactory.getInstance().getAsyncMessageManager()
				.stopListener();
		/* !ST DO NOT REMOVE/MODIFY OR PUT ANYTHING BELOW */
	}

}
