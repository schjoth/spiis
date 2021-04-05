<template>
  <div class="comment-section">
    <div class="comments" v-if="comments !== null">
      <Comment
        v-for="comment in comments"
        :key="comment.commentId"
        :comment="comment"
        @editMade="loadComments"
      />
    </div>
    <div class="new-comment">
      <div class="field">
        <label class="label">Ny Kommentar</label>
        <div class="control">
          <textarea
            v-model="newComment"
            class="textarea"
            placeholder="Innhold"
          />
        </div>
      </div>
      <div class="field is-grouped">
        <div class="control">
          <div class="select">
            <select v-model="newCommentVisibility">
              <option value="public">For alle</option>
              <option value="guests_only" v-if="isGuest || isHost || isAdmin">
                For gjester
              </option>
              <option value="host_only">Kun for vert</option>
            </select>
          </div>
        </div>
        <div class="control">
          <button
            @click="postNewComment"
            :disabled="newComment.length === 0"
            class="button is-link"
          >
            Post
          </button>
        </div>
      </div>
    </div>
    <div class="error" v-if="errorText !== null">{{ errorText }}</div>
  </div>
</template>

<script lang="ts">
import {
  CommentRequest,
  CommentResponse,
  CommentVisibility
} from "@/api/types";
import { defineComponent, onMounted, ref } from "vue";
import { authorized } from "@/api/client";
import { getCommentsOnDinner, postComment } from "@/api/comment";
import Comment from "@/components/comments/Comment.vue";

export default defineComponent({
  name: "CommentSection",
  components: { Comment },
  props: ["dinnerId", "isGuest", "isHost", "isAdmin"],
  setup(props) {
    const errorText = ref<string | null>(null);
    const comments = ref<CommentResponse[] | null>(null);

    const loadComments = async () => {
      try {
        errorText.value = null;
        comments.value = await getCommentsOnDinner(props.dinnerId);
      } catch (e) {
        errorText.value = e.message;
      }
    };

    onMounted(() => authorized(loadComments));

    const newComment = ref("");
    const newCommentVisibility = ref("public");
    const postNewComment = async () => {
      const request: CommentRequest = {
        content: newComment.value,
        visibility: newCommentVisibility.value as CommentVisibility
      };
      try {
        errorText.value = null;
        await postComment(props.dinnerId, request);
        newComment.value = ""; //Clear the input text
        await loadComments();
      } catch (e) {
        errorText.value = e.message;
      }
    };

    return {
      errorText,
      comments,
      loadComments,
      newComment,
      newCommentVisibility,
      postNewComment
    };
  }
});
</script>

<style lang="scss" scoped></style>
