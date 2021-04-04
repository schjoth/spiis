import client from "@/api/client";

export async function hasUserRatedDinner(
  dinnerId: number,
  guestId: number
): Promise<boolean> {
  return client.get(`/dinners/${dinnerId}/rater/${guestId}`);
}

export async function setHostRating(dinnerId: number, rating: number) {
  return client.put(`/dinners/${dinnerId}/hostRating`, { value: rating });
}

export async function getHostRating(hostId: number): Promise<number> {
  return client.get(`/users/${hostId}/hostRating`);
}
