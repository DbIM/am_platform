package com.amr.project.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Collection;

@Entity
@Table(name = "favorite")
@Data
@NoArgsConstructor
@ToString(exclude = "user")
public class Favorite {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "favorite_shop",
            joinColumns = {@JoinColumn(name = "favorite_id")},
            inverseJoinColumns = {@JoinColumn(name = "shop_id")})
    private Collection<Shop> shops;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "favorite_item",
            joinColumns = {@JoinColumn(name = "favorite_id")},
            inverseJoinColumns = {@JoinColumn(name = "item_id")})
    private Collection<Item> items;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
