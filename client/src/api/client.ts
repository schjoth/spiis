import axios, { AxiosInstance, AxiosRequestConfig, Method } from "axios";
import { logOut } from "./login";
import { getLogInState } from "@/store/loginState";
import { watchEffect, Ref, ref } from "vue";

export const DEFAULT_BASEURL =
  process.env.NODE_ENV == "production"
    ? "https://spiis.haved.no/api/"
    : process.env.NODE_ENV == "test"
    ? location.href
    : "http://localhost:8180";

/**
 * The type thrown if a request failed.
 * Failing can either mean not getting a response from the server,
 * og getting a response with a status code outside of [200,300>.
 */
export interface RESTClientError {
  status?: number;
  message: string;
}

class RESTClient {
  httpInstance: AxiosInstance;
  setToken: Ref<string | null>;

  constructor() {
    this.httpInstance = axios.create({
      baseURL: DEFAULT_BASEURL,
      timeout: 3600,
      responseType: "json",
      validateStatus: function (status) {
        return status >= 200 && status < 300;
      }
    });
    this.setToken = ref(null);

    watchEffect(() => {
      const token = getLogInState().token;
      if (token)
        this.httpInstance.defaults.headers.common["Authorization"] = token;
      else delete this.httpInstance.defaults.headers.common["Authorization"];
      this.setToken.value = token;
    });
  }

  request<T>(
    url: string,
    method: Method,
    data?: object,
    config?: AxiosRequestConfig
  ): Promise<T> {
    return new Promise<T>((resolve, reject) => {
      this.httpInstance(url, { method, data, ...config })
        .then((response) => {
          resolve(response.data);
        })
        .catch(async (error) => {
          if (error.response) {
            if (error.response.status == 401) {
              //Unauthorized, we need to get a (new) token!
              if (getLogInState().status === "loggedIn") await logOut();
            }
            reject({
              status: error.response.status,
              message:
                error.response?.data?.message ??
                `Status code ${error.response.status}`
            } as RESTClientError);
          } else {
            reject({
              message: error.message
            } as RESTClientError);
          }
        });
    });
  }

  get<T>(url: string, config?: AxiosRequestConfig): Promise<T> {
    return this.request(url, "GET", undefined, config);
  }

  post<T>(url: string, data?: object, config?: AxiosRequestConfig): Promise<T> {
    return this.request(url, "POST", data, config);
  }

  put<T>(url: string, data?: object, config?: AxiosRequestConfig): Promise<T> {
    return this.request(url, "PUT", data, config);
  }

  delete<T>(url: string, config?: AxiosRequestConfig): Promise<T> {
    return this.request(url, "DELETE", undefined, config);
  }

  authorized(callback: () => void) {
    watchEffect(() => {
      if (this.setToken.value !== null) callback();
    });
  }
}

const client = new RESTClient();
export default client;
export const authorized = client.authorized.bind(client);
