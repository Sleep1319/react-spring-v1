import React from "react";
import { Routes, Route } from "react-router-dom";
import ProtectedRoute from "./ProtectedRoute";
import Home from "../view/Home";
import SignIn from "../view/SignIn";
import SignUp from "../view/SignUp";
function AppRoutes() {
    return (
    <Routes>
        <Route path='/'element={<Home/>} />
        <Route element={<ProtectedRoute requireAuth={false} /> }>
            <Route path="/sign-in" element={<SignIn/>} />
            <Route path="/sign-up" element={<SignUp />} />
        </Route>
        <Route element={<ProtectedRoute requireAuth={true} /> }>

        </Route>
    </Routes>
    );
}

export default AppRoutes