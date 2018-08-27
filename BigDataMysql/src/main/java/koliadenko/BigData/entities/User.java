/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package koliadenko.BigData.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Data;

/**
 *
 * @author koliadenko
 */
@Entity
@Data
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//auto
    private Integer id;

    private String name;
    private Long hash = 42L; //потом будет что-то более секурное
    private Date resistrationDate;
    private String role;
    private String caption;//подпись

    @OneToMany(mappedBy = "author", orphanRemoval = false)
    private List<Message> messages;

    public User() {
    }

    public User(String name, Date resistrationDate, String role, String caption) {
        this.name = name;
        this.resistrationDate = resistrationDate;
        this.role = role;
        this.caption = caption;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", resistrationDate=" + resistrationDate + ", role=" + role + ", caption=" + caption + '}';
    }

}
