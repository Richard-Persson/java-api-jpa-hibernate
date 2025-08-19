package com.booleanuk.api.controller;

import com.booleanuk.api.model.Game;
import com.booleanuk.api.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("games")
public class GameController {


    @Autowired
    private GameRepository gameRepository;

    @GetMapping
    public List<Game> getAll() {
        return this.gameRepository.findAll();
    }

    @GetMapping("{id}")
    public Game getById(@PathVariable("id") Integer id)
    {
        return this.gameRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "NOT FOUND"));
    }

    @PostMapping
    public ResponseEntity<Game> create(@RequestBody Game game) {
        Game newGame = new Game(
                game.getTitle(),
                game.getGenre(),
                game.getPublisher(),
                game.getDeveloper(),
                game.getReleaseYear(),
                game.isEarlyAccess());

        return new ResponseEntity<>(this.gameRepository.save(newGame), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Game> updateGame(@PathVariable int id, @RequestBody Game game){

        Game gameToUpdate = gameRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "NOT FOUND"));

        gameToUpdate.setTitle(game.getTitle());
        gameToUpdate.setGenre(game.getGenre());
        gameToUpdate.setPublisher(game.getPublisher());
        gameToUpdate.setDeveloper(game.getDeveloper());
        gameToUpdate.setReleaseYear(game.getReleaseYear());
        gameToUpdate.setEarlyAccess(game.isEarlyAccess());

        return new ResponseEntity<>(gameRepository.save(gameToUpdate),HttpStatus.CREATED);

    }



    @DeleteMapping("{id}")
    public ResponseEntity<Game> deleteGame(@PathVariable int id){

        Game userToDelete = gameRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "NOT FOUND"));

        gameRepository.delete(userToDelete);
        return ResponseEntity.ok(userToDelete);
    }
}
