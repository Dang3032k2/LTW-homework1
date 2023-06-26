package com.thuvien.btl.Feedback;

import java.sql.SQLException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin
public class FeedbackController {
	private FeedbackDAO fbDAO = new FeedbackDAO();
	@GetMapping("/feedbacks/{idbook}")
	public List<Feedback> getFbsByIdbook(@PathVariable String idbook) {
		List<Feedback> fbs= fbDAO.selectFbsByIdbook(idbook);
		return fbs;
	}
	@PostMapping("/feedback")
	public ResponseEntity<String> addFb(@RequestBody Feedback fb) throws SQLException {
		return fbDAO.insertFb(fb);
	}
}

