import { fetchBaseQuery } from "@reduxjs/toolkit/query";
import { isExpired } from "react-jwt";
import { getAccessToken, getRefreshToken, removeUserContext, updateAccessToken } from "../util/userContextManagement";
import { BaseQueryFn, FetchArgs, FetchBaseQueryError } from "@reduxjs/toolkit/query";
import { Tokens } from "../auth/types";
import { unSetCurrentUser } from "../slice/CurrentUserSlice";

export const baseQuery = fetchBaseQuery({
    baseUrl: '/api',
    prepareHeaders: (headers) => {
      const token = getAccessToken()?.token
      if (token) {
        headers.set("authorization", `Bearer ${token}`);
      }
      return headers;
    },
  })
  
  export const refreshTokenQuery = fetchBaseQuery({
    baseUrl: '/api/auth',
    prepareHeaders: (headers) => {
      const token = getRefreshToken()?.token
      if (token) {
        headers.set("authorization", `Bearer ${token}`);
      }
      return headers;
    }
  })

  export const baseQueryWithReauth: BaseQueryFn<string | FetchArgs, unknown, FetchBaseQueryError> = async (args, api, extraOptions) => {
    let accessToken = getAccessToken()?.token;
    if (typeof accessToken === 'undefined' || isExpired(accessToken)) {
      const refreshTokenResult = await refreshTokenQuery("/accessToken", api, extraOptions);
      if (!refreshTokenResult.error && refreshTokenResult.data) {
        let tokens = refreshTokenResult.data as Tokens;
        let refreshedAccessToken = tokens.tokens[0];
        updateAccessToken(refreshedAccessToken);
      } else {
        removeUserContext();
        api.dispatch(unSetCurrentUser());
      }
    }
    let result = await baseQuery(args, api, extraOptions);
    return result;
  }
  
