import client from "./client";
import { DinnerRequest, DinnerResponse } from "@/api/types";
import { RouteParamValue } from "vue-router";

export async function createDinner(
  request: DinnerRequest
): Promise<DinnerResponse> {
  return client.post("/dinner", request);
}

export async function deleteDinner(id: number) {
  await client.delete(`/dinner/${id}`);
}

export async function updateDinner(
  id: string | RouteParamValue[],
  request: DinnerRequest
): Promise<DinnerResponse> {
  return client.put(`/dinner/${id}`, request);
}

export async function getDinner(
  id: string | RouteParamValue[]
): Promise<DinnerResponse> {
  return client.get(`/dinner/${id}`);
}

export async function getAllDinners(): Promise<DinnerResponse> {
  return client.get(`/dinner`);
}
