package dashboard.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

public abstract class BaseEntityDto {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Date createDate;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Date updateDate;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Date deleteDate;

    public BaseEntityDto() {
    }

    public BaseEntityDto(Date createDate, Date updateDate, Date deleteDate) {
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.deleteDate = deleteDate;
    }

    public BaseEntityDto(Date createDate, Date updateDate) {
        this.createDate = createDate;
        this.updateDate = updateDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Date getDeleteDate() {
        return deleteDate;
    }

    public void setDeleteDate(Date deleteDate) {
        this.deleteDate = deleteDate;
    }
}
