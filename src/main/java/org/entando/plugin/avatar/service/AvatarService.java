package org.entando.plugin.avatar.service;

import org.entando.plugin.avatar.domain.Avatar;
import org.entando.plugin.avatar.repository.AvatarRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Avatar.
 */
@Service
@Transactional
public class AvatarService {

    private final Logger log = LoggerFactory.getLogger(AvatarService.class);

    private final AvatarRepository avatarRepository;

    public AvatarService(AvatarRepository avatarRepository) {
        this.avatarRepository = avatarRepository;
    }

    /**
     * Save a avatar.
     *
     * @param avatar the entity to save
     * @return the persisted entity
     */
    public Avatar save(Avatar avatar) {
        log.debug("Request to save Avatar : {}", avatar);
        return avatarRepository.save(avatar);
    }

    /**
     * Get all the avatars.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Avatar> findAll() {
        log.debug("Request to get all Avatars");
        return avatarRepository.findAll();
    }


    /**
     * Get one avatar by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Avatar> findOne(Long id) {
        log.debug("Request to get Avatar : {}", id);
        return avatarRepository.findById(id);
    }

    /**
     * Delete the avatar by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Avatar : {}", id);
        avatarRepository.deleteById(id);
    }
}
