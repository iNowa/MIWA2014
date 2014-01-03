package fr.epita.sigl.miwa.application.GC;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DemandeNiveauStockGC {
	// numero de la demande
	private String numero;
	private Date date;
	private List<DemandeNiveauStockArticlesGC> articles = new ArrayList<DemandeNiveauStockArticlesGC>();
	
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	public void setDate(String dateString) {
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		try {
			this.date = df.parse(dateString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public List<DemandeNiveauStockArticlesGC> getArticles() {
		return articles;
	}
	public void setArticles(List<DemandeNiveauStockArticlesGC> articles) {
		this.articles = articles;
	}
	
	public void print()
	{
		System.out.println("DEMANDENIVEAUDESTOCKINTERNET : [");
		if (this.numero != null)
			System.out.println("	Numero : " + this.numero);
		else
			System.out.println("	Numero : NULL");
		
		if (this.numero != null)
			System.out.println("	Date : " + this.date);
		else
			System.out.println("	Date : NULL");
		
		if (articles != null || !articles.isEmpty())
		{
			System.out.println("	Articles : [");
			for(DemandeNiveauStockArticlesGC article : articles)
				article.print();
			System.out.println("	]");
		}
		else
			System.out.println("	Articles : NULL");
		System.out.println("]");
	}
}