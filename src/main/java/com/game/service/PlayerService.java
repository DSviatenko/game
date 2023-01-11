package com.game.service;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface PlayerService {
    List<Player> getWithFilter(Pageable pageable,
                               String name, String title,
                               Race race, Profession profession,
                               Date after, Date before,
                               Boolean banned,
                               Integer minExp, Integer maxExp,
                               Integer minLvl, Integer maxLvl);

    void create(Player player);

    Player getPlayer(Long id);

    Player update(Long id, Player player);

    void delete(Long id);
}
