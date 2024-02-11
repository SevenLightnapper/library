package com.my.library.repository;

import com.my.library.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

  List<Comment> findByBookId(Long bookId);

  List<Comment> findByUserId(Long userId);

}
