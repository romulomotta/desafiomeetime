package com.lobato.desafiomeetime.repository.internal;

import com.lobato.desafiomeetime.repository.internal.entity.ContactEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<ContactEntity, Long> {
}
