import { createRouter, createWebHistory, RouteRecordRaw } from "vue-router";
import Home from "@/views/Home.vue";

const routes: Array<RouteRecordRaw> = [
  {
    path: "/",
    name: "Home",
    component: Home
  },
  {
    path: "/CreateNewDinner",
    name: "CreateNewDinner",
    component: () => import("@/views/CreateNewDinner.vue")
  },
  {
    path: "/MyProfile",
    name: "MyProfile",
    component: () => import("@/views/MyProfile.vue")
  },
  {
    path: "/DinnerEvent/:dinnerId",
    name: "DinnerEvent",
    component: () => import("@/views/DinnerEvent.vue")
  },
  {
    path: "/Login",
    name: "Login",
    component: () => import("@/views/Login.vue")
  },
  {
    path: "/signup",
    name: "Signup",
    component: () => import("@/views/Signup.vue")
  },
  {
    path: "/User/:userid",
    name: "User",
    component: () => import("@/views/User.vue")
  }
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
});

export default router;
