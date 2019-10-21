package dashboard.commons;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class ActionUtils {
    public static Pageable preparePageable(String sort, Integer page, Integer size) {
        String[] splitData = sort.split(",");
        Sort sortable = Sort.by("createDate").ascending();
        if (splitData.length == 2) {
            sortable = splitData[1].equals("ASC") ? Sort.by(splitData[0]).ascending() : Sort.by(splitData[0]).descending();
        }
        page = 1 >= page ? 0 : (page - 1);
        return PageRequest.of(page, size, sortable);
    }
}
