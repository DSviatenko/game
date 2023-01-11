package com.game.controller;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.service.PlayerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/rest/players")
public class PlayerController {

    @Autowired
    PlayerServiceImpl playerService;

    @GetMapping
    public List<Player> getAll(@RequestParam(required = false, defaultValue = "") String name,
                               @RequestParam(required = false, defaultValue = "") String title,
                               @RequestParam(required = false) Race race,
                               @RequestParam(required = false) Profession profession,
                               @RequestParam(required = false, defaultValue = "0") Long after,
                               @RequestParam(required = false, defaultValue = "32535122400001") Long before,
                               @RequestParam(required = false) Boolean banned,
                               @RequestParam(required = false, defaultValue = "0") Integer minExperience,
                               @RequestParam(required = false, defaultValue = "10000001") Integer maxExperience,
                               @RequestParam(required = false, defaultValue = "0") Integer minLevel,
                               @RequestParam(required = false, defaultValue = "446") Integer maxLevel,
                               @RequestParam(required = false, defaultValue = "ID") PlayerOrder order,
                               @RequestParam(required = false, defaultValue = "0") Integer pageNumber,
                               @RequestParam(required = false, defaultValue = "3") Integer pageSize) {
        return playerService.getWithFilter(PageRequest.of(pageNumber, pageSize, Sort.by(order.getFieldName())),
                name, title, race, profession, new Date(after), new Date(before), banned, minExperience, maxExperience,
                minLevel, maxLevel);
    }


    @GetMapping("/count")
    public Long getCount(@RequestParam(required = false, defaultValue = "") String name,
                         @RequestParam(required = false, defaultValue = "") String title,
                         @RequestParam(required = false) Race race,
                         @RequestParam(required = false) Profession profession,
                         @RequestParam(required = false, defaultValue = "0") Long after,
                         @RequestParam(required = false, defaultValue = "32535122400001") Long before,
                         @RequestParam(required = false) Boolean banned,
                         @RequestParam(required = false, defaultValue = "0") Integer minExperience,
                         @RequestParam(required = false, defaultValue = "10000001") Integer maxExperience,
                         @RequestParam(required = false, defaultValue = "0") Integer minLevel,
                         @RequestParam(required = false, defaultValue = "447") Integer maxLevel) {
        return (long) playerService.getWithFilter(Pageable.unpaged(), name, title, race, profession, new Date(after),
                new Date(before), banned, minExperience, maxExperience, minLevel, maxLevel).size();
    }

    @PostMapping
    public Player createPlayer(@RequestBody Player player) {
        playerService.create(player);
        return player;
    }

    @GetMapping("/{id}")
    public Player getPlayer(@PathVariable Long id) {
        return playerService.getPlayer(id);
    }

    @PostMapping("/{id}")
    public Player updatePlayer(@PathVariable Long id, @RequestBody Player player) {
        return playerService.update(id, player);
    }

    @DeleteMapping("/{id}")
    public void deletePlayer(@PathVariable Long id) {
        playerService.delete(id);
    }
}

