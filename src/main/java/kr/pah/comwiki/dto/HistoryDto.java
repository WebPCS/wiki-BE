package kr.pah.comwiki.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class HistoryDto {
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class WriteHistoryDto {
        private String content;
        private Long postId;
    }
}
