package com.example.timetable.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Station {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "station", cascade = CascadeType.ALL)
    private List<TimeTableRelation> timeTable;

    @ManyToMany(cascade = { CascadeType.REFRESH, CascadeType.MERGE })
    private List<Train> trains;
}
