package com.bay.diaryapp.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class PostResource {

    @Autowired
    private PostRepository postRepository;

    @GetMapping("/posts")
    public List<Post> retrieveAllPosts() {
        return postRepository.findAll();
    }

    @GetMapping("/posts/{id}")
    public Post retrievePost(@PathVariable long id) {
        Optional<Post> post = postRepository.findById(id);
        if (!post.isPresent())
            throw new PostNotFoundException("id-" + id);
        return post.get();
    }

    @DeleteMapping("/posts/{id}")
    public void deleteStudent(@PathVariable long id) {
        postRepository.deleteById(id);
    }

    @PostMapping("/posts")
    public ResponseEntity<Object> createPost(@RequestBody Post post) {
        Post savedPost = postRepository.save(post);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedPost.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/students/{id}")
    public ResponseEntity<Object> updateStudent(@RequestBody Post post, @PathVariable long id) {
        Optional<Post> postOptional = postRepository.findById(id);
        if (!postOptional.isPresent())
            return ResponseEntity.notFound().build();
        post.setId(id);
        postRepository.save(post);
        return ResponseEntity.noContent().build();
    }
}
