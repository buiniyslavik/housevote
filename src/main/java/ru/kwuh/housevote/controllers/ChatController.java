package ru.kwuh.housevote.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import ru.kwuh.housevote.entities.ChatMessage;
import ru.kwuh.housevote.entities.House;
import ru.kwuh.housevote.repository.ChatRepository;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/chat", produces = "application/json")
public class ChatController {
    @Autowired
    ChatRepository chatRepository;

    @GetMapping(value = {"/all", "/all/{page}"})
    public Iterable<ChatMessage> showAllMessage(@PathVariable(name = "page", required = false) Integer pageNumber){
        PageRequest page;
        if (pageNumber != null)
            page = PageRequest.of(pageNumber, 25, Sort.by("postingDate").ascending());
        else
            page = PageRequest.of(0, 25, Sort.by("postingDate").ascending());
        return chatRepository.findAll(page).getContent();
    }

    @PostMapping(path = "/add", consumes = "application/json;charset=UTF-8")
    public ChatMessage addMessage(@RequestBody @Valid ChatMessage chatMessage) {
        return chatRepository.save(chatMessage);
    }
}
