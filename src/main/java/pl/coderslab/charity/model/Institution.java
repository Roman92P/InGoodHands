package pl.coderslab.charity.model;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "institutions")
public class Institution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "institution_id")
    private Long id;

    @Column(name = "institution_name")
    private String name;

    @Column(name = "institution_description")
    private String description;

    @OneToMany(mappedBy = "institution",cascade = CascadeType.ALL)
    private Set<Donation>donations;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Institution(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Institution() {
    }
}
