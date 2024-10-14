package com.betrybe.alexandria.controllers;

import com.betrybe.alexandria.controllers.dto.AuthorDTO;
import com.betrybe.alexandria.controllers.dto.PublisherDTO;
import com.betrybe.alexandria.controllers.dto.ResponseDTO;
import com.betrybe.alexandria.models.entities.Author;
import com.betrybe.alexandria.models.entities.Publisher;
import com.betrybe.alexandria.services.PublisherService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/publishers")
public class PublisherController {
  private final PublisherService publisherService;

  @Autowired
  public PublisherController(PublisherService publisherService){
    this.publisherService = publisherService;
  }

  @PostMapping()
  public ResponseEntity<ResponseDTO<Publisher>> createPublisher(@RequestBody PublisherDTO publisherDTO){
    Publisher newPublisher = publisherService.insertPublisher(publisherDTO.toPublisher());
    ResponseDTO<Publisher> responseDTO = new ResponseDTO<>("Publisher criado com sucesso!", newPublisher);
    return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
  }

  @PutMapping("/{publisherId}")
  public ResponseEntity<ResponseDTO<Publisher>> updatePublisher(@PathVariable Long publisherId, @RequestBody PublisherDTO publisherDTO){
    Optional<Publisher> optionalPublisher = PublisherService.updatePublisher(publisherId,publisherDTO.toPublisher());
    if (optionalPublisher.isEmpty()){
      ResponseDTO<Publisher> responseDTO = new ResponseDTO<>( String.format("Não foi encontrado o publisher de ID %d", publisherId), null);
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
    }
    ResponseDTO<Publisher> responseDTO = new ResponseDTO<>("Publisher atualizado com sucesso!",
        optionalPublisher.get());

    return ResponseEntity.ok(responseDTO);
  }

  @GetMapping("/{publisherId}")
  public ResponseEntity<ResponseDTO<Publisher>> getPublisherById(@PathVariable Long publisherId) {
    Optional<Publisher> optionalPublisher = publisherService.getPublisherById(publisherId);
    if (optionalPublisher.isEmpty()) {
      ResponseDTO<Publisher> responseDTO = new ResponseDTO<>(
          String.format("Não foi encontrado o publisher de ID %d", publisherId), null);
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
    }
    ResponseDTO<Publisher> responseDTO = new ResponseDTO<>("Publisher encontrado com sucesso!",
        optionalPublisher.get());

    return ResponseEntity.ok(responseDTO);
  }

  @GetMapping()
  public List<PublisherDTO> getAllPublishers() {
    List<Publisher> allPublisher= publisherService.getAllPublishers();
    return allPublisher.stream()
        .map((publisher) -> new PublisherDTO(publisher.getId(), publisher.getName(), publisher.getAddress(), publisher.getBooks()))
        .collect(Collectors.toList());
  }

}
