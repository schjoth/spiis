export function isValidInteger(input: number | string) {
  return !isNaN(+input) && Number.isInteger(+input);
}

export function todayAsIsoDate() {
  return new Date().toISOString().split("T")[0];
}
