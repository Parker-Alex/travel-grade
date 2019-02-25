package com.leo.pojo;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "leo_travel_system_search")
public class TravelSearch {
    @Id
    private String id;

    private String content;

}