package dashboard.entities.base;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import dashboard.constants.PatternConstants;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@MappedSuperclass
public class BaseEntity {

    @Column(name = "create_date", updatable = false)
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Date createDate;

    @Column(name = "update_date")
    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Date updateDate;

    @JsonIgnore
    @Column(name = "delete_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deleteDate;

    public Date getCreateDate() {
        return createDate;
    }

//    public String getCreateDateString() {
//        return convertToStringDateTime(createDate);
//    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

//    @JsonProperty("updateDate")
//    @JsonInclude(JsonInclude.Include.NON_NULL)
//    public String getUpdateDateString() {
//        return convertToStringDateTime(updateDate);
//    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Date getDeleteDate() {
        return deleteDate;
    }
//
//    @JsonProperty("deleteDate")
//    @JsonInclude(JsonInclude.Include.NON_NULL)
//    public String getDeleteDateString() {
//        return deleteDate != null ? convertToStringDateTime(deleteDate) : null;
//    }

    public void setDeleteDate(Date deleteDate) {
        this.deleteDate = deleteDate;
    }

//    private String convertToStringDateTime(Date date) {
//        return new SimpleDateFormat(PatternConstants.DATETIME_VIETNAM).format(createDate);
//    }
}
