package com.wdcloud.lms.base.dto;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class PageDefault {

    private Integer pageIndex;

    private Integer pageSize;
}
