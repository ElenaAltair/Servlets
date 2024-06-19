package ru.netology.repository;

import ru.netology.model.Post;

import java.util.Map;



public interface PostRepository {
    //static Map<Long, Post> posts = new ConcurrentHashMap<>();
    //static AtomicLong index = new AtomicLong(10L);

    Map<Long, Post> all();

    Post getById(long id);

    Post save(Post post);

    void removeById(long id);
}