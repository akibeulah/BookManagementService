package com.beulah.bookmanagementservice.enums;

import lombok.Getter;

public enum BookType {
    HARD_COVER("Hard Cover", "This is a hard cover book"),
    SOFT_COVER("Soft Cover", "This is a soft cover book"),
    AUDIO_BOOK("Audio Book", "This is an audio book"),
    E_BOOK("eBook", "This is an eBook");

    @Getter
    private String name;
    @Getter
    private String description;

    BookType(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
