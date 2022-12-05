package com.bookapp.app.service;

import com.bookapp.app.dto.requests.CreatePublisherRequest;
import com.bookapp.app.dto.requests.UpdatePublisherRequest;
import com.bookapp.app.dto.responses.GetAllPublisherResponse;
import com.bookapp.app.dto.responses.GetPublisherById;
import com.bookapp.app.exeptions.CustomExeption;
import com.bookapp.app.exeptions.NotValidExeption;
import com.bookapp.app.model.Publisher;
import com.bookapp.app.repository.PublisherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PublisherService {
    private final PublisherRepository publisherRepository;

    public List<GetAllPublisherResponse> getAllPublisher() {
        List<Publisher> publisherList = publisherRepository.findAll();
        return publisherList.stream()
                .map(publisher -> {
                    return GetAllPublisherResponse.builder()
                            .id(publisher.getId())
                            .name(publisher.getName())
                            .build();
                })
                .collect(Collectors.toList());
    }

    public CreatePublisherRequest createPublisher(CreatePublisherRequest createPublisherRequest) {
        String publisherName = createPublisherRequest.getName();
        if (publisherName == null || publisherName.trim().isEmpty()){
            throw new NotValidExeption("Publisher Name not valid!");
        }
        if (publisherRepository.existsByNameContainingIgnoreCase(publisherName)){
            throw new NotValidExeption("Publisher name already exists!");
        }
        Publisher createdPublisher = Publisher.builder()
                .name(createPublisherRequest.getName())
                .build();
        publisherRepository.save(createdPublisher);
        return createPublisherRequest;
    }

    public UpdatePublisherRequest updatePublisher(Integer id, UpdatePublisherRequest updatePublisherRequest) {
        String updateName = updatePublisherRequest.getName();
        if (updateName == null || updateName.trim().isEmpty()){
            throw new NotValidExeption("Publisher Name not valid!");
        }
        if (publisherRepository.existsByNameContainingIgnoreCase(updateName)){
            throw new NotValidExeption("Publisher name already exists!");
        }
        Publisher updatedPublisher = publisherRepository.findById(id)
                .orElseThrow(() -> new CustomExeption("Publisher Id not found!"));
        updatedPublisher.setName(updatePublisherRequest.getName());
        publisherRepository.save(updatedPublisher);
        return updatePublisherRequest;
    }

    public void deletePublisher(Integer id) {
        Publisher publisher = publisherRepository.findById(id)
                .orElseThrow(() -> new CustomExeption("Publisher Id not found!"));
        publisherRepository.delete(publisher);
    }

    public GetPublisherById getPublisherById(Integer id) {
        Publisher publisher = publisherRepository.findById(id)
                .orElseThrow(() -> new CustomExeption("Publisher Id not found!"));
        return GetPublisherById.builder()
                .id(publisher.getId())
                .name(publisher.getName())
                .books(publisher.getBookList())
                .build();
    }

    protected Publisher findPublisherByName(String name){
        return publisherRepository.findByName(name)
                .orElseThrow(()-> new CustomExeption("Publisher name not found!"));
    }









}
