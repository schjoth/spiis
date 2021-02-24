import { createRouter, createWebHistory, RouteRecordRaw } from "vue-router";
import { getLogInState } from "@/store/loginState";
import { watch, computed } from "vue";
import Home from "@/views/Home.vue";

const routes: Array<RouteRecordRaw> = [
  {
    path: "/",
    name: "Home",
    component: Home
  },
  {
    path: "/event/new",
    name: "CreateNewDinner",
    meta: { requiresAuth: true },
    component: () => import("@/views/CreateNewDinner.vue")
  },
  {
    path: "/MyProfile",
    name: "MyProfile",
    meta: { requiresAuth: true },
    component: () => import("@/views/MyProfile.vue")
  },
  {
    path: "/event/:dinnerId",
    name: "DinnerEvent",
    meta: { requiresAuth: true },
    component: () => import("@/views/DinnerEvent.vue")
  },
  {
    path: "/Login",
    name: "Login",
    meta: { requiresAnon: true },
    component: () => import("@/views/Login.vue")
  },
  {
    path: "/signup",
    name: "Signup",
    meta: { requiresAnon: true },
    component: () => import("@/views/Signup.vue")
  },
  {
    path: "/user/:userid",
    name: "User",
    meta: { requiresAuth: true },
    component: () => import("@/views/User.vue")
  },
  {
    path: "/MyDinners",
    name: "MyDinners",
    meta: { requiresAuth: true },
    component: () => import("@/views/MyDinners.vue")
  },
  {
    path: "/event/:eventId/edit",
    name: "EditEvent",
    meta: { requiresAuth: true },
    component: () => import("@/views/EditEvent.vue")
  }
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
});

const status = computed(() => getLogInState().status);

router.beforeEach((to, from, next) => {
  if (to.meta.requiresAuth && status.value === "loggedOut") next("/login");
  else if (to.meta.requiresAnon && status.value === "loggingIn") next("/");
  else next();
});

watch(status, async (status) => {
  if (router.currentRoute.value.meta.requiresAuth && status === "loggedOut")
    await router.replace("/login?invalidated=true");
  else if (router.currentRoute.value.meta.requiresAnon && status === "loggedIn")
    await router.replace("/");
});

export default router;
