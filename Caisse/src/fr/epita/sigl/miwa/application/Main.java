package fr.epita.sigl.miwa.application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;
import javax.xml.ws.Holder;
import org.eclipse.swt.widgets.DateTime;
import fr.epita.sigl.miwa.application.clock.ClockClient;
import fr.epita.sigl.miwa.application.ihm.Home;
import fr.epita.sigl.miwa.application.messaging.AsyncMessageListener;
import fr.epita.sigl.miwa.application.messaging.SyncMessHandler;
import fr.epita.sigl.miwa.st.Conf;
import fr.epita.sigl.miwa.st.EApplication;
import fr.epita.sigl.miwa.st.async.file.exception.AsyncFileException;
import fr.epita.sigl.miwa.st.async.message.AsyncMessageFactory;
import fr.epita.sigl.miwa.st.async.message.exception.AsyncMessageException;
import fr.epita.sigl.miwa.st.sync.SyncMessFactory;

public class Main {
	private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
	public static final BddAccess bdd = new BddAccess();
	
	public static void main(String[] args) throws AsyncFileException,
			AsyncMessageException {
		/* ST DO NOT REMOVE/MODIFY OR PUT ANYTHING ABOVE */
		Conf.getInstance();
		SyncMessFactory.initSyncMessReceiver();	
		AsyncMessageFactory.getInstance().getAsyncMessageManager()
				.initListener(new AsyncMessageListener());
		/* !ST DO NOT REMOVE/MODIFY OR PUT ANYTHING ABOVE */
		/* CODE HERE */
		//connect BDD		
		bdd.connect();
		try {
			bdd.insert("insert into produit (produit_prix, produit_nom, produit_pourcentagepromo) values (5.5, 'chocapic', 10)");
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//je veux etre reveillé à 9H00
		Calendar dateouverture = Calendar.getInstance();
		dateouverture.setTime(ClockClient.getClock().getHour());
		Date nextOccurence = new Date();
		dateouverture.set(Calendar.HOUR_OF_DAY, 9);
		dateouverture.set(Calendar.MINUTE, 0);
		dateouverture.set(Calendar.SECOND, 0);
		dateouverture.set(Calendar.MILLISECOND, 0);
		if (dateouverture.get(Calendar.HOUR_OF_DAY) > 9) 
		dateouverture.add((Calendar.DAY_OF_MONTH), 1);
		nextOccurence = dateouverture.getTime();
		ClockClient.getClock().wakeMeUpEveryDays(nextOccurence, "ouverture");
		//je veux etre reveillé à 21H00
		dateouverture = Calendar.getInstance();
		dateouverture.setTime(ClockClient.getClock().getHour());
		nextOccurence = new Date();
		dateouverture.set(Calendar.HOUR_OF_DAY, 21);
		dateouverture.set(Calendar.MINUTE, 0);
		dateouverture.set(Calendar.SECOND, 0);
		dateouverture.set(Calendar.MILLISECOND, 0);
		if (dateouverture.get(Calendar.HOUR_OF_DAY) > 21) 
		dateouverture.add((Calendar.DAY_OF_MONTH), 1);
		nextOccurence = dateouverture.getTime();
		ClockClient.getClock().wakeMeUpEveryDays(nextOccurence, "fermeture");
		//Fin des réveilles
		
		
		//fin de la clock
				Home home = new Home();
		home.open();
		try {
			new BufferedReader(new InputStreamReader(System.in)).readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//SyncMessHandler.getSyncMessSender().sendMessage(EApplication.BI, "Coucou BI");
		/* !CODE HERE */
		/* ST DO NOT REMOVE/MODIFY OR PUT ANYTHING BELOW */
		AsyncMessageFactory.getInstance().getAsyncMessageManager()
				.stopListener();
		/* !ST DO NOT REMOVE/MODIFY OR PUT ANYTHING BELOW */
	}

}
