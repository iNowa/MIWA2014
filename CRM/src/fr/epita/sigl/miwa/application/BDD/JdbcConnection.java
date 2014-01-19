package fr.epita.sigl.miwa.application.BDD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import fr.epita.sigl.miwa.application.crm.TicketReduc;
import fr.epita.sigl.miwa.application.crm.LivraisonFournisseur;
import fr.epita.sigl.miwa.application.crm.ReassortBO;
import fr.epita.sigl.miwa.application.object.Client;
import fr.epita.sigl.miwa.application.object.Segmentation;


public class JdbcConnection
{
	private static JdbcConnection instance = null;
	private Connection connection = null;
	
	public static JdbcConnection getInstance()
	{
		if (instance == null)
			instance = new JdbcConnection();
		
		return instance;
	}
	
	public void getConnection()
	{
		System.out.println("-------- PostgreSQL " + "JDBC Connection Testing ------------");
 
		try
		{
			Class.forName("org.postgresql.Driver");
 
		}
		catch (ClassNotFoundException e)
		{
			System.out.println("Where is your PostgreSQL JDBC Driver? Include in your library path!");
			e.printStackTrace();
			return;
		}
 
		System.out.println("PostgreSQL JDBC Driver Registered!");
 
		try
		{
			connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/postgres", "postgres", "root");
		}
		catch (SQLException e)
		{
 
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		}
 
		if (connection != null)
			System.out.println("You made it, take control your database now!");
		else
			System.out.println("Failed to make connection!");
	}
	
	public void closeConnection()
	{
		try
		{
			if (connection != null)
				connection.close();
			
			System.out.println("Connection closed !");
		}
		catch (SQLException e)
		{
			System.out.println("Failed to close connection!");
			e.printStackTrace();
		}
	}
	
	public void insertCommandeInternet(Segmentation command)
	{
		try
		{
			System.out.println("insertion commande internet");
			if (connection != null)
			{
				String request = "INSERT INTO segmentation (commandnumber, datebc, datebl, customerref, customerlastname, customerfirstname, customeraddress) VALUES (?, ?, ?, ?, ?, ?, ?)";
				
				PreparedStatement statement = connection.prepareStatement(request);
				/*statement.setString(1, command.getCommandNumber());
				statement.setString(2, command.getDateBC());
				statement.setString(3, command.getDateBL());
				statement.setString(4, command.getCustomerRef());
				statement.setString(5, command.getCustomerLastname());
				statement.setString(6, command.getCustomerFirstname());
				statement.setString(7, command.getCustomerAddress());
*/
				/*int rowsInserted = statement.executeUpdate();
				if (rowsInserted > 0)
				{
					System.out.println("Nouvelle commande ajoutée en base !");
					for (TicketReduc a : command.getArticles())
					{
						String request2 = "INSERT INTO article (articleref, category) VALUES (?, ?)";
						
						statement = connection.prepareStatement(request2);
						statement.setString(1, a.getReference());
						statement.setString(2, a.getCategory());
						statement.executeUpdate();
						
						String request3 = "INSERT INTO commandeinternet_article (articleref, commandref) VALUES (?, ?)";
						statement = connection.prepareStatement(request3);
						statement.setString(1, a.getReference());
						statement.setString(2, command.getCommandNumber());
						statement.executeUpdate();
					}
				}*/
			}
		}
		catch (SQLException e)
		{
			System.out.println("Erreur insertion en base");
			e.printStackTrace();
		}
	}
	
	
	public void insertClientInternet(Client client)
	{
		try
		{
			System.out.println("insertion client internet");
			if (connection != null)
			{
				String request = "INSERT INTO Client (nom, prenom, cp, adresse, mail, tel, matricule) VALUES (?, ?, ?, ?, ?, ?, ?)";
				
				PreparedStatement statement = connection.prepareStatement(request);
				statement.setString(1, client.getNom());
				statement.setString(2, client.getPrenom());
				statement.setString(3, client.getCodePostal());
				statement.setString(4, client.getAdresse());
				statement.setString(5, client.getMail());
				statement.setString(6, client.getTelephone());
				statement.setString(7, Integer.toString(client.getMatricule()));

				int rowsInserted = statement.executeUpdate();
				if (rowsInserted > 0)
				{
					System.out.println("Nouveau client internet ajouté en base !");
				}
			}
		}
		catch (SQLException e)
		{
			System.out.println("Erreur insertion en base");
			e.printStackTrace();
		}
	}
	
