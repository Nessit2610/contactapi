package io.getarrays.contactapi.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.getarrays.contactapi.Constant.Constant;
import io.getarrays.contactapi.domain.Contact;
import io.getarrays.contactapi.repo.ContactRepo;
import jakarta.transaction.Transactional;

@Service
@Transactional(rollbackOn = Exception.class)
public class ContactService {

	@Autowired
	private final ContactRepo contactRepo;
	

    public ContactService(ContactRepo contactRepo) {
		super();
		this.contactRepo = contactRepo;
	}


	public Page<Contact> getAllContacts(int page, int size){
    	return contactRepo.findAll(PageRequest.of(page, size, Sort.by("name")));
    }
	
	public Contact getContact(String id) {
		return contactRepo.findById(id).orElseThrow(()-> new RuntimeException("Contact not found"));
	}
	
	public Contact createContact(Contact contact) {
		return contactRepo.save(contact);
	}
	
	public void deleteContact(Contact contact) {
		
	}
	
	public String uploadPhoto(String id, MultipartFile file) {
		Contact contact = getContact(id);
		String photoUrl = photoFunction.apply(id, file);
		contact.setPhotourl(photoUrl);
		contactRepo.save(contact);
		return photoUrl;
	}
	
	private final Function<String, String> fileExtension = filename -> Optional.of(filename).filter(name -> name.contains("."))
            .map(name -> "." + name.substring(filename.lastIndexOf(".") + 1)).orElse(".png");
	
	private final BiFunction<String, MultipartFile, String> photoFunction = (id,image)->{
		String filename  =  id + fileExtension.apply(image.getOriginalFilename());
		try {
			Path fileStorageLocation = Paths.get(Constant.PHOTO_DIRECTORY).toAbsolutePath().normalize();
			if(!Files.exists(fileStorageLocation)) {
				Files.createDirectories(fileStorageLocation);
			}
			Files.copy(image.getInputStream(), fileStorageLocation.resolve(id + fileExtension.apply(image.getOriginalFilename())),StandardCopyOption.REPLACE_EXISTING);
			return ServletUriComponentsBuilder.fromCurrentContextPath().path("/contacts/image/" + filename).toUriString();
		} catch (Exception e) {
			throw new RuntimeException("Unable to save image");
		}
	};
}
