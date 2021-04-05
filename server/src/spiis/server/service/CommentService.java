package spiis.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import spiis.server.api.CommentReplyResponse;
import spiis.server.api.CommentRequest;
import spiis.server.api.CommentResponse;
import spiis.server.error.ForbiddenException;
import spiis.server.model.Comment;
import spiis.server.model.CommentReply;
import spiis.server.model.Dinner;
import spiis.server.model.User;
import spiis.server.repository.CommentReplyRepository;
import spiis.server.repository.CommentRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentReplyRepository replyRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, CommentReplyRepository replyRepository) {
        this.commentRepository = commentRepository;
        this.replyRepository = replyRepository;
    }

    /**
     * Makes a serializable object with info about the comment.
     * Includes id and name of poster, and all replies
     * @param comment the comment model object
     * @return the serializable object
     */
    @Transactional(propagation = Propagation.MANDATORY)
    public CommentResponse makeCommentResponse(Comment comment) {
        var builder = CommentResponse.builder();
        builder.commentId(comment.getId())
                .content(comment.isDeleted() ? null : comment.getContent())
                .dinnerId(comment.getDinner().getId())
                .visibility(comment.getVisibility())
                .postedAt(comment.getCreatedTime())
                .editedAt(comment.getLastModifiedTime());
        User commenter = comment.getCommenter();
        if (commenter != null && !comment.isDeleted())
            builder.userId(commenter.getId())
                    .userFirstName(commenter.getFirstName())
                    .userLastName(commenter.getLastName());

        List<CommentReplyResponse> replies = new ArrayList<>();
        for (CommentReply reply : comment.getReplies())
            replies.add(makeCommentReplyResponse(reply));

        replies.sort(Comparator.comparing(CommentReplyResponse::getPostedAt));
        builder.replies(replies);

        return builder.build();
    }

    /**
     * Makes a serializable object with info about the comment reply.
     * @param reply the reply
     * @return the serializable object
     */
    @Transactional(propagation = Propagation.MANDATORY)
    public CommentReplyResponse makeCommentReplyResponse(CommentReply reply) {
        var builder = CommentReplyResponse.builder();
        builder.replyId(reply.getId())
                .content(reply.getContent())
                .postedAt(reply.getCreatedTime())
                .editedAt(reply.getLastModifiedTime());
        User commenter = reply.getCommenter();
        if (commenter != null)
            builder.userId(commenter.getId())
                    .userFirstName(commenter.getFirstName())
                    .userLastName(commenter.getLastName());
        return builder.build();
    }

    /**
     * Retrieve all comments on the given dinner that the user can see
     * @param dinner the dinner in question
     * @param user the user requesting the comments
     * @return a list of all visible comments, including their replies
     */
    @Transactional(propagation = Propagation.MANDATORY)
    public List<CommentResponse> getAllCommentsForDinnerForUser(Dinner dinner, User user) {
        final boolean isGuest = dinner.getGuests().contains(user);
        final boolean isHost = user.equals(dinner.getHost());
        final boolean isAdmin = user.isAdmin();

        List<CommentResponse> responses = new ArrayList<>();

        for (Comment comment : dinner.getComments()) {
            if (canUserSeeCommentInternal(user, comment, isGuest, isHost, isAdmin))
                responses.add(makeCommentResponse(comment));
        }

        responses.sort(Comparator.comparing(CommentResponse::getPostedAt));

        return responses;
    }

    /**
     * Creates a new comment on the given dinner, if allowed to
     * @param request the requested comment
     * @param dinner the dinner
     * @param user the user making the comment
     * @return the newly created Comment
     */
    @Transactional(propagation = Propagation.MANDATORY)
    public Comment addCommentToDinner(CommentRequest request, Dinner dinner, User user) {
        if (!canUserComment(user, dinner, request.getVisibility()))
            throw new ForbiddenException("You cant make this type of comment on this dinner");

        Comment comment = new Comment();
        comment.setDinner(dinner);
        comment.setCommenter(user);
        editCommentInternal(comment, request);
        commentRepository.save(comment);
        return comment;
    }

    /**
     * Creates a new reply on the given comment, if the user can see the comment and is allowed to comment
     * @param content the requested reply
     * @param parent the parent comment
     * @param user the user making the reply
     */
    @Transactional(propagation = Propagation.MANDATORY)
    public CommentReply addReplyToComment(String content, Comment parent, User user) {
        Dinner dinner = parent.getDinner();
        Objects.requireNonNull(dinner);

        if (!canUserSeeComment(user, parent))
            throw new ForbiddenException("You can't reply to comments you can't see");
        if (!canUserComment(user, dinner, Comment.Visibility.PUBLIC))
            throw new ForbiddenException("You do not have permission to reply to this comment");

        CommentReply reply = new CommentReply();
        reply.setParentComment(parent);
        reply.setCommenter(user);
        reply.setContent(content);
        replyRepository.save(reply);
        return reply;
    }

    /**
     * Edits the given comment, if the user is allowed to
     * @param request the new comment content
     * @param comment the comment
     * @param user the user
     */
    @Transactional(propagation = Propagation.MANDATORY)
    public void editComment(CommentRequest request, Comment comment, User user) {
        if (user.isAdmin() || user.equals(comment.getCommenter()))
            editCommentInternal(comment, request);
        else
            throw new ForbiddenException("Only the commenter or an admin can edit a comment");
    }

    /**
     * Edits the given comment reply, if the user is allowed to
     * @param request the new comment content
     * @param reply the comment reply
     * @param user the user
     */
    @Transactional(propagation = Propagation.MANDATORY)
    public void editCommentReply(String request, CommentReply reply, User user) {
        if (user.isAdmin() || user.equals(reply.getCommenter()))
            reply.setContent(request);
        else
            throw new ForbiddenException("Only the comment replier or an admin can edit a comment reply");
    }

    /**
     * Deletes the given comment, if the user is allowed to
     * @param comment the comment
     * @param user the user
     */
    @Transactional(propagation = Propagation.MANDATORY)
    public void deleteComment(Comment comment, User user) {
        if (user.isAdmin() || user.equals(comment.getCommenter()))
            comment.setDeleted(true);
        else
            throw new ForbiddenException("Only the commenter or an admin can delete a comment");
    }

    /**
     * Deletes the given comment reply, if the user is allowed to
     * @param reply the comment reply
     * @param user the user
     */
    @Transactional(propagation = Propagation.MANDATORY)
    public void deleteCommentReply(CommentReply reply, User user) {
        if (user.isAdmin() || user.equals(reply.getCommenter()))
            replyRepository.delete(reply);
        else
            throw new ForbiddenException("Only the replier or an admin can delete a comment reply");
    }

    /**
     * Is the given user allowed to comment on the given post with the desired visibility
     * @param user the user
     * @param dinner the dinner
     * @param visibility the desired visibility
     * @return true if the comment can be made
     */
    private boolean canUserComment(User user, Dinner dinner, Comment.Visibility visibility) {
        final boolean isGuest = dinner.getGuests().contains(user);
        final boolean isHost = user.equals(dinner.getHost());

        if (user.isAdmin())
            return true;

        if (dinner.getBlockedGuests().contains(user))
            return false;

        if (visibility == Comment.Visibility.GUESTS_ONLY)
            return isGuest || isHost;

        return true;
    }

    /**
     * Checks if a user should be able to see a comment and its replies
     * @param user the user
     * @param comment the comment
     * @return true if the comment is visible to the user
     */
    private boolean canUserSeeComment(User user, Comment comment) {
        final Dinner dinner = comment.getDinner();
        Objects.requireNonNull(dinner);
        final boolean isGuest = dinner.getGuests().contains(user);
        final boolean isHost = user.equals(dinner.getHost());

        return canUserSeeCommentInternal(user, comment, isGuest, isHost, user.isAdmin());
    }

    /**
     * Faster version, avoids checking user's relation to dinner
     */
    private boolean canUserSeeCommentInternal(User user, Comment comment,
                                              boolean isGuest, boolean isHost, boolean isAdmin) {
        if (isAdmin) //Admin sees all!
            return true;

        // We can always see comments left by ourselves
        if (user.equals(comment.getCommenter()))
            return true;

        Comment.Visibility visibility = comment.getVisibility();

        if (visibility == Comment.Visibility.HOST_ONLY)
            return isHost;
        else if (visibility == Comment.Visibility.GUESTS_ONLY)
            return isHost || isGuest;

        return true;
    }

    private void editCommentInternal(Comment comment, CommentRequest request) {
        comment.setContent(request.getContent());
        comment.setVisibility(request.getVisibility());
    }
}
