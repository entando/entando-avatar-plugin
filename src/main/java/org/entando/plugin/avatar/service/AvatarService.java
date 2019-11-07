package org.entando.plugin.avatar.service;

import org.entando.plugin.avatar.domain.Avatar;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Avatar}.
 */
public interface AvatarService {

    /**
     * Save a avatar.
     *
     * @param avatar the entity to save.
     * @return the persisted entity.
     */
    Avatar save(Avatar avatar);

    /**
     * Get all the avatars.
     *
     * @return the list of entities.
     */
    List<Avatar> findAll();


    /**
     * Get the "id" avatar.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Avatar> findOne(Long id);

    /**
     * Delete the "id" avatar.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);


    public Optional<Avatar> findByUsername(String username);

    public void deleteByUsername(String username);

    public Avatar upload(String username, MultipartFile image) throws IOException;
}
