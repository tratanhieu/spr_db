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

    @JsonIgnore
    @Column(name = "create_date", updatable = false)
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    @JsonIgnore
    @Column(name = "update_date")
    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private Date updateDate;

    @JsonIgnore
    @Column(name = "delete_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deleteDate;

    public Date getCreateDate() {
        return createDate;
    }

    @JsonProperty("create_date")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getCreateDateString() {
        return convertToStringDateTime(createDate);
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    @JsonProperty("update_date")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getUpdateDateString() {
        return convertToStringDateTime(updateDate);
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Date getDeleteDate() {
        return deleteDate;
    }

    @JsonProperty("delete_date")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getDeleteDateString() {
        return deleteDate != null ? convertToStringDateTime(deleteDate) : null;
    }

    public void setDeleteDate(Date deleteDate) {
        this.deleteDate = deleteDate;
    }

    private String convertToStringDateTime(Date date) {
        return new SimpleDateFormat(PatternConstants.DATETIME_VIETNAM).format(createDate);
    }
}
