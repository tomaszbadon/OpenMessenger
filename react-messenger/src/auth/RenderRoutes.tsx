import { Navigate, Route, Routes } from 'react-router-dom';
import { navigationItems } from "./Navigation";
import Layout from "../pages/_layout";
import { getUserContext } from '../util/userContextManagement';

export const RenderRoutes = () => {

     let user = getUserContext().user
     
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
               </Route>
          </Routes>
     )
}
