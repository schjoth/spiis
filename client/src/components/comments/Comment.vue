<template>
  <div class="comment">
    <div v-if="comment.content !== null">
      <div class="comment-bubble has-background-warning-light">
        <router-link :to="`/user/${comment.userId}`">
          <h3>{{ comment.userFirstName }} {{ comment.userLastName }}</h3>
        </router-link>
        <p>
          <span class="time"> Postet: {{ formatDate(comment.postedAt) }} </span>
          <span class="time" v-if="comment.editedAt !== comment.postedAt">
            Redigert: {{ formatDate(comment.editedAt) }}
          </span>
          <span class="time">
            Synlig
            {{
              comment.visibility === "public"
                ? "for alle"
                : comment.visibility === "guests_only"
                ? "kun for gjester"
                : "kun for vert"
            }}</span
          >
        </p>
        <div class="comment-body" v-if="!editing">
          {{ comment.content }}
        </div>
        <div v-else>
          <div class="field">
            <label class="label">Rediger kommentar</label>
            <div class="control">
              <textarea
                v-model="editDraft"
                class="textarea"
                placeholder="Rediger kommentar"
                rows="4"
              />
            </div>
          </div>
          <div class="field">
            <div class="control">
              <button
                @click="putEditedComment"
                :disabled="editDraft.length === 0"
                class="button is-link"
              >
                Lagre
              </button>
            </div>
          </div>
        </div>
      </div>
      <p>
        <a class="action" @click="onShowReplyBox">Svar</a>
        <a
          class="action"
          @click="onEditComment"
          v-if="isAdmin || userId === comment.userId"
        >
          Rediger
        </a>
        <a
          class="action"
          @click="onDeleteComment"
          v-if="isAdmin || userId === comment.userId"
        >
          Slett
        </a>
      </p>
    </div>
    <div v-else>Slettet kommentar</div>
    <div class="replies">
      <div class="reply" v-for="reply in comment.replies" :key="reply.replyId">
        <div v-if="reply.content !== null">
          <div class="comment-bubble has-background-warning-light">
            <router-link :to="`/user/${reply.userId}`">
              <h3>{{ reply.userFirstName }} {{ reply.userLastName }}</h3>
            </router-link>
            <p>
              <span class="time">
                Postet: {{ formatDate(reply.postedAt) }}
              </span>
              <span class="time" v-if="reply.editedAt !== reply.postedAt">
                Redigert: {{ formatDate(reply.editedAt) }}
              </span>
            </p>
            <div class="comment-body" v-if="editingReply !== reply.replyId">
              {{ reply.content }}
            </div>
            <div v-else>
              <div class="field">
                <label class="label">Rediger svar</label>
                <div class="control">
                  <textarea
                    v-model="replyEditDraft"
                    class="textarea"
                    placeholder="Rediger svar"
                    rows="1"
                  />
                </div>
              </div>
              <div class="field">
                <div class="control">
                  <button
                    @click="putEditedReply"
                    :disabled="replyEditDraft.length === 0"
                    class="button is-link"
                  >
                    Lagre
                  </button>
                </div>
              </div>
            </div>
          </div>
          <p v-if="isAdmin || userId === reply.userId">
            <a class="action" @click="onEditReply(reply.replyId)">Rediger</a>
            <a class="action" @click="onDeleteReply(reply.replyId)">Slett</a>
          </p>
        </div>
        <div v-else>Slettet svar</div>
      </div>

      <div class="replyBox" v-if="showReply" ref="replyBox">
        <div class="field">
          <label class="label">Nytt Svar</label>
          <div class="control">
            <textarea
              v-model="newReply"
              class="textarea"
              placeholder="Svar"
              rows="1"
            />
          </div>
        </div>
        <div class="field">
          <div class="control">
            <button
              @click="postNewReply"
              :disabled="newReply.length === 0"
              class="button is-link"
            >
              Svar
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { computed, defineComponent, ref, nextTick } from "vue";
import { getLogInState } from "@/store/loginState";
import {
  deleteComment,
  deleteCommentReply,
  editComment,
  editCommentReply,
  postCommentReply
} from "@/api/comment";
import { CommentReplyResponse, CommentRequest } from "@/api/types";

export default defineComponent({
  name: "Comment",
  props: ["comment", "isAdmin"],
  setup(props, { emit }) {
    const formatDate = (date: string) => new Date(date).toLocaleString("no");
    const userId = computed(() => getLogInState().user?.id);

    const editing = ref(false);
    const editDraft = ref("");
    const onEditComment = () => {
      editing.value = true;
      editDraft.value = props.comment.content;
    };

    const putEditedComment = async () => {
      try {
        const request: CommentRequest = {
          content: editDraft.value,
          visibility: props.comment.visibility
        };
        await editComment(props.comment.commentId, request);
        editing.value = false;
        emit("editMade");
      } catch (e) {
        editDraft.value = `Error: ${e.message}`;
      }
    };

    const onDeleteComment = async () => {
      try {
        await deleteComment(props.comment.commentId);
        emit("editMade");
      } catch (e) {
        console.log(e);
      }
    };

    const editingReply = ref(-1);
    const replyEditDraft = ref("");

    const onEditReply = (replyId: number) => {
      editingReply.value = replyId;
      replyEditDraft.value = props.comment.replies.find(
        (it: CommentReplyResponse) => it.replyId == replyId
      ).content;
    };

    const putEditedReply = async () => {
      try {
        await editCommentReply(editingReply.value, {
          value: replyEditDraft.value
        });
        editingReply.value = -1;
        emit("editMade");
      } catch (e) {
        replyEditDraft.value = `Error: ${e.message}`;
      }
    };

    const onDeleteReply = async (replyId: number) => {
      try {
        await deleteCommentReply(replyId);
        emit("editMade");
      } catch (e) {
        console.log(e);
      }
    };

    const showReply = ref(false);
    const replyBox = ref<null | Element>(null);

    const onShowReplyBox = async () => {
      showReply.value = true;
      await nextTick();
      replyBox.value?.scrollIntoView({ behavior: "smooth", block: "center" });
    };

    const newReply = ref("");
    const postNewReply = async () => {
      try {
        await postCommentReply(props.comment.commentId, {
          value: newReply.value
        });
        showReply.value = false;
        emit("editMade");
      } catch (e) {
        newReply.value = `Error: ${e.message}`;
      }
    };

    return {
      formatDate,
      userId,
      editing,
      editDraft,
      onEditComment,
      putEditedComment,
      onDeleteComment,
      editingReply,
      replyEditDraft,
      onEditReply,
      putEditedReply,
      onDeleteReply,
      showReply,
      replyBox,
      onShowReplyBox,
      newReply,
      postNewReply
    };
  }
});
</script>

<style lang="scss" scoped>
.comment,
.reply {
  margin-top: 10px;
  margin-bottom: 20px;
}

.comment-bubble {
  border-radius: 10px;
  padding: 10px;
}

.time {
  color: gray;
  font-size: 0.9em;
  margin-right: 20px;
}

.comment-body {
  margin-top: 10px;
}

.action {
  margin-left: 10px;
}

.replies {
  padding-left: 40px;
}
</style>
