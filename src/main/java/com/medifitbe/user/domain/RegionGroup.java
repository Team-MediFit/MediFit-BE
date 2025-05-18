package com.medifitbe.user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RegionGroup {
    private RegionMajor major;
    private List<String> minors;
}