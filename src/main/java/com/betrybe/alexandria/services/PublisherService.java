package com.betrybe.alexandria.services;

import com.betrybe.alexandria.models.entities.Publisher;
import com.betrybe.alexandria.models.repositories.PublisherRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PublisherService {
  private static PublisherRepository publisherRepository;

  @Autowired
  public PublisherService(PublisherRepository publisherRepository){
    this.publisherRepository = publisherRepository;
  }

  public Publisher insertPublisher(Publisher publisher) {
    return this.publisherRepository.save(publisher);
  }

  public static Optional<Publisher> updatePublisher(Long id, Publisher publisher) {
    Optional<Publisher> optionalPublisher = publisherRepository.findById(id);
    if(optionalPublisher.isPresent()){
      Publisher publisherFromDb = optionalPublisher.get();
      publisherFromDb.setName(publisher.getName());
      publisherFromDb.setAddress(publisher.getAddress());

      Publisher updatedPublisher = publisherRepository.save(publisherFromDb);
      return Optional.of(updatedPublisher);
    }

    return optionalPublisher;
  }

  public Optional<Publisher> deletePublisher(Long id) {
    Optional<Publisher> optionalPublisher = publisherRepository.findById(id);
    if(optionalPublisher.isPresent()){
      publisherRepository.deleteById(id);
    }

    return optionalPublisher;
  }

  public Optional<Publisher> getPublisherById (Long id) {
    return publisherRepository.findById(id);
  }

  public List<Publisher> getAllPublishers(){
    return publisherRepository.findAll();
  }

}
