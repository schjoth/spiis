import client from "@/api/client";
import { UserResponse } from "@/api/types";
import { RouteParamValue } from "vue-router";

export async function getUser(
  id: string | RouteParamValue[]
): Promise<UserResponse> {
  return client.get(`/users/${id}`);
}
