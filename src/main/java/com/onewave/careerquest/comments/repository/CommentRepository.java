package com.onewave.careerquest.comments.repository;

import com.onewave.careerquest.comments.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllBySubmissionIdOrderByCreatedAtDesc(Long submissionId);
}
