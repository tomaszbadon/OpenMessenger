import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react'
import { CurrentUser } from '../datamodel/CurrentUser';
import { baseQueryWithReauth } from './base';

export const currentUserApi = createApi({
    reducerPath: "currentUserApi",
    baseQuery: baseQueryWithReauth,
    endpoints: (builder) => ({
      getCurrentUser: builder.query<CurrentUser, void>({
        query: () => {
          return {
            url: "/users/current",
            method: "GET",
          };
        },
      }),
    }),
  });

  export const { useGetCurrentUserQuery } = currentUserApi;
