package model.entities;


import java.io.Serializable;
import java.util.Date;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "reviews")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Review implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long reviewId;

    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
    @ManyToOne
    @ToString.Exclude
    private Customer customer;

    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    @ManyToOne
    @ToString.Exclude
    private Product product;

    @Column(name = "rating")
    private Integer rating;

    @Lob
    @Column(name = "review_text")
    private String reviewText;

    @Column(name = "review_date")
    @Temporal(TemporalType.DATE)
    private Date reviewDate;

    @Column(name = "flagged_as_spam")
    private Boolean flaggedAsSpam;

}