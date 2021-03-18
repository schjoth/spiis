import { createRouter, createWebHistory, RouteRecordRaw } from "vue-router";
import { getLogInState } from "@/store/loginState";
import { watch, computed } from "vue";
import Home from "@/views/Home.vue";
import AdminInfo from "@/components/AdminInfo.vue";

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
    path: "/loggedOut",
    name: "Logged Out",
    meta: { requiresAnon: true },
    component: () => import("@/views/LoggedOut.vue")
  },
  {
    path: "/user/:userId",
    name: "User",
    meta: { requiresAuth: true },
    component: () => import("@/views/User.vue")
  },
  {
    path: "/admin",
    name: "Admin",
    meta: { requiresAuth: true },
    component: () => import("@/views/AdminPage.vue"),
    children: [
      {
        path: "info",
        name: "AdminInfo",
        meta: { requiresAdmin: true },
        component: () => import("@/components/AdminInfo.vue")
      },
      {
        path: "",
        redirect: AdminInfo
      },
      {
        path: "ads",
        meta: { requiresAdmin: true },
        component: () => import("@/components/AdvertOverview.vue")
      }
    ]
  },
  {
    path: "/MyDinners",
    name: "MyDinners",
    meta: { requiresAuth: true },
    component: () => import("@/views/MyDinners.vue")
  },
  {
    path: "/event/:dinnerId/edit",
    name: "EditEvent",
    meta: { requiresAuth: true },
    component: () => import("@/views/EditEvent.vue")
  },
  {
    path: "/admin/ads/new",
    name: "NewAdvert",
    meta: { requiresAdmin: true, requiresAuth: true },
    component: () => import("@/views/NewAdvert.vue")
  },
  {
    path: "/:pathMatch(.*)*",
    name: "NotFound",
    component: () => import("@/views/NotFound.vue")
  }
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
});

const status = computed(() => getLogInState().status);
const isAdmin = computed(() => getLogInState().user?.admin);

router.beforeEach((to, from, next) => {
  if (to.meta.requiresAuth && status.value === "loggedOut") next("/login");
  else if (to.meta.requiresAnon && status.value === "loggedIn") next("/");
  else next();
});

router.afterEach(() => {
  if (router.currentRoute.value.meta.requiresAdmin && !isAdmin.value) {
    router.replace("/admin");
  }
});

watch(status, async (status) => {
  if (router.currentRoute.value.meta.requiresAuth && status === "loggedOut")
    await router.replace("/login?invalidated=true");
  else if (router.currentRoute.value.meta.requiresAnon && status === "loggedIn")
    await router.replace("/");
});

export default router;
