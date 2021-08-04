package com.amr.project.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "shop")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiIgnore
@Builder
public class Shop {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @Column
    private String email;

    @Column
    private String phone;

    @Column
    private String description;

    @OneToOne(fetch = FetchType.LAZY)
    private Country location;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "shop_item",
            joinColumns = {@JoinColumn(name = "shop_id")},
            inverseJoinColumns = {@JoinColumn(name = "item_id")})
    private List<Item> items;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "shop_review",
            joinColumns = {@JoinColumn(name = "shop_id")},
            inverseJoinColumns = {@JoinColumn(name = "review_id")})
    private List<Review> reviews;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Image logo;

    @Column
    private int count;

    @Column
    private double rating;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST})
    @JoinTable(name = "user_shop",
            joinColumns = {@JoinColumn(name = "shop_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")})
    private User user;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinTable(name = "shop_discount",
            joinColumns = {@JoinColumn(name = "shop_id")},
            inverseJoinColumns = {@JoinColumn(name = "discount_id")})
    private Collection<Discount> discounts;

    @Column(name = "is_moderated")
    private boolean isModerated = false;

    @Column(name = "is_moderate_accept")
    private boolean isModerateAccept = false;

    @Column(name = "moderated_reject_reason")
    private String moderatedRejectReason;

    //поле для подтверждения модератором
    @Column
    private int activity;

    @Transient
    private MultipartFile file;

}
