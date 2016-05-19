package ca.uqam.projet.controllers;

import ca.uqam.projet.repositories.CitationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class TaskController {

  @Autowired CitationRepository repository;

}
