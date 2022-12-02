package com.bookapp.app.controller;

import com.bookapp.app.dto.requests.CreatePublisherRequest;
import com.bookapp.app.dto.requests.UpdatePublisherRequest;
import com.bookapp.app.dto.responses.GetAllPublisherResponse;
import com.bookapp.app.dto.responses.GetPublisherById;
import com.bookapp.app.service.PublisherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/publishers")
@RequiredArgsConstructor
public class PublisherController {
    private final PublisherService publisherService;

    @GetMapping
    public ResponseEntity<List<GetAllPublisherResponse>> getAllPublisher() {
        return new ResponseEntity<>(publisherService.getAllPublisher(), OK);
    }

    @PostMapping
    public ResponseEntity<CreatePublisherRequest> createPublisher(@RequestBody CreatePublisherRequest createPublisherRequest) {
        return new ResponseEntity<>(publisherService.createPublisher(createPublisherRequest), CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdatePublisherRequest> updatePublisher(@PathVariable Integer id,
                                                                  @RequestBody UpdatePublisherRequest updatePublisherRequest) {
        return new ResponseEntity<>(publisherService.updatePublisher(id, updatePublisherRequest), CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePublisher(@PathVariable Integer id) {
        publisherService.deletePublisher(id);
        return new ResponseEntity<>(OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetPublisherById> getPublisherById(@PathVariable Integer id) {
        return new ResponseEntity<>(publisherService.getPublisherById(id), OK);
    }
}
