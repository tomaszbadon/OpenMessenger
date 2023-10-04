import { Navigate, Route, Routes, useNavigate } from 'react-router-dom';
import { navigationItems } from "./Navigation";
import Layout from "../pages/_layout";
import { getUserContext } from '../util/userContextManagement';
import { useEffect } from 'react';

export const RenderRoutes = () => {

     let user = getUserContext().user

     const navigate = useNavigate()

     useEffect(() => {
          if(user?.isAuthenticated && window.location.href.endsWith('/login')) {
              navigate("/chat")
          }
      })
     
     return (
          <Routes>
               <Route path="/" element={<Layout />}>
                    {navigationItems.map((r, i) => {
                         if (r.isPrivate && user?.isAuthenticated) {
                              return <Route key={i} path={r.path} element={r.element} />
                         } else if (!r.isPrivate) {
                              return <Route key={i} path={r.path} element={r.element} />
                         } else return false
                    })}
                    <Route path='*' element={<Navigate to='/login' />} />
                    <Route path='' element={<Navigate to='/login' />} />
               </Route>
          </Routes>
     )
}
