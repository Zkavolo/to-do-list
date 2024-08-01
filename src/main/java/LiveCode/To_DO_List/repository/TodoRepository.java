package LiveCode.To_DO_List.repository;

import LiveCode.To_DO_List.model.Todo;
import LiveCode.To_DO_List.util.DTO.TodoDTO.ResponseTodoDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Integer>, JpaSpecificationExecutor<Todo> {
}
