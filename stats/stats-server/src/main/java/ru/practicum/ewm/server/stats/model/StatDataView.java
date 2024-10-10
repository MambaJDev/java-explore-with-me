package ru.practicum.ewm.server.stats.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StatDataView {
    private String app;
    private String uri;
    private Long hits;
}