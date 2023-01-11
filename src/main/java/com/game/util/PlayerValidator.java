package com.game.util;

import com.game.entity.Player;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

@Component
public class PlayerValidator {

    public void validate(Player player) {

        LocalDate date;

        if (player.getBirthday() != null) {
            date = player.getBirthday().toLocalDate();
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        if (player.getName() == null || player.getName() == "" || player.getName().length() > 12 ||
                player.getTitle() == null || player.getTitle().length() > 30 ||
                player.getExperience() == null || player.getExperience() < 0 || player.getExperience() > 10000000 ||
                player.getRace() == null || player.getProfession() == null ||
                player.getBirthday().getTime() < 0 || date.getYear() < 2000 ||
                date.getYear() > 3000) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    public void validateId(Long id) {
        if (id <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

}
