package cz.vlasimsky.springboot.statisticsMicroservice.entities;

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
@Table(name = "latest_statistics")
@AllArgsConstructor
@NoArgsConstructor
public class Statistics {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter @Setter
    private Integer id;

    @Column(name = "DESCRIPTION")
    @NotNull @NotBlank @NotEmpty
    @Getter @Setter
    private String description;

    @Column(name = "DATE")
//    @NotNull @NotBlank @NotEmpty
    @Getter @Setter
    private Date date;

    @Column(name = "EMAIL")
    @NotNull @NotBlank @NotEmpty
    @Getter @Setter
    private String email;

    @PrePersist
    void getTimeOperation() {
        this.date = new Date();
    }

}
