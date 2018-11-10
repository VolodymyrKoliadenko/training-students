package org.koliadenko.KeyValue.domain;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String owner;

    private String key;
    private String value;

    public Property(String owner, String key, String value) {
        this.owner = owner;
        this.key = key;
        this.value = value;
    }
}
