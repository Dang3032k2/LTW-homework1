package com.thuvien.btl.Image;

import java.sql.SQLException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class ImageController {
	private ImageDAO imageDAO = new ImageDAO();
	
	@GetMapping("/images/{idbook}")
	public List<Image> getImages(@PathVariable String idbook) {
		List<Image> images = imageDAO.selectImagesByIdbook(idbook);
		return images;
	}
	
	@PostMapping("/images/{idbook}")
	public ResponseEntity<String> addImage(@RequestBody List<Image> images, @PathVariable String idbook) throws SQLException {
		imageDAO.deleteImageByIdBook(idbook);
		return imageDAO.insertImages(images, idbook);
	}
	
	@PutMapping("/images")
	public ResponseEntity<String> updateImage(@RequestBody List<Image> images) throws SQLException {
		return imageDAO.updateImages(images);
	}
	
	@DeleteMapping("/images")
	public ResponseEntity<String> deleteImage(@RequestBody List<Image> images) throws SQLException {
		return imageDAO.deleteImage(images);
	}
	@DeleteMapping("/images/{idbook}")
	public ResponseEntity<String> deleteImageByIdBook(@PathVariable String idbook) throws SQLException {
		return imageDAO.deleteImageByIdBook(idbook);
	}
}
