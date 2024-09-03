package io.getarrays.contactapi.resource;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.getarrays.contactapi.Constant.Constant;
import io.getarrays.contactapi.Service.ContactService;
import io.getarrays.contactapi.domain.Contact;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/contacts")
public class ContactResource {
	
	@Autowired
	private final ContactService contactService;

	public ContactResource(ContactService contactService) {
		this.contactService = contactService;
	}
	
	@PostMapping
	public ResponseEntity<Contact> createContact(@RequestBody Contact contact){
		return ResponseEntity.created(URI.create("/contacts/userID")).body(contactService.createContact(contact));
	}
	
	@GetMapping
	public ResponseEntity<Page<Contact>> getContact(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value ="size", defaultValue = "10") int size){
		return ResponseEntity.ok().body(contactService.getAllContacts(page, size));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Contact> getContacts(@PathVariable(value = "id") String id){
		return ResponseEntity.ok().body(contactService.getContact(id));
	}
	
	@PutMapping("/photo")
	public ResponseEntity<String> uploadPhoto(@RequestParam("id") String id, @RequestParam("file") MultipartFile file){
		return ResponseEntity.ok().body(contactService.uploadPhoto(id, file));
	}
	
	 @GetMapping(path = "/image/{filename}", produces = { MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE })
	    public byte[] getPhoto(@PathVariable("filename") String filename) throws IOException {
	        return Files.readAllBytes(Paths.get(Constant.PHOTO_DIRECTORY + filename));
	    }
	
	
}
