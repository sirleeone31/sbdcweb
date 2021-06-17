package com.sbdc.sbdcweb.board.domain.response;

import lombok.Data;

@Data
public class AttachAllDto {
    private Long attachKey;
    private String guidName;
    private Long articleKey;
    private String fileExt;

    public AttachAllDto(Long attachKey, String guidName, Long articleKey, String fileExt) {
        this.attachKey = attachKey;
        this.guidName = guidName;
        this.articleKey = articleKey;
        this.fileExt = fileExt;
    }

}