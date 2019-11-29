package dashboard.generics;

import com.fasterxml.jackson.annotation.JsonProperty;
import dashboard.enums.EntityStatus;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.List;

public class MultipleExecute<T, E> {
    // status
    @Enumerated(EnumType.STRING)
    private E status;
    // The list data
    private List<T> listId;

    public E getStatus() {
        return status;
    }

    public void setStatus(E status) {
        this.status = status;
    }

    public List<T> getListId() {
        return listId;
    }

    public void setListId(List<T> listId) {
        this.listId = listId;
    }
}
