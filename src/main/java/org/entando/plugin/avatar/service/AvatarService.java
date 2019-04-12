package org.entando.plugin.avatar.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import org.entando.plugin.avatar.domain.Avatar;
import org.entando.plugin.avatar.repository.AvatarRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import org.entando.plugin.avatar.config.AvatarConfig;
import org.entando.plugin.avatar.config.AvatarConfigManager;
import org.entando.plugin.avatar.web.rest.errors.BadRequestAlertException;
import org.springframework.web.multipart.MultipartFile;

/**
 * Service Implementation for managing Avatar.
 */
@Service
@Transactional
public class AvatarService {

    private static final String ENTITY_NAME = "avatarPluginAvatar";
    private static final Logger log = LoggerFactory.getLogger(AvatarService.class);

    private final AvatarRepository avatarRepository;
    private final AvatarConfigManager avatarConfigManager;

    public AvatarService(AvatarRepository avatarRepository, AvatarConfigManager avatarConfigManager) {
        this.avatarRepository = avatarRepository;
        this.avatarConfigManager = avatarConfigManager;
    }

    public Avatar upload(String username, MultipartFile image) throws IOException {

        AvatarConfig avatarConfig = avatarConfigManager.getAvatarConfig();

        String fileName = image.getOriginalFilename();
        String extension = fileName.substring(fileName.lastIndexOf('.') + 1).trim();

        if (!avatarConfig.getImageTypes().contains(extension)) {
            throw new BadRequestAlertException("Invalid image type: " + extension, ENTITY_NAME, "invalidtype");
        }

        if (image.getSize() > avatarConfig.getImageMaxSize() * 1024) {
            throw new BadRequestAlertException("Image size too big. Max allowed " + avatarConfig.getImageMaxSize() + " MB",
                    ENTITY_NAME, "toobig");
        }

        Avatar avatar = new Avatar();

        try (InputStream in = image.getInputStream()) {
            BufferedImage bufImg = ImageIO.read(in);
            if (bufImg.getWidth() != avatarConfig.getImageWidth()
                    || bufImg.getHeight() != avatarConfig.getImageHeight()) {
                throw new BadRequestAlertException("Wrong image dimensions", ENTITY_NAME, "invaliddimensions");
            }

            try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
                ImageIO.write(bufImg, extension, os);
                avatar.setImage(os.toByteArray());
            }
        }

        avatar.setImageContentType(image.getContentType());
        avatar.setUsername(username);

        return avatarRepository.save(avatar);
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
    
    @Transactional(readOnly = true)
    public Optional<Avatar> findByUsername(String username) {
        log.debug("Request to get Avatar of {}", username);
        return avatarRepository.findByUsername(username);
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

    /**
     * Delete the avatar by username.
     *
     * @param id the id of the entity
     */
    public void deleteByUsername(String username) {
        log.debug("Request to delete Avatar of {}", username);
        avatarRepository.deleteByUsername(username);
    }
}
