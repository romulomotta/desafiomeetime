package com.lobato.desafiomeetime.repository.internal.entity;

import jakarta.persistence.*;

import java.math.BigInteger;

@Entity
@Table(name = "tb_contact")
public class ContactEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigInteger objectId;

    public ContactEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigInteger getObjectId() {
        return objectId;
    }

    public void setObjectId(BigInteger objectId) {
        this.objectId = objectId;
    }
}
