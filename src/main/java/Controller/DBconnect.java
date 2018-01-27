package Controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javafx.scene.control.TextArea;

public class DBconnect {

	public Connection con;
	public Statement st;
	public ResultSet rs;
	public Boolean kontoistnieje = null;
	private MainController mainController;

	public DBconnect() {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Properties prop = new Properties();
			prop.put("charSet", "UTF-8");
			prop.put("user", "root");
			prop.put("password", "");
			con = DriverManager
					.getConnection("jdbc:mysql://localhost:3306/zars?useUnicode=true&characterEncoding=UTF-8", prop);

			// con =
			// DriverManager.getConnection("jdbc:mysql://localhost:3306/zars","root","");
			st = con.createStatement();

		} catch (Exception ex) {
			System.out.println("Erro: " + ex);
		}
	}

	public void getData(TextArea output) {
		try {
			String query = "select * from uzytkownicy";
			rs = st.executeQuery(query);
			System.out.println("Records from database");
			while (rs.next()) {
				String name = rs.getString("name");
				String surname = rs.getString("surname");
				String email = rs.getString("email");
				String username = rs.getString("username");
				String password = rs.getString("password");
				output.appendText("Name: " + name + " " + "surname" + "  " + surname + " " + "email   " + email + " "
						+ "username  " + username + "  " + password + "  " + "password");
				System.out.println();

			}
		} catch (Exception ex) {

		}
	}

	public void putData() {

		try {

			String query = "INSERT INTO `uzytkownicy` VALUES ('Java','Javuchna','50','Java','Java123');";
			st.executeUpdate(query);
			// System.out.println("Records from database");
			/*
			 * while (rs.next()) {
			 * 
			 * System.out.println("Name: " + name + " " + "surname" + "  "+
			 * surname + " " + "age   " + age + " " + "username  " + username +
			 * "  " + password + "  " + "password"); System.out.println(); }
			 */
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void Zarejestruj(String name, String surname, String email, String username, String password) {
		try {
			String query = "select * from uzytkownicy where username='" + username + "'";

			rs = st.executeQuery(query);

			if (rs.next()) {

				/*
				 * System.out.println("Name: " + rs.getString("name") + " " +
				 * "surname" + "  "+ rs.getString("surname") + " " + "age   " +
				 * rs.getInt("age") + " " + "username  " +
				 * rs.getString("username") + "  " + rs.getString("password") +
				 * "  " + "password");
				 */
				System.out.println("Konto istnieje");
				System.out.println();
			} else {
				String queryDodaj = "INSERT INTO `uzytkownicy`(name,surname,email,username,password) VALUES ('" + name
						+ "','" + surname + "','" + email + "','" + username + "','" + password + "');";
				st.executeUpdate(queryDodaj);

				System.out.println("gówno");
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}

		try {
			// String query = "INSERT INTO
			// `uzytkownicy`(name,surname,age,username,password) VALUES
			// ('"+name+"','"+surname+"','"+age+"','"+username+"','"+password+"');";
			// st.executeUpdate(query);
			System.out.println("Hurra");
			// System.out.println("Records from database");
			/*
			 * while (rs.next()) {
			 * 
			 * System.out.println("Name: " + name + " " + "surname" + "  "+
			 * surname + " " + "age   " + age + " " + "username  " + username +
			 * "  " + password + "  " + "password"); System.out.println(); }
			 */
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
