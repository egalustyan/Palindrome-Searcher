package katherina.galustyan.testtask.irens.palindrome.entity;

import jdk.nashorn.internal.objects.annotations.Getter;

import javax.persistence.*;

/**
 * Created by kate on 06.04.2019.
 */
@Entity
@Table(name = "USER")
public class User {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "login", length = 20, nullable = false)
    private String login;

    @Column(name = "password", length = 20, nullable = false)
    private String password;


    public User() {
    }

    public User(String login) {
        this.login = login;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
