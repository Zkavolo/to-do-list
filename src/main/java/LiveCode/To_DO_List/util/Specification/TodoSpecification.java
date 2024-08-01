package LiveCode.To_DO_List.util.Specification;

import LiveCode.To_DO_List.model.Todo;
import LiveCode.To_DO_List.util.DTO.TodoDTO.ResponseTodoDTO;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class TodoSpecification {
    public static Specification<Todo> getSpecification(String status) {
        List<Predicate> predicates = new ArrayList<>();

        return (root, query, criteriaBuilder) -> {
            if (status != null && !status.isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("status"), "%" + status + "%"));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
