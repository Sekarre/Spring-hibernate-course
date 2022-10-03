package com.sekarre.springdataandhibernatecourseinterceptors.listeners;

import com.sekarre.springdataandhibernatecourseinterceptors.services.EncryptionService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.event.spi.PostLoadEvent;
import org.hibernate.event.spi.PostLoadEventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PostLoadListener extends AbstractEncryptionListener implements PostLoadEventListener {

    public PostLoadListener(EncryptionService encryptionService) {
        super(encryptionService);
    }

    @Override
    public void onPostLoad(PostLoadEvent event) {
        log.info("PostLoad");
        this.decrypt(event.getEntity());
    }
}
