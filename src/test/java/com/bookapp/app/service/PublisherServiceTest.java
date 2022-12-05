package com.bookapp.app.service;

import com.bookapp.app.dto.requests.CreatePublisherRequest;
import com.bookapp.app.dto.requests.UpdatePublisherRequest;
import com.bookapp.app.exeptions.NotValidExeption;
import com.bookapp.app.model.Publisher;
import com.bookapp.app.repository.PublisherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PublisherServiceTest {
    private PublisherService publisherService;
    @Mock
    private PublisherRepository publisherRepository;

    @BeforeEach
    void setUp() {
        publisherService = new PublisherService(publisherRepository);
    }

    @Test
    void whenGetAllPublisherRequest() {
        publisherService.getAllPublisher();
        verify(publisherRepository).findAll();
    }

    @Test
    void whenCreatePublisherValidRequest() {
        CreatePublisherRequest request = new CreatePublisherRequest();
        request.setName("test");

        publisherService.createPublisher(request);

        ArgumentCaptor<Publisher> argumentCaptor = ArgumentCaptor.forClass(Publisher.class);
        verify(publisherRepository).save(argumentCaptor.capture());
        Publisher expected = argumentCaptor.getValue();

        assertEquals(expected.getName(), request.getName());
    }

    @Test
    void whenCreatePublisherNameNullRequest_thenThrowExeption() {
        CreatePublisherRequest request = new CreatePublisherRequest();
        request.setName(null);

        assertThrows(NotValidExeption.class, () -> publisherService.createPublisher(request));

        verify(publisherRepository, never()).save(any());
    }

    @Test
    void whenCreatePublisherNameEXISTSRequest_thenThrowExeption() {
        CreatePublisherRequest request = new CreatePublisherRequest();
        request.setName("test");
        given(publisherRepository.existsByNameContainingIgnoreCase(request.getName())).willReturn(true);

        assertThrows(NotValidExeption.class, () -> publisherService.createPublisher(request));

        verify(publisherRepository, never()).save(any());
    }

    @Test
    void whenCreatePublisherNameEMPTYRequest_thenThrowExeption() {
        CreatePublisherRequest request = new CreatePublisherRequest();
        request.setName("");

        assertThrows(NotValidExeption.class, () -> publisherService.createPublisher(request));

        verify(publisherRepository, never()).save(any());
    }

    @Test
    void whenUpdatePublisherValidRequest() {
        UpdatePublisherRequest request = new UpdatePublisherRequest();
        request.setName("testUpdate");
        Integer id = 1;
        Publisher publisher = new Publisher(id, "test", List.of());

        Mockito.when(publisherRepository.findById(id)).thenReturn(Optional.of(publisher));

        publisherService.updatePublisher(id, request);
        ArgumentCaptor<Publisher> argumentCaptor = ArgumentCaptor.forClass(Publisher.class);
        verify(publisherRepository).save(argumentCaptor.capture());
        Publisher expected = argumentCaptor.getValue();

        assertEquals(expected.getName(), request.getName());
    }

    @Test
    void whenUpdatePublisherNameNullRequest() {
        UpdatePublisherRequest request = new UpdatePublisherRequest();
        request.setName(null);

        assertThrows(NotValidExeption.class, () -> publisherService.updatePublisher(1, request));

        verify(publisherRepository, never()).save(any());
    }

    @Test
    void whenUpdatePublisherNameEXISTSRequest() {
        UpdatePublisherRequest request = new UpdatePublisherRequest();
        request.setName("test");
        given(publisherRepository.existsByNameContainingIgnoreCase(request.getName())).willReturn(true);

        assertThrows(NotValidExeption.class, () -> publisherService.updatePublisher(1, request));

        verify(publisherRepository, never()).save(any());
    }

    @Test
    void whenUpdatePublisherNameEmptyRequest() {
        UpdatePublisherRequest request = new UpdatePublisherRequest();
        request.setName("");

        assertThrows(NotValidExeption.class, () -> publisherService.updatePublisher(1, request));

        verify(publisherRepository, never()).save(any());
    }


    @Test
    void whenDeletePublisherRequest() {
        Integer id = 1;
        Publisher publisher = new Publisher(id, "test", List.of());

        when(publisherRepository.findById(id)).thenReturn(Optional.of(publisher));

        publisherService.deletePublisher(id);

        verify(publisherRepository).delete(publisher);

    }

    @Test
    void whenGetPublisherByIdRequest() {
        Integer id = 1;
        Publisher publisher = new Publisher(id, "test", List.of());

        when(publisherRepository.findById(id)).thenReturn(Optional.of(publisher));

        publisherService.getPublisherById(id);

        verify(publisherRepository).findById(id);
    }
}