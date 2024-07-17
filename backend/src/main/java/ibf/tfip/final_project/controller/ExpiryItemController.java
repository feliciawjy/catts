package ibf.tfip.final_project.controller;

import ibf.tfip.final_project.model.ExpiryItem;
import ibf.tfip.final_project.service.ExpiryItemService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/expiry-items")
public class ExpiryItemController {
    private final ExpiryItemService expiryItemService;

    public ExpiryItemController(ExpiryItemService expiryItemService) {
        this.expiryItemService = expiryItemService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ExpiryItem> addExpiryItem(
            @RequestParam("image") MultipartFile image,
            @RequestParam("itemName") String itemName,
            @RequestParam("userId") int userId,
            @RequestParam("expiryDate") String expiryDate) {
        try {
            ExpiryItem item = new ExpiryItem();
            item.setItemName(itemName);
            item.setUserId(userId);
            item.setExpiryDate(LocalDate.parse(expiryDate));

            ExpiryItem savedItem = expiryItemService.addExpiryItem(item, image);
            return ResponseEntity.ok(savedItem);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ExpiryItem>> getExpiryItemsForUser(@PathVariable int userId) {
        List<ExpiryItem> items = expiryItemService.getExpiryItemsForUser(userId);
        return ResponseEntity.ok(items);
    }

    @GetMapping("/user/{userId}/expiring")
    public ResponseEntity<List<ExpiryItem>> getExpiringItemsForUser(@PathVariable int userId,
            @RequestParam(defaultValue = "7") int daysLeft) {
        List<ExpiryItem> items = expiryItemService.getExpiringItemsForUser(userId, daysLeft);
        return ResponseEntity.ok(items);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpiryItem(@PathVariable String id) {
        boolean deleted = expiryItemService.deleteExpiryItem(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}