package model;



import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author admin
 */
public class UserDetails {
	private final IntegerProperty id;
	private final StringProperty name;
	private final StringProperty surname;
	private final StringProperty email;
	private final StringProperty password;
	private final StringProperty username;
	private final StringProperty poziomkonta;

	// Default constructor
	public UserDetails(Integer id, String name, String surname, String username, String email, String password,
			String poziomkonta) {
		this.id = new SimpleIntegerProperty(id);
		this.name = new SimpleStringProperty(name);
		this.surname = new SimpleStringProperty(surname);
		this.email = new SimpleStringProperty(email);
		this.username = new SimpleStringProperty(username);
		this.password = new SimpleStringProperty(password);
		this.poziomkonta = new SimpleStringProperty(poziomkonta);
	}

	// Getters
	public Integer getid() {
		return id.get();
	}

	public String getName() {
		return name.get();
	}

	public String getSurname() {
		return surname.get();
	}

	public String getemail() {
		return email.get();
	}

	public String getusername() {
		return username.get();
	}

	public String getpassword() {
		return password.get();
	}

	public String getpoziomkonta() {
		return poziomkonta.get();
	}

	// Setters
	public void setid(Integer value) {
		id.set(value);
	}

	public void setName(String value) {
		name.set(value);
	}

	public void setSurname(String value) {
		surname.set(value);
	}

	public void setemail(String value) {
		email.set(value);
	}

	public void setusername(String value) {
		username.set(value);
	}

	public void setpassword(String value) {
		password.set(value);
	}

	public void setpoziomkonta(String value) {
		poziomkonta.set(value);
	}

	// Property values
	public IntegerProperty idProperty() {
		return id;
	}

	public StringProperty nameProperty() {
		return name;
	}

	public StringProperty surnameProperty() {
		return surname;
	}

	public StringProperty emailProperty() {
		return email;
	}

	public StringProperty usernameProperty() {
		return username;
	}

	public StringProperty passwordProperty() {
		return password;
	}

	public StringProperty poziomkontaProperty() {
		return poziomkonta;
	}
}
