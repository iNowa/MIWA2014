package fr.epita.sigl.miwa.application.BI;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.DOMOutputter;
import org.w3c.dom.Document;

import fr.epita.sigl.miwa.application.MiwaBDDIn;
import fr.epita.sigl.miwa.application.ParseXML;

public class EnvoiInformationVentesBI {
	private static final Logger LOGGER = Logger.getLogger(ParseXML.class.getName());
	private EnteteBI entete;
	private String lieu = "internet";
	private List<CategorieVenteBI> categories = new ArrayList<CategorieVenteBI>();
	
	public EnvoiInformationVentesBI()
	{
		
	}
	
	public EnvoiInformationVentesBI(EnteteBI entete, List<CategorieVenteBI> categories)
	{
		this.entete = entete;
		this.categories = categories;
	}
	
	public EnvoiInformationVentesBI(EnteteBI entete, List<CategorieVenteBI> categories, String lieu)
	{
		this.entete = entete;
		this.categories = categories;
		this.lieu = lieu;
	}
	
	public CategorieVenteBI existsCategorie(String cat)
	{
		for (CategorieVenteBI c : categories)
			if (c.getRef_categorie().equals(cat))
				return c;
		return null;
	}
	
	public Boolean getBDD()
	{
		MiwaBDDIn bdd = MiwaBDDIn.getInstance();
		
		ResultSet rs = bdd.executeStatement_result("SELECT * FROM article;");
		
		try {
			while (rs.next())
			{
				String cat = rs.getString("categorie");
				CategorieVenteBI categorie = existsCategorie(cat);
				
				if (categorie != null)
				{
					categorie.setQuantite_vendue(rs.getInt("vente"));
					categorie.setMontant_fournisseur(rs.getFloat("prix_fournisseur") * rs.getInt("vente"));
					categorie.setMontant_vente(rs.getFloat("prix_vente") * rs.getInt("vente"));
				}
				else
				{
					categorie = new CategorieVenteBI();
					
					categorie.setRef_categorie(rs.getString("categorie"));
					categorie.setQuantite_vendue(rs.getInt("vente"));
					categorie.setMontant_fournisseur(rs.getFloat("prix_fournisseur"));
					categorie.setMontant_vente(rs.getFloat("prix_vente"));
					
					categories.add(categorie);
				}
			}
			return true;
		} catch (SQLException e) {
			LOGGER.info("***** Erreur sql : " + e.getMessage());
			return false;
		}
	}
	
	public String sendXML()
	{
		StringBuilder result = new StringBuilder();
		
		LOGGER.info("***** Envoi d'un message à BI : envoi des informations sur les ventes Internet");
		
		result.append("<XML>");
		
		result.append(entete.sendXML());
		
		result.append("<VENTES lieu=\"" + lieu + "\">");
		for (CategorieVenteBI c : categories)
			result.append(c.sendXML());
		result.append("</VENTES>");
		
		result.append("</XML>");
		
		return result.toString();
	}
	
	public Document sendXMLDocument()
	{
		SAXBuilder saxBuilder = new SAXBuilder();
		
		File file = new File("temp.xml");
		
		BufferedWriter out;
		try {
			out = new BufferedWriter(new FileWriter(file));
			out.write(this.sendXML());
			out.close();
			
			org.jdom2.Document documentJdom = saxBuilder.build(file);
			DOMOutputter outputDocument = new DOMOutputter();
			
			return outputDocument.output(documentJdom);
		} catch (IOException | JDOMException e) {
			LOGGER.info("***** Erreur lors de la création du flux XML : " + e.getMessage());
		}
		return null;
	}

	public EnteteBI getEntete() {
		return entete;
	}

	public void setEntete(EnteteBI entete) {
		this.entete = entete;
	}

	public String getLieu() {
		return lieu;
	}

	public void setLieu(String lieu) {
		this.lieu = lieu;
	}

	public List<CategorieVenteBI> getCategories() {
		return categories;
	}

	public void setCategories(List<CategorieVenteBI> categories) {
		this.categories = categories;
	}
}
