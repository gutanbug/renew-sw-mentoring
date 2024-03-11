package com.renew.sw.mentoring.domain.post.repository.spec;

import com.renew.sw.mentoring.domain.post.model.entity.Post;
import org.springframework.data.jpa.domain.Specification;

public class PostSpec {
    public static <T extends Post> Specification<T> withTitleOrBody(String keyword) {
        if (keyword == null || keyword.equals("null")) {
            return Specification.where(null);
        }

        String pattern = "%" + keyword + "%";

        return (root, query, builder) ->
                builder.or(
                        builder.like(root.get("title"), pattern),
                        builder.like(root.get("body"), pattern)
                );
    }
}
