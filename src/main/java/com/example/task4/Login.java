package com.example.task4;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Objects;

@Entity
@Getter
@Table(name = "logins")
@AllArgsConstructor
@NoArgsConstructor
public class Login {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "access_date")
    @Setter
    private Date accessDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @Setter
    private User user;

    @Column(nullable = false)
    @Setter
    private String application;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Login login)) return false;

        if (!Objects.equals(id, login.id)) return false;
        if (!Objects.equals(accessDate, login.accessDate)) return false;
        if (!Objects.equals(user, login.user)) return false;
        return Objects.equals(application, login.application);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (accessDate != null ? accessDate.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (application != null ? application.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Login{" +
                "id=" + id +
                ", accessDate=" + accessDate +
                ", user=" + user +
                ", application='" + application + '\'' +
                '}';
    }
}
