package com.musicapp.springbootmusicapp.dao;

import com.musicapp.springbootmusicapp.entity.Song;
import com.musicapp.springbootmusicapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    User findByEmail(String email);

    @Query("SELECT s FROM Song s WHERE s.musicUser.username = :username")
    List<Song> findSongsByUserUsername(@Param("username") String username);

    @Query("SELECT DISTINCT u FROM User u " +
            "JOIN Message m ON u.id = m.sender.id OR u.id = m.receiver.id " +
            "WHERE (m.sender.id = :userId OR m.receiver.id = :userId) AND u.id != :userId")
    List<User> findDistinctUsersWithMessages(Long userId);
}
