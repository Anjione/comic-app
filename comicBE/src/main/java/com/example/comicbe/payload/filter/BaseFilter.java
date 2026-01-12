package com.example.comicbe.payload.filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class BaseFilter implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private List<Long> ids;
}
