package ibf.tfip.final_project.repository;

import ibf.tfip.final_project.model.ExpiryItem;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.time.LocalDate;
import java.util.List;

public interface ExpiryItemRepository extends MongoRepository<ExpiryItem, String> {
    List<ExpiryItem> findByUserId(int userId);
    List<ExpiryItem> findByUserIdAndExpiryDateBefore(int userId, LocalDate date);
}