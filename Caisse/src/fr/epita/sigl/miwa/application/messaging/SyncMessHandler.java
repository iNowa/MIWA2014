package fr.epita.sigl.miwa.application.messaging;

import org.w3c.dom.Document;

import fr.epita.sigl.miwa.st.EApplication;
import fr.epita.sigl.miwa.st.sync.ISyncMessSender;
import fr.epita.sigl.miwa.st.sync.SyncMessFactory;

public class SyncMessHandler {
	
	/* 
	 * A utiliser pour pouvoir envoyer des messages synchrones ou faire des request
	 */
	static public ISyncMessSender getSyncMessSender() {
		return SyncMessFactory.getSyncMessSender();
		
		// A APPELER DANS NOS FONCTIONS POUR UN SYNCHRONE FICHIER A ENVOYER A QQUN (ET ATTENDRE UN RETOUR)
		//LEUR RETOUR XML (FORMAT XML) = SyncMessHandler.getSyncMessSender().requestXML(EApplication.BACK_OFFICE, MON XML format string)
	
		// ENVOYER UN MESSAGE ASYNCHRONE A QQUN
		//AsyncMessageFactory.getInstance().getAsyncMessageManager().send(content, EApplication.GESTION_COMMERCIALE);
	}

	/*
	 * l'application sender vous envoie la string message.
	 * Vous ne devez faire aucun appel � cette fonction, seulement remplir le code
	 * Elle est automatiquement appel�e lorsqu'une application vous contacte
	 */
	@Deprecated
	static public boolean receiveMessage(EApplication sender, String message) {
		return false;
		// TODO LA RECEPTION DU MESSAGE DE VALIDATION OU REFUS CB PAR MONETIQUE
	}

	/*
	 * L'application sender vous demande request
	 * Vous devez lui renvoyer une string
	 * Vous ne devez faire aucun appel � cette fonction, seulement remplir le code
	 * Elle est automatiquement appel�e lorsqu'une application vous contacte
	 */
	@Deprecated
	static public String answerToRequestMessage(EApplication sender, String request){
		// TODO Auto-generated method stub
		return null;
	}
	
	/*
	* L'application sender vous envoie le XML xml
	* Vous ne devez faire aucun appel � cette fonction, seulement remplir le code
	* Elle est automatiquement appel�e lorsqu'une application vous contacte
	*/
	@Deprecated
	static public boolean receiveXML(EApplication sender, Document xml){
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * L'application sender vous demande un XML avec la requete request
	 * Vous ne devez faire aucun appel � cette fonction, seulement remplir le code
	 * Elle est automatiquement appel�e lorsqu'une application vous contacte
	 */
	@Deprecated
	static public Document answerToRequestXML(EApplication sender, String request){
		// TODO Auto-generated method stub
		return null;
	}
	
	private SyncMessHandler() {
		
	}
}
