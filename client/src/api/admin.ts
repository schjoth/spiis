import client from "./client";

export async function setAdministrator(userId: number, admin: boolean) {
  await client.put(`/users/${userId}/admin`, { value: admin });
}

export async function makeMeAdmin(secret: string) {
  await client.post("/makeMeAdmin", { value: secret });
}
