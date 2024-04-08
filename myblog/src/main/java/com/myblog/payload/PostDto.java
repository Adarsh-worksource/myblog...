package com.myblog.payload;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostDto {

    private Long id;

    @NotEmpty
    @Size(min = 2, message = "post title should be atleast 2 characters")
    private String title;

    @NotEmpty
    @Size(min = 4, message = "post description should be atleast 4 characters")
    private String description;

    @NotEmpty
    @Size(min = 5, message = "post content should be atleast 5 characters")
    private String content;
}