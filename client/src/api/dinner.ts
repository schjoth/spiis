import client from "./client";
import { DinnerRequest, DinnerResponse } from "@/api/types";
import { RouteParamValue } from "vue-router";

export async function createDinner(
  request: DinnerRequest
): Promise<DinnerResponse> {
  return client.post("/dinners", request);
}

export async function updateDinner(
  id: string | RouteParamValue[],
  request: DinnerRequest
) {
  await client.put(`/dinners/${id}`, request);
}

export async function getDinner(
  id: string | RouteParamValue[]
): Promise<DinnerResponse> {
  return client.get(`/dinners/${id}`);
}

export async function getAllDinners(): Promise<DinnerResponse[]> {
  return client.get(`/dinners`);
}
