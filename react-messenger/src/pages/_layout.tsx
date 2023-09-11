import { Outlet } from "react-router-dom";
import React from 'react';
import '../index.sass'

const Layout = () => {
  return (
    <>
      <Outlet />
    </>
  )
};

export default Layout;