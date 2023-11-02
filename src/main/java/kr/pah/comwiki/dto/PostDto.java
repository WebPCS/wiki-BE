package kr.pah.comwiki.dto;

import lombok.*;

    public class PostDto {
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class WritePostDto {
        private String title;
        private String content;
    }
}
