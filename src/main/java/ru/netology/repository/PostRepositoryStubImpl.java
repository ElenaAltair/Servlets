package ru.netology.repository;

import org.springframework.stereotype.Repository;
import ru.netology.model.Post;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class PostRepositoryStubImpl implements PostRepository{
    static Map<Long, Post> posts = new ConcurrentHashMap<>();
    static AtomicLong index = new AtomicLong(10L);

    public PostRepositoryStubImpl() {
        posts.put(1L, new Post(1, "post 1"));
        posts.put(2L, new Post(2, "post 2"));
        posts.put(3L, new Post(3, "post 3"));
        posts.put(4L, new Post(4, "post 4"));
        posts.put(5L, new Post(5, "post 5"));
    }


    public Map<Long, Post> all() {

        return posts; //Collections.emptyList();
    }


    public Post getById(long id) {
        return posts.get(id);
    }

    /*
    Если от клиента приходит пост с id=0, значит, это создание нового поста.
    Вы сохраняете его в списке и присваиваете ему новый id.
    Достаточно хранить счётчик с целым числом и увеличивать на 1 при создании каждого нового поста.

    Если от клиента приходит пост с id !=0, значит, это сохранение (обновление) существующего поста.
    Вы ищете его в списке по id и обновляете. Продумайте самостоятельно, что вы будете делать,
    если поста с таким id не оказалось: здесь могут быть разные стратегии.
     */

    public Post save(Post post) {
        if (post.getId() == 0) {
            post.setId(index.getAndIncrement());
            posts.put(post.getId(), post);
        } else {
            int help = 0;
            if (posts.containsKey(post.getId())) {
                help = 1;
                posts.put(post.getId(), post);
            }
            if (help == 0) {
                post.setId(index.getAndIncrement());
                posts.put(post.getId(), post);
            }
        }
        return post;
    }


    public void removeById(long id) {
        posts.remove(id);
    }
}
