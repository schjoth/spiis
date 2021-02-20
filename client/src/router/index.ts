import {createRouter, createWebHistory, RouteLocationNormalized, RouteRecordRaw} from "vue-router";
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
    meta: {requiresAuth: true},
    component: () => import("@/views/CreateNewDinner.vue")
  },
  {
    path: "/MyProfile",
    name: "MyProfile",
    meta: {requiresAuth: true},
    component: () => import("@/views/MyProfile.vue")
  },
  {
    path: "/DinnerEvent/:dinnerId",
    name: "DinnerEvent",
    meta: {requiresAuth: true},
    component: () => import("@/views/DinnerEvent.vue")
  },
  {
    path: "/Login",
    name: "Login",
    meta: {requiresAnon: true},
    component: () => import("@/views/Login.vue")
  },
  {
    path: "/signup",
    name: "Signup",
    meta: {requiresAnon: true},
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

const loggedIn = computed(() => getLogInState().status === "loggedIn");

const ensureCorrectPage = (page: RouteLocationNormalized, changePage: (path: string)=>void, stay: ()=>void) => {
  if (page.meta.requiresAuth && !loggedIn.value)
    changePage("/");
  else if(page.meta.requiresAnon && loggedIn.value)
    changePage("/");
  else
    stay();
}

router.beforeEach((to, from, next) => ensureCorrectPage(to, next, next));
watch(loggedIn, () => ensureCorrectPage(router.currentRoute.value, router.replace, ()=>{/* Nothing */}));

export default router;
