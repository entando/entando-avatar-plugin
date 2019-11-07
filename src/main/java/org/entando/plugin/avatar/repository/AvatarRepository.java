package org.entando.plugin.avatar.repository;

import java.util.Optional;

import org.entando.plugin.avatar.domain.Avatar;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Avatar entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AvatarRepository extends JpaRepository<Avatar, Long> {

    Optional<Avatar> findByUsername(String username);
    
    void deleteByUsername(String username);
}
