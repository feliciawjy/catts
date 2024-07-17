package ibf.tfip.final_project.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "expiry_items")
public class ExpiryItem {
    @Id
    private String id;
    private int userId;
    private String itemName;
    private String imageUrl;
    private LocalDate expiryDate;
    private String googleCalendarEventId;
}