	public void updateClientInternet(Client client)
	{
		try
		{
			System.out.println("update client internet");
			if (connection != null)
			{
				String request = "UPDATE Client SET nom=?, prenom=?, cp=?, adresse=?, mail=?, tel=? WHERE matricule = ?";
				
				PreparedStatement statement = connection.prepareStatement(request);
				statement.setString(1, client.getNom());
				statement.setString(2, client.getPrenom());
				statement.setString(3, client.getCodePostal());
				statement.setString(4, client.getAdresse());
				statement.setString(5, client.getMail());
				statement.setString(6, client.getTelephone());
				statement.setString(7, Integer.toString(client.getMatricule()));

				int rowsInserted = statement.executeUpdate();
				if (rowsInserted > 0)
				{
					System.out.println("Client internet modifié en base !");
				}
			}
		}
		catch (SQLException e)
		{
			System.out.println("Erreur update en base");
			e.printStackTrace();
		}
	}
	
	
	public void deleteClientInternet(int matricule)
	{
		try
		{
			System.out.println("suppr client internet");
			if (connection != null)
			{
				String request = "DELETE FROM Client WHERE matricule = ?";
				
				PreparedStatement statement = connection.prepareStatement(request);
				statement.setString(1, Integer.toString(matricule));

				int rowsInserted = statement.executeUpdate();
				if (rowsInserted > 0)
				{
					System.out.println("Client internet suppr en base !");
				}
			}
		}
		catch (SQLException e)
		{
			System.out.println("Erreur suppr en base");
			e.printStackTrace();
		}
	}
	
	
	public Client GetClientInternet(String id)
	{
		Client client = new Client();
		try
		{
			if (connection != null)
			{
				String request = "SELECT * FROM Client WHERE matricule = '" + id + "'";
				
				PreparedStatement statement = connection.prepareStatement(request);

				ResultSet result = statement.executeQuery();
				while(result.next())
				{
					client.setNom(result.getString(2));
					client.setPrenom(result.getString(3));
					client.setCodePostal(result.getString(4));
					client.setAdresse(result.getString(5));
					client.setMail(result.getString(6));
					client.setTelephone(result.getString(7));
					client.setMatricule(Integer.parseInt(result.getString(8)));
				}
			}
		}
		catch (SQLException e)
		{
			System.out.println("Erreur insertion en base");
			e.printStackTrace();
		}
		return client;
	}
	
	/*
	public void insertLivraisonFournisseur(LivraisonFournisseur command)
	{
		try
		{
			System.out.println("insertion livraison fournisseur");
			if (connection != null)
			{
				String request = "INSERT INTO livraisonfournisseur (commandnumber, datebc, datebl) VALUES (?, ?, ?)";
				
				PreparedStatement statement = connection.prepareStatement(request);
				statement.setString(1, command.getCommandNumber());
				statement.setString(2, command.getDateBC());
				statement.setString(3, command.getDateBL());

				int rowsInserted = statement.executeUpdate();
				if (rowsInserted > 0)
				{
					System.out.println("Nouvelle commande ajoutée en base !");
					for (TicketReduc a : command.getArticles())
					{
						String request2 = "INSERT INTO article (articleref, category) VALUES (?, ?)";
						
						statement = connection.prepareStatement(request2);
						statement.setString(1, a.getReference());
						statement.setString(2, a.getCategory());
						statement.executeUpdate();
						
						String request3 = "INSERT INTO livraisonfournisseur_article (articleref, commandref) VALUES (?, ?)";
						statement = connection.prepareStatement(request3);
						statement.setString(1, a.getReference());
						statement.setString(2, command.getCommandNumber());
						statement.executeUpdate();
					}
				}
			}
		}
		catch (SQLException e)
		{
			System.out.println("Erreur insertion en base");
			e.printStackTrace();
		}
	}*/
	/*
	public void insertReassortBO(ReassortBO command)
	{
		try
		{
			System.out.println("insertion reassort bo");
			if (connection != null)
			{
				String request = "INSERT INTO reassortbo (commandnumber, datebc, datebl, backofficeref, backofficephone, backofficeaddress) VALUES (?, ?, ?, ?, ?, ?)";
				
				PreparedStatement statement = connection.prepareStatement(request);
				statement.setString(1, command.getCommandNumber());
				statement.setString(2, command.getDateBC());
				statement.setString(3, command.getDateBL());
				statement.setString(4, command.getBackOfficeRef());
				statement.setString(5, command.getBackOfficePhone());
				statement.setString(6, command.getBackOfficeAddress());
				
				int rowsInserted = statement.executeUpdate();
				if (rowsInserted > 0)
				{
					System.out.println("Nouvelle commande ajoutée en base !");
					for (TicketReduc a : command.getArticles())
					{
						String request2 = "INSERT INTO article (articleref, category) VALUES (?, ?)";
						
						statement = connection.prepareStatement(request2);
						statement.setString(1, a.getReference());
						statement.setString(2, a.getCategory());
						statement.executeUpdate();
						
						String request3 = "INSERT INTO reassortbo_article (articleref, commandref) VALUES (?, ?)";
						statement = connection.prepareStatement(request3);
						statement.setString(1, a.getReference());
						statement.setString(2, command.getCommandNumber());
						statement.executeUpdate();
					}
				}
			}
		}
		catch (SQLException e)
		{
			System.out.println("Erreur insertion en base");
			e.printStackTrace();
		}
	}*/
}