package ibf.tfip.final_project.service;

import ibf.tfip.final_project.model.ExpiryItem;
import ibf.tfip.final_project.repository.ExpiryItemRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.io.IOException;

@Service
public class ExpiryItemService {
    private final ExpiryItemRepository expiryItemRepo;
    private final UploadService uploadService;

    public ExpiryItemService(ExpiryItemRepository expiryItemRepo, UploadService uploadService) {
        this.expiryItemRepo = expiryItemRepo;
        this.uploadService = uploadService;
    }

    public ExpiryItem addExpiryItem(ExpiryItem item, MultipartFile image) throws IOException {
        String imageUrl = uploadService.uploadFile(image);
        item.setImageUrl(imageUrl);
        return expiryItemRepo.save(item);
    }

    public List<ExpiryItem> getExpiryItemsForUser(int userId) {
        return expiryItemRepo.findByUserId(userId);
    }

    public List<ExpiryItem> getExpiringItemsForUser(int userId, int daysLeft) {
        LocalDate thresholdDate = LocalDate.now().plusDays(daysLeft);
        return expiryItemRepo.findByUserIdAndExpiryDateBefore(userId, thresholdDate);
    }

    public boolean deleteExpiryItem(String id) {
        if (expiryItemRepo.existsById(id)) {
            expiryItemRepo.deleteById(id);
            return true;
        }
        return false;
    }

}