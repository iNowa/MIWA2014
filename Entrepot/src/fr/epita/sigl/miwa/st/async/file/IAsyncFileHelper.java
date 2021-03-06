package fr.epita.sigl.miwa.st.async.file;

import java.io.File;

import fr.epita.sigl.miwa.st.EApplication;
import fr.epita.sigl.miwa.st.async.file.exception.AsyncFileException;

/**
 * Interface de l'utilisataire permettant d'envoyer et de récupérer des fichiers
 * 
 * @author francois
 * 
 */
public interface IAsyncFileHelper {

	/**
	 * Récupère le fichier
	 * 
	 * @param filename
	 *            le nom du fichier à récupérer
	 * @throws AsyncFileException
	 */
	public File retrieve(String filename) throws AsyncFileException;

	/**
	 * Envoi le fichier à l'application
	 * 
	 * @param filename
	 *            le nom du fichier à envoyer
	 * @param destination
	 *            l'application à qui envoyer le fichier
	 * @throws AsyncFileException
	 */
	public void send(String filename, EApplication destination)
			throws AsyncFileException;
}
