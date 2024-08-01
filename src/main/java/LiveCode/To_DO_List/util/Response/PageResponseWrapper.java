package LiveCode.To_DO_List.util.Response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
public class PageResponseWrapper<T>{
    private List<T> items;
    private Long totalElements;
    private Integer totalPages;
    private Integer size;
    private Integer page;
    public PageResponseWrapper(Page<T> page){
        this.items = page.getContent();
        this.totalElements = page.getTotalElements();
        this.totalPages = page.getTotalPages();
        this.size = page.getSize();
        this.page = page.getNumber();
    }
}
