package com.sepp.nattymals.service;

import com.sepp.nattymals.domain.Message;
import com.sepp.nattymals.repository.MessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Message.
 */
@Service
@Transactional
public class MessageService {

    private final Logger log = LoggerFactory.getLogger(MessageService.class);
    
    @Inject
    private MessageRepository messageRepository;
    
    /**
     * Save a message.
     * @return the persisted entity
     */
    public Message save(Message message) {
        log.debug("Request to save Message : {}", message);
        Message result = messageRepository.save(message);
        return result;
    }

    /**
     *  get all the messages.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Message> findAll(Pageable pageable) {
        log.debug("Request to get all Messages");
        Page<Message> result = messageRepository.findAll(pageable); 
        return result;
    }

    /**
     *  get one message by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Message findOne(Long id) {
        log.debug("Request to get Message : {}", id);
        Message message = messageRepository.findOne(id);
        return message;
    }

    /**
     *  delete the  message by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete Message : {}", id);
        messageRepository.delete(id);
    }
}
