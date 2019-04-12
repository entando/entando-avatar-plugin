package org.entando.plugin.avatar.repository;

import org.entando.plugin.avatar.domain.Avatar;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Avatar entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AvatarRepository extends JpaRepository<Avatar, Long> {

}
