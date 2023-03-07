package web.model;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "The field \"First Name\" cannot be empty")
    @Size(min = 2, max = 20, message = "Field \"First Name\" should be between 2 and 20 characters")
    @Column(name = "firstname")
    private String firstName;
    @NotEmpty(message = "The field \"Last Name\" cannot be empty")
    @Size(min = 2, max = 20, message = "Field \"Last Name\" should be between 2 and 20 characters")
    @Column(name = "lastname")
    private String lastName;
    @Min(value = 0, message = "The age cannot be less than 0")
    @Max(value = 127, message = "The age cannot be more than 127")
    @Column(name = "age")
    private byte age;
    @NotEmpty(message = "The field \"Email\" cannot be empty")
    @Email(message = "Email is not valid")
    @Column(name = "email")
    private String email;

    public User() {

    }

    public User(String firstName, String lastName, byte age, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
    }

    public User(Long id, String  firstName, String lastName, byte age, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public byte getAge() {
        return age;
    }

    public void setAge(byte age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return getId() + " "
                + getFirstName() + " "
                + getLastName() + " "
                + getAge() + " "
                + getEmail();
    }

}
