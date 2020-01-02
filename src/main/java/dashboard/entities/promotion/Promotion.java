package dashboard.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import dashboard.entities.base.BaseEntity;
import dashboard.enums.EntityStatus;
import dashboard.enums.PromotionStatus;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "promotion",
        uniqueConstraints = {
            @UniqueConstraint(name = "UK_name", columnNames = "name")
        })
@EntityListeners(AuditingEntityListener.class)
public class Promotion  extends BaseEntity implements Serializable {

    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "promotion_id")
    @JsonProperty("promotionId")
    private Long promotionId;

    @NotBlank(message = "name is not blank")
    @Column(name = "name")
    @JsonProperty("name")
    private String name;

    @Column(name = "percent")
    @JsonProperty("percent")
    private String percent;

    @Column(name = "from_date")
    @JsonProperty("fromDate")
    private Date fromDate;

    @Column(name = "to_date")
    @JsonProperty("toDate")
    private Date todate;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private PromotionStatus status;



}
