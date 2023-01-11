package com.game.repository;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

    @Query("SELECT p FROM Player p " +
            "WHERE (p.name LIKE %:name%)" +
            "AND (p.title LIKE %:title%)" +
            "AND (:race IS NULL OR p.race = :race)" +
            "AND (:profession IS NULL OR p.profession = :profession)" +
            "AND (:banned IS NULL OR p.banned = :banned)" +
            "AND (p.birthday BETWEEN :after AND :before)" +
            "AND (p.experience BETWEEN :minExp AND :maxExp)" +
            "AND (p.level BETWEEN :minLvl AND :maxLvl)")
    List<Player> findWithFilter(Pageable pageable,
                                @Param("name") String name, @Param("title") String title,
                                @Param("race") Race race, @Param("profession") Profession profession,
                                @Param("after") Date after, @Param("before") Date before,
                                @Param("banned") Boolean banned,
                                @Param("minExp") Integer minExp, @Param("maxExp") Integer maxExp,
                                @Param("minLvl") Integer minLvl, @Param("maxLvl") Integer maxLvl);

}
