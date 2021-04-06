package spiis.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import spiis.server.api.CommentReplyResponse;
import spiis.server.api.CommentRequest;
import spiis.server.api.CommentResponse;
import spiis.server.api.ValueWrapper;
import spiis.server.error.NotFoundException;
import spiis.server.model.Comment;
import spiis.server.model.CommentReply;
import spiis.server.model.Dinner;
import spiis.server.model.User;
import spiis.server.repository.CommentReplyRepository;
import spiis.server.repository.CommentRepository;
import spiis.server.repository.DinnerRepository;
import spiis.server.service.AuthService;
import spiis.server.service.CommentService;

import java.util.List;

@RestController
public class CommentController {

    private final CommentService commentService;
    private final DinnerRepository dinnerRepository;
    private final CommentRepository commentRepository;
    private final CommentReplyRepository replyRepository;
    private final AuthService authService;

    @Autowired
    public CommentController(CommentService commentService, DinnerRepository dinnerRepository,
                             CommentRepository commentRepository, CommentReplyRepository replyRepository,
                             AuthService authService) {
        this.commentService = commentService;
        this.dinnerRepository = dinnerRepository;
        this.commentRepository = commentRepository;
        this.replyRepository = replyRepository;
        this.authService = authService;
    }

    /**
     * Gets all comments on the requested dinner, that the requesting user is allowed to see.
     * @param dinnerId the id of the dinner
     * @param token the token of the requesting user
     * @return a serializable list of info about comments and their replies
     */
    @GetMapping("/dinners/{dinnerId}/comments")
    @Transactional
    public List<CommentResponse> getCommentsOnDinner(@PathVariable("dinnerId") Long dinnerId,
                                                     @RequestHeader("Authorization") String token) {
        User user = authService.getUserForToken(token);
        Dinner dinner = dinnerRepository.findById(dinnerId).orElseThrow(NotFoundException::new);
        return commentService.getAllCommentsForDinnerForUser(dinner, user);
    }

    /**
     * Posts a new comment to a dinner, if the user is allowed to
     * @param dinnerId the id of the dinner
     * @param request the content of the new comment
     * @param token the token for the posting user
     * @return the comment response object for the new comment
     */
    @PostMapping("/dinners/{dinnerId}/comments")
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    public ResponseEntity<CommentResponse> postComment(@PathVariable("dinnerId") Long dinnerId,
                                       @RequestBody CommentRequest request,
                                       @RequestHeader("Authorization") String token) {
        User user = authService.getUserForToken(token);
        Dinner dinner = dinnerRepository.findById(dinnerId).orElseThrow(NotFoundException::new);
        Comment comment = commentService.addCommentToDinner(request, dinner, user);
        CommentResponse response = commentService.makeCommentResponse(comment);
        return RESTControllerUtil.makePOSTResponse(response, "/comments", comment.getId());
    }

    /**
     * Posts a reply to a comment, if the user is allowed to.
     * The user must not be blocked, and must be able to see the comment.
     * @param commentId the id of the comment being replied to
     * @param request the content of the reply
     * @param token the token for the user replying
     * @return a response object for the new reply
     */
    @PostMapping("/comments/{commentId}/replies")
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    public ResponseEntity<CommentReplyResponse> postCommentReply(@PathVariable("commentId") Long commentId,
                                                                 @RequestBody ValueWrapper<String> request,
                                                                 @RequestHeader("Authorization") String token) {
        User user = authService.getUserForToken(token);
        Comment parent = commentRepository.findById(commentId).orElseThrow(NotFoundException::new);
        CommentReply reply = commentService.addReplyToComment(request.getValue(), parent, user);
        CommentReplyResponse response = commentService.makeCommentReplyResponse(reply);
        return RESTControllerUtil.makePOSTResponse(response, "/replies", reply.getId());
    }

    /**
     * Allow a user to edit a comment, if allowed to
     * @param commentId the id of the comment
     * @param request the new content of the comment
     * @param token the token of the user
     */
    @PutMapping("/comments/{commentId}")
    @Transactional
    public void editComment(@PathVariable("commentId") Long commentId,
                            @RequestBody CommentRequest request,
                            @RequestHeader("Authorization") String token) {
        User user = authService.getUserForToken(token);
        Comment comment = commentRepository.findById(commentId).orElseThrow(NotFoundException::new);
        commentService.editComment(request, comment, user);
    }

    /**
     * Allow a user to edit a comment reply, if allowed to
     * @param replyId the id of the comment reply
     * @param request the new content of the reply
     * @param token the token of the user
     */
    @PutMapping("/replies/{replyId}")
    @Transactional
    public void editCommentReply(@PathVariable("replyId") Long replyId,
                            @RequestBody ValueWrapper<String> request,
                            @RequestHeader("Authorization") String token) {
        User user = authService.getUserForToken(token);
        CommentReply reply = replyRepository.findById(replyId).orElseThrow(NotFoundException::new);
        commentService.editCommentReply(request.getValue(), reply, user);
    }

    /**
     * Allow a user to delete a comment, if allowed to
     * @param commentId the id of the comment
     * @param token the token of the user
     */
    @DeleteMapping("/comments/{commentId}")
    @Transactional
    public void deleteComment(@PathVariable("commentId") Long commentId,
                            @RequestHeader("Authorization") String token) {
        User user = authService.getUserForToken(token);
        Comment comment = commentRepository.findById(commentId).orElseThrow(NotFoundException::new);
        commentService.deleteComment(comment, user);
    }

    /**
     * Allow a user to delete a comment reply, if allowed to
     * @param replyId the id of the comment reply
     * @param token the token of the user
     */
    @DeleteMapping("/replies/{replyId}")
    @Transactional
    public void deleteCommentReply(@PathVariable("replyId") Long replyId,
                            @RequestHeader("Authorization") String token) {
        User user = authService.getUserForToken(token);
        CommentReply reply = replyRepository.findById(replyId).orElseThrow(NotFoundException::new);
        commentService.deleteCommentReply(reply, user);
    }
}
