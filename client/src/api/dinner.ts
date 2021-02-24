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

export async function addGuest(
  dinnerId: string | RouteParamValue[],
  userId: string | RouteParamValue[]
) {
  await client.put(`/dinners/${dinnerId}/guests/${userId}`);
}

export async function removeGuest(
  dinnerId: string | RouteParamValue[],
  userId: string | RouteParamValue[]
) {
  await client.delete(`/dinners/${dinnerId}/guests/${userId}`);
}

export async function getHostDinners(
  userId: string | RouteParamValue[]
): Promise<DinnerResponse[]> {
  return client.get(`/users/${userId}/hosting`);
}

export async function getGuestDinners(
  userId: string | RouteParamValue[]
): Promise<DinnerResponse[]> {
  return client.get(`/users/${userId}/guesting`);
}
