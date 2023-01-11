package com.game.service;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.repository.PlayerRepository;
import com.game.util.PlayerValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PlayerServiceImpl implements PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private PlayerValidator playerValidator;


    @Override
    public List<Player> getWithFilter(Pageable pageable,
                                      String name, String title,
                                      Race race, Profession profession,
                                      Date after, Date before,
                                      Boolean banned,
                                      Integer minExp, Integer maxExp,
                                      Integer minLvl, Integer maxLvl) {
        return playerRepository.findWithFilter(pageable, name, title, race, profession, after, before, banned,
                minExp, maxExp, minLvl, maxLvl);
    }

    @Override
    public void create(Player player) {
        playerValidator.validate(player);

        calculateLevelAndUntil(player);

        playerRepository.save(player);
    }

    private void calculateLevelAndUntil(Player player) {
        int level = (int) Math.floor((Math.sqrt(2500 + (200 * player.getExperience())) - 50) / 100);
        player.setLevel(level);

        int untilNextLevel = 50 * (player.getLevel() + 1) * (player.getLevel() + 2) - player.getExperience();
        player.setUntilNextLevel(untilNextLevel);
    }

    @Override
    public Player getPlayer(Long id) {
        playerValidator.validateId(id);

        Optional<Player> player = playerRepository.findById(id);

        if (player.isPresent()) {
            return player.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Player update(Long id, Player playerUpdate) {
        playerValidator.validateId(id);

        Optional<Player> playerFromDbOptional = playerRepository.findById(id);

        if (!playerFromDbOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        Player playerFromDB = playerFromDbOptional.get();

        if (playerUpdate.getName() != null) {
            playerFromDB.setName(playerUpdate.getName());
        }

        if (playerUpdate.getTitle() != null) {
            playerFromDB.setTitle(playerUpdate.getTitle());
        }

        if (playerUpdate.getRace() != null) {
            playerFromDB.setRace(playerUpdate.getRace());
        }

        if (playerUpdate.getProfession() != null) {
            playerFromDB.setProfession(playerUpdate.getProfession());
        }

        if (playerUpdate.getBirthday() != null) {
            playerFromDB.setBirthday(playerUpdate.getBirthday());
        }

        if (playerUpdate.getBanned() != null) {
            playerFromDB.setBanned(playerUpdate.getBanned());
        }

        if (playerUpdate.getExperience() != null) {
            playerFromDB.setExperience(playerUpdate.getExperience());

            calculateLevelAndUntil(playerFromDB);
        }

        playerValidator.validate(playerFromDB);

        playerRepository.save(playerFromDB);

        return playerFromDB;
    }

    @Override
    public void delete(Long id) {
        playerValidator.validateId(id);

        if (playerRepository.findById(id).isPresent()) {
            playerRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
