import client from "./client";
import { DinnerRequest, DinnerResponse } from "@/api/types";

export async function createDinner(
  request: DinnerRequest
): Promise<DinnerResponse> {
  return client.post("/dinners", request);
}

export async function updateDinner(id: number, request: DinnerRequest) {
  await client.put(`/dinners/${id}`, request);
}

export async function getDinner(id: number): Promise<DinnerResponse> {
  return client.get(`/dinners/${id}`);
}

export async function getAllDinners(): Promise<DinnerResponse[]> {
  return client.get(`/dinners`);
}

export async function addGuest(dinnerId: number, userId: number) {
  await client.put(`/dinners/${dinnerId}/guests/${userId}`);
}

export async function removeGuest(dinnerId: number, userId: number) {
  await client.delete(`/dinners/${dinnerId}/guests/${userId}`);
}

export async function blockGuest(dinnerId: number, userId: number) {
  await client.put(`/dinners/${dinnerId}/guests/blocked/${userId}`);
}

export async function getHostDinners(
  userId: number
): Promise<DinnerResponse[]> {
  return client.get(`/users/${userId}/hosting`);
}

export async function getGuestDinners(
  userId: number
): Promise<DinnerResponse[]> {
  return client.get(`/users/${userId}/guesting`);
}

export async function setDinnerCancelled(dinnerId: number, cancelled: boolean) {
  await client.put(`/dinners/${dinnerId}/cancelled`, { value: cancelled });
}

export async function lockDinnerByAdmin(
  dinnerId: number,
  lockedByAdmin: boolean
) {
  await client.put(`/dinners/${dinnerId}/lockedByAdmin`, {
    value: lockedByAdmin
  });
}
