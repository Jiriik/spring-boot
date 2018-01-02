package cz.vlasimsky.springboot.todoMicroservice.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "todos")
@AllArgsConstructor
@NoArgsConstructor
public class Todo {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    @Getter @Setter
    private Integer id;

    @Column(name = "DESCRIPTION")
    @NotNull @NotEmpty @NotBlank
    @Getter @Setter
    private String description;

    @Column(name = "DATE")
    @Getter @Setter
    private Date date;

    @Column(name = "PRIORITY")
    @NotNull @NotEmpty @NotBlank
    @Getter @Setter
    private String priority;

    @Column(name = "FK_USER")
    @NotNull @NotEmpty @NotBlank
    @Getter @Setter
    private String fkUser;

    @PrePersist
    void getTimeOperation() {
        this.date = new Date();
    }
}
