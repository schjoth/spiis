import client from "@/api/client";
import { UserResponse } from "@/api/types";
import { RouteParamValue } from "vue-router";

export async function getUser(
  id: string | RouteParamValue[]
): Promise<UserResponse> {
  return client.get(`/users/${id}`);
}

export async function getAllUsers(): Promise<UserResponse[]> {
  return client.get("/users");
}

export async function blockUser(id: number, blocked: boolean) {
  return client.put(`/users/${id}/blocked`, { value: blocked });
}
