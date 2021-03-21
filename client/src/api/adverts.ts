import client from "./client";
import { AdvertRequest, AdvertResponse } from "@/api/types";

export async function createAdvert(
  request: AdvertRequest
): Promise<AdvertResponse> {
  return client.post("/adverts", request);
}

export async function updateAdvert(id: number, request: AdvertRequest) {
  await client.put(`/adverts/${id}`, request);
}

export async function getAdvert(id: number): Promise<AdvertResponse> {
  return client.get(`/adverts/${id}`);
}

export async function getAllAdverts(): Promise<AdvertResponse[]> {
  return client.get(`/adverts`);
}

export async function deleteAdvert(id: number) {
  await client.delete(`/adverts/${id}`);
}
