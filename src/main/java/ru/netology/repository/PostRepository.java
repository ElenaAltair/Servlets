package ru.netology.repository;

import ru.netology.model.Post;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


// Stub
public class PostRepository {

    private static List<Post> posts = new CopyOnWriteArrayList<>();
    private static Long index = 10L;

    public PostRepository() {
        posts.add(new Post(1, "post 1"));
        posts.add(new Post(2, "post 2"));
        posts.add(new Post(3, "post 3"));
        posts.add(new Post(4, "post 4"));
        posts.add(new Post(5, "post 5"));
    }

    public List<Post> all() {

        return posts; //Collections.emptyList();
    }

    public Post getById(long id) {
        for (Post post : posts) {
            if (post.getId() == id) {
                return post;
            }
        }
        return null;
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
            post.setId(index + 1);
            posts.add(post);
        } else {
            int help = 0;
            for (Post postF : posts) {
                if (postF.getId() == post.getId()) {
                    help = 1;
                    postF.setContent(post.getContent());
                }
            }
            if (help == 0) {
                post.setId(index + 1);
                posts.add(post);
            }
        }
        return post;
    }

    public void removeById(long id) {
        posts.removeIf(post -> post.getId() == id);
    }
}