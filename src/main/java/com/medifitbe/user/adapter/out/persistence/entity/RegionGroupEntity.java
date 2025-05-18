package com.medifitbe.user.adapter.out.persistence.entity;

import com.medifitbe.user.domain.RegionMajor;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class RegionGroupEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private RegionMajor major;

    @ElementCollection
    @CollectionTable(name = "region_minor_list", joinColumns = @JoinColumn(name = "region_group_id"))
    private List<String> minors;

}
