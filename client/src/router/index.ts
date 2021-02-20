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
    path: "/CreateNewDinner",
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
    path: "/DinnerEvent/:dinnerId",
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
    path: "/User/:userid",
    name: "User",
    meta: { requiresAuth: true },
    component: () => import("@/views/User.vue")
  },
  {
    path: "/MyDinners",
    name: "MyDinners",
    meta: { requiresAuth: true },
    component: () => import("@/views/MyDinners.vue")
  }
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
});

const loggedIn = computed(() => getLogInState().status === "loggedIn");

router.beforeEach((to, from, next) => {
  if (to.meta.requiresAuth && !loggedIn.value) next("/login");
  else if (to.meta.requiresAnon && loggedIn.value) next("/");
  next();
});

watch(loggedIn, async () => {
  if (router.currentRoute.value.meta.requiresAuth && !loggedIn.value)
    await router.replace("/login?invalidated=true");
  else if (router.currentRoute.value.meta.requiresAnon && loggedIn.value)
    await router.replace("/");
});

export default router;
