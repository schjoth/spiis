import client from "./client";
import {
  CommentReplyRequest,
  CommentReplyResponse,
  CommentRequest,
  CommentResponse
} from "@/api/types";

export async function getCommentsOnDinner(
  dinnerId: number
): Promise<CommentResponse[]> {
  return client.get(`/dinners/${dinnerId}/comments`);
}

export async function postComment(
  dinnerId: number,
  request: CommentRequest
): Promise<CommentResponse> {
  return client.post(`/dinners/${dinnerId}/comments`, request);
}

export async function postCommentReply(
  commentId: number,
  request: CommentReplyRequest
): Promise<CommentReplyResponse> {
  return client.post(`/comments/${commentId}/replies`, request);
}

export async function editComment(commentId: number, request: CommentRequest) {
  await client.put(`/comments/${commentId}`, request);
}

export async function editCommentReply(
  replyId: number,
  request: CommentReplyRequest
) {
  await client.put(`/replies/${replyId}`, request);
}

export async function deleteComment(commentId: number) {
  await client.delete(`/comments/${commentId}`);
}

export async function deleteCommentReply(replyId: number) {
  await client.delete(`/replies/${replyId}`);
}
